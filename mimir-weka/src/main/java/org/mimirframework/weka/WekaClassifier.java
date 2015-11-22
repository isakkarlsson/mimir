package org.mimirframework.weka;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.briljantframework.array.DoubleArray;
import org.briljantframework.data.Is;
import org.briljantframework.data.dataframe.DataFrame;
import org.briljantframework.data.vector.Convert;
import org.briljantframework.data.vector.Vector;
import org.briljantframework.data.vector.Vectors;
import org.mimirframework.classification.AbstractClassifier;
import org.mimirframework.classification.ClassifierCharacteristic;
import org.mimirframework.supervised.Characteristic;
import org.mimirframework.supervised.Predictor;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Isak Karlsson <isak-kar@dsv.su.se>
 */
public class WekaClassifier<T extends weka.classifiers.Classifier> extends AbstractClassifier {

  private final T classifier;
  private final FastVector names;

  protected WekaClassifier(T classifier, FastVector names, Vector classes) {
    super(classes);
    this.classifier = classifier;
    this.names = names;
  }

  public T getClassifier() {
    return classifier;
  }

  @Override
  public Set<Characteristic> getCharacteristics() {
    return Collections.singleton(ClassifierCharacteristic.ESTIMATOR);
  }

  @Override
  public DoubleArray estimate(Vector record) {
    Instance instance = new Instance(record.size() + 1);
    Instances instances = new Instances("Crap", names, 1);
    instance.setDataset(instances);
    for (int i = 0; i < record.size(); i++) {
      addValue(instance, i, record.loc().get(i));
    }
    instance.setMissing(record.size());
    instances.setClassIndex(record.size());
    try {
      double[] value = getClassifier().distributionForInstance(instance);
      return DoubleArray.of(value);
    } catch (Exception e) {
      DoubleArray p = DoubleArray.zeros(getClasses().size());
      try {
        double value = getClassifier().classifyInstance(instance);
        for (int i = 0; i < getClasses().size(); i++) {
          if (i == value) {
            p.set(i, 1);
          }
        }
        return p;
      } catch (Exception e1) {
        throw new IllegalStateException("Can't make classification", e1);
      }
    }

  }

  public static class Learner<T extends weka.classifiers.Classifier> implements
      Predictor.Learner<WekaClassifier<T>> {

    private final T classifier;

    public Learner(T classifier) {
      this.classifier = classifier;
    }

    @Override
    public WekaClassifier<T> fit(DataFrame x, Vector y) {
      try {
        Vector classes = Vectors.unique(y);
        FastVector classVector = new FastVector();
        classes.toList().forEach(classVector::addElement);

        @SuppressWarnings("unchecked")
        T copy = (T) weka.classifiers.Classifier.makeCopy(classifier);
        FastVector names = new FastVector();
        for (Object column : x.getColumnIndex()) {
          // Guess numeric for now
          Attribute element = new Attribute(column.toString());
          names.addElement(element);
        }
        names.addElement(new Attribute("Class", classVector));
        Instances instances = new Instances("dataFrameCopy", names, x.rows());
        List<Vector> records = x.getRecords();
        for (int i = 0; i < records.size(); i++) {
          Vector record = records.get(i);
          Instance instance = new Instance(record.size() + 1);
          instance.setDataset(instances);

          for (int j = 0; j < record.size(); j++) {
            Object value = record.loc().get(j);
            addValue(instance, j, value);
          }
          instance.setValue(record.size(), y.loc().get(String.class, i));
          instances.add(instance);
        }
        instances.setClassIndex(x.columns());
        copy.buildClassifier(instances);
        return new WekaClassifier<>(copy, names, classes);
      } catch (Exception e) {
        throw new IllegalStateException("Unsupported classifier", e);
      }
    }
  }

  private static void addValue(Instance instance, int j, Object value) {
    if (Is.NA(value)) {
      instance.setMissing(j);
    } else if (Is.numeric(value)) {
      instance.setValue(j, Convert.to(Number.class, value).doubleValue());
    } else {
      instance.setValue(j, value.toString());
    }
  }

}
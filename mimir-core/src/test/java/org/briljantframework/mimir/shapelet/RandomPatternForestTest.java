/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Isak Karlsson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.briljantframework.mimir.shapelet;

import java.util.*;

import org.briljantframework.array.BooleanArray;
import org.briljantframework.array.DoubleArray;
import org.briljantframework.data.dataframe.DataFrame;
import org.briljantframework.data.series.Series;
import org.briljantframework.mimir.classification.Classifier;
import org.briljantframework.mimir.classification.conformal.ConformalClassifier;
import org.briljantframework.mimir.classification.tree.pattern.*;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class RandomPatternForestTest {


  @Test
  public void testLength() throws Exception {
//    DataFrame data = null;
//    Input<TimeSeries> in = new MutableInput<>();
//    Output<Integer> out = new MutableOutput<>();
//
//    for (Series series : data.getRows()) {
//      in.add(TimeSeries.copyOf(series.values().subList(1, series.size())));
//      out.add(series.values().getInt(0));
//    }
//
//
//    EarlyAbandonSlidingDistance d = new EarlyAbandonSlidingDistance();
//    PatternDistance<TimeSeries, Shapelet> distance = new PatternDistance<TimeSeries, Shapelet>() {
//      @Override
//      public double computeDistance(TimeSeries a, Shapelet b) {
//        return d.compute(a, b);
//      }
//    };
//
//    PatternFactory<TimeSeries, Shapelet> patternFactory =
//        new SamplingPatternFactory<TimeSeries, Shapelet>() {
//          @Override
//          protected Shapelet createPattern(TimeSeries input) {
//            int start = ThreadLocalRandom.current().nextInt(input.size() - 40);
//            // int end = ThreadLocalRandom.current().nextInt(start + 2, input.size() -1);
//            return new IndexSortedNormalizedShapelet(start, 40, input);
//          }
//        };
//
//    RandomPatternForest.Learner<TimeSeries, Object> f =
//        new RandomPatternForest.Learner<>(patternFactory, distance, 100);
//
//    f.set(PatternTree.PATTERN_COUNT, 100);
//    ClassifierValidator<TimeSeries, Object> validator = ClassifierValidator.crossValidator(10);
//
//    System.out.println(validator.test(f, in, out).getMeasures().reduce(Series::mean));



  }

  // @Test
  // public void testLOOCV() throws Exception {
  // String name = "BirdChicken";
  // String trainFile = String.format("/Users/isak-kar/Downloads/dataset3/%s/%s.arff", name, name);
  // try (DataInputStream train = new ArffInputStream(new FileInputStream(trainFile))) {
  // DataFrame trainingSet = MixedDataFrame.read(train);
  // Transformer znorm = new DataSeriesNormalization();
  // // DataFrame xTrain =
  // // znorm.transform(new DataSeriesCollection.Builder(DoubleVector.TYPE)
  // // .stack(0, trainingSet.drop(trainingSet.columns() - 1))
  // // .build());
  // Series yTrain = Convert.toStringVector(trainingSet.get(trainingSet.columns() - 1));
  //
  // RandomShapeletForest f =
  // RandomShapeletForest
  // .withSize(100)
  // .withInspectedShapelets(50)
  // .withLowerLength(0.025)
  // .withUpperLength(0.3)
  // .withAssessment(ShapeletTree.Assessment.FSTAT)
  // .build();
  // List<Evaluator> evaluatorList = Evaluator.getDefaultClassificationEvaluators();
  // evaluatorList.add(new Evaluator() {
  // private int fold = 0;
  //
  // @Override
  // public void accept(EvaluationContextImpl ctx) {
  // System.out.printf("Fold %d\n", fold++);
  // }
  // });
  // Result re = Validators.leaveOneOutValidation().test(f, xTrain, yTrain);
  // System.out.println(re);
  // }
  // throw new UnsupportedOperationException();

  // }

  // @Test
  // public void testClassifiy2() throws Exception {
  // String name = "DP_Middle";
  // String trainFile =
  // String.format("/Users/isak-kar/Downloads/dataset3/%s/%s_TRAIN.arff", name, name);
  // String testFile =
  // String.format("/Users/isak-kar/Downloads/dataset3/%s/%s_TEST.arff", name, name);
  // try (DataInputStream train = new ArffInputStream(new FileInputStream(trainFile));
  // DataInputStream test = new ArffInputStream(new FileInputStream(testFile))) {
  // train.readColumnIndex(); // TODO!
  // DataFrame trainingSet =
  // new MixedDataFrame.Builder(train.readColumnTypes()).read(train)
  // .build();
  //
  // test.readColumnIndex(); // TODO:
  // DataFrame validationSet =
  // new MixedDataFrame.Builder(test.readColumnTypes()).read(test)
  // .build();
  //
  // Transformer znorm = new DataSeriesNormalization();
  // DataFrame xTrain =
  // znorm.transform(new DataSeriesCollection.Builder(DoubleVector.TYPE).stack(
  // 0, trainingSet.drop(trainingSet.columns() - 1)).build());
  // Series yTrain = Convert.toStringVector(trainingSet.get(trainingSet.columns() - 1));
  //
  // DataFrame xTest =
  // znorm.transform(new DataSeriesCollection.Builder(DoubleVector.TYPE).stack(
  // 0, validationSet.drop(validationSet.columns() - 1)).build());
  // Series yTest = Convert.toStringVector(validationSet.get(validationSet.columns() - 1));
  //
  // long start = System.nanoTime();
  // DoubleArray upper = Arrays.array(new double[]{0.05, 0.1, 0.3, 0.5, 0.7, 1});
  // IntArray sizes = Arrays.array(new int[]{100});
  // // IntMatrix sizes = IntMatrix.of(500);
  // System.out.println("Size,Correlation,Strength,Quality,Expected Error,"
  // + "Accuracy,OOB Accuracy,Variance,Bias,Brier,Depth");
  // for (int i = 0; i < sizes.size(); i++) {
  // RandomShapeletForest forest =
  // RandomShapeletForest.withSize(1000).withInspectedShapelets(sizes.get(i))
  // .withLowerLength(0.025).withUpperLength(0.5)
  // // .withSampleMode(ShapeletTree.SampleMode.RANDOMIZE)
  // .withAssessment(ShapeletTree.Assessment.FSTAT).build();
  // Result result = HoldoutValidator.withHoldout(xTest, yTest).test(forest, xTrain, yTrain);
  // // System.out.println(result);
  // System.out.println(sizes.get(i) + ", "
  // + result.getAverage(Ensemble.Correlation.class) + ", "
  // + result.getAverage(Ensemble.Strength.class) + ", "
  // + result.getAverage(Ensemble.Quality.class) + ", "
  // + result.getAverage(Ensemble.ErrorBound.class) + ", "
  // + result.getAverage(Accuracy.class) + ", "
  // + result.getAverage(Ensemble.OobAccuracy.class) + ", "
  // + result.getAverage(Ensemble.Variance.class) + ", "
  // + result.getAverage(Ensemble.MeanSquareError.class) + ", "
  // + result.getAverage(Brier.class) + ", "
  // + result.getAverage(RandomShapeletForest.Depth.class));
  // }
  // System.out.println((System.nanoTime() - start) / 1e6);
  //
  // }
  // }


  private final Random random = new Random(123);

  static double maxnot(DoubleArray array, int not) {
    Double max = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < array.size(); i++) {
      if (i == not) {
        continue;
      }
      double m = array.get(i);
      if (m > max) {
        max = m;
      }
    }
    return max;
  }

  @Test
  public void testSynticControl() throws Exception {
    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new dDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/SwedishLeaf/SwedishLeaf_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/SwedishLeaf/SwedishLeaf_TEST")));

    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Gun_Point/Gun_Point_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Gun_Point/Gun_Point_TEST")));
    //
    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/SonyAIBORobotSurfaceII/SonyAIBORobotSurfaceII_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/SonyAIBORobotSurfaceII/SonyAIBORobotSurfaceII_TEST")));
    //
    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/ECGFiveDays/ECGFiveDays_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/ECGFiveDays/ECGFiveDays_TEST")));

    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Coffee/Coffee_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Coffee/Coffee_TEST")));

    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream("/Users/isak-kar/Downloads/dataset2/wafer/wafer_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream("/Users/isak-kar/Downloads/dataset2/wafer/wafer_TEST")));

    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/MoteStrain/MoteStrain_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset2/MoteStrain/MoteStrain_TEST")));
    //
    // DataFrame train = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Two_Patterns/Two_Patterns_TRAIN")));
    // DataFrame test = Datasets.load(
    // (i) -> new DataSeriesCollection.Builder(double.class), new MatlabDatasetReader(
    // new FileInputStream(
    // "/Users/isak-kar/Downloads/dataset/Two_Patterns/Two_Patterns_TEST")));
    //
    // String fileName = "synthetic_control";
    // String path = "/Users/isak-kar/Downloads/dataset";
    // DataFrame train =
    // DataFrames
    // .permute(
    // Datasets.load((i) -> new DataSeriesCollection.Builder(double.class),
    // new MatlabDatasetReader(new FileInputStream(
    // String.format("%s/%s/%s_TRAIN", path, fileName, fileName)))),
    // new Random(123));
    // DataFrame test = Datasets.load((i) -> new DataSeriesCollection.Builder(double.class),
    // new MatlabDatasetReader(
    // new FileInputStream(String.format("%s/%s/%s_TEST", path, fileName, fileName))));
    //
    // train.setColumnIndex(Index.range(train.columns()));
    // test.setColumnIndex(Index.range(test.columns()));
    // System.out.println(test.get(0).valueCounts());
    // Series y = train.get(0);
    // System.out.println(y.valueCounts());

    // System.out.println(train.head(5));
    // System.out.println(train.getRecord(0).asList(Double.class).subList(1, 85));
    // System.out.println(train.drop(0).getRecord(0).asList(Double.class));
    // System.out.println(train.drop(0).columns());
    // Classifier rsf =
    // RandomShapeletForest.withSize(100).withAssessment(ShapeletTree.Assessment.IG)
    // .withInspectedShapelets(100).withUpperLength(1).build();
    // Classifier knn = KNearestNeighbors.withNeighbors(1).build();
    // System.out.println(HoldoutValidator.withHoldout(test.drop(0), test.get(0)).test(knn,
    // train.drop(0), train.get(0)).get(ErrorRate.class));

    // .getAverage(Accuracy.class));
    // CBF = 0.3
    // synthetic_control = 0.1
    // Gun_Point = 0.3
    // Mote_strain = 0.5
    // DataFrame x = train.drop(0);
    // Input<Instance> input = Inputs.newInput(x);
    // Output<Object> output = new MutableOutput<>(y.toList());
    //
    // SplitPartitioner<Instance, Object> partitioner = new SplitPartitioner<>(0.1);
    // Partition<Instance, Object> trainPart = partitioner.partition(input,
    // output).iterator().next();
    //
    // DistanceNonconformity.Learner<Instance> nc =
    // new DistanceNonconformity.Learner<>(1, EuclideanDistance.getInstance());
    // // Nonconformity.Learner nc =
    // // new ProbabilityEstimateNonconformity.Learner(
    // // new
    // //
    // RandomShapeletForest.Configurator(100).setAssessment(ShapeletTree.Learner.Assessment.IG).configure(),
    // // new Margin());
    // InductiveConformalClassifier.Learner<Instance> learner =
    // new InductiveConformalClassifier.Learner<>(nc);
    // InductiveConformalClassifier<Instance> classifier =
    // learner.fit(trainPart.getTrainingData(), trainPart.getTrainingTarget());
    // classifier.calibrate(trainPart.getValidationData(), trainPart.getValidationTarget());
    // // testEarlyClassification(test, classifier);
  }

  private void testEarlyClassification(DataFrame test, ConformalClassifier classifier) {
    // Series c = classifier.getClasses();
    // DataFrame x = test.drop(0);
    // Series y = test.get(0);
    //
    // IntArray d = Arrays.intArray(x.rows());
    // double correct = 0;
    // for (int i = 0; i < x.rows(); i++) {
    // if (i % 100 == 0) {
    // System.out.printf("Processing test instance %d/%d\n", i, x.rows());
    // }
    // Series record = x.loc().getRecord(i);
    // Object trueLabel = y.loc().get(Object.class, i);
    // boolean found = false;
    // for (int j = 5; j < record.size() && !found; j++) {
    // double minSignificance = 0.05;
    // double minConfidence = 0.95;
    // DoubleArray estimates = classifier.estimate(record.select(0, j));
    // int prediction = Arrays.argmax(estimates);
    // double credibility = estimates.get(prediction);
    // double confidence = 1 - maxnot(estimates, prediction);
    // System.out.println(confidence + " " + credibility + " from " + estimates);
    // if (confidence >= minConfidence && credibility >= minSignificance) {
    // d.set(i, j);
    // correct += Is.equal(c.loc().get(prediction), trueLabel) ? 1 : 0;
    // found = true;
    // }
    // }

    // Classify it once all time has passed
    // if (!found) {
    // Object prediction = c.loc().get(Arrays.argmax(classifier.estimate(record)));
    // correct += Is.equal(trueLabel, prediction) ? 1 : 0;
    // d.set(i, record.size());
    // }
    // }
    //
    // System.out.println(correct / x.rows());
    // System.out.println(Arrays.mean(d.get(Arrays.range(5, x.rows())).asDouble()) / x.columns());
  }

  private Series shift(Series row) {
    Series.Builder builder = row.newBuilder();
    int start = random.nextInt(row.size() - 1);
    for (int i = start; i < row.size(); i++) {
      builder.addFromLocation(row, i);
    }
    for (int i = 0; i < start; i++) {
      builder.addFromLocation(row, i);
    }

    return builder.build();
  }

  @Test
  public void testShift() throws Exception {
    System.out.println(shift(Series.of(1, 2, 3, 4, 5, 6)));
  }

  private DoubleArray computeOobAccuracy(RandomPatternForest predictor, DataFrame x, Series y) {

    BooleanArray oob = predictor.getOobIndicator();
    for (int i = 0; i < x.rows(); i++) {
      Series record = x.loc().getRow(i);
      // List<Classifier> oobMembers = getOobMembers(oob.getRow(i), predictor.getEnsembleMembers());
      // DoubleArray estimate = predictOob(oobMembers, record);

    }
    return null;
  }

  private List<Classifier> getOobMembers(BooleanArray oob, List<Classifier> classifiers) {
    List<Classifier> oobPredictors = new ArrayList<>();
    for (int i = 0; i < classifiers.size(); i++) {
      if (oob.get(i)) {
        oobPredictors.add(classifiers.get(i));
      }
    }

    return oobPredictors;
  }

  private DoubleArray predictOob(List<Classifier> members, Series record) {

    return null;
  }

  public DataFrame vectorize(DataFrame x) {
    Set<Object> columns = new HashSet<>();
    for (Object recordKey : x.getIndex().keySet()) {
      List<Object> list = x.ix().getRow(recordKey).values();
      columns.addAll(list.subList(1, list.size() - 1));
    }

    DataFrame.Builder builder = DataFrame.newBuilder();
    builder.setColumn("Class", x.get(0));
    for (Object column : columns) {
//      if (StringUtils.isWhitespace(column.toString())) {
//        continue;
//      }
      Series.Builder columnBuilder = Series.Builder.of(Boolean.class);
      for (Object recordKey : x.getIndex().keySet()) {
        columnBuilder.add(x.ix().getRow(recordKey).values().contains(column));
      }
      builder.setColumn(column, columnBuilder);
    }

    return builder.build();
  }

  @Test
  public void testSequences() throws Exception {
    // String ade = "G444";
    // EntryReader in = new SequenceDatasetReader(
    // new FileInputStream("/Users/isak-kar/Desktop/sequences/" + ade + ".seq"));
    //
    // DataFrame frame = new DataSeriesCollection.Builder(String.class).readAll(in).build();
    // frame = vectorize(frame);
    //
    // // System.out.println(frame.rows() + ", " + frame.columns());
    // // Utils.setRandomSeed(32);
    // frame = DataFrames.permuteRecords(frame);
    // //
    // Series y = frame.get("Class");
    // DataFrame x = frame.drop("Class");
    // System.out.println(y.size());
    // System.out.println(x.rows());
    // Map<Object, Integer> freq = Vectors.count(y);
    // int sum = freq.values().stream().reduce(0, Integer::sum);
    // int min = freq.values().stream().min(Integer::min).get();
    // System.out.println(freq + " => " + ((double) min / sum));
    // //
    // Predictor.Learner<Instance, Object, ? extends Classifier<Instance>> forest =
    // new RandomForest.Configurator(100).setMaximumFeatures(100).configure();
    // // new RandomShapeletForest.Configurator(100).setF.configure();
    // // new RandomShapeletForest.Configurator(25).setDistance(
    // // new SlidingDistance(new HammingDistance())).configure();
    // // new NearestNeighbours.Learner(1, new SimilarityDistance(
    // // new SmithWatermanSimilarity(1, 0, 0)));
    //
    // Validator<Instance, Object, Classifier<Instance>> cv = crossValidator(10);
    // cv.add(Evaluator.foldOutput(fold -> System.out.printf("Completed fold %d\n", fold)));
    // Result<?> result = cv.test(forest, Inputs.newInput(x), Outputs.newOutput(y));
    // System.out.println(result.getMeasures().mean());
    // // System.out.println(result.getAverageConfusionMatrix().getPrecision("ade"));
    // // System.out.println(result.getAverageConfusionMatrix().getRecall("ade"));
    // // System.out.println(result.getAverageConfusionMatrix().getFMeasure("ade", 2));
    // // System.out.println(result);
  }
}

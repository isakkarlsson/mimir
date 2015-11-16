package org.mimirframework.examples;

import org.briljantframework.array.Arrays;
import org.briljantframework.data.Is;
import org.briljantframework.data.SortOrder;
import org.briljantframework.data.dataframe.DataFrame;
import org.briljantframework.data.dataframe.DataFrames;
import org.briljantframework.data.vector.Vector;
import org.briljantframework.dataset.io.Datasets;
import org.mimirframework.classification.Classifier;
import org.mimirframework.classification.RandomForest;
import org.mimirframework.classification.conformal.ConformalClassifier;
import org.mimirframework.classification.conformal.InductiveConformalClassifier;
import org.mimirframework.classification.conformal.Nonconformity;
import org.mimirframework.classification.conformal.ProbabilityCostFunction;
import org.mimirframework.classification.conformal.ProbabilityEstimateNonconformity;
import org.mimirframework.classification.conformal.evaluation.ConformalClassifierValidator;
import org.mimirframework.evaluation.Result;
import org.mimirframework.evaluation.Validator;
import org.mimirframework.supervised.Predictor;

/**
 * @author Isak Karlsson
 */
public class InductiveConformalPredictionExample {
  public static void main(String[] args) {
    // Load the iris data set
    DataFrame iris = DataFrames.permuteRecords(Datasets.loadIris());

    // Remove the class variable from the input data and set each NA value to the column mean
    DataFrame x = iris.drop("Class").apply(v -> v.set(v.where(Is::NA), v.mean()));

    // Get the class variable
    Vector y = iris.get("Class");

    // Create a classifier learner to use for estimating the non-conformity scores
    Predictor.Learner<? extends Classifier> classifier =
        new RandomForest.Configurator(100).configure();

    // Initialize the non-conformity learner using the margin as cost function
    Nonconformity.Learner nc =
        new ProbabilityEstimateNonconformity.Learner(classifier, ProbabilityCostFunction.margin());

    // Initialize an inductive conformal classifier using the non-conformity learner
    InductiveConformalClassifier.Learner cp = new InductiveConformalClassifier.Learner(nc);

    // Create a validator for evaluating the validity and efficiency of the conformal classifier. In
    // this case, we evaluate the classifier using 10-fold cross-validation and 9 significance
    // levels between 0.1 and 0.1
    Validator<ConformalClassifier> validator =
        ConformalClassifierValidator.crossValidator(10, 0.3, Arrays.linspace(0.01, 0.1, 9));

    Result result = validator.test(cp, x, y);

    // Get the measures
    DataFrame measures = result.getMeasures();

    // Compute the mean of all measures grouped by significance level
    DataFrame meanPerSignificance =
        measures.groupBy("significance").collect(Vector::mean).sort(SortOrder.ASC);
    System.out.println(meanPerSignificance);
  }
}
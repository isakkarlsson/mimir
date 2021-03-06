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
package org.briljantframework.mimir.evaluation;

import java.util.*;

import org.briljantframework.mimir.classification.Classifier;
import org.briljantframework.mimir.data.Input;
import org.briljantframework.mimir.evaluation.partition.FoldPartitioner;
import org.briljantframework.mimir.evaluation.partition.Partition;
import org.briljantframework.mimir.evaluation.partition.Partitioner;
import org.briljantframework.mimir.evaluation.partition.SplitPartitioner;
import org.briljantframework.mimir.supervised.Predictor;

/**
 * A validator evaluates the performance of a given {@linkplain Predictor.Learner learning
 * algorithm} using a specified data set. The dataset is partition using a specified
 * {@link Partitioner} into {@linkplain Partition partitions}. Finally, the validator can also be
 * given a set of {@linkplain Evaluator evaluators} responsible for measuring the performance of the
 * given predictor.
 * 
 * <pre>
 * // We use 10 train and test partitions
 * ClassifierValidator<Instance, Classifier<Instance>> validator =
 *     ClassifierValidator.crossValidator(10);
 * LogisticRegression.Learner learner = new LogisticRegression.Learner();
 * DataFrame iris = Datasets.loadIris();
 * DataFrame x = iris.drop(&quot;Class&quot;).apply(v -&gt; v.set(v.where(Is::NA), v.mean()));
 * Vector y = iris.get(&quot;Class&quot;);
 * 
 * Result&lt;?&gt; result = validator.test(learner, Inputs.newInput(x), Outputs.newOutput(y));
 * DataFrame measures = result.getMeasures();
 * measures.mean();
 * </pre>
 * 
 * produces, something like:
 * 
 * <pre>
 * ACCURACY         0.96
 * AUCROC           0.19999999999999998
 * BRIER_SCORE      0.032292661080829614
 * ERROR            0.03999999999999999
 * FIT_TIME         42.444722999999996
 * PREDICT_TIME     0.9753513000000001
 * TRAINING_SIZE    135.0
 * VALIDATION_SIZE  15.0
 * type: double
 * </pre>
 * <p>
 * The above specified validator can be used to evaluate any classifier (i.e. any class implementing
 * the {@link Classifier} interface).
 */
public abstract class Validator<In, Out, P extends Predictor<In, Out>> {

  private final Set<Evaluator<In, Out>> evaluators;
  private final Partitioner<In, Out> partitioner;

  public Validator(Set<? extends Evaluator<In, Out>> evaluators, Partitioner<In, Out> partitioner) {
    this.evaluators = new HashSet<>(evaluators);
    this.partitioner = partitioner;
  }

  public Validator(Partitioner<In, Out> partitioner) {
    this(Collections.emptySet(), partitioner);
  }

  /**
   * Evaluate {@code classifier} using the given data
   *
   * @param learner classifier to use for classification
   * @param x the data frame to use during evaluation
   * @param y the target to used during evaluation
   * @return a result
   */
  public Result<Out> test(Predictor.Learner<In, Out, ? extends P> learner, Input<In> x,
      List<Out> y) {
    Collection<Partition<In, Out>> partitions = getPartitioner().partition(x, y);
    MutableEvaluationContext<In, Out> ctx = new MutableEvaluationContext<>();
    List<Out> actual = new ArrayList<>();
    List<Out> predictions = new ArrayList<>();
    double avgFitTime = 0, avgPredictTime = 0, avgTrainingSize = 0, avgValidationSize = 0;
    double noPartition = partitions.size();
    int iteration = 0;
    for (Partition<In, Out> partition : partitions) {
      Input<In> trainingData = partition.getTrainingData();
      List<Out> trainingTarget = partition.getTrainingTarget();
      Input<In> validationData = partition.getValidationData();
      List<Out> validationTarget = partition.getValidationTarget();
      ctx.setPartition(partition);

      // Step 1: Fit the classifier using the training data
      long start = preFit();
      P predictor = fit(learner, trainingData, trainingTarget);
      ctx.setPredictor(predictor);
      double fitTime = postFit(start);

      // Step 3: Make predictions on the validation data
      start = prePredict();
      predict(ctx);
      double predictTime = postPredict(start);

      // Step 4: Compute the given measures
      EvaluationContext<In, Out> evaluationContext = ctx.getEvaluationContext();
      evaluate(evaluationContext, iteration++);

      actual.addAll(validationTarget);
      predictions.addAll(evaluationContext.getPredictions());

      avgFitTime += fitTime / noPartition;
      avgPredictTime += predictTime / noPartition;
      avgTrainingSize += trainingData.size() / noPartition;
      avgValidationSize += validationData.size() / noPartition;
    }

    return new Result<>(ctx.getEvaluationContext().getMeasureCollection(), actual, predictions,
        avgTrainingSize, avgValidationSize, avgFitTime, avgPredictTime);
  }

  protected double postPredict(long start) {
    return (System.nanoTime() - start) / 1e6;
  }

  protected long prePredict() {
    return System.nanoTime();
  }

  protected double postFit(long start) {
    return (System.nanoTime() - start) / 1e6;
  }

  protected long preFit() {
    return System.nanoTime();
  }

  /**
   * Evaluate the model using the given context
   *
   * @param evaluationContext the evaluation context
   * @param fold the current partition number
   */
  protected void evaluate(EvaluationContext<In, Out> evaluationContext, int fold) {
    evaluationContext.getMeasureCollection().add("fold", fold);
    acceptEvaluators(evaluationContext);
  }

  /**
   * Fit the given predictor using the supplied training data
   * 
   * @param learner the learner for learning a predictor of the given type
   * @param x the input features
   * @param y the input label
   */
  protected abstract P fit(Predictor.Learner<In, Out, ? extends P> learner, Input<In> x,
      List<Out> y);

  protected abstract void predict(MutableEvaluationContext<In, Out> ctx);

  protected void acceptEvaluators(EvaluationContext<In, Out> context) {
    evaluators.forEach(evaluator -> evaluator.accept(context));
  }

  /**
   * Returns true if the validator contains the specified evaluator
   *
   * @param evaluator the evaluator
   * @return true if the validator contains the specified evaluator
   */
  public final boolean contains(Evaluator<In, Out> evaluator) {
    return evaluators.contains(evaluator);
  }

  /**
   * Remove the specified evaluator from this validator
   *
   * @param evaluator the evaluator to remove
   * @return boolean if the validator contained the specified evaluator
   */
  public final boolean remove(Evaluator<In, Out> evaluator) {
    return evaluators.remove(evaluator);
  }

  /**
   * Remove all evaluator in this validator
   */
  public final void clear() {
    evaluators.clear();
  }

  /**
   * Add an evaluator to the validator for computing additional measures. Callers should ensure that
   * a unique measure is only computed by one {@link Evaluator}
   * <p>
   * 
   * <pre>
   * Validator&lt;Classifier&gt; cv = ClassifierValidator.crossValidation(10);
   * cv.add((ctx) -&gt; System.out.println(&quot;Running fold.&quot;));
   * // For each fold, print &quot;Running fold.&quot; to std-out
   * </pre>
   *
   * @param evaluator the evaluator
   */
  public final void add(Evaluator<In, Out> evaluator) {
    this.evaluators.add(evaluator);
  }

  /**
   * Gets the partitioner used for this validator. The partitioner partitions the data into training
   * and validation folds. For example, {@link FoldPartitioner} partitions the data into {@code k}
   * folds and {@link SplitPartitioner} partitions the data into one fold.
   *
   * @return the partitioner used by this validator
   */
  public final Partitioner<In, Out> getPartitioner() {
    return partitioner;
  }
}

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
package org.briljantframework.mimir.classification.conformal.evaluation;

import org.briljantframework.array.DoubleArray;
import org.briljantframework.mimir.classification.conformal.BootstrapConformalClassifier;
import org.briljantframework.mimir.data.Input;
import org.briljantframework.mimir.evaluation.partition.Partitioner;
import org.briljantframework.mimir.supervised.Predictor;

import java.util.List;

/**
 * Created by isak on 2016-12-07.
 */
public class BootstrapConformalClassifierValidator<In, Out>
    extends ConformalClassifierValidator<In, Out, BootstrapConformalClassifier<In, Out>> {

  public BootstrapConformalClassifierValidator(Partitioner<In, Out> partitioner,
      DoubleArray confidences) {
    super(partitioner, confidences);
  }

  public BootstrapConformalClassifierValidator(Partitioner<In, Out> partitioner) {
    super(partitioner);
  }

  // @Override
  protected BootstrapConformalClassifier<In, Out> fit(
      Predictor.Learner<In, Out, ? extends BootstrapConformalClassifier<In, Out>> learner,
      Input<In> x, List<Out> y) {
    return learner.fit(x, y);
  }
}

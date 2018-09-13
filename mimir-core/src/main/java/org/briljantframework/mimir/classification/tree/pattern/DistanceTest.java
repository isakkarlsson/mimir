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
package org.briljantframework.mimir.classification.tree.pattern;

import org.briljantframework.mimir.classification.tree.Direction;
import org.briljantframework.mimir.classification.tree.TreeNodeTest;

/**
 * Created by isak on 2017-03-17.
 */
public class DistanceTest<In, E> implements TreeNodeTest<In> {
  private final E shapelet;
  private final double threshold;
  private final PatternDistance<? super In, ? super E> patternDistance;

  public DistanceTest(PatternDistance<? super In, ? super E> patternDistance, E shapelet,
      double threshold) {
    this.patternDistance = patternDistance;
    this.shapelet = shapelet;
    this.threshold = threshold;
  }

  public double getThreshold() {
    return threshold;
  }

  public E getShapelet() {
    return shapelet;
  }

  @Override
  public Direction test(In ex) {
    if (patternDistance.computeDistance(ex, shapelet) < threshold) {
      return Direction.LEFT;
    } else {
      return Direction.RIGHT;
    }
  }
}

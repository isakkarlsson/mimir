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
package org.briljantframework.mimir.timeseries.data;

import org.briljantframework.Check;
import org.briljantframework.mimir.data.Input;
import org.briljantframework.mimir.data.MutableInput;
import org.briljantframework.mimir.data.Schema;

/**
 * Created by isak on 2017-02-28.
 */
public class MultivariateTimeSeriesSchema implements Schema<MultivariateTimeSeries> {

  private final int dimensionality;
  private String[] dimensionNames;

  public MultivariateTimeSeriesSchema(int dimensionality) {
    Check.argument(dimensionality > 0, "dimensionality <= 0");
    this.dimensionality = dimensionality;
    this.dimensionNames = new String[dimensionality];
  }

  public void setDimensionName(int i, String name) {
    this.dimensionNames[i] = name;
  }

  public String getDimensionName(int i) {
    return dimensionNames[i];
  }

  @Override
  public Input<MultivariateTimeSeries> newInput() {
    return new MutableInput<>(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (obj instanceof MultivariateTimeSeriesSchema) {
      return ((MultivariateTimeSeriesSchema) obj).dimensionality == dimensionality;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return dimensionality;
  }

  @Override
  public boolean isValid(MultivariateTimeSeries ex) {
    return ex.dimensions() == dimensionality;
  }
}

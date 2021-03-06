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
package org.briljantframework.mimir;

import java.util.function.Function;

/**
 * A key with an associated type. Referential equality and identity hash code is guaranteed.
 *
 * @author Isak Karlsson
 */
public abstract class Property<T> {

  /**
   * Returns a property with the specified name and type
   *
   * @param name the name
   * @param cls the type
   * @param <T> the class
   * @return a new key
   */
  public static <T> Property<T> of(String name, Class<T> cls) {
    return new StringProperty<>(cls, name);
  }

  /**
   * Returns a property with the specified name, type and default value
   *
   * @param name the name
   * @param cls the type
   * @param defaultValue the default value
   * @param <T> the class
   * @return a new key
   */
  public static <T> Property<T> of(String name, Class<T> cls, T defaultValue) {
    return new StringProperty<>(cls, name, defaultValue);
  }

  /**
   * Returns a key with the specified name, type, default value and value validator
   *
   * @param name the name
   * @param cls the type
   * @param defaultValue the default value
   * @param validator the value validator
   * @param <T> the class
   * @return a new key
   */
  public static <T> Property<T> of(String name, Class<T> cls, T defaultValue,
      Function<? super T, Boolean> validator) {
    return new StringProperty<T>(cls, name, defaultValue, validator);
  }

  /**
   * Get the default value for this key, or null if no default value.
   *
   * @return the default value
   */
  public T defaultValue() {
    return null;
  }

  /**
   * Validate that the value is appropriate for this type key.
   *
   * @param value the value
   * @return true if the value is valid (default: value != null)
   */
  public boolean validate(T value) {
    return value != null;
  }

  /**
   * Return the class of the given property
   *
   * @return the class of the property
   */
  public abstract Class<T> getType();

  /**
   * Return the name of the property
   *
   * @return the property name
   */
  public abstract String getName();

  /**
   * Return the description of the property
   * 
   * @return the property description
   */
  public abstract String getDescription();

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    return System.identityHashCode(this);
  }

  /**
   * The default typed key based on string values. Two keys are the same if the have the same name
   * and type.
   *
   * @author Isak Karlsson
   */
  private static class StringProperty<T> extends Property<T> {
    private final Class<T> cls;
    private final String name;
    private final T defaultValue;
    private final Function<? super T, Boolean> validator;

    StringProperty(Class<T> cls, String name) {
      this(cls, name, null);
    }

    StringProperty(Class<T> cls, String name, T defaultValue) {
      this(cls, name, defaultValue, v -> v != null);
    }

    StringProperty(Class<T> cls, String name, T defaultValue,
        Function<? super T, Boolean> validator) {
      this.cls = cls;
      this.name = name;
      this.defaultValue = defaultValue;
      this.validator = validator;
    }

    @Override
    public Class<T> getType() {
      return cls;
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public String getDescription() {
      return name;
    }

    @Override
    public T defaultValue() {
      return defaultValue;
    }

    @Override
    public boolean validate(T value) {
      return validator.apply(value);
    }

    // @Override
    // public boolean equals(Object o) {
    // if (this == o)
    // return true;
    // if (o == null || getClass() != o.getClass())
    // return false;
    //
    // StringProperty<?> that = (StringProperty<?>) o;
    // if (cls != null ? !cls.equals(that.cls) : that.cls != null) {
    // return false;
    // } else {
    // return name != null ? name.equals(that.name) : that.name == null;
    // }
    // }
    //
    // @Override
    // public int hashCode() {
    // int result = cls != null ? cls.hashCode() : 0;
    // result = 31 * result + (name != null ? name.hashCode() : 0);
    // return result;
    // }

    @Override
    public String toString() {
      return getName();
    }
  }
}

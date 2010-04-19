/*
 * Copyright 2009 Jiemamy Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jiemamy.utils.gtree.converter;

import java.util.ArrayList;
import java.util.List;

import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Value;

/**
 * 任意の配列を{@link Sequence}に変換する。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public enum ArrayConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * オブジェクト配列、またはプリミティブ配列を{@link Sequence}に変換する。
	 * <p>
	 * 変換対象の配列に含まれる要素は、引数{@code converter}によって再帰的に
	 * {@link Value}に変換される。
	 * この呼び出しが返す値は、配列の各要素をそれぞれ {@link Value}へ変換し、
	 * 同一の順序を保持する{@link Sequence}である。
	 * なお、配列以外の要素が指定された場合や、配列の要素に変換できない値が含まれていた場合は
	 * この呼び出しは{@code null}を返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) { // CHECKSTYLE IGNORE THIS LINE
		if (object instanceof int[]) {
			return convertArray((int[]) object, converter);
		}
		if (object instanceof long[]) {
			return convertArray((long[]) object, converter);
		}
		if (object instanceof float[]) {
			return convertArray((float[]) object, converter);
		}
		if (object instanceof double[]) {
			return convertArray((double[]) object, converter);
		}
		if (object instanceof byte[]) {
			return convertArray((byte[]) object, converter);
		}
		if (object instanceof short[]) {
			return convertArray((short[]) object, converter);
		}
		if (object instanceof char[]) {
			return convertArray((char[]) object, converter);
		}
		if (object instanceof boolean[]) {
			return convertArray((boolean[]) object, converter);
		}
		if (object instanceof Object[]) {
			return convertArray((Object[]) object, converter);
		}
		return null;
	}
	
	private Value convertArray(int[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(long[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(float[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(double[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(byte[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(short[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(char[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(boolean[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
	
	private Value convertArray(Object[] array, ObjectConverter converter) {
		List<Value> values = new ArrayList<Value>(array.length);
		for (Object elem : array) {
			Value value = converter.convert(elem);
			if (value == null) {
				return null;
			}
			values.add(value);
		}
		return Sequence.ordered(values);
	}
}

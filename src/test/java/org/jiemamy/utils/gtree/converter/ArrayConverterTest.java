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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.jiemamy.utils.gtree.converter.ArrayConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link ArrayConverter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class ArrayConverterTest {
	
	private ObjectConverter conv = GeneralObjectConverter.INSTANCE;
	

	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_int() {
		Value s = INSTANCE.convert(new int[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1, 2, 3)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_long() {
		Value s = INSTANCE.convert(new long[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1L, 2L, 3L)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_float() {
		Value s = INSTANCE.convert(new float[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1.f, 2.f, 3.f)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_double() {
		Value s = INSTANCE.convert(new double[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1.0, 2.0, 3.0)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_byte() {
		Value s = INSTANCE.convert(new byte[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1, 2, 3)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_short() {
		Value s = INSTANCE.convert(new short[] {
			1,
			2,
			3
		}, conv);
		assertThat(s, is(seq(1, 2, 3)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_char() {
		Value s = INSTANCE.convert(new char[] {
			'a',
			'b',
			'c'
		}, conv);
		assertThat(s, is(seq('a', 'b', 'c')));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_boolean() {
		Value s = INSTANCE.convert(new boolean[] {
			true,
			false
		}, conv);
		assertThat(s, is(seq(true, false)));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Object() {
		Value s = INSTANCE.convert(new Object[] {
			"a",
			"b",
			"c"
		}, conv);
		assertThat(s, is(seq("a", "b", "c")));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Other() {
		Value s = INSTANCE.convert("HELLO", conv);
		assertThat(s, is(nullValue()));
	}
	
	/**
	 * Test method for {@link ArrayConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_null() {
		Value s = INSTANCE.convert(null, conv);
		assertThat(s, is(nullValue()));
	}
	
	private static final Value seq(Object... values) {
		List<Terminal> v = new ArrayList<Terminal>();
		for (Object s : values) {
			v.add(Terminal.of(String.valueOf(s)));
		}
		return Sequence.ordered(v);
	}
}

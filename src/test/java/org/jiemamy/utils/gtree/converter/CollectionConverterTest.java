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
import static org.jiemamy.utils.gtree.converter.CollectionConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link CollectionConverter}.
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class CollectionConverterTest {
	
	private ObjectConverter conv = GeneralObjectConverter.INSTANCE;
	

	/**
	 * Test method for {@link CollectionConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_List() {
		Value result = INSTANCE.convert(listOf(1, 2, 3), conv);
		assertThat(result, is(seq(1, 2, 3)));
	}
	
	/**
	 * Test method for {@link CollectionConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Set() {
		Value result = INSTANCE.convert(setOf(3, 1, 2), conv);
		assertThat(result, is(set(1, 2, 3)));
	}
	
	/**
	 * Test method for {@link CollectionConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Other() {
		Value result = INSTANCE.convert(this, conv);
		assertThat(result, is(nullValue()));
	}
	
	/**
	 * Test method for {@link CollectionConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		Value result = INSTANCE.convert(null, conv);
		assertThat(result, is(nullValue()));
	}
	
	private static List<?> listOf(Object... values) {
		return Arrays.asList(values);
	}
	
	private static Set<?> setOf(Object... values) {
		return new HashSet<Object>(Arrays.asList(values));
	}
	
	private static final Value seq(Object... values) {
		List<Terminal> v = new ArrayList<Terminal>();
		for (Object s : values) {
			v.add(Terminal.of(String.valueOf(s)));
		}
		return Sequence.ordered(v);
	}
	
	private static final Value set(Object... values) {
		List<Terminal> v = new ArrayList<Terminal>();
		for (Object s : values) {
			v.add(Terminal.of(String.valueOf(s)));
		}
		return Sequence.unordered(v);
	}
}

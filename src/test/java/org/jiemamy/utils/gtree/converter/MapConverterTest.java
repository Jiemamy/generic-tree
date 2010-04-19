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
import static org.jiemamy.utils.gtree.converter.MapConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link MapConverter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class MapConverterTest {
	
	private ObjectConverter conv = GeneralObjectConverter.INSTANCE;
	

	/**
	 * Test method for {@link MapConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Empty() {
		Value result = INSTANCE.convert(Collections.emptyMap(), conv);
		assertThat(result, is(rec()));
	}
	
	/**
	 * Test method for {@link MapConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Singular() {
		Value result = INSTANCE.convert(Collections.singletonMap("field", "value"), conv);
		assertThat(result, is(rec("field", "value")));
	}
	
	/**
	 * Test method for {@link MapConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Many() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "A");
		map.put("b", "B");
		map.put("c", "C");
		Value result = INSTANCE.convert(map, conv);
		assertThat(result, is(rec("a", "A", "b", "B", "c", "C")));
	}
	
	/**
	 * Test method for {@link MapConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Other() {
		Value result = INSTANCE.convert(this, conv);
		assertThat(result, is(nullValue()));
	}
	
	/**
	 * Test method for {@link MapConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		Value result = INSTANCE.convert(null, conv);
		assertThat(result, is(nullValue()));
	}
	
	private static Value rec(String... pairs) {
		assert pairs.length % 2 == 0;
		ArrayList<Entry> results = new ArrayList<Entry>();
		for (int i = 0; i < pairs.length; i += 2) {
			Terminal key = Terminal.of(pairs[i]);
			Terminal value = Terminal.of(pairs[i + 1]);
			results.add(Entry.of(key, value));
		}
		return Record.of(results);
	}
}

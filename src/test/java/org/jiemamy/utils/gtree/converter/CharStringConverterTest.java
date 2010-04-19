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
import static org.jiemamy.utils.gtree.converter.CharStringConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link CharStringConverter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class CharStringConverterTest {
	
	/**
	 * Test method for {@link CharStringConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_String() {
		Value value = INSTANCE.convert("a", new MockObjectConverter());
		assertThat(value, is((Object) Terminal.of("\"a\"")));
	}
	
	/**
	 * Test method for {@link CharStringConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Char() {
		Value value = INSTANCE.convert('\'', new MockObjectConverter());
		assertThat(value, is((Object) Terminal.of("'\\''")));
	}
	
	/**
	 * Test method for {@link CharStringConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		Value value = INSTANCE.convert(null, new MockObjectConverter());
		assertThat(value, is(nullValue()));
	}
	
	/**
	 * Test method for {@link CharStringConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Object() {
		Value value = INSTANCE.convert(this, new MockObjectConverter());
		assertThat(value, is(nullValue()));
	}
}

/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
 * Created on 2009/02/02
 *
 * This file is part of Jiemamy.
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
import static org.jiemamy.utils.gtree.converter.PrimitiveConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link PrimitiveConverter}.
 * @author Suguru ARAKAWA
 */
public class PrimitiveConverterTest {
	
	private Value t(Object value) {
		return Terminal.of(String.valueOf(value));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_BigDecimal() {
		Value result = INSTANCE.convert(new BigDecimal(10), new MockObjectConverter());
		assertThat(result, is(t("10")));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Boolean() {
		Value result = INSTANCE.convert(false, new MockObjectConverter());
		assertThat(result, is(t("false")));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Byte() {
		Value result = INSTANCE.convert((byte) 6, new MockObjectConverter());
		assertThat(result, is(t(6)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Character() {
		Value result = INSTANCE.convert('a', new MockObjectConverter());
		assertThat(result, is(t("a")));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Double() {
		Value result = INSTANCE.convert(4.0d, new MockObjectConverter());
		assertThat(result, is(t(4.0d)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Float() {
		Value result = INSTANCE.convert(3.0f, new MockObjectConverter());
		assertThat(result, is(t(3.0f)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Integer() {
		Value result = INSTANCE.convert(1, new MockObjectConverter());
		assertThat(result, is(t(1)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Long() {
		Value result = INSTANCE.convert(2L, new MockObjectConverter());
		assertThat(result, is(t(2L)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		Value result = INSTANCE.convert(null, new MockObjectConverter());
		assertThat(result, is(t("")));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Otherwise() {
		Value result = INSTANCE.convert(new StringBuilder(), new MockObjectConverter());
		assertThat(result, is(nullValue()));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Short() {
		Value result = INSTANCE.convert((short) 5, new MockObjectConverter());
		assertThat(result, is(t(5)));
	}
	
	/**
	 * Test method for {@link PrimitiveConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_String() {
		Value result = INSTANCE.convert("Hello, world!", new MockObjectConverter());
		assertThat(result, is(t("Hello, world!")));
	}
}

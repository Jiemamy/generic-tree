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
import static org.jiemamy.utils.gtree.converter.EnumConverter.INSTANCE;
import static org.junit.Assert.assertThat;

import java.lang.Thread.State;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Terminal;

/**
 * Test for {@link EnumConverter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class EnumConverterTest {
	
	/**
	 * Test method for {@link EnumConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert() {
		assertThat(INSTANCE.convert(TimeUnit.SECONDS, new MockObjectConverter()), is((Object) Terminal
			.of("java.util.concurrent.TimeUnit.SECONDS")));
		assertThat(INSTANCE.convert(State.RUNNABLE, new MockObjectConverter()), is((Object) Terminal
			.of("java.lang.Thread.State.RUNNABLE")));
	}
	
	/**
	 * Test method for {@link EnumConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Other() {
		assertThat(INSTANCE.convert(this, new MockObjectConverter()), is(nullValue()));
	}
	
	/**
	 * Test method for {@link EnumConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		assertThat(INSTANCE.convert(null, new MockObjectConverter()), is(nullValue()));
	}
}

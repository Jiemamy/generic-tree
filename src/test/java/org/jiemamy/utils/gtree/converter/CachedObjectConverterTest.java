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

import java.util.Arrays;

import org.junit.Test;

/**
 * Test for {@link CachedObjectConverter}.
 * @author Suguru ARAKAWA
 */
public class CachedObjectConverterTest {
	
	/**
	 * Test method for {@link CachedObjectConverter#convert(Object)}.
	 */
	@Test(timeout = 1000L)
	public void testConvert_Brother() {
		Object[] parent = new Object[2];
		Object[] child = new Object[0];
		parent[0] = parent[1] = child;
		CachedObjectConverter conv = CachedObjectConverter.newInstance(Arrays.<ConverterDriver> asList());
		conv.convert(parent);
	}
	
	/**
	 * Test method for {@link CachedObjectConverter#convert(Object)}.
	 */
	@Test(timeout = 1000L)
	public void testConvert_ChildLoop() {
		Object[] a1 = new Object[1];
		a1[0] = a1;
		CachedObjectConverter conv = CachedObjectConverter.newInstance(Arrays.<ConverterDriver> asList());
		conv.convert(a1);
	}
}

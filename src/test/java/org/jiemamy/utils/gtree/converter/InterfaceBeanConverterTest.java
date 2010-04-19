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
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import org.jiemamy.utils.gtree.converter.bean.BadPropertiesImpl;
import org.jiemamy.utils.gtree.converter.bean.Bean;
import org.jiemamy.utils.gtree.converter.bean.EnhancedBean;
import org.jiemamy.utils.gtree.converter.bean.IndirectVia;
import org.jiemamy.utils.gtree.converter.bean.InheritedBean;
import org.jiemamy.utils.gtree.converter.bean.Pojo;
import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link InterfaceBeanConverter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class InterfaceBeanConverterTest {
	
	private ObjectConverter conv = GeneralObjectConverter.INSTANCE;
	

	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		Bean obj = new Bean();
		obj.active = true;
		obj.hello = "hello";
		obj.number = 1;
		assertThat(driver.convert(obj, conv), is(rec("active", "true", "hello", "hello", "number", "1")));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_WithNotGetter() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		EnhancedBean obj = new EnhancedBean();
		obj.active = true;
		obj.hello = "hello";
		obj.number = 1;
		assertThat(driver.convert(obj, conv), is(rec("active", "true", "hello", "hello", "number", "1")));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_BadProperties() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		BadPropertiesImpl obj = new BadPropertiesImpl();
		assertThat(driver.convert(obj, conv), is(nullValue()));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Inherited() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		InheritedBean obj = new InheritedBean();
		obj.active = true;
		obj.hello = "hello";
		obj.number = 1;
		assertThat(driver.convert(obj, conv), is(rec("active", "true", "hello", "hello", "number", "1")));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Indirect() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		IndirectVia obj = new IndirectVia();
		obj.active = true;
		obj.hello = "hello";
		obj.number = 1;
		assertThat(driver.convert(obj, conv), is(rec("active", "true", "hello", "hello", "number", "1")));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Pojo() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		Pojo obj = new Pojo();
		obj.active = true;
		obj.hello = "hello";
		obj.number = 1;
		assertThat(driver.convert(obj, conv), is(nullValue()));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Enum() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		assertThat(driver.convert(TimeUnit.SECONDS, conv), is(nullValue()));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Array() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		assertThat(driver.convert(new Object[0], conv), is(nullValue()));
	}
	
	/**
	 * Test method for {@link InterfaceBeanConverter#convert(Object, ObjectConverter)}.
	 */
	@Test
	public void testConvert_Null() {
		InterfaceBeanConverter driver = new InterfaceBeanConverter();
		assertThat(driver.convert(null, conv), is(nullValue()));
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

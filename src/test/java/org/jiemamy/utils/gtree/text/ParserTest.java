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
package org.jiemamy.utils.gtree.text;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link Parser}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class ParserTest {
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Terminal() throws Exception {
		Value v = parse("'Hello'");
		assertThat(v, is(terminal("Hello")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_OList_0() throws Exception {
		Value v = parse("[]");
		assertThat(v, is(ol()));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_OList_1() throws Exception {
		Value v = parse("['HELLO']");
		assertThat(v, is(ol("HELLO")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_OList_3() throws Exception {
		Value v = parse("['A', 'B', 'C']");
		assertThat(v, is(ol("A", "B", "C")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_UList_0() throws Exception {
		Value v = parse("{}");
		assertThat(v, is(ul()));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_UList_1() throws Exception {
		Value v = parse("{'HELLO'}");
		assertThat(v, is(ul("HELLO")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_UList_3() throws Exception {
		Value v = parse("{'A', 'B', 'C'}");
		assertThat(v, is(ul("A", "B", "C")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Rec_0() throws Exception {
		Value v = parse("<>");
		assertThat(v, is(rc()));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Rec_1() throws Exception {
		Value v = parse("<'a':'A'>");
		assertThat(v, is(rc("a", "A")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Rec_3() throws Exception {
		Value v = parse("<'a':'A', 'b':'B', 'c':'C'>");
		assertThat(v, is(rc("a", "A", "b", "B", "c", "C")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_OListMixed() throws Exception {
		Value v = parse("[" + "  'A'," + "  ['B', 'C']," + "  {'D', 'E'}," + "  <'f':'G'>" + "]");
		assertThat(v, is(ol("A", ol("B", "C"), ul("D", "E"), rc("f", "G"))));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_UListMixed() throws Exception {
		Value v = parse("{" + "  'A'," + "  ['B', 'C']," + "  {'D', 'E'}," + "  <'f':'G'>" + "}");
		assertThat(v, is(ul("A", ol("B", "C"), ul("D", "E"), rc("f", "G"))));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_RecMixed() throws Exception {
		Value v = parse("<" + "  'tr':'A'," + "  'ol':['B', 'C']," + "  'ul':{'D', 'E'}," + "  'rc':<'f':'G'>" + ">");
		assertThat(v, is(rc("tr", "A", "ol", ol("B", "C"), "ul", ul("D", "E"), "rc", rc("f", "G"))));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader, java.util.Map)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Variable() throws Exception {
		Map<String, Value> vars = new HashMap<String, Value>();
		vars.put("a", value("Hello"));
		Value v = parse("$a", vars);
		assertThat(v, is(terminal("Hello")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader, java.util.Map)}.
	 * @throws Exception if occur
	 */
	@Test
	public void testParse_Variables() throws Exception {
		Map<String, Value> vars = new HashMap<String, Value>();
		vars.put("a", value("A"));
		vars.put("b", value("B"));
		vars.put("c", value("C"));
		Value v = parse("<'a':$a, 'b':$b, 'c':$c>", vars);
		assertThat(v, is(rc("a", "A", "b", "B", "c", "C")));
	}
	
	/**
	 * Test method for {@link Parser#parse(java.io.Reader, java.util.Map)}.
	 * @throws Exception if occur
	 */
	@Test(expected = IOException.class)
	public void testParse_Variables_Unbound() throws Exception {
		Map<String, Value> vars = new HashMap<String, Value>();
		vars.put("a", value("A"));
		vars.put("b", value("B"));
		vars.put("c", value("C"));
		parse("<'a':$a, 'b':$b, 'c':$c, 'd':$d>", vars);
	}
	
	private Value terminal(Object content) {
		return Terminal.of(String.valueOf(content));
	}
	
	private Value ol(Object... contents) {
		List<Value> list = new ArrayList<Value>();
		for (Object o : contents) {
			list.add(value(o));
		}
		return Sequence.ordered(list);
	}
	
	private Value ul(Object... contents) {
		List<Value> list = new ArrayList<Value>();
		for (Object o : contents) {
			list.add(value(o));
		}
		return Sequence.unordered(list);
	}
	
	private Value rc(Object... nameAndValues) {
		assert nameAndValues.length % 2 == 0;
		ArrayList<Entry> results = new ArrayList<Entry>();
		for (int i = 0; i < nameAndValues.length; i += 2) {
			Value key = value(nameAndValues[i]);
			Value value = value(nameAndValues[i + 1]);
			results.add(Entry.of(key, value));
		}
		return Record.of(results);
	}
	
	private Value value(Object contentOrValue) {
		if (contentOrValue instanceof Value) {
			return (Value) contentOrValue;
		} else {
			return terminal(contentOrValue);
		}
	}
	
	private Value parse(String script) throws IOException {
		return Parser.parse(new StringReader(script));
	}
	
	private Value parse(String script, Map<String, ? extends Value> vars) throws IOException {
		return Parser.parse(new StringReader(script), vars);
	}
}

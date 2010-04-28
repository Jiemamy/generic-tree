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

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link Emitter}
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class EmitterTest {
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_Terminal() {
		assertRestore(terminal("HELLO"));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_OList_0() {
		assertRestore(ol());
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_OList_1() {
		assertRestore(ol("Hello!, world!"));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_OList_5() {
		assertRestore(ol(1, 5, 3, 2, 4));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_UList_0() {
		assertRestore(ul());
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_UList_1() {
		assertRestore(ul("Hello!, world!"));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_UList_5() {
		assertRestore(ul(1, 5, 3, 2, 4));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_Record_0() {
		assertRestore(rc());
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_Record_1() {
		assertRestore(rc("a", "A"));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_Record_5() {
		assertRestore(rc("a", "A", "c", "C", "b", "B", "d", "D", "e", "E"));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_OListMixed() {
		assertRestore(ol("A", ol("B", "C"), ul("D", "E"), rc("f", "G")));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_UListMixed() {
		assertRestore(ul("A", ol("B", "C"), ul("D", "E"), rc("f", "G")));
	}
	
	/**
	 * Test method for {@link Emitter#emit(Value, PrintWriter)}.
	 */
	@Test
	public void testEmit_RecMixed() {
		assertRestore(rc("tr", "A", "ol", ol("B", "C"), "ul", ul("D", "E"), "rc", rc("f", "G")));
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
	
	private Value restore(Value value) {
		return parse(emit(value));
	}
	
	private String emit(Value value) {
		StringWriter out = new StringWriter();
		PrintWriter pw = new PrintWriter(out);
		Emitter.emit(value, pw);
		pw.close();
		String script = out.toString();
		return script;
	}
	
	private void assertRestore(Value value) {
		assertThat(restore(value), equalTo(value));
	}
	
	private Value parse(String script) {
		try {
			return Parser.parse(new StringReader(script));
		} catch (IOException e) {
			throw (AssertionError) new AssertionError(script).initCause(e);
		}
	}
}

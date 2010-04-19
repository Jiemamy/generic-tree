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
package org.jiemamy.utils.gtree.model;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test for {@link Entry}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class EntryTest {
	
	/**
	 * Test method for {@link Entry#of(Value, Value)}.
	 */
	@Test
	public void testOf() {
		Entry entry = Entry.of(Terminal.of("hello"), Terminal.of("world"));
		assertThat(entry.getKey(), is((Object) Terminal.of("hello")));
		assertThat(entry.getValue(), is((Object) Terminal.of("world")));
	}
	
	/**
	 * Test method for {@link Entry#of(Value, Value)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testOf_NullName() {
		Entry.of(null, Terminal.of("world"));
	}
	
	/**
	 * Test method for {@link Entry#of(Value, Value)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testOf_NullValue() {
		Entry.of(Terminal.of("hello"), null);
	}
	
	/**
	 * Test method for {@link Entry#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		Entry e1 = Entry.of(Terminal.of("a"), Terminal.of("b"));
		Entry e2 = Entry.of(Terminal.of("a"), Terminal.of("b"));
		Entry e3 = Entry.of(Terminal.of("A"), Terminal.of("b"));
		Entry e4 = Entry.of(Terminal.of("a"), Terminal.of("B"));
		
		assertThat(e1.equals(e1), is(true));
		assertThat(e1.equals(e2), is(true));
		assertThat(e1.equals(e3), is(false));
		assertThat(e1.equals(e4), is(false));
		assertThat(e1.equals("a:b"), is(false));
		assertThat(e1.equals(null), is(false));
	}
	
	/**
	 * Test method for {@link Entry#compareTo(Entry)}.
	 */
	@Test
	public void testCompareTo() {
		Entry e1 = Entry.of(Terminal.of("a"), Terminal.of("a"));
		Entry e2 = Entry.of(Terminal.of("0"), Terminal.of("a"));
		Entry e3 = Entry.of(Terminal.of("b"), Terminal.of("a"));
		Entry e4 = Entry.of(Terminal.of("a"), Terminal.of("0"));
		Entry e5 = Entry.of(Terminal.of("a"), Terminal.of("b"));
		Entry e6 = Entry.of(Terminal.of("0"), Terminal.of("b"));
		assertThat(e1.compareTo(e1), is(0));
		assertThat(e1.compareTo(e2), greaterThan(0));
		assertThat(e1.compareTo(e3), lessThan(0));
		assertThat(e1.compareTo(e4), greaterThan(0));
		assertThat(e1.compareTo(e5), lessThan(0));
		assertThat(e1.compareTo(e6), greaterThan(0));
	}
	
	/**
	 * Test method for {@link Entry#accept(ElementVisitor, java.lang.Object)}.
	 */
	@Test
	public void testAccept() {
		Entry e = Entry.of(Terminal.of("a"), Terminal.of("b"));
		ElementVisitor<String, Void, RuntimeException> visitor = new ElementVisitor<String, Void, RuntimeException>() {
			
			@Override
			protected String visitEntry(Entry elem, Void context) {
				return elem.getKey().toString() + elem.getValue();
			}
		};
		assertThat(e.accept(visitor, null), is("ab"));
	}
}

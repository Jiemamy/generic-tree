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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Value.Kind;

/**
 * Test for {@link Record}.
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class RecordTest {
	
	/**
	 * Test method for {@link Record#of(java.util.List)}.
	 */
	@Test
	public void testOf() {
		Record r = Record.of(es("a", "A", "b", "B"));
		assertThat(r.getEntries(), is(es("a", "A", "b", "B")));
	}
	
	/**
	 * Test method for {@link Record#of(java.util.List)}.
	 */
	@Test
	public void testOf_Sorted() {
		Record r = Record.of(es("a", "A", "b", "B", "0", "0", "0", "0", "a", "0", "0", "a"));
		assertThat(r.getEntries(), is(es("0", "0", "0", "0", "0", "a", "a", "0", "a", "A", "b", "B")));
	}
	
	/**
	 * Test method for {@link Record#of(java.util.List)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testOf_Null() {
		Record.of(null);
	}
	
	/**
	 * Test method for {@link Record#getKind()}.
	 */
	@Test
	public void testGetKind() {
		Record r = Record.of(es("a", "A", "b", "B"));
		assertThat(r.getKind(), is(Kind.RECORD));
	}
	
	/**
	 * Test method for {@link Record#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		Record r1 = Record.of(es("a", "A", "b", "B"));
		Record r2 = Record.of(es("a", "A", "b", "B"));
		Record r3 = Record.of(es("a", "A"));
		Record r4 = Record.of(es("a", "A", "b", "B", "c", "C"));
		Record r5 = Record.of(es("x", "A", "b", "B"));
		Record r6 = Record.of(es("a", "A", "b", "X"));
		Record r7 = Record.of(es("b", "B", "a", "A"));
		assertThat(r1.equals(r1), is(true));
		assertThat(r1.equals(r2), is(true));
		assertThat(r1.equals(r3), is(false));
		assertThat(r1.equals(r4), is(false));
		assertThat(r1.equals(r5), is(false));
		assertThat(r1.equals(r6), is(false));
		assertThat(r1.equals(r7), is(true));
	}
	
	/**
	 * Test method for {@link Record#compareTo(Value)}.
	 */
	@Test
	public void testCompareTo() {
		Record r1 = Record.of(es("a", "A", "c", "C"));
		Record r2 = Record.of(es("a", "A", "c", "C"));
		Record r3 = Record.of(es("a", "A"));
		Record r4 = Record.of(es("a", "A", "c", "C", "e", "E"));
		Record r5 = Record.of(es("0", "A", "c", "C"));
		Record r6 = Record.of(es("a", "A", "b", "C"));
		Record r7 = Record.of(es("b", "A", "c", "C"));
		Record r8 = Record.of(es("a", "A", "d", "C"));
		Record r9 = Record.of(es("c", "C", "a", "A"));
		
		Terminal v1 = Terminal.of("");
		Sequence v2 = Sequence.unordered(Arrays.<Value> asList());
		Sequence v3 = Sequence.ordered(Arrays.<Value> asList());
		
		// test is not a record
		assertThat(r1.compareTo(v1), greaterThan(0));
		assertThat(r1.compareTo(v2), greaterThan(0));
		assertThat(r1.compareTo(v3), greaterThan(0));
		
		// entries
		assertThat(r1.compareTo(r1), is(0));
		assertThat(r1.compareTo(r2), is(0));
		assertThat(r1.compareTo(r3), greaterThan(0));
		assertThat(r1.compareTo(r4), lessThan(0));
		assertThat(r1.compareTo(r5), greaterThan(0));
		assertThat(r1.compareTo(r6), greaterThan(0));
		assertThat(r1.compareTo(r7), lessThan(0));
		assertThat(r1.compareTo(r8), lessThan(0));
		assertThat(r1.compareTo(r9), is(0));
	}
	
	/**
	 * Test method for {@link Record#accept(ElementVisitor, java.lang.Object)}.
	 */
	@Test
	public void testAccept() {
		Record r1 = Record.of(es("a", "A"));
		ElementVisitor<String, Void, RuntimeException> visitor = new ElementVisitor<String, Void, RuntimeException>() {
			
			@Override
			protected String visitRecord(Record elem, Void context) {
				return elem.getEntries().get(0).getKey().toString();
			}
		};
		assertThat(r1.accept(visitor, null), is("a"));
	}
	
	private static List<Entry> es(String... pairs) {
		assert pairs.length % 2 == 0;
		ArrayList<Entry> results = new ArrayList<Entry>();
		for (int i = 0; i < pairs.length; i += 2) {
			Terminal key = Terminal.of(pairs[i]);
			Terminal value = Terminal.of(pairs[i + 1]);
			results.add(Entry.of(key, value));
		}
		return results;
	}
}

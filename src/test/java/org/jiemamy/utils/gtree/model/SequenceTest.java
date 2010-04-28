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

/**
 * Test for {@link Sequence}.
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class SequenceTest {
	
	/**
	 * Test method for {@link Sequence#ordered(java.util.List)}.
	 */
	@Test
	public void testOrdered() {
		Sequence s = Sequence.ordered(ls("1", "2", "3", "1", "2"));
		assertThat(s.getValues(), is(ls("1", "2", "3", "1", "2")));
		assertThat(s.getKind(), is(Value.Kind.ORDERED_LIST));
	}
	
	/**
	 * Test method for {@link Sequence#ordered(java.util.List)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testOrdered_Null() {
		Sequence.ordered(null);
	}
	
	/**
	 * Test method for {@link Sequence#unordered(java.util.List)}.
	 */
	@Test
	public void testUnordered() {
		Sequence s = Sequence.unordered(ls("1", "2", "3", "1", "2"));
		assertThat(s.getValues(), is(ls("1", "1", "2", "2", "3")));
		assertThat(s.getKind(), is(Value.Kind.UNORDERED_LIST));
	}
	
	/**
	 * Test method for {@link Sequence#unordered(java.util.List)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testUnordered_Null() {
		Sequence.unordered(null);
	}
	
	/**
	 * Test method for {@link Sequence#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals_Ordered() {
		Sequence s1 = Sequence.ordered(ls("1", "2"));
		Sequence s2 = Sequence.ordered(ls("1", "2"));
		Sequence s3 = Sequence.ordered(ls("2", "1"));
		Sequence s4 = Sequence.ordered(ls("1", "3"));
		Sequence s5 = Sequence.unordered(ls("1", "2"));
		
		assertThat(s1.equals(s1), is(true));
		assertThat(s1.equals(s2), is(true));
		assertThat(s1.equals(s3), is(false));
		assertThat(s1.equals(s4), is(false));
		assertThat(s1.equals(s5), is(false));
		assertThat(s1.equals(ls("1", "2")), is(false));
		assertThat(s1.equals(null), is(false));
	}
	
	/**
	 * Test method for {@link Sequence#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals_Unordered() {
		Sequence s1 = Sequence.unordered(ls("1", "2"));
		Sequence s2 = Sequence.unordered(ls("1", "2"));
		Sequence s3 = Sequence.unordered(ls("2", "1"));
		Sequence s4 = Sequence.unordered(ls("1", "3"));
		Sequence s5 = Sequence.ordered(ls("1", "2"));
		
		assertThat(s1.equals(s1), is(true));
		assertThat(s1.equals(s2), is(true));
		assertThat(s1.equals(s3), is(true));
		assertThat(s1.equals(s4), is(false));
		assertThat(s1.equals(s5), is(false));
		assertThat(s1.equals(ls("1", "2")), is(false));
		assertThat(s1.equals(null), is(false));
	}
	
	/**
	 * Test method for {@link Sequence#compareTo(Value)}.
	 */
	@Test
	public void testCompareTo_Ordered() {
		Sequence s1 = Sequence.ordered(ls("1", "2"));
		Sequence s2 = Sequence.ordered(ls("1", "2"));
		Sequence s3 = Sequence.ordered(ls("1"));
		Sequence s4 = Sequence.ordered(ls("1", "2", "3"));
		Sequence s5 = Sequence.ordered(ls("1", "3"));
		Sequence s6 = Sequence.ordered(ls("1", "1"));
		Sequence s7 = Sequence.ordered(ls("0", "3"));
		Sequence s8 = Sequence.ordered(ls("2", "1"));
		Terminal v1 = Terminal.of("12");
		Sequence v2 = Sequence.unordered(ls("1", "2"));
		Record v3 = Record.of(Arrays.<Entry> asList());
		
		// test is not an ordered list
		assertThat(s1.compareTo(v1), greaterThan(0));
		assertThat(s1.compareTo(v2), greaterThan(0));
		assertThat(s1.compareTo(v3), lessThan(0));
		
		// entity
		assertThat(s1.compareTo(s1), is(0));
		assertThat(s1.compareTo(s2), is(0));
		assertThat(s1.compareTo(s3), greaterThan(0));
		assertThat(s1.compareTo(s4), lessThan(0));
		assertThat(s1.compareTo(s5), lessThan(0));
		assertThat(s1.compareTo(s6), greaterThan(0));
		assertThat(s1.compareTo(s7), greaterThan(0));
		assertThat(s1.compareTo(s8), lessThan(0));
	}
	
	/**
	 * Test method for {@link Sequence#compareTo(Value)}.
	 */
	@Test
	public void testCompareTo_Unordered() {
		Sequence s1 = Sequence.unordered(ls("1", "2"));
		Sequence s2 = Sequence.unordered(ls("1", "2"));
		Sequence s3 = Sequence.unordered(ls("1"));
		Sequence s4 = Sequence.unordered(ls("1", "2", "3"));
		Sequence s5 = Sequence.unordered(ls("1", "3"));
		Sequence s6 = Sequence.unordered(ls("1", "1"));
		Sequence s7 = Sequence.unordered(ls("0", "3"));
		Sequence s8 = Sequence.unordered(ls("2", "1"));
		Terminal v1 = Terminal.of("12");
		Sequence v2 = Sequence.ordered(ls("1", "2"));
		Record v3 = Record.of(Arrays.<Entry> asList());
		
		// test is not an unordered list
		assertThat(s1.compareTo(v1), greaterThan(0));
		assertThat(s1.compareTo(v2), lessThan(0));
		assertThat(s1.compareTo(v3), lessThan(0));
		
		// entity
		assertThat(s1.compareTo(s1), is(0));
		assertThat(s1.compareTo(s2), is(0));
		assertThat(s1.compareTo(s3), greaterThan(0));
		assertThat(s1.compareTo(s4), lessThan(0));
		assertThat(s1.compareTo(s5), lessThan(0));
		assertThat(s1.compareTo(s6), greaterThan(0));
		assertThat(s1.compareTo(s7), greaterThan(0));
		assertThat(s1.compareTo(s8), is(0));
	}
	
	/**
	 * Test method for {@link Sequence#accept(ElementVisitor, java.lang.Object)}.
	 */
	@Test
	public void testAccept() {
		Sequence s = Sequence.ordered(ls("value"));
		ElementVisitor<String, Void, RuntimeException> visitor = new ElementVisitor<String, Void, RuntimeException>() {
			
			@Override
			protected String visitSequence(Sequence elem, Void context) {
				Value v = elem.getValues().get(0);
				return ((Terminal) v).getRepresentation();
			}
		};
		assertThat(s.accept(visitor, null), is("value"));
	}
	
	private List<Value> ls(String... ss) {
		List<Value> list = new ArrayList<Value>();
		for (String s : ss) {
			list.add(Terminal.of(s));
		}
		return list;
	}
}

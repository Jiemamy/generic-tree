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

import java.util.Arrays;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Value.Kind;

/**
 * Test for {@link Terminal}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class TerminalTest {
	
	/**
	 * Test method for {@link Terminal#of(java.lang.String)}.
	 */
	@Test
	public void testOf() {
		Terminal hello = Terminal.of("hello");
		assertThat(hello.getRepresentation(), is("hello"));
	}
	
	/**
	 * Test method for {@link Terminal#of(java.lang.String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testOf_Null() {
		Terminal.of(null);
	}
	
	/**
	 * Test method for {@link Terminal#getKind()}.
	 */
	@Test
	public void testGetKind() {
		Terminal v = Terminal.of("a");
		assertThat(v.getKind(), is(Kind.TERMINAL));
	}
	
	/**
	 * Test method for {@link Terminal#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		Terminal v1 = Terminal.of("a");
		Terminal v2 = Terminal.of("a");
		Terminal v3 = Terminal.of("b");
		assertThat(v1.equals(v1), is(true));
		assertThat(v1.equals(v2), is(true));
		assertThat(v1.equals(v3), is(false));
		assertThat(v1.equals("hello"), is(false));
		assertThat(v1.equals("a"), is(false));
		assertThat(v1.equals(null), is(false));
	}
	
	/**
	 * Test method for {@link Terminal#compareTo(Value)}.
	 */
	@Test
	public void testCompareTo() {
		Terminal v1 = Terminal.of("a");
		Terminal v2 = Terminal.of("a");
		Terminal v3 = Terminal.of("0");
		Terminal v4 = Terminal.of("b");
		Sequence e1 = Sequence.ordered(Arrays.<Value> asList());
		Sequence e2 = Sequence.unordered(Arrays.<Value> asList());
		Record e3 = Record.of(Arrays.<Entry> asList());
		
		// that is not a Terminal
		assertThat(v1.compareTo(e1), lessThan(0));
		assertThat(v1.compareTo(e2), lessThan(0));
		assertThat(v1.compareTo(e3), lessThan(0));
		
		// representation
		assertThat(v1.compareTo(v1), is(0));
		assertThat(v1.compareTo(v2), is(0));
		assertThat(v1.compareTo(v3), greaterThan(0));
		assertThat(v1.compareTo(v4), lessThan(0));
	}
	
	/**
	 * Test method for {@link Terminal#accept(ElementVisitor, java.lang.Object)}.
	 */
	@Test
	public void testAccept() {
		Terminal v = Terminal.of("a");
		ElementVisitor<String, Void, RuntimeException> visitor = new ElementVisitor<String, Void, RuntimeException>() {
			
			@Override
			protected String visitTerminal(Terminal elem, Void context) {
				return elem.getRepresentation();
			}
		};
		assertThat(v.accept(visitor, null), is("a"));
	}
}

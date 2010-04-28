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
package org.jiemamy.utils.gtree.rewrite;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link Rewriter}.
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class RewriterTest {
	
	private RewriteRule insert = new RewriteRule() {
		
		@Override
		protected Value rewriteTerminal(Terminal value) {
			return Terminal.of("*" + value.getRepresentation());
		}
	};
	
	private RewriteRule ol2ul = new RewriteRule() {
		
		@Override
		protected Value rewriteOrderedList(Sequence value) {
			return Sequence.unordered(value.getValues());
		}
	};
	
	private RewriteRule removeUl = new RewriteRule() {
		
		@Override
		protected Value rewriteUnorderedList(Sequence value) {
			return null;
		}
	};
	
	private RewriteRule removeQ = new RewriteRule() {
		
		@Override
		protected Value rewriteTerminal(Terminal value) {
			if (value.getRepresentation().equals("?")) {
				return null;
			} else {
				return value;
			}
		}
	};
	

	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_Keep() {
		Rewriter rewriter = new Rewriter(r(insert));
		Value result =
				rewriter.apply(rc("t", "v", "o", ol("a", "b", ol("x")), "u", ul("c", "d", ol("y")), "r", rc("e", "f")),
						true);
		assertThat(result, is(rc("t", "*v", "o", ol("*a", "*b", ol("*x")), "u", ul("*c", "*d", ol("*y")), "r", rc("e",
				"*f"))));
	}
	
	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_All() {
		Rewriter rewriter = new Rewriter(r(insert));
		Value result =
				rewriter.apply(rc("t", "v", "o", ol("a", "b", ol("x")), "u", ul("c", "d", ol("y")), "r", rc("e", "f")),
						false);
		assertThat(result, is(rc("*t", "*v", "*o", ol("*a", "*b", ol("*x")), "*u", ul("*c", "*d", ol("*y")), "*r", rc(
				"*e", "*f"))));
	}
	
	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_Many() {
		Rewriter rewriter = new Rewriter(r(ol2ul, insert));
		Value result =
				rewriter.apply(rc("t", "v", "o", ol("a", "b", ol("x")), "u", ul("c", "d", ol("y")), "r", rc("e", "f")),
						true);
		assertThat(result, is(rc("t", "*v", "o", ul("*a", "*b", ul("*x")), "u", ul("*c", "*d", ul("*y")), "r", rc("e",
				"*f"))));
	}
	
	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_RemoveAndConvert() {
		Rewriter rewriter = new Rewriter(r(removeUl, ol2ul));
		Value result =
				rewriter.apply(rc("t", "v", "o", ol("a", "b", ol("x")), "u", ul("c", "d", ol("y")), "r", rc("e", "f")),
						true);
		assertThat(result, is(rc("t", "v", "o", ul("a", "b", ul("x")), "r", rc("e", "f"))));
	}
	
	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_RemoveKey() {
		Rewriter rewriter = new Rewriter(r(removeQ));
		Value result = rewriter.apply(rc("a", "a", "b", "?", "?", "c"), false);
		assertThat(result, is(rc("a", "a")));
	}
	
	/**
	 * Test method for {@link Rewriter#apply(Value, boolean)}.
	 */
	@Test
	public void testApply_ConvertAndRemove() {
		Rewriter rewriter = new Rewriter(r(ol2ul, removeUl));
		Value result =
				rewriter.apply(rc("t", "v", "o", ol("a", "b", ol("x")), "u", ul("c", "d", ol("y")), "r", rc("e", "f")),
						true);
		assertThat(result, is(rc("t", "v", "r", rc("e", "f"))));
	}
	
	private List<RewriteRule> r(RewriteRule... rules) {
		return Arrays.asList(rules);
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
			return Terminal.of(String.valueOf(contentOrValue));
		}
	}
}

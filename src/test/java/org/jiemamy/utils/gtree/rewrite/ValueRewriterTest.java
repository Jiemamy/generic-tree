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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * Test for {@link ValueRewriter}.
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class ValueRewriterTest {
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Terminal_Once() {
		ValueRewriter rewriter = new ValueRewriter(r(new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Terminal.of("*" + value.getRepresentation());
			}
		}));
		Value result = rewriter.rewrite(value("Hello"));
		assertThat(result, is(value("*Hello")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_OList_Once() {
		ValueRewriter rewriter = new ValueRewriter(r(new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Terminal.of("*" + value.getRepresentation());
			}
			
			@Override
			protected Value rewriteOrderedList(Sequence value) {
				List<Value> modified = new ArrayList<Value>();
				modified.addAll(value.getValues());
				modified.add(Terminal.of("*"));
				return Sequence.ordered(modified);
			}
		}));
		Value result = rewriter.rewrite(ol("Hello", "World"));
		assertThat(result, is(ol("Hello", "World", "*")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_UList_Once() {
		ValueRewriter rewriter = new ValueRewriter(r(new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Terminal.of("*" + value.getRepresentation());
			}
			
			@Override
			protected Value rewriteUnorderedList(Sequence value) {
				List<Value> modified = new ArrayList<Value>();
				modified.addAll(value.getValues());
				modified.add(Terminal.of("*"));
				return Sequence.unordered(modified);
			}
		}));
		Value result = rewriter.rewrite(ul("Hello", "World"));
		assertThat(result, is(ul("Hello", "World", "*")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Record_Once() {
		ValueRewriter rewriter = new ValueRewriter(r(new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Terminal.of("*" + value.getRepresentation());
			}
			
			@Override
			protected Value rewriteRecord(Record value) {
				List<Entry> modified = new ArrayList<Entry>();
				modified.addAll(value.getEntries());
				modified.add(Entry.of(Terminal.of("+"), Terminal.of("-")));
				return Record.of(modified);
			}
		}));
		Value result = rewriter.rewrite(rc("a", "b", "c", "d"));
		assertThat(result, is(rc("a", "b", "c", "d", "+", "-")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Terminal_Three() {
		final AtomicInteger counter = new AtomicInteger();
		RewriteRule insert = new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Terminal.of(value.getRepresentation() + counter.incrementAndGet());
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(insert, insert, insert));
		Value result = rewriter.rewrite(value("Hello"));
		assertThat(result, is(value("Hello123")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_OList_Three() {
		final AtomicInteger counter = new AtomicInteger();
		RewriteRule insert = new RewriteRule() {
			
			@Override
			protected Value rewriteOrderedList(Sequence value) {
				List<Value> modified = new ArrayList<Value>();
				modified.addAll(value.getValues());
				modified.add(Terminal.of(String.valueOf(counter.incrementAndGet())));
				return Sequence.ordered(modified);
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(insert, insert, insert));
		Value result = rewriter.rewrite(ol("Hello"));
		assertThat(result, is(ol("Hello", "1", "2", "3")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_UList_Three() {
		final AtomicInteger counter = new AtomicInteger();
		RewriteRule insert = new RewriteRule() {
			
			@Override
			protected Value rewriteUnorderedList(Sequence value) {
				List<Value> modified = new ArrayList<Value>();
				modified.addAll(value.getValues());
				modified.add(Terminal.of(String.valueOf(counter.incrementAndGet())));
				return Sequence.unordered(modified);
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(insert, insert, insert));
		Value result = rewriter.rewrite(ul("Hello"));
		assertThat(result, is(ul("Hello", "1", "2", "3")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Record_Three() {
		final AtomicInteger counter = new AtomicInteger();
		RewriteRule insert = new RewriteRule() {
			
			@Override
			protected Value rewriteRecord(Record value) {
				List<Entry> modified = new ArrayList<Entry>();
				modified.addAll(value.getEntries());
				String key = String.valueOf(counter.incrementAndGet());
				String val = String.valueOf(counter.incrementAndGet());
				modified.add(Entry.of(Terminal.of(key), Terminal.of(val)));
				return Record.of(modified);
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(insert, insert, insert));
		Value result = rewriter.rewrite(rc("a", "b"));
		assertThat(result, is(rc("a", "b", "1", "2", "3", "4", "5", "6")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Around() {
		RewriteRule around = new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Sequence.ordered(Collections.singletonList(value));
			}
			
			@Override
			protected Value rewriteOrderedList(Sequence value) {
				return Sequence.unordered(value.getValues());
			}
			
			@Override
			protected Value rewriteUnorderedList(Sequence value) {
				Value e = value.getValues().get(0);
				return Record.of(Collections.singletonList(Entry.of(e, e)));
			}
			
			@Override
			protected Value rewriteRecord(Record value) {
				Entry e = value.getEntries().get(0);
				return Terminal.of(e.getKey().toString() + e.getValue());
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(around, // 'a' -> ['a']
				around, // ['a'] -> {'a'}
				around, // {'a'} -> <'a':'a'>
				around // <'a':'a'> -> 'aa'
				));
		Value result = rewriter.rewrite(value("Hello"));
		assertThat(result, is(value("HelloHello")));
	}
	
	/**
	 * Test method for {@link ValueRewriter#rewrite(org.jiemamy.utils.gtree.model.Value)}.
	 */
	@Test
	public void testRewrite_Abort() {
		RewriteRule around = new RewriteRule() {
			
			@Override
			protected Value rewriteTerminal(Terminal value) {
				return Sequence.ordered(Collections.singletonList(value));
			}
			
			@Override
			protected Value rewriteOrderedList(Sequence value) {
				return Sequence.unordered(value.getValues());
			}
			
			@Override
			protected Value rewriteUnorderedList(Sequence value) {
				return null;
			}
			
			@Override
			protected Value rewriteRecord(Record value) {
				Entry e = value.getEntries().get(0);
				return Terminal.of(e.getKey().toString() + e.getValue());
			}
		};
		ValueRewriter rewriter = new ValueRewriter(r(around, // 'a' -> ['a']
				around, // ['a'] -> {'a'}
				around, // {'a'} -> null
				around // <'a':'a'> -> 'aa'
				));
		Value result = rewriter.rewrite(value("Hello"));
		assertThat(result, is(nullValue()));
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

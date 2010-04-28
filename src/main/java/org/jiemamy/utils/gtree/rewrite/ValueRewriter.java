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

import java.util.List;

import org.jiemamy.utils.gtree.model.ElementVisitor;
import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.model.Value.Kind;

/**
 * 単一の{@link Value}オブジェクトを変換する。
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
class ValueRewriter {
	
	/**
	 * 単一の値を変換する戦略オブジェクト。
	 */
	private static final SingleRewriter ENGINE = new SingleRewriter();
	
	/**
	 * 適用するルールの一覧。
	 */
	private final List<? extends RewriteRule> rules;
	

	/**
	 * インスタンスを生成する。
	 * @param ruleList 適用するルールの一覧
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public ValueRewriter(List<? extends RewriteRule> ruleList) {
		super();
		if (ruleList == null) {
			throw new NullPointerException("ruleList"); //$NON-NLS-1$
		}
		this.rules = ruleList;
	}
	
	/**
	 * 指定の値を変換する。
	 * @param value 変換する値
	 * @return 変換結果、削除する場合は{@code null}
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public Value rewrite(Value value) {
		if (value == null) {
			throw new NullPointerException("value"); //$NON-NLS-1$
		}
		Value current = value;
		for (RewriteRule rule : rules) {
			current = current.accept(ENGINE, rule);
			if (current == null) {
				return null;
			}
		}
		return current;
	}
	

	private static class SingleRewriter extends ElementVisitor<Value, RewriteRule, RuntimeException> {
		
		/**
		 * インスタンスを生成する。
		 */
		SingleRewriter() {
			super();
		}
		
		@Override
		protected Value visitTerminal(Terminal elem, RewriteRule context) {
			assert elem != null;
			return context.rewriteTerminal(elem);
		}
		
		@Override
		protected Value visitSequence(Sequence elem, RewriteRule context) {
			assert elem != null;
			Kind kind = elem.getKind();
			if (kind == Value.Kind.ORDERED_LIST) {
				return context.rewriteOrderedList(elem);
			} else if (kind == Value.Kind.UNORDERED_LIST) {
				return context.rewriteUnorderedList(elem);
			} else {
				throw new AssertionError(elem);
			}
		}
		
		@Override
		protected Value visitRecord(Record elem, RewriteRule context) {
			assert elem != null;
			return context.rewriteRecord(elem);
		}
		
		@Override
		protected Value visitEntry(Entry elem, RewriteRule context) {
			throw new AssertionError(elem);
		}
	}
}

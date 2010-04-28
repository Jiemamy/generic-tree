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

import java.util.ArrayList;
import java.util.List;

import org.jiemamy.utils.gtree.model.ElementVisitor;
import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link RewriteRule}の一覧を元に{@link Value}が表現するツリー全体を書き換える。
 * <p>
 * 複数の変換ルールが一度に指定された場合、
 * 対象の値をまず最初に指定されたルールによって変換し、その結果をさらに次のルールによって変換する。
 * 指定された変換ルールを順に適用していくことによって、最終的な結果を作成する。
 * ただし、値が削除されるような変換ルールがこの過程に存在する場合、
 * 以降のルール適用を行わずに直ちにその値を削除する。
 * </p>
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Rewriter {
	
	private static final RewriteVisitor KEEP_KEY = new RewriteVisitor(true);
	
	private static final RewriteVisitor ALL = new RewriteVisitor(false);
	
	private final ValueRewriter valueRewriter;
	

	/**
	 * インスタンスを生成する。
	 * @param ruleList 適用するルールの一覧
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public Rewriter(List<? extends RewriteRule> ruleList) {
		if (ruleList == null) {
			throw new NullPointerException("ruleList"); //$NON-NLS-1$
		}
		this.valueRewriter = new ValueRewriter(ruleList);
	}
	
	/**
	 * 指定の値を変換する。
	 * <p>
	 * これは値の種類ごとに、次のように変換規則が適用される。
	 * </p>
	 * <dl>
	 *   <dt>
	 *     {@link Terminal}
	 *   </dt>
	 *   <dd>
	 *     対象の値に対して直ちに変換規則が適用され、変換結果の値を利用する。
	 *   </dd>
	 *   <dt>
	 *     {@link Sequence}
	 *   </dt>
	 *   <dd>
	 *     まず、それぞれの子要素に対して変換規則が適用され、適用結果を子要素とする
	 *     {@link Sequence}を再構築する。
	 *     このとき、{@link Sequence}が順序付きリストを表現する場合、
	 *     それぞれの子要素の順序は再構築後も保持される。
	 *     ただし、子要素の変換結果が{@code null}となるようなものは、
	 *     再構築される{@link Sequence}からは除去される。
	 *     その後、再構築した{@link Sequence}に対してさらに変換規則が適用され、
	 *     その変換結果の値を対象の値の変換結果として利用する。
	 *   </dd>
	 *   <dt>
	 *     {@link Record}
	 *   </dt>
	 *   <dd>
	 *     まず、それぞれの子要素である{@link Entry}の{@link Entry#getKey() キー}に対して
	 *     変換規則を適用する。この結果が{@code null}でない場合、次に
	 *     同{@link Entry#getValue() 値}に対して変換規則を適用する。
	 *     いずれの結果も{@code null}でない場合、変換規則のキーと値からなる{@link Entry}を再構築し、
	 *     さらにそれらを子要素に持つような{@link Record}を作成する。
	 *     いずれかの変換結果が{@code null}となるような{@link Entry}は、
	 *     再構築する{@link Record}からは除去される。
	 *     その後、再構築した{@link Record}に対してさらに変換規則が適用され、
	 *     その変換結果の値を対象の値の変換結果として利用する。
	 *   </dd>
	 * </dl>
	 * @param value 変換する値
	 * @param keepEntryKey
	 *     {@code true}が指定された場合は{@link Entry#getKey()}の値に対して
	 *     変換規則を適用しない、
	 *     {@code false}が指定された場合は{@link Entry#getKey()}の値に対しても
	 *     変換規則を適用する
	 * @return
	 *     変換結果、
	 *     引数に指定された値自体が削除される場合は{@code null}
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public Value apply(Value value, boolean keepEntryKey) {
		if (value == null) {
			throw new NullPointerException("value"); //$NON-NLS-1$
		}
		RewriteVisitor engine = keepEntryKey ? KEEP_KEY : ALL;
		return value.accept(engine, valueRewriter);
	}
	

	private static class RewriteVisitor extends ElementVisitor<Value, ValueRewriter, RuntimeException> {
		
		private boolean keepEntryKey;
		

		/**
		 * インスタンスを生成する。
		 * @param keepEntryKey {@link Entry#getKey()}を保持するかどうか
		 */
		RewriteVisitor(boolean keepEntryKey) {
			super();
			this.keepEntryKey = keepEntryKey;
		}
		
		@Override
		protected Value visitTerminal(Terminal elem, ValueRewriter context) {
			assert elem != null;
			assert context != null;
			return context.rewrite(elem);
		}
		
		@Override
		protected Value visitSequence(Sequence elem, ValueRewriter context) {
			assert elem != null;
			assert context != null;
			List<Value> results = new ArrayList<Value>();
			for (Value target : elem.getValues()) {
				Value applied = target.accept(this, context);
				if (applied != null) {
					results.add(applied);
				}
			}
			if (elem.getKind() == Value.Kind.ORDERED_LIST) {
				return context.rewrite(Sequence.ordered(results));
			} else if (elem.getKind() == Value.Kind.UNORDERED_LIST) {
				return context.rewrite(Sequence.unordered(results));
			} else {
				throw new AssertionError(elem);
			}
		}
		
		@Override
		protected Value visitRecord(Record elem, ValueRewriter context) {
			assert elem != null;
			assert context != null;
			List<Entry> results = new ArrayList<Entry>();
			for (Entry target : elem.getEntries()) {
				Value appliedKey;
				if (keepEntryKey) {
					appliedKey = target.getKey();
				} else {
					appliedKey = target.getKey().accept(this, context);
					if (appliedKey == null) {
						continue; // skip
					}
				}
				Value appliedValue = target.getValue().accept(this, context);
				if (appliedValue == null) {
					continue; // skip
				}
				results.add(Entry.of(appliedKey, appliedValue));
			}
			return context.rewrite(Record.of(results));
		}
		
		@Override
		protected Value visitEntry(Entry elem, ValueRewriter context) {
			throw new AssertionError(elem);
		}
	}
}

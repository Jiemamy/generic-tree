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

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link Value}を変換するルール。
 * <p>
 * このクラスにおけるすべての変換ルールの実装は、何も変換を行わない。
 * それぞれのメソッドをオーバーライドして別の値を返すことにより、
 * {@code Generic Tree}上の任意の値を書き換えることができる。
 * </p>
 * <p>
 * 変換結果として{@code null}が返された場合、変換対象の親要素の種類によって
 * それぞれ次のようなことが行われる。
 * </p>
 * <table border="1">
 *   <tr>
 *     <th>
 *       親要素の種類
 *     </th>
 *     <th>
 *       {@code null}が返された時の動作
 *     </th>
 *   </tr>
 *   <tr>
 *     <td>
 *       {@link Sequence}
 *     </td>
 *     <td>
 *       対象の値が{@link Sequence}上より除去される
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       {@link Entry}
 *     </td>
 *     <td>
 *       対象のキーや値を含むエントリ全体が{@link Record}上より除去される
 *       (キー、値のいずれか一つでも{@code null}である場合)
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       (なし)
 *     </td>
 *     <td>
 *       全体の結果が{@code null}となる
 *     </td>
 *   </tr>
 * </table>
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public abstract class RewriteRule {
	
	/**
	 * {@link Terminal}を書き換える。
	 * @param value
	 *     変換前の値
	 * @return
	 *     変換後の値、
	 *     この値をツリー上から除去する場合は{@code null}
	 */
	protected Value rewriteTerminal(Terminal value) {
		return value;
	}
	
	/**
	 * 順序を考慮しないリスト({@link Sequence})を書き換える。
	 * @param value
	 *     変換前の値
	 * @return
	 *     変換後の値、
	 *     この値をツリー上から除去する場合は{@code null}
	 */
	protected Value rewriteOrderedList(Sequence value) {
		return value;
	}
	
	/**
	 * 順序付きリスト({@link Sequence})を書き換える。
	 * @param value
	 *     変換前の値
	 * @return
	 *     変換後の値、
	 *     この値をツリー上から除去する場合は{@code null}
	 */
	protected Value rewriteUnorderedList(Sequence value) {
		return value;
	}
	
	/**
	 * {@link Record}を書き換える。
	 * @param value
	 *     変換前の値
	 * @return
	 *     変換後の値、
	 *     この値をツリー上から除去する場合は{@code null}
	 */
	protected Value rewriteRecord(Record value) {
		return value;
	}
}

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 名前つきの値の列。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public final class Record extends Value {
	
	private static final long serialVersionUID = 6668732562104736964L;
	
	/**
	 * レコードの一覧。
	 */
	private final ArrayList<Entry> entries;
	

	/**
	 * インスタンスを生成する。
	 * @param entries エントリの一覧
	 */
	private Record(ArrayList<Entry> entries) {
		super();
		assert entries != null;
		this.entries = entries;
	}
	
	/**
	 * 指定のエントリの一覧からなるレコードを作成して返す。
	 * @param entries エントリの一覧
	 * @return 生成したインスタンス
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Record of(List<? extends Entry> entries) {
		if (entries == null) {
			throw new NullPointerException("entries"); //$NON-NLS-1$
		}
		ArrayList<Entry> copy = new ArrayList<Entry>(entries);
		Collections.sort(copy);
		return new Record(copy);
	}
	
	/**
	 * 常に{@link Value.Kind#RECORD}を返す。
	 * @return {@link Value.Kind#RECORD}
	 */
	@Override
	public Value.Kind getKind() {
		return Value.Kind.RECORD;
	}
	
	/**
	 * エントリの一覧を返す。
	 * <p>
	 * 返されるエントリの一覧は、エントリの自然な順序で整列されている。
	 * 返されるリストは変更できない。
	 * </p>
	 * @return エントリの一覧
	 */
	public List<Entry> getEntries() {
		return Collections.unmodifiableList(entries);
	}
	
	/**
	 * 指定のビジタを受け入れ、対応する{@link ElementVisitor}内のメソッドを呼び戻す。
	 */
	@Override
	public <R, C, E extends Throwable>R accept(ElementVisitor<R, C, E> visitor, C context) throws E {
		if (visitor == null) {
			throw new NullPointerException("visitor"); //$NON-NLS-1$
		}
		return visitor.visitRecord(this, context);
	}
	
	/**
	 * このオブジェクトのハッシュ値を返す。
	 * @return このオブジェクトのハッシュ値
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entries.hashCode();
		return result;
	}
	
	/**
	 * このオブジェクトとほかのオブジェクトを比較し、同値性を返す。
	 * @param obj 比較するオブジェクト
	 * @return 同値性
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Record other = (Record) obj;
		if (entries.equals(other.entries) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * この要素とほかの要素の自然な順序付けを返す。
	 * <p>
	 * これは、それぞれの要素が持つ{@link Record#getEntries()}の値を元に
	 * 次の手順で行われる。
	 * ただし、
	 * {@code this.getEntries()}の値を{@code s1},
	 * {@code that.getEntries()}の値を{@code s2}
	 * とよび、
	 * これらの要素数をそれぞれ{@code |s1|}, {@code |s2|}
	 * とよぶことにする。
	 * また、
	 * 任意の{@code s}に含まれる{@code i}番目の要素を{@code s[i]}
	 * と表現する。
	 * また、
	 * {@code a}が{@code b}よりも小さいことを{@code a << b}
	 * とよび、
	 * {@code a}が{@code b}と等しいことを{@code a == b}
	 * とよぶことにする。
	 * </p>
	 * <ul>
	 *   <li>
	 *     {@code that}がレコードでない場合、{@code this >> that}となる
	 *   </li>
	 *   <li>
	 *     そうでなく、{@code |s1| < |s2|}である場合、{@code this << that}となる
	 *   </li>
	 *   <li>
	 *     そうでなく、{@code |s1| > |s2|}である場合、{@code this >> that}となる
	 *   </li>
	 *   <li>
	 *     そうでなく、{@code |s1| = |s2| = n}である場合、
	 *     <ul>
	 *       <li>
	 *         すべての{@code i∈(1..n)}に対して
	 *         {@code s1[i] == s2[i]}が成り立つ場合、{@code this == that}となる
	 *       </li>
	 *       <li>
	 *         そうでなく、
	 *         すべての{@code i∈(1..k-1)}に対して
	 *         {@code s1[i] == s2[i]}が成り立ち、
	 *         かつ
	 *         {@code s1[k] == s2[k]}が<b>成り立たない</b>ような
	 *         {@code k}が存在する場合、
	 *         {@code (this, that)}の順序と{@code (s1[k], s2[k])}の順序は一致する
	 *       </li>
	 *     </ul>
	 *   </li>
	 * </ul>
	 * @param that
	 *     比較する要素
	 * @return
	 *     {@link Comparable}の規約に従った比較結果
	 * @throws NullPointerException
	 *     引数に{@code null}が指定された場合
	 * @throws ClassCastException
	 *     引数が不正に{@link Record}でない場合
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compareTo(Value that) {
		if (that == null) {
			throw new NullPointerException("other"); //$NON-NLS-1$
		}
		if (this.getKind() != that.getKind()) {
			return this.getKind().compareTo(that.getKind());
		}
		Record other = (Record) that;
		return compareList(this.entries, other.entries);
	}
	
	/**
	 * この要素の文字列表現を返す。
	 * @return この要素の文字列表現
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append('{');
		if (entries.isEmpty() == false) {
			buf.append(entries.get(0));
			for (int i = 1, n = entries.size(); i < n; i++) {
				buf.append(", "); //$NON-NLS-1$
				buf.append(entries.get(i));
			}
		}
		buf.append('}');
		return buf.toString();
	}
}

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
 * 値の一覧。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public final class Sequence extends Value {
	
	private static final long serialVersionUID = 8037951917602590075L;
	
	/**
	 * 一覧の特性
	 * ({@link Value.Kind#UNORDERED_LIST}または{@link Value.Kind#ORDERED_LIST})
	 */
	private final Value.Kind kind;
	
	/**
	 * 値の一覧
	 */
	private final ArrayList<Value> values;
	

	/**
	 * インスタンスを生成する。
	 * @param kind 値の種類
	 * @param values 値の一覧
	 */
	private Sequence(Value.Kind kind, ArrayList<Value> values) {
		super();
		assert values != null;
		assert kind != null;
		assert kind == Value.Kind.ORDERED_LIST || kind == Value.Kind.UNORDERED_LIST;
		this.kind = kind;
		this.values = values;
	}
	
	/**
	 * {@link Value.Kind#UNORDERED_LIST}または
	 * {@link Value.Kind#ORDERED_LIST}を返す。
	 * @return この一覧の種類
	 */
	@Override
	public Kind getKind() {
		return kind;
	}
	
	/**
	 * 指定の値を要素にもつ、順序つきのリストを返す。
	 * <p>
	 * このメソッドによって生成される{@link Sequence}オブジェクトは、
	 * {@link Sequence#getValues()}によって引数に渡された値の一覧と
	 * 同じ順序のリストを返す。
	 * </p>
	 * @param values 値の一覧
	 * @return 生成したインスタンス
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Sequence ordered(List<? extends Value> values) {
		if (values == null) {
			throw new NullPointerException("values"); //$NON-NLS-1$
		}
		ArrayList<Value> copy = new ArrayList<Value>(values);
		return new Sequence(Value.Kind.ORDERED_LIST, copy);
	}
	
	/**
	 * 指定の値を要素にもつ、順序を考慮しないリストを返す。
	 * <p>
	 * このメソッドによって生成される{@link Sequence}オブジェクトは、
	 * {@link Sequence#getValues()}によって
	 * {@link Value}の自然順序関係によって整列されたリストを返す。
	 * </p>
	 * @param values 値の一覧
	 * @return 生成したインスタンス
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Sequence unordered(List<? extends Value> values) {
		if (values == null) {
			throw new NullPointerException("values"); //$NON-NLS-1$
		}
		ArrayList<Value> copy = new ArrayList<Value>(values);
		Collections.sort(copy);
		return new Sequence(Value.Kind.UNORDERED_LIST, copy);
	}
	
	/**
	 * 値の一覧を返す。
	 * <p>
	 * 返されるリストは、値の一覧をすべて含む。
	 * リストに含まれる要素の順序は、
	 * このリストを変更することはできない。
	 * </p>
	 * @return 値の一覧
	 */
	public List<Value> getValues() {
		return Collections.unmodifiableList(values);
	}
	
	/**
	 * 指定のビジタを受け入れ、対応する{@link ElementVisitor}内のメソッドを呼び戻す。
	 */
	@Override
	public <R, C, E extends Throwable>R accept(ElementVisitor<R, C, E> visitor, C context) throws E {
		if (visitor == null) {
			throw new NullPointerException("visitor"); //$NON-NLS-1$
		}
		return visitor.visitSequence(this, context);
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
		result = prime * result + kind.hashCode();
		result = prime * result + values.hashCode();
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
		Sequence other = (Sequence) obj;
		if (kind.equals(other.kind) == false) {
			return false;
		}
		if (values.equals(other.values) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * この要素とほかの要素の自然な順序付けを返す。
	 * <p>
	 * これは、それぞれの要素が持つ{@link Sequence#getValues()}の値を元に
	 * 次の手順で行われる。
	 * ただし、
	 * {@code this.getValues()}の値を{@code s1},
	 * {@code that.getValues()}の値を{@code s2}
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
	 *     {@code this}が「順序を考慮しないリスト」であり、
	 *     かつ{@code that}が「順序を考慮しないリスト」でない場合
	 *     <ul>
	 *       <li> {@code that}が終端値である場合、{@code this >> that}となる </li>
	 *       <li> それ以外の場合、{@code this << that}となる </li>
	 *     </ul>
	 *   </li>
	 *   <li>
	 *     そうでなく、{@code this}が「順序を考慮するリスト」であり、
	 *     かつ{@code that}が「順序を考慮するリスト」でない場合
	 *     <ul>
	 *       <li> {@code that}がレコードである場合、{@code this << that}となる </li>
	 *       <li> それ以外の場合、{@code this >> that}となる </li>
	 *     </ul>
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
	 *     引数が不正に{@link Sequence}でない場合
	 */
	public int compareTo(Value that) {
		if (that == null) {
			throw new NullPointerException("other"); //$NON-NLS-1$
		}
		if (this.getKind() != that.getKind()) {
			return this.getKind().compareTo(that.getKind());
		}
		Sequence other = (Sequence) that;
		return compareList(values, other.values);
	}
	
	/**
	 * このオブジェクトの文字列表現を返す。
	 * @return このオブジェクトの文字列表現
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getValues().toString();
	}
}

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

/**
 * 終端値。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public final class Terminal extends Value {
	
	private static final long serialVersionUID = -5646236984087399228L;
	
	/**
	 * この値の表現。
	 */
	private final String representation;
	

	/**
	 * インスタンスを生成する。
	 * @param representation この値の表現
	 */
	private Terminal(String representation) {
		super();
		this.representation = representation;
	}
	
	/**
	 * インスタンスを生成する。
	 * @param representation 生成するオブジェクトの文字列による表現
	 * @return 生成したインスタンス
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Terminal of(String representation) {
		if (representation == null) {
			throw new NullPointerException("representation"); //$NON-NLS-1$
		}
		return new Terminal(representation);
	}
	
	/**
	 * 常に{@link Value.Kind#TERMINAL}を返す。
	 * @return {@link Value.Kind#TERMINAL}
	 */
	@Override
	public Value.Kind getKind() {
		return Value.Kind.TERMINAL;
	}
	
	/**
	 * この要素の表現を返す。
	 * @return この要素の表現
	 */
	public String getRepresentation() {
		return representation;
	}
	
	/**
	 * 指定のビジタを受け入れ、対応する{@link ElementVisitor}内のメソッドを呼び戻す。
	 */
	@Override
	public <R, C, E extends Throwable>R accept(ElementVisitor<R, C, E> visitor, C context) throws E {
		if (visitor == null) {
			throw new NullPointerException("visitor"); //$NON-NLS-1$
		}
		return visitor.visitTerminal(this, context);
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
		result = prime * result + representation.hashCode();
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
		Terminal other = (Terminal) obj;
		if (representation.equals(other.representation) == false) {
			return false;
		}
		return true;
	}
	
	/**
	 * この要素とほかの要素の自然な順序付けを返す。
	 * <p>
	 * {@code a}が{@code b}よりも小さいことを{@code a << b}
	 * とよび、
	 * {@code a}が{@code b}と等しいことを{@code a == b}
	 * とよぶことにする。
	 * </p>
	 * <ul>
	 *   <li>
	 *     {@code that}が終端値でない場合、{@code this << that}となる
	 *   </li>
	 *   <li>
	 *     そうでない場合、{@code this, that}の順序は
	 *     {@code this.representtion, that.representation}の
	 *     自然な順序付け(辞書式順序)と一致する。
	 *   </li>
	 * </ul>
	 * @param that 比較対象
	 * @return
	 *     {@link Comparable}の規約に従った比較結果
	 * @throws NullPointerException
	 *     引数に{@code null}が指定された場合
	 * @throws ClassCastException
	 *     引数のいずれかが{@link Terminal}でない場合
	 * @see String#compareTo(String)
	 */
	public int compareTo(Value that) {
		if (that == null) {
			throw new NullPointerException("other"); //$NON-NLS-1$
		}
		if (this.getKind() != that.getKind()) {
			return this.getKind().compareTo(that.getKind());
		}
		Terminal other = (Terminal) that;
		return representation.compareTo(other.representation);
	}
	
	/**
	 * この要素の文字列表現を返す。
	 * @return この要素の文字列表現
	 */
	@Override
	public String toString() {
		return getRepresentation();
	}
}

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

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 名前と値のペア。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public final class Entry extends Element implements Comparable<Entry>, Serializable {
	
	private static final long serialVersionUID = 2055189300212326226L;
	
	/**
	 * このエントリの名称。
	 */
	private final Value key;
	
	/**
	 * このエントリの値。
	 */
	private final Value value;
	

	/**
	 * インスタンスを生成する。
	 * @param key このエントリのキー
	 * @param value このエントリの値
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	private Entry(Value key, Value value) {
		super();
		assert key != null;
		assert value != null;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * インスタンスを生成する。
	 * @param key このエントリのキー
	 * @param value このエントリの値
	 * @return 生成したインスタンス
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Entry of(Value key, Value value) {
		if (key == null) {
			throw new NullPointerException("key"); //$NON-NLS-1$
		}
		if (value == null) {
			throw new NullPointerException("value"); //$NON-NLS-1$
		}
		return new Entry(key, value);
	}
	
	/**
	 * このエントリのキーを返す。
	 * @return このエントリのキー
	 */
	public Value getKey() {
		return key;
	}
	
	/**
	 * このエントリの値を返す。
	 * @return このエントリの値
	 */
	public Value getValue() {
		return value;
	}
	
	/**
	 * 指定のビジタを受け入れ、対応する{@link ElementVisitor}内のメソッドを呼び戻す。
	 */
	@Override
	public <R, C, E extends Throwable>R accept(ElementVisitor<R, C, E> visitor, C context) throws E {
		if (visitor == null) {
			throw new NullPointerException("visitor"); //$NON-NLS-1$
		}
		return visitor.visitEntry(this, context);
	}
	
	/**
	 * このオブジェクトの自然な順序を返す。
	 * <p>
	 * 次のように定義される。
	 * </p>
	 * <ul>
	 *   <li>
	 *     {@link Entry#getKey()}が同値でない場合、
	 *     {@link Entry#getKey()}の自然な順序
	 *   </li>
	 *   <li>
	 *     それ以外の場合、{@link Entry#getValue()}の自然な順序
	 *   </li>
	 * </ul>
	 * @param that 比較対象のエントリ
	 * @return 自然な順序
	 * @throws NullPointerException
	 *     引数に{@code null}が指定された場合
	 * @throws ClassCastException
	 *     引数のいずれかが{@link Terminal}でない場合
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Entry that) {
		int nameOrder = this.key.compareTo(that.key);
		if (nameOrder != 0) {
			return nameOrder;
		}
		return this.value.compareTo(that.value);
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
		result = prime * result + key.hashCode();
		result = prime * result + value.hashCode();
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
		Entry other = (Entry) obj;
		if (key.equals(other.key) == false) {
			return false;
		}
		if (value.equals(other.value) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return MessageFormat.format("\"{0}\":\"{1}\"", //$NON-NLS-1$
				getKey(), getValue());
	}
}

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
 * ツリー上に出現する要素。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public abstract class Element {
	
	/**
	 * 指定のビジタを受け入れる。
	 * @param <R> 戻り値の型
	 * @param <C> コンテキストオブジェクトの型
	 * @param <E> ビジタで発生する例外の型
	 * @param visitor 受け入れるビジタ
	 * @param context コンテキストオブジェクト(省略可)
	 * @return ビジタの実行結果
	 * @throws E ビジタでの処理中に例外が発生した場合
	 */
	public abstract <R, C, E extends Throwable>R accept(ElementVisitor<R, C, E> visitor, C context) throws E;
}

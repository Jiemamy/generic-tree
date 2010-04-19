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
 * {@link Element}を操作するビジタ。
 * <p>
 * この実装では、すべてのメソッドが何も行わずに{@code null}を返す。
 * </p>
 * @param <R> 戻り値の型
 * @param <C> コンテキストオブジェクトの型
 * @param <E> 例外の型
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public abstract class ElementVisitor<R, C, E extends Throwable> {
	
	/**
	 * {@link Terminal#accept(ElementVisitor,Object)}
	 * が呼び出された際にコールバックされる。
	 * @param elem
	 *     {@link Terminal#accept(ElementVisitor,Object)}
	 *     が呼び出されたオブジェクト
	 * @param context コンテキストオブジェクト(省略可)
	 * @return このビジタの実行結果
	 * @throws E この処理中に例外が発生した場合
	 */
	protected R visitTerminal(Terminal elem, C context) throws E {
		return null;
	}
	
	/**
	 * {@link Sequence#accept(ElementVisitor,Object)}
	 * が呼び出された際にコールバックされる。
	 * @param elem
	 *     {@link Sequence#accept(ElementVisitor,Object)}
	 *     が呼び出されたオブジェクト
	 * @param context コンテキストオブジェクト(省略可)
	 * @return このビジタの実行結果
	 * @throws E この処理中に例外が発生した場合
	 */
	protected R visitSequence(Sequence elem, C context) throws E {
		return null;
	}
	
	/**
	 * {@link Record#accept(ElementVisitor,Object)}
	 * が呼び出された際にコールバックされる。
	 * @param elem
	 *     {@link Record#accept(ElementVisitor,Object)}
	 *     が呼び出されたオブジェクト
	 * @param context コンテキストオブジェクト(省略可)
	 * @return このビジタの実行結果
	 * @throws E この処理中に例外が発生した場合
	 */
	protected R visitRecord(Record elem, C context) throws E {
		return null;
	}
	
	/**
	 * {@link Entry#accept(ElementVisitor,Object)}
	 * が呼び出された際にコールバックされる。
	 * @param elem
	 *     {@link Entry#accept(ElementVisitor,Object)}
	 *     が呼び出されたオブジェクト
	 * @param context コンテキストオブジェクト(省略可)
	 * @return このビジタの実行結果
	 * @throws E この処理中に例外が発生した場合
	 */
	protected R visitEntry(Entry elem, C context) throws E {
		return null;
	}
}

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
package org.jiemamy.utils.gtree.text;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.Map;

import org.jiemamy.utils.gtree.model.Value;

/**
 * {@code Generic Tree Notation}によって記述されたテキストから{@link Value}を復元する。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public final class Parser {
	
	/**
	 * 指定のソースから{@code Generic Tree Notation}形式のテキストを読み出し、
	 * 対応する{@link Value}を構築する。
	 * @param source {@code Generic Tree Notation}のテキストを保持するソース
	 * @return 対応する{@link Value}
	 * @throws IOException 解析に失敗した場合
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Value parse(Reader source) throws IOException {
		if (source == null) {
			throw new NullPointerException("source"); //$NON-NLS-1$
		}
		return parse(source, Collections.<String, Value> emptyMap());
	}
	
	/**
	 * 指定のソースから{@code Generic Tree Notation}形式のテキストを読み出し、
	 * 対応する{@link Value}を構築する。
	 * <p>
	 * ソース中に値の代わりに{@code $id}という形式の変数を指定することができ、
	 * {@code id}の名前で登録された値を{@code variables}から検出して利用する。
	 * そのような名前が{@code variables}に指定されていない場合、解析は失敗する。
	 * </p>
	 * @param source {@code Generic Tree Notation}のテキストを保持するソース
	 * @param variables 変数名と束縛された値の一覧
	 * @return 対応する{@link Value}
	 * @throws IOException 解析に失敗した場合
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static Value parse(Reader source, Map<String, ? extends Value> variables) throws IOException {
		if (source == null) {
			throw new NullPointerException("source"); //$NON-NLS-1$
		}
		if (variables == null) {
			throw new NullPointerException("variables"); //$NON-NLS-1$
		}
		GtreeParser0 parser = new GtreeParser0(source);
		try {
			return parser.parse(new Variables(variables));
		} catch (ParseException e) {
			throw (IOException) new IOException("Parse failure").initCause(e); //$NON-NLS-1$
		} finally {
			source.close();
		}
	}
	
	private Parser() {
	}
}

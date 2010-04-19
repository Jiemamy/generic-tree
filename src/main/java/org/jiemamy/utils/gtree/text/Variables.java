/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
 * Created on 2009/02/06
 *
 * This file is part of Jiemamy.
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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jiemamy.utils.gtree.model.Value;

/**
 * 変数表。
 * @author Suguru ARAKAWA
 */
class Variables {
	
	/**
	 * 値がひとつも束縛されてない変数表。
	 */
	public static final Variables NULL = new Variables(Collections.<String, Value> emptyMap());
	
	/**
	 * 変数表。
	 */
	private final Map<String, Value> entity;
	

	/**
	 * インスタンスを生成する。
	 * @param entity 変数名と値の束縛表
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public Variables(Map<String, ? extends Value> entity) {
		super();
		if (entity == null) {
			throw new NullPointerException("entity"); //$NON-NLS-1$
		}
		this.entity = Collections.unmodifiableMap(new HashMap<String, Value>(entity));
	}
	
	/**
	 * 指定のトークンを解析して、対応する変数の内容を返す。
	 * @param token 変数を表現するトークン
	 * @return 対象の変数に束縛された値
	 * @throws ParseException 指定の変数に値が束縛されていない場合
	 */
	public Value resolve(Token token) throws ParseException {
		if (token == null) {
			throw new NullPointerException("token"); //$NON-NLS-1$
		}
		if (token.image.startsWith("$") == false) {
			throw new IllegalArgumentException(token.toString());
		}
		String name = token.image.substring(1);
		Value bound = entity.get(name);
		if (bound == null) {
			throw new ParseException(MessageFormat.format("Undefined variable \"{0}\" (line {1}, column {2})", //$NON-NLS-1$
					token.image, token.beginLine, token.endColumn));
		}
		return bound;
	}
}

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
package org.jiemamy.utils.gtree.converter;

import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link Enum}を{@link Terminal}に変換する。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public enum EnumConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * {@link Enum}を{@link Terminal}に変換する。
	 * <p>
	 * 変換後の文字列は、{@code 列挙型名 "." 定数名}となる。
	 * 列挙以外が指定された場合、この呼び出しは{@code null}を返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object instanceof Enum<?>) {
			Enum<?> constant = (Enum<?>) object;
			String declaring = constant.getDeclaringClass().getName();
			declaring = declaring.replace('$', '.');
			String name = constant.name();
			String rep = declaring + '.' + name;
			return Terminal.of(rep);
		}
		return null;
	}
}

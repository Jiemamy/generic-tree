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
import org.jiemamy.utils.gtree.text.JavaEscape;

/**
 * {@link Character}および{@link String}を{@link Terminal}に変換する。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public enum CharStringConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * {@link Character}および{@link String}を{@link Terminal}に変換する。
	 * <p>
	 * {@link Character}であれば、表現する文字の前後に{@code ''}を付与した後、
	 * 必要ならば文字をエスケープする。
	 * そうでなく、{@link String}であれば、表現する文字の前後に{@code ""}を付与した後、
	 * 必要ならば文字をエスケープする。
	 * いずれでもない場合は{@code null}を返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object instanceof String) {
			String s = (String) object;
			String literal = '"' + JavaEscape.escape(s, false, false) + '"';
			return Terminal.of(literal);
		}
		if (object instanceof Character) {
			String c = String.valueOf(object);
			String literal = "'" + JavaEscape.escape(c, true, false) + "'";
			return Terminal.of(literal);
		}
		return null;
	}
}

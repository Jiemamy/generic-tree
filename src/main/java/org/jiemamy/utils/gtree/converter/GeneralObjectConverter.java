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
 * 任意のオブジェクトを{@link Terminal}に変換する。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public enum GeneralObjectConverter implements ObjectConverter, ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * 任意のオブジェクトを、その文字列表現からなる{@link Terminal}に変換する。
	 */
	public Value convert(Object object) {
		String representation = String.valueOf(object);
		if (representation == null) {
			representation = "null"; //$NON-NLS-1$
		}
		return Terminal.of(representation);
	}
	
	/**
	 * 任意のオブジェクトを、その文字列表現からなる{@link Terminal}に変換する。
	 */
	public Value convert(Object object, ObjectConverter converter) {
		return convert(object);
	}
}

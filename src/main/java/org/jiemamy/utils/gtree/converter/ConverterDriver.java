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

import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link ObjectConverter}の変換ルール。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public interface ConverterDriver {
	
	/**
	 * 指定の変換器を利用してオブジェクトを{@link Value}型の値に変換する。
	 * @param object 変換対象、または{@code null}の値
	 * @param converter 利用する変換器
	 * @return 変換後の値、変換できない場合は{@code null}
	 */
	Value convert(Object object, ObjectConverter converter);
}

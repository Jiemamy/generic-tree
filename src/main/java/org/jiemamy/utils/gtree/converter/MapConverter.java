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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link Map}を{@link Record}に変換する。
 * <p>
 * キーは常に文字列として取り扱う。
 * </p>
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public enum MapConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * {@link Map}を{@link Record}に変換する。
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) object;
			List<Entry> entries = new ArrayList<Entry>(map.size());
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				Value key = converter.convert(entry.getKey());
				Value value = converter.convert(entry.getValue());
				// ひとつでも変換に失敗したらアウト
				if (value == null) {
					return null;
				}
				entries.add(Entry.of(key, value));
			}
			return Record.of(entries);
		}
		return null;
	}
}

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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link Collection}を変換する。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public enum CollectionConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	/**
	 * {@link Collection}型の値を{@link Sequence}に変換する。
	 * <p>
	 * 値が{@link Set}である場合、要素をすべて{@link Value}に変換した上で
	 * 順序を考慮しない{@link Sequence}を返す。
	 * それ以外の{@link Collection}では、要素をすべて{@link Value}に変換した上で
	 * 順序を考慮する{@link Sequence}を返す。
	 * {@link Collection}でない場合は{@code null}を返す。
	 * また、{@link Collection}であってもいずれかの要素の変換に失敗した場合は
	 * {@code null}を返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object instanceof Collection<?>) {
			Collection<?> c = (Collection<?>) object;
			List<Value> values = new ArrayList<Value>(c.size());
			for (Object elem : c) {
				Value value = converter.convert(elem);
				// ひとつでも変換できないと失敗
				if (value == null) {
					return null;
				}
				values.add(value);
			}
			
			// Setのときはunorderedで
			if (c instanceof Set<?>) {
				return Sequence.unordered(values);
			} else {
				return Sequence.ordered(values);
			}
		}
		return null;
	}
}

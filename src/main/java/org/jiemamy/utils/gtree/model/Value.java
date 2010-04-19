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

import java.io.Serializable;
import java.util.List;

/**
 * ツリー上に出現する値。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public abstract class Value extends Element implements Comparable<Value>, Serializable {
	
	private static final long serialVersionUID = 8404215527421996693L;
	

	/**
	 * 2つのリストの自然な順序付けを返す。
	 * @param <T> リストの要素型
	 * @param s1 比較されるリスト
	 * @param s2 比較するリスト
	 * @return 順序付け
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	static <T extends Comparable<T>>int compareList(List<T> s1, List<T> s2) {
		if (s1 == null) {
			throw new NullPointerException("s1"); //$NON-NLS-1$
		}
		if (s2 == null) {
			throw new NullPointerException("s2"); //$NON-NLS-1$
		}
		if (s1.size() < s2.size()) {
			return -1;
		} else if (s1.size() > s2.size()) {
			return +1;
		}
		for (int i = 0, n = s1.size(); i < n; i++) {
			int elemOrder = s1.get(i).compareTo(s2.get(i));
			if (elemOrder != 0) {
				return elemOrder;
			}
		}
		return +-0; // CHECKSTYLE IGNORE THIS LINE
	}
	
	/**
	 * この要素の種類を返す。
	 * @return この要素の種類
	 */
	public abstract Value.Kind getKind();
	

	/**
	 * 値の種類。
	 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
	 * @author Suguru ARAKAWA
	 */
	public enum Kind {
		
		/**
		 * 終端値 ({@link Terminal})。
		 */
		TERMINAL,

		/**
		 * 順序を持たないリスト ({@link Sequence})。
		 */
		UNORDERED_LIST,

		/**
		 * 順序を持つリスト ({@link Sequence})。
		 */
		ORDERED_LIST,

		/**
		 * レコード ({@link Record})。
		 */
		RECORD;
	}
}

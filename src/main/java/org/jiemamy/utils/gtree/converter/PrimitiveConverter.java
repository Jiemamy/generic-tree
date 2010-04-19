/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
 * Created on 2009/02/02
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
package org.jiemamy.utils.gtree.converter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * プリミティブな値を変換する。
 * @author Suguru ARAKAWA
 */
public enum PrimitiveConverter implements ConverterDriver {
	
	/**
	 * この変換器のインスタンス。
	 */
	INSTANCE;
	
	private static final Set<Class<?>> TARGETS;
	static {
		Set<Class<?>> targets = new HashSet<Class<?>>();
		targets.add(Integer.class);
		targets.add(Long.class);
		targets.add(Float.class);
		targets.add(Double.class);
		targets.add(Byte.class);
		targets.add(Short.class);
		targets.add(Character.class);
		targets.add(Boolean.class);
		targets.add(BigDecimal.class);
		targets.add(String.class);
		TARGETS = Collections.unmodifiableSet(targets);
	}
	

	/**
	 * またはプリミティブな値、および{@code null}を{@link Terminal}に変換する。
	 * <p>
	 * この変換器が対象とする値は、以下のクラスに属するものである。
	 * </p>
	 * <ul>
	 *   <li> {@link Integer} </li>
	 *   <li> {@link Long} </li>
	 *   <li> {@link Float} </li>
	 *   <li> {@link Double} </li>
	 *   <li> {@link Short} </li>
	 *   <li> {@link Byte} </li>
	 *   <li> {@link Character} </li>
	 *   <li> {@link Boolean} </li>
	 *   <li> {@link BigDecimal} </li>
	 *   <li> {@link String} </li>
	 * </ul>
	 * <p>
	 * {@code null}が引数に渡された場合、空文字列として返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object == null) {
			return Terminal.of(""); //$NON-NLS-1$
		}
		Class<? extends Object> klass = object.getClass();
		if (TARGETS.contains(klass)) {
			String representation = String.valueOf(object);
			return Terminal.of(representation);
		}
		return null;
	}
}

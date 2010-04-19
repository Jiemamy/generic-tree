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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * 任意のオブジェクトがインターフェース上で公開するBean形式のプロパティを元に、
 * {@link Record}を構築する。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class InterfaceBeanConverter implements ConverterDriver {
	
	/**
	 * 空の引数リスト。
	 */
	private static final Object[] EMPTY_ARGS = new Object[0];
	
	private static final Pattern BEAN_GETTER_NAME = Pattern.compile("(get|is)(.)(.*)"); //$NON-NLS-1$
	
	
	/**
	 * 任意のオブジェクトがインターフェース上で公開するBean形式のプロパティを元に、
	 * {@link Record}を構築する。
	 * <p>
	 * 対象のオブジェクトがクラスインスタンスでない場合(列挙定数, 配列, {@code null}など)や、
	 * インターフェースを公開しない場合、Bean形式のプロパティを公開しない場合は
	 * 変換に失敗して{@code null}を返す。
	 * </p>
	 */
	public Value convert(Object object, ObjectConverter converter) {
		if (object == null) {
			return null;
		}
		Class<? extends Object> klass = object.getClass();
		if (klass.isArray() || klass.isEnum()) {
			return null;
		}
		
		Collection<Method> methods = collectMethods(klass);
		if (methods.isEmpty()) {
			return null;
		}
		
		Record record = convertEach(object, methods, converter);
		return record;
	}
	
	private Collection<Method> collectMethods(Class<?> klass) {
		Map<String, Method> methods = new HashMap<String, Method>();
		Class<?> current = klass;
		while (current != null) {
			Class<?>[] superInterfaces = current.getInterfaces();
			for (Class<?> interfaceType : superInterfaces) {
				// publicなインターフェース以外は無視
				if (Modifier.isPublic(interfaceType.getModifiers()) == false) {
					continue;
				}
				putBeanMethods(interfaceType, methods);
			}
			current = current.getSuperclass();
		}
		return methods.values();
	}
	
	private Record convertEach(Object object, Collection<Method> methods, ObjectConverter converter) {
		
		List<Entry> entries = new ArrayList<Entry>(methods.size());
		for (Method m : methods) {
			try {
				Object result = m.invoke(object, EMPTY_ARGS);
				if (result == object) {
					// 自己返戻くらいは防いでおく
					return null;
				}
				Value key = Terminal.of(toBeanName(m));
				Value value = converter.convert(result);
				if (value == null) {
					// 変換に失敗したら全体を失敗させる
					return null;
				}
				entries.add(Entry.of(key, value));
			} catch (Exception e) {
				// あらゆる例外で失敗
				return null;
			}
		}
		return Record.of(entries);
	}
	
	private String toBeanName(Method m) throws AssertionError {
		Matcher matcher = BEAN_GETTER_NAME.matcher(m.getName());
		if (matcher.matches() == false) {
			// プログラミング例外
			throw new AssertionError(m);
		}
		String first = matcher.group(2);
		String rest = matcher.group(3);
		return first.toLowerCase() + rest;
	}
	
	private void putBeanMethods(Class<?> klass, Map<String, Method> target) {
		for (Method m : klass.getMethods()) {
			// 同じ名前のメソッドがすでにある(=同一のプロパティがすでに登録されている)
			if (target.containsKey(m.getName())) {
				continue;
			}
			// Getterじゃなければ無視
			if (isGetter(m) == false) {
				continue;
			}
			target.put(m.getName(), m);
		}
	}
	
	private boolean isGetter(Method method) {
		Class<?> ret = method.getReturnType();
		if (ret == void.class) {
			return false;
		}
		// check name is getter-like
		String name = method.getName();
		if (ret == boolean.class) {
			if (name.startsWith("is") == false || name.length() == 2) {
				return false;
			}
		} else {
			if (name.startsWith("get") == false || name.length() == 3) {
				return false;
			}
		}
		// check parameter is empty
		if (method.getParameterTypes().length >= 1) {
			return false;
		}
		return true;
	}
}

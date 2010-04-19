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
package org.jiemamy.utils.gtree.example.driver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jiemamy.utils.gtree.converter.ConverterDriver;
import org.jiemamy.utils.gtree.converter.ObjectConverter;
import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link PrimaryInterface}の指定があるオブジェクトを対象にする。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class AnnotatedObjectConverter implements ConverterDriver {
	
	public Value convert(Object object, ObjectConverter converter) {
		if (object == null) {
			return null;
		}
		PrimaryInterface primaryAnnotation = object.getClass().getAnnotation(PrimaryInterface.class);
		if (primaryAnnotation == null) {
			return null;
		}
		
		Class<?> primary = primaryAnnotation.value();
		if (primary.isInstance(object) == false) {
			// invalid primary interface
			return null;
		}
		
		List<Entry> entries = new ArrayList<Entry>();
		for (Method m : primary.getMethods()) {
			if (m.getParameterTypes().length == 0) {
				try {
					Object result = m.invoke(object);
					Value value = converter.convert(result);
					if (value == null) {
						return null;
					}
					entries.add(Entry.of(Terminal.of(m.getName()), value));
				} catch (Exception e) {
					return null; // failure
				}
			}
		}
		return Record.of(entries);
	}
}

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
package org.jiemamy.utils.gtree.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * カラム。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class ColmunImpl implements Column {
	
	private String name;
	
	private Type type;
	
	private Set<Attribute> attributes;
	

	/**
	 * インスタンスを生成する。
	 * @param name 名前
	 * @param type 型
	 * @param attributes 属性一覧
	 */
	public ColmunImpl(String name, Type type, Attribute... attributes) {
		super();
		this.name = name;
		this.type = type;
		this.attributes = new HashSet<Attribute>(Arrays.asList(attributes));
	}
	
	/**
	 * @return 名前
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return 型
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return 属性一覧
	 */
	public Set<Attribute> getAttributes() {
		return attributes;
	}
}

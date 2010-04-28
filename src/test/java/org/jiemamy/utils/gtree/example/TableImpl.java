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
import java.util.List;

/**
 * テーブル。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class TableImpl implements Table {
	
	private String name;
	
	private List<Column> columns;
	

	/**
	 * インスタンスを生成する。
	 * @param name 名前
	 * @param columns カラム一覧
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public TableImpl(String name, Column... columns) {
		super();
		this.name = name;
		this.columns = Arrays.asList(columns);
	}
	
	/**
	 * @return 名前
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return カラム一覧
	 */
	public List<Column> getColumns() {
		return columns;
	}
}

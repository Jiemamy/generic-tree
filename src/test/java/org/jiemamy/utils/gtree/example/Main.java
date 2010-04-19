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

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import org.jiemamy.utils.gtree.converter.DefaultObjectConverter;
import org.jiemamy.utils.gtree.converter.InterfaceBeanConverter;
import org.jiemamy.utils.gtree.converter.ObjectConverter;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.text.Emitter;
import org.jiemamy.utils.gtree.text.Parser;

/**
 * サンプルプログラムのエントリ。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
public class Main {
	
	/**
	 * プログラムエントリ。
	 * @param args 引数一覧
	 * @throws Exception if occur
	 */
	public static void main(String[] args) throws Exception {
		// モデルを作る
		Root root = createModel();
		
		// コンバータを作る
		ObjectConverter converter = DefaultObjectConverter.newInstance(Arrays.asList(new InterfaceBeanConverter()));
		
		// コンバータでモデルをGeneric Treeに変換
		Value genericTree = converter.convert(root);
		
		// エミッタでPrintWriterに出力
		StringWriter out = new StringWriter();
		PrintWriter pw = new PrintWriter(out);
		Emitter.emit(genericTree, pw);
		pw.close();
		String stringValue = out.toString();
		
		// コンソールに吐いてみる
		System.out.println(stringValue);
		
		// パーサで復元
		Value restored = Parser.parse(new StringReader(stringValue));
		
		// 復元したものを比較
		if (restored.equals(genericTree) == false) {
			throw new AssertionError(stringValue);
		}
	}
	
	private static Root createModel() {
		Root root =
				new RootImpl(new TableImpl("HOGE", new ColmunImpl("ID", Type.INT, Attribute.NOT_NULL,
						Attribute.PRIMARY_KEY), new ColmunImpl("NAME", Type.TEXT, Attribute.NOT_NULL), new ColmunImpl(
						"AGE", Type.INT, Attribute.NOT_NULL)), new TableImpl("FOO", new ColmunImpl("ID", Type.INT,
						Attribute.NOT_NULL, Attribute.PRIMARY_KEY), new ColmunImpl("HOGE_ID", Type.INT,
						Attribute.NOT_NULL), new ColmunImpl("RATE", Type.INT)));
		return root;
	}
}

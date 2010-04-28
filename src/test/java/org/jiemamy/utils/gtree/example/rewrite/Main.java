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
package org.jiemamy.utils.gtree.example.rewrite;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jiemamy.utils.gtree.converter.ConverterDriver;
import org.jiemamy.utils.gtree.converter.DefaultObjectConverter;
import org.jiemamy.utils.gtree.converter.ObjectConverter;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.rewrite.Rewriter;
import org.jiemamy.utils.gtree.text.Emitter;

/**
 * {@link Rewriter}のサンプルプログラム。
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class Main {
	
	/**
	 * プログラムエントリ。
	 * @param args 無視される
	 */
	public static void main(String[] args) {
		
		// ツリーを構築
		Map<Object, Object> root = new HashMap<Object, Object>();
		root.put("string", "Hello");
		root.put("null", null);
		root.put("list", Arrays.asList("a", "b"));
		root.put("emptyList", Arrays.asList());
		root.put("set", new HashSet<Object>(Arrays.asList("c", "d")));
		root.put("emptySet", new HashSet<Object>(Arrays.asList()));
		root.put("map", Collections.singletonMap("Hello", "World"));
		root.put("emptyMap", Collections.emptyMap());
		
		// 普通の変換器で変換する
		ObjectConverter converter = DefaultObjectConverter.newInstance(Collections.<ConverterDriver> emptyList());
		Value tree = converter.convert(root);
		
		// EmptyEliminator を使ってツリーを書き換える (エントリのキーは書き換えない)
		Rewriter optimizer = new Rewriter(Arrays.asList(new EmptyEliminator()));
		Value opt = optimizer.apply(tree, true);
		
		PrintWriter writer = new PrintWriter(System.out);
		
		// 普通の変換
		writer.println("=== Original Tree");
		Emitter.emit(tree, writer);
		
		// 書き換えたツリー
		writer.println("=== Optimized Tree");
		Emitter.emit(opt, writer);
		
		writer.flush();
	}
}

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

import java.io.PrintWriter;
import java.util.Arrays;

import org.jiemamy.utils.gtree.converter.DefaultObjectConverter;
import org.jiemamy.utils.gtree.converter.ObjectConverter;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.text.Emitter;

/**
 * サンプルプログラムのエントリ。
 * @version $Date$
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
		HogeImpl hoge = new HogeImpl();
		
		// コンバータを作る
		ObjectConverter converter = DefaultObjectConverter.newInstance(Arrays.asList(new AnnotatedObjectConverter()));
		
		Value genericTree = converter.convert(hoge);
		PrintWriter pw = new PrintWriter(System.out);
		Emitter.emit(genericTree, pw);
		pw.flush();
	}
}

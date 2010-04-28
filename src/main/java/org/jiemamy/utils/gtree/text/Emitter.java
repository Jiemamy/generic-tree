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
package org.jiemamy.utils.gtree.text;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jiemamy.utils.gtree.model.ElementVisitor;
import org.jiemamy.utils.gtree.model.Entry;
import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.model.Value.Kind;

/**
 * {@link Value}を{@code Generic Tree Notation}で出力する。
 * @version $Date$
 * @author Suguru ARAKAWA
 */
public class Emitter {
	
	/**
	 * インデントに利用する文字列。
	 */
	private static final String INDENT = "    "; //$NON-NLS-1$
	
	static final Map<Kind, String> BLOCK_ENTER;
	
	static final Map<Value.Kind, String> BLOCK_EXIT;
	static {
		Map<Kind, String> enter = new EnumMap<Kind, String>(Kind.class);
		Map<Kind, String> exit = new EnumMap<Kind, String>(Kind.class);
		enter.put(Kind.ORDERED_LIST, "[");
		exit.put(Kind.ORDERED_LIST, "]");
		enter.put(Kind.UNORDERED_LIST, "{");
		exit.put(Kind.UNORDERED_LIST, "}");
		enter.put(Kind.RECORD, "<");
		exit.put(Kind.RECORD, ">");
		BLOCK_ENTER = Collections.unmodifiableMap(enter);
		BLOCK_EXIT = Collections.unmodifiableMap(exit);
	}
	

	/**
	 * 指定の値を{@code Generic Tree Notation}形式で指定の出力先に出力する。
	 * @param value 出力する値
	 * @param output 出力先
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static void emit(Value value, PrintWriter output) {
		if (value == null) {
			throw new NullPointerException("value"); //$NON-NLS-1$
		}
		if (output == null) {
			throw new NullPointerException("output"); //$NON-NLS-1$
		}
		PrettyPrintVisitor visitor = new PrettyPrintVisitor(INDENT, 0);
		value.accept(visitor, output);
	}
	
	/**
	 * インスタンス化の禁止。
	 */
	private Emitter() {
		throw new AssertionError();
	}
	

	/**
	 * {@link Value}を{@link PrintWriter}へ出力しながら走査する。
	 * @version $Date$
	 * @author Suguru ARAKAWA
	 */
	private static class PrettyPrintVisitor extends ElementVisitor<Void, PrintWriter, RuntimeException> {
		
		/**
		 * インデントに利用する文字列。
		 */
		private String indent;
		
		/**
		 * 現在のインデントの深さ。{@code 0}ならばインデントなし。
		 */
		private int depth;
		
		/**
		 * 現在のキャレットが行頭にいるかどうか。
		 */
		private boolean lineHead;
		

		/**
		 * インスタンスを生成する。
		 * @param indent インデント文字列
		 * @param initDepth 初期インデント深度
		 * @throws NullPointerException 引数に{@code null}が指定された場合
		 */
		public PrettyPrintVisitor(String indent, int initDepth) {
			assert indent != null;
			assert initDepth >= 0;
			this.indent = indent;
			this.depth = initDepth;
			this.lineHead = true;
		}
		
		/**
		 * {@link Entry}を出力する。
		 * <pre>
		 * [key] : [value]
		 * </pre>
		 */
		@Override
		protected Void visitEntry(Entry elem, PrintWriter context) {
			elem.getKey().accept(this, context);
			printEntrySeparator(elem, context);
			elem.getValue().accept(this, context);
			return null;
		}
		
		/**
		 * {@link Record}を出力する。
		 * <pre>
		 * &lt;
		 * [indent] [entry]
		 * ...
		 * &gt;
		 * </pre>
		 */
		@Override
		protected Void visitRecord(Record elem, PrintWriter context) {
			List<Entry> entries = elem.getEntries();
			enterBlock(elem, context);
			for (int i = 0, n = entries.size(); i < n; i++) {
				entries.get(i).accept(this, context);
				lineBreak(i < n - 1, context);
			}
			exitBlock(elem, context);
			return null;
		}
		
		/**
		 * {@link Sequence}を出力する。
		 * <p> 順序を考慮するリスト </p>
		 * <pre>
		 * [
		 * [indent] [value]
		 * ...
		 * ]
		 * </pre>
		 * <p> 順序を考慮しないリスト </p>
		 * <pre>
		 * {
		 * [indent] [value]
		 * ...
		 * }
		 * </pre>
		 */
		@Override
		protected Void visitSequence(Sequence elem, PrintWriter context) {
			List<Value> values = elem.getValues();
			enterBlock(elem, context);
			for (int i = 0, n = values.size(); i < n; i++) {
				values.get(i).accept(this, context);
				lineBreak(i < n - 1, context);
			}
			exitBlock(elem, context);
			return null;
		}
		
		/**
		 * {@link Terminal}を出力する。
		 * <pre>
		 * "[representation]"
		 * </pre>
		 */
		@Override
		protected Void visitTerminal(Terminal elem, PrintWriter context) {
			printString(elem.getRepresentation(), context);
			return null;
		}
		
		private void enterBlock(Value elem, PrintWriter context) {
			String separator = BLOCK_ENTER.get(elem.getKind());
			assert separator != null;
			print(separator, context);
			lineBreak(false, context);
			depth++;
		}
		
		private void exitBlock(Value elem, PrintWriter context) {
			String separator = BLOCK_EXIT.get(elem.getKind());
			assert separator != null;
			--depth;
			lineBreak(false, context);
			print(separator, context);
		}
		
		private void printEntrySeparator(Entry elem, PrintWriter context) {
			print(":", context); //$NON-NLS-1$
		}
		
		private void printString(String str, PrintWriter context) {
			print('\'' + JavaEscape.escape(str, true, false) + '\'', context);
		}
		
		private void lineBreak(boolean withSeparator, PrintWriter context) {
			if (withSeparator) {
				print(",", context);
			}
			if (lineHead == false) {
				context.println();
			}
			lineHead = true;
		}
		
		private void print(String str, PrintWriter context) {
			// インデント処理
			if (lineHead) {
				for (int i = 0, n = depth; i < n; i++) {
					context.print(indent);
				}
				lineHead = false;
			}
			context.print(str);
		}
	}
}

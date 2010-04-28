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

import org.jiemamy.utils.gtree.model.Record;
import org.jiemamy.utils.gtree.model.Sequence;
import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;
import org.jiemamy.utils.gtree.rewrite.RewriteRule;

/**
 * 空の要素を削除する規則。
 * @version $Date$
 * @author Suguru ARAKAWA (Gluegent, Inc.)
 */
public class EmptyEliminator extends RewriteRule {
	
	@Override
	protected Value rewriteTerminal(Terminal value) {
		String content = value.getRepresentation();
		
		// 空なら消去
		if (content.length() == 0) {
			return null;
		}
		// ++ しておく
		return Terminal.of(content + "++");
	}
	
	@Override
	protected Value rewriteOrderedList(Sequence value) {
		// 空なら消去
		if (value.getValues().isEmpty()) {
			return null;
		}
		// そのまま返す
		return value;
	}
	
	@Override
	protected Value rewriteUnorderedList(Sequence value) {
		// 空なら消去
		if (value.getValues().isEmpty()) {
			return null;
		}
		// そのまま返す
		return value;
	}
	
	@Override
	protected Value rewriteRecord(Record value) {
		// 空なら消去
		if (value.getEntries().isEmpty()) {
			return null;
		}
		// そのまま返す
		return value;
	}
}

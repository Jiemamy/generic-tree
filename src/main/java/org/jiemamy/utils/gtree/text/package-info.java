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

/**
 * {@code Generic Tree Notation}によって記述されたテキストを取り扱う。
 * <p>
 * {@code Generic Tree Notation}の構文文法は、
 * 下記の非終端記号{@code Script}をゴール記号とする
 * 生成規則の集合によって表現される。
 * </p>
 * <pre><code>
 * Script :
 *     Value
 * 
 * Value :
 *     Terminal
 *     OrderedList
 *     UnorderedList
 *     Record
 *     Variable
 * 
 * Terminal :
 *     STRING
 * 
 * OrderedList :
 *     [ ]
 *     [ ValueList ]
 * 
 * UnorderedList :
 *     { }
 *     { ValueList }
 * 
 * Record :
 *     &lt; &gt;
 *     &lt; EntryList &gt;
 * 
 * Variable :
 *     $ IDENTIFIER
 *     
 * ValueList :
 *     Value
 *     ValueList , Value
 * 
 * EntryList :
 *     Entry
 *     EntryList , Entry
 * 
 * Entry :
 *     Value : Value
 * 
 * STRING :
 *     (Javaの文字列リテラル)
 * 
 * IDENTIFIER :
 *     [A-Za-z0-9]+
 * 
 * </code></pre>
 * <p>
 * このうち、{@code Variable}は擬似的な構文規則で、あらかじめ登録された値を
 * 変数表から探し出し、評価結果を該当の変数に格納された値とする。
 * 変数表には変数の名前を束縛する値の一覧を指定できるが、このとき
 * 先頭の{@code $}を除いた識別子で変数の名前を指定する。
 * </p>
 */
package org.jiemamy.utils.gtree.text;


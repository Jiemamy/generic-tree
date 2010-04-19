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
package org.jiemamy.utils.gtree.converter.bean;

/**
 * 微妙にプロパティになれないもの。
 * @version $Date: 2009-09-29 23:06:33 +0900 (火, 29  9 2009) $
 * @author Suguru ARAKAWA
 */
@SuppressWarnings("all")
public interface BadProperties {
	
	int GetValue(); // capital
	
	int IsValue(); // not boolean
	
	int get(); // too short
	
	int isValue(); // not boolean
	
	boolean getValue(); // boolean
	
	void getVoid(); // get but void
	
	String getProperty(String key); // with parameter
}

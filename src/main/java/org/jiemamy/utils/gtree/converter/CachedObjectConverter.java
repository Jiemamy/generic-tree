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
package org.jiemamy.utils.gtree.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jiemamy.utils.gtree.model.Terminal;
import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link ConverterDriver}の組み合わせによる{@link ObjectConverter}の実装。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public class CachedObjectConverter implements ObjectConverter {
	
	private static class ObjectGraphCache {
		
		/**
		 * コールフレームを管理する。
		 */
		private Map<Object, Integer> frame;
		

		/**
		 * インスタンスを生成する。
		 */
		public ObjectGraphCache() {
			super();
		}
		
		/**
		 * 指定のオブジェクトをキャッシュに追加する。
		 * @param object 追加するオブジェクト
		 * @return 追加済みならそのオブジェクトの番号({@code >= 0})、そうでなければ　{@code -1}
		 */
		public int enter(Object object) {
			if (frame == null) {
				reinitialize();
			}
			int result;
			if (object != null) {
				if (frame.containsKey(object)) {
					result = frame.get(object);
				} else {
					result = -1;
					frame.put(object, frame.size());
				}
			} else {
				result = -1;
			}
			return result;
		}
		
		/**
		 * 現在のオブジェクトに対する処理が終了したことを通知する。
		 * @param object 抜けるオブジェクト
		 */
		public void exit(Object object) {
			if (object != null) {
				frame.remove(object);
				if (frame.isEmpty()) {
					frame = null;
				}
			}
		}
		
		private void reinitialize() {
			frame = new IdentityHashMap<Object, Integer>();
		}
	}
	

	/**
	 * {@link Terminal}に変換する組み込みの変換器。
	 */
	private static final List<ConverterDriver> TERMINALS;
	
	static {
		ArrayList<ConverterDriver> list = new ArrayList<ConverterDriver>();
		list.add(PrimitiveConverter.INSTANCE);
		list.add(EnumConverter.INSTANCE);
		list.trimToSize();
		TERMINALS = Collections.unmodifiableList(list);
	}
	

	/**
	 * 標準的なドライバセットに、拡張のドライバセットを追加した変換器を生成して返す。
	 * <p>
	 * 生成される変換器は、次のようなドライバセットを有する。
	 * </p>
	 * <ol>
	 *   <li> {@link PrimitiveConverter} </li>
	 *   <li> {@link EnumConverter} </li>
	 *   <li> {@link ArrayConverter} </li>
	 *   <li> {@link CollectionConverter} </li>
	 *   <li> {@link MapConverter} </li>
	 *   <li> {@code [extraDrivers]} </li>
	 *   <li> {@link GeneralObjectConverter} </li>
	 * </ol>
	 * <p>
	 * 登録されるドライバは上記の順に並べられており、番号が若いドライバほど優先して適用される。
	 * ただし、あるドライバがオブジェクトを変換できなかった場合、この変換器は次のドライバによって
	 * オブジェクトの変換を試みる。
	 * すべてのドライバによって変換できない場合、この変換器は結果として{@code null}を返す。
	 * ただし、{@link GeneralObjectConverter}は基本的に失敗しないため、
	 * この呼び出しによって生成されたインスタンスが{@link #convert(Object)}の結果によって
	 * {@code null}を返すことはほぼない。
	 * </p>
	 * <p>
	 * 登録されたドライバセットのうち、{@link PrimitiveConverter}と{@link EnumConverter}を
	 * <b>のぞく</b>変換器に同一のオブジェクトが2回以上対象となった場合、
	 * そのオブジェクトは循環が存在するものとして強制的に
	 * {@code #COPY-n}という形式に変換される (nには任意の数値が入る)。
	 * </p>
	 * @param extraDrivers ユーザ定義のドライバ一覧
	 * @return 生成したオブジェクト
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static CachedObjectConverter newInstance(List<? extends ConverterDriver> extraDrivers) {
		if (extraDrivers == null) {
			throw new NullPointerException("extraDrivers"); //$NON-NLS-1$
		}
		List<ConverterDriver> drivers = new ArrayList<ConverterDriver>();
		drivers.add(ArrayConverter.INSTANCE);
		drivers.add(CollectionConverter.INSTANCE);
		drivers.add(MapConverter.INSTANCE);
		drivers.addAll(extraDrivers);
		return new CachedObjectConverter(drivers);
	}
	

	private final List<ConverterDriver> drivers;
	
	private final ThreadLocal<ObjectGraphCache> cache;
	

	/**
	 * インスタンスを生成する。
	 * @param drivers この変換器に登録するドライバの一覧
	 */
	private CachedObjectConverter(List<ConverterDriver> drivers) {
		super();
		assert drivers != null;
		this.drivers = new ArrayList<ConverterDriver>(drivers);
		cache = new ThreadLocal<ObjectGraphCache>() {
			
			@Override
			protected ObjectGraphCache initialValue() {
				return new ObjectGraphCache();
			}
		};
	}
	
	public Value convert(Object object) {
		// ターミナルに変換される(循環構造を持たない)ことが自明であるものだけ先に処理
		for (ConverterDriver drv : TERMINALS) {
			Value result = drv.convert(object, this);
			if (result != null) {
				return result;
			}
		}
		
		// コピーを検出する。
		ObjectGraphCache graph = cache.get();
		int number = graph.enter(object);
		try {
			if (number >= 0) {
				return Terminal.of(String.format("#COPY-%d (%s)", number, object.getClass().getName()));
			}
			for (ConverterDriver drv : drivers) {
				Value result = drv.convert(object, this);
				if (result != null) {
					return result;
				}
			}
		} finally {
			if (number < 0) {
				graph.exit(object);
			}
		}
		
		// 最後に汎用の変換機で変換
		return GeneralObjectConverter.INSTANCE.convert(object);
	}
}

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
import java.util.List;

import org.jiemamy.utils.gtree.model.Value;

/**
 * {@link ConverterDriver}の組み合わせによる{@link ObjectConverter}の実装。
 * @version $Date: 2009-09-21 02:27:46 +0900 (月, 21  9 2009) $
 * @author Suguru ARAKAWA
 */
public class DefaultObjectConverter implements ObjectConverter {
	
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
	 * @param extraDrivers ユーザ定義のドライバ一覧
	 * @return 生成したオブジェクト
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public static DefaultObjectConverter newInstance(List<? extends ConverterDriver> extraDrivers) {
		if (extraDrivers == null) {
			throw new NullPointerException("extraDrivers"); //$NON-NLS-1$
		}
		List<ConverterDriver> drivers = new ArrayList<ConverterDriver>();
		drivers.add(PrimitiveConverter.INSTANCE);
		drivers.add(EnumConverter.INSTANCE);
		drivers.add(ArrayConverter.INSTANCE);
		drivers.add(CollectionConverter.INSTANCE);
		drivers.add(MapConverter.INSTANCE);
		drivers.addAll(extraDrivers);
		drivers.add(GeneralObjectConverter.INSTANCE);
		return new DefaultObjectConverter(drivers);
	}
	

	private final List<ConverterDriver> drivers;
	

	/**
	 * インスタンスを生成する。
	 * <p>
	 * ドライバは引数{@code drivers}に格納された順序で登録され、
	 * 順序が若いドライバほど優先して適用される。
	 * ただし、あるドライバがオブジェクトを変換できなかった場合、この変換器は次のドライバによって
	 * オブジェクトの変換を試みる。
	 * すべてのドライバによって変換できない場合、この変換器の
	 * {@link #convert(Object)}メソッドは結果として{@code null}を返す。
	 * </p>
	 * <p>
	 * このコンストラクタによるインスタンス生成では、標準的なドライバセットはひとつも登録されない。
	 * 標準的なドライバセットの登録を自動的に行う場合、
	 * {@link #newInstance(List)}を利用する。
	 * </p>
	 * @param drivers この変換器に登録するドライバの一覧
	 * @throws NullPointerException 引数に{@code null}が指定された場合
	 */
	public DefaultObjectConverter(List<ConverterDriver> drivers) {
		super();
		if (drivers == null) {
			throw new NullPointerException("drivers"); //$NON-NLS-1$
		}
		this.drivers = new ArrayList<ConverterDriver>(drivers);
	}
	
	public Value convert(Object object) {
		for (ConverterDriver drv : drivers) {
			Value result = drv.convert(object, this);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}

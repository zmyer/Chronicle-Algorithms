/*
 *     Copyright (C) 2015  higherfrequencytrading.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.openhft.chronicle.algo.bytes;

import java.lang.reflect.Field;

final class Java9HotSpotStringAccessor implements Accessor.Read<String, byte[]> {
    public static final Java9HotSpotStringAccessor INSTANCE = new Java9HotSpotStringAccessor();

    private static final long valueOffset;

    static {
        try {
            Field valueField = String.class.getDeclaredField("value");
            valueOffset = NativeAccess.U.objectFieldOffset(valueField);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    private Java9HotSpotStringAccessor() {
    }

    @Override
    public ReadAccess<byte[]> access() {
        return NativeAccess.instance();
    }

    @Override
    public byte[] handle(String source) {
        return (byte[]) NativeAccess.U.getObject(source, valueOffset);
    }

    @Override
    public long offset(String source, long index) {
        return ArrayAccessors.Byte.INSTANCE.offset(null, index);
    }

    @Override
    public long size(long size) {
        return size * 2L;
    }
}

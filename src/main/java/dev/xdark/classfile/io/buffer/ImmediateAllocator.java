package dev.xdark.classfile.io.buffer;

import java.nio.ByteBuffer;

interface ImmediateAllocator extends ByteBufferAllocator {

    @Override
    default ByteBuffer reallocate(ByteBuffer buffer, int size) {
        return allocate(size);
    }
}

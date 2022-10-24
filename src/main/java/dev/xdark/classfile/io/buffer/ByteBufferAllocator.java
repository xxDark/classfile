package dev.xdark.classfile.io.buffer;

import java.nio.ByteBuffer;

/**
 * {@link ByteBuffer} allocator.
 *
 * @author xDark
 */
public interface ByteBufferAllocator {
    ByteBufferAllocator HEAP = (ImmediateAllocator) ByteBuffer::allocate;
    ByteBufferAllocator DIRECT = (ImmediateAllocator) ByteBuffer::allocateDirect;

    /**
     * @param size Buffer size.
     * @return Allocated buffer.
     */
    ByteBuffer allocate(int size);

    ByteBuffer reallocate(ByteBuffer buffer, int size);
}

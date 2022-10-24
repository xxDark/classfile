package dev.xdark.classfile.io.buffer;

import dev.xdark.classfile.io.Output;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

/**
 * Byte buffer output.
 */
public final class ByteBufferOutput implements Output {

    private static final ByteBuffer EMPTY = ByteBuffer.wrap(new byte[0]);

    private final ByteBufferAllocator allocator;
    private ByteBuffer buffer = EMPTY;

    /**
     * @param allocator Buffer allocator.
     */
    public ByteBufferOutput(ByteBufferAllocator allocator) {
        this.allocator = allocator;
    }

    /**
     * @return Resulting buffer.
     */
    public ByteBuffer consume() {
        ByteBuffer buffer = this.buffer;
        buffer.flip();
        return buffer;
    }

    @Override
    public int position() {
        return buffer.position();
    }

    @Override
    public Output position(int position) {
        buffer.position(position);
        return this;
    }

    @Override
    public void write(int b) {
        ensureWriteable(1).put((byte) b);
    }

    @Override
    public void write(byte[] b) {
        ensureWriteable(b.length).put(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        ensureWriteable(len).put(b, off, len);
    }

    @Override
    public void writeBoolean(boolean v) {
        ensureWriteable(1).put((byte) (v ? 1 : 0));
    }

    @Override
    public void writeByte(int v) {
        ensureWriteable(1).put((byte) v);
    }

    @Override
    public void writeShort(int v) {
        ensureWriteable(2).putShort((short) v);
    }

    @Override
    public void writeChar(int v) {
        ensureWriteable(2).putChar((char) v);
    }

    @Override
    public void writeInt(int v) {
        ensureWriteable(4).putInt(v);
    }

    @Override
    public void writeLong(long v) {
        ensureWriteable(8).putLong(v);
    }

    @Override
    public void writeFloat(float v) {
        ensureWriteable(4).putFloat(v);
    }

    @Override
    public void writeDouble(double v) {
        ensureWriteable(8).putDouble(v);
    }

    @Override
    public void writeBytes(String s) {
        int len = s.length();
        ByteBuffer buf = ensureWriteable(len);
        for (int i = 0; i < len; i++) {
            buf.put((byte) s.charAt(i));
        }
    }

    @Override
    public void writeChars(String s) {
        int len = s.length();
        ByteBuffer buf = ensureWriteable(len * 2);
        for (int i = 0; i < len; i++) {
            int v = s.charAt(i);
            buf.put((byte) (v >>> 8));
            buf.put((byte) (v & 0xFF));
        }
    }

    @Override
    public void writeUTF(String s) {
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        CharBuffer cb = CharBuffer.wrap(s);
        // Each character will take up
        // at least 1 byte if all string is ASCII encoded,
        // so preallocate larger buffer.
        ByteBuffer buffer = ensureWriteable(2 + s.length());
        int position = buffer.position();
        // Add dummy length, it will be replaced
        // later after whole string is encoded.
        buffer.putShort((short) 0);
        while (true) {
            CoderResult result = encoder.encode(cb, buffer, true);
            if (result.isUnderflow()) {
                if (cb.hasRemaining()) {
                    throw new IllegalStateException("Buffer must have no data left");
                }
                int newPosition = buffer.position();
                buffer.putShort(position, (short) (newPosition - position - 2));
                break;
            } else if (result.isOverflow()) {
                // Encoder might overflow if there are non-ASCII characters
                // in the string, take a guess of how much more data we have to encode.
                buffer = ensureWriteable(Math.max((int) encoder.averageBytesPerChar() * cb.remaining(), 64));
                continue;
            }
            throw new IllegalStateException("Unexpected coder result: " + result);
        }
    }

    private ByteBuffer ensureWriteable(int size) {
        ByteBuffer buffer = this.buffer;
        if (buffer.remaining() < size) {
            int pos = buffer.position();
            size = Integer.highestOneBit(pos + size - 1) << 1;
            if (size == 0) {
                size = 16;
            }
            ByteBuffer newBuffer = allocator.allocate(size);
            buffer.position(0).limit(pos);
            newBuffer.put(buffer);
            buffer = newBuffer;
            this.buffer = buffer;
        }
        return buffer;
    }
}

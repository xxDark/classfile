package dev.xdark.classfile.io.buffer;

import dev.xdark.classfile.io.Input;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Byte buffer input.
 *
 * @author xDark
 */
public final class ByteBufferInput implements Input {

    private final ByteBuffer buffer;

    public ByteBufferInput(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public byte[] read(int length) throws IOException {
        checkRemaining(length);
        byte[] buf = new byte[length];
        readFully(buf);
        return buf;
    }

    @Override
    public boolean hasRemaining() {
        return buffer.hasRemaining();
    }

    @Override
    public int position() {
        return buffer.position();
    }

    @Override
    public Input position(int position) throws IOException {
        ByteBuffer buffer = this.buffer;
        if (buffer.limit() < position) {
            throw new EOFException();
        }
        buffer.position(position);
        return this;
    }

    @Override
    public void readFully(byte[] b) throws IOException {
        checkRemaining(b.length);
        buffer.get(b);
    }

    @Override
    public void readFully(byte[] b, int off, int len) throws IOException {
        checkRemaining(len);
        buffer.get(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        ByteBuffer buffer = this.buffer;
        int skipped = Math.min(n, buffer.remaining());
        buffer.position(buffer.position() + skipped);
        return skipped;
    }

    @Override
    public boolean readBoolean() throws IOException {
        return checkRemaining(1).get() != 0;
    }

    @Override
    public byte readByte() throws IOException {
        return checkRemaining(1).get();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return readByte() & 0xFF;
    }

    @Override
    public short readShort() throws IOException {
        return checkRemaining(2).getShort();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return readShort() & 0xFFFF;
    }

    @Override
    public char readChar() throws IOException {
        return (char) readUnsignedShort();
    }

    @Override
    public int readInt() throws IOException {
        return checkRemaining(4).getInt();
    }

    @Override
    public long readLong() throws IOException {
        return checkRemaining(8).getLong();
    }

    @Override
    public float readFloat() throws IOException {
        return checkRemaining(4).getFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return checkRemaining(8).getDouble();
    }

    @Override
    public String readLine() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String readUTF() throws IOException {
        int length = readUnsignedShort();
        ByteBuffer buffer = checkRemaining(length);
        ByteBuffer slice = buffer.slice().order(buffer.order());
        slice.limit(length);
        CharBuffer result = StandardCharsets.UTF_8.newDecoder().decode(slice);
        buffer.position(buffer.position() + slice.position());
        return result.toString();
    }

    private ByteBuffer checkRemaining(int length) throws EOFException {
        ByteBuffer buffer = this.buffer;
        if (buffer.remaining() < length) {
            throw new EOFException();
        }
        return buffer;
    }
}

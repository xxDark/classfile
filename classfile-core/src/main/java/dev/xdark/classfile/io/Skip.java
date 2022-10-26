package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;

/**
 * Skips read operation.
 *
 * @author xDark
 */
@FunctionalInterface
public interface Skip {

    /**
     * @param input Input to skip bytes in.
     * @throws IOException If any I/O error occurs.
     */
    void skip(@NotNull Input input) throws IOException;

    /**
     * Makes this skip context-aware, ignoring
     * the actual context.
     *
     * @return Skip.
     */
    default <CTX> ContextSkip<CTX> contextAwareSkip() {
        return (input, ctx) -> skip(input);
    }

    /**
     * @param next Next skip.
     * @return Skip chain.
     */
    default Skip then(Skip next) {
        return input -> {
            skip(input);
            next.skip(input);
        };
    }

    /**
     * @param numBytes Skips the exact amount of bytes.
     * @return Skip.
     */
    static Skip exact(int numBytes) {
        return input -> {
            int skipped = input.skipBytes(numBytes);
            if (skipped != numBytes) {
                throw new EOFException(skipped + " <> " + numBytes);
            }
        };
    }

    /**
     * Skips variable amount of bytes.
     *
     * @param lengthSkip Length reader.
     * @return Skip.
     */
    static Skip length(LengthSkip lengthSkip) {
        return input -> input.skipBytes(lengthSkip.readLength(input));
    }

    /**
     * @return Shortcut skip for {@code length(Input::readInt)}.
     */
    static Skip u32() {
        return length(DataInput::readInt);
    }

    /**
     * @return Shortcut skip for {@code length(Input::readUnsignedShort)}.
     */
    static Skip u16() {
        return length(DataInput::readUnsignedShort);
    }

    /**
     * Skips an array of unsigned shorts.
     *
     * @param length Length reader.
     * @return Skip.
     */
    static Skip u16Array(LengthSkip length) {
        return input -> input.skipBytes(length.readLength(input) * 2);
    }

    @FunctionalInterface
    interface LengthSkip {
        /**
         * @param input Input to read from.
         * @return Read length of how much bytes to skip.
         * @throws IOException If any I/O error occurs.
         */
        int readLength(@NotNull Input input) throws IOException;

        /**
         * @return Length based on unsigned short.
         */
        static LengthSkip u16() {
            return DataInput::readUnsignedShort;
        }
    }
}

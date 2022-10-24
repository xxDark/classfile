package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Composed decoder and encoder.
 *
 * @param <T> Instance type.
 * @author xDark
 */
public interface Codec<T> extends Decoder<T>, Encoder<T> {

    /**
     * Makes this codec context-aware, ignoring
     * the actual context.
     * @return Composed codec.
     */
    default <D_CTX, E_CTX> ContextCodec<T, D_CTX, E_CTX> asContextAware() {
        return new ContextCodec<T, D_CTX, E_CTX>() {
            @Override
            public @NotNull T read(@NotNull Input input, @NotNull D_CTX d_ctx) throws IOException {
                return Codec.this.read(input);
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value, @NotNull E_CTX e_ctx) throws IOException {
                Codec.this.write(output, value);
            }
        };
    }

    /**
     * @param decoder Decoder.
     * @param encoder Encoder.
     * @return Composed codec.
     */
    static <T> Codec<T> of(Decoder<T> decoder, Encoder<T> encoder) {
        return new Codec<T>() {
            @Override
            public @NotNull T read(@NotNull Input input) throws IOException {
                return decoder.read(input);
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value) throws IOException {
                encoder.write(output, value);
            }
        };
    }

    /**
     * Codec for singleton value.
     *
     * @param instance Singleton instance.
     * @return New codec.
     */
    static <T> Codec<T> singleton(T instance) {
        return new Codec<T>() {
            @Override
            public @NotNull T read(@NotNull Input input) throws IOException {
                return instance;
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value) throws IOException {
                // no-op
            }
        };
    }
}
package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Composed decoder and encoder.
 *
 * @param <T> Instance type.
 * @author xDark
 */
public interface Codec<T>
        extends Decoder<T>,
        Encoder<T>,
        Skip {

    /**
     * Makes this codec context-aware, ignoring
     * the actual context.
     *
     * @return Composed codec.
     */
    default <D_CTX, E_CTX> ContextCodec<T, D_CTX, E_CTX> contextAwareCodec() {
        return new ContextCodec<T, D_CTX, E_CTX>() {
            @Override
            public @NotNull T read(@NotNull Input input, @NotNull D_CTX d_ctx) throws IOException {
                return Codec.this.read(input);
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value, @NotNull E_CTX e_ctx) throws IOException {
                Codec.this.write(output, value);
            }

            @Override
            public void skip(@NotNull Input input, D_CTX d_ctx) throws IOException {
                Codec.this.skip(input);
            }
        };
    }

    /**
     * @param decoder Decoder.
     * @param encoder Encoder.
     * @param skip    Skip.
     * @return Composed codec.
     */
    static <T> Codec<T> of(Decoder<T> decoder, Encoder<T> encoder, Skip skip) {
        return new Codec<T>() {
            @Override
            public @NotNull T read(@NotNull Input input) throws IOException {
                return decoder.read(input);
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value) throws IOException {
                encoder.write(output, value);
            }

            @Override
            public void skip(@NotNull Input input) throws IOException {
                skip.skip(input);
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

            @Override
            public void skip(@NotNull Input input) throws IOException {
            }
        };
    }
}

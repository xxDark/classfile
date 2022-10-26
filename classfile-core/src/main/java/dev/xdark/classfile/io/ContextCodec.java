package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Context-aware codec.
 *
 * @author xDark
 */
public interface ContextCodec<T, D_CTX, E_CTX>
        extends ContextDecoder<T, D_CTX>,
        ContextEncoder<T, E_CTX>,
        ContextSkip<D_CTX> {

    /**
     * @param decoder Decoder.
     * @param encoder Encoder.
     * @return Composed codec.
     */
    static <T, D_CTX, E_CTX> ContextCodec<T, D_CTX, E_CTX> of(@NotNull ContextDecoder<T, D_CTX> decoder, @NotNull ContextEncoder<T, E_CTX> encoder, ContextSkip<D_CTX> skip) {
        return new ContextCodec<T, D_CTX, E_CTX>() {
            @Override
            public @NotNull T read(@NotNull Input input, @NotNull D_CTX d_ctx) throws IOException {
                return decoder.read(input, d_ctx);
            }

            @Override
            public void write(@NotNull Output output, @NotNull T value, @NotNull E_CTX e_ctx) throws IOException {
                encoder.write(output, value, e_ctx);
            }

            @Override
            public void skip(@NotNull Input input, D_CTX d_ctx) throws IOException {
                skip.skip(input, d_ctx);
            }
        };
    }
}

package dev.xdark.classfile.io;

import java.io.IOException;

/**
 * Context-aware codec.
 *
 * @author xDark
 */
public interface ContextCodec<T, D_CTX, E_CTX> extends ContextDecoder<T, D_CTX>, ContextEncoder<T, E_CTX> {

    /**
     * @param decoder Decoder.
     * @param encoder Encoder.
     * @return Composed codec.
     */
    static <T, D_CTX, E_CTX> ContextCodec<T, D_CTX, E_CTX> of(ContextDecoder<T, D_CTX> decoder, ContextEncoder<T, E_CTX> encoder) {
        return new ContextCodec<T, D_CTX, E_CTX>() {
            @Override
            public T read(Input input, D_CTX d_ctx) throws IOException {
                return decoder.read(input, d_ctx);
            }

            @Override
            public void write(Output output, T value, E_CTX e_ctx) throws IOException {
                encoder.write(output, value, e_ctx);
            }
        };
    }
}

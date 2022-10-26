package dev.xdark.classfile.io;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Context-aware skip.
 *
 * @author xDark
 */
@FunctionalInterface
public interface ContextSkip<CTX> {

    /**
     * @param input Input to skip bytes in.
     * @param ctx   Context.
     * @throws IOException If any I/O error occurs.
     */
    void skip(@NotNull Input input, CTX ctx) throws IOException;

    /**
     * @param next Next skip.
     * @return Skip chain.
     */
    default ContextSkip<CTX> then(ContextSkip<CTX> next) {
        return (input, ctx) -> {
            skip(input, ctx);
            next.skip(input, ctx);
        };
    }
}

package com.xiaoxiao.nio.sourcecode;

public interface CompletionHandler<V,A> {

    /**
     * 异步操作完成时调用。
     *
     * @param   result
     *          I/O操作的结果.
     * @param   attachment
     *          操作时附加到I/O操作的对象。
     */
    void completed(V result, A attachment);

    /**
     * 异步操作失败时调用。
     *
     * @param   exc
     *          I/O操作失败原因的异常
     * @param   attachment
     *          操作时附加到I/O操作的对象。
     */
    void failed(Throwable exc, A attachment);
}

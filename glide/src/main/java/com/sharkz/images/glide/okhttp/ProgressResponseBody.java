package com.sharkz.images.glide.okhttp;

import androidx.annotation.NonNull;

import com.sharkz.images.glide.listener.OnProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  17:33
 * 描    述 这里做的是将响应体拦截做处理在返回 图片下载进度 响应体
 * 修订历史：
 * ================================================
 */
public class ProgressResponseBody extends ResponseBody {

    private String imageUrl;                        // 图片下载地址
    private ResponseBody responseBody;              // 响应体
    private OnProgressListener progressListener;    // 进度监听器

    /**
     * 一种源代码，它在内部保留一个缓冲区，以便调用者可以进行少量读取而不会影响性能。它还允许客户端提前读取，
     * 在使用输入之前进行必要的缓冲。
     */
    private BufferedSource bufferedSource;


    // =============================================================================================


    public ProgressResponseBody(String url, ResponseBody responseBody, OnProgressListener progressListener) {
        this.imageUrl = url;
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }


    // =============================================================================================


    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }


    // =============================================================================================


    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += (bytesRead == -1) ? 0 : bytesRead;

                if (progressListener != null) {
                    progressListener.onProgress(
                            imageUrl,               // 图片地址
                            totalBytesRead,         // 下载了多少字节
                            contentLength(),        // 总共的大小
                            (bytesRead == -1),      // 是否完成
                            null);        // 异常
                }
                return bytesRead;
            }
        };
    }


}

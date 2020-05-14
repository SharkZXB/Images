package com.sharkz.glide.okhttp;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.GlideException;
import com.sharkz.glide.listener.OnProgressListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  17:32
 * 描    述 图片下载进度管理器 返回了一个 OKHttp对象
 * 修订历史：
 * ================================================
 */
public class ProgressManager {

    /**
     * 这里是用了弱引用 保存监听器  对象
     */
    private static List<WeakReference<OnProgressListener>>
            listeners = Collections.synchronizedList(new ArrayList<WeakReference<OnProgressListener>>());

    /**
     * 网络请求客户端
     */
    private static OkHttpClient okHttpClient;


    private ProgressManager() {

    }

    /**
     * OkHttp 请求对象
     */
    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() { // 添加了一个网络请求拦截器
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request request = chain.request();              // 请求
                            Response response = chain.proceed(request);     // 响应

                            return response.newBuilder()
                                    .body(new ProgressResponseBody(request.url().toString(), response.body(), LISTENER))
                                    .build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }

    /**
     * 下载进度监听器 集中监听
     */
    private static final OnProgressListener LISTENER = new OnProgressListener() {
        @Override
        public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception) {
            if (listeners == null || listeners.size() == 0) return;
            // 分发
            for (int i = 0; i < listeners.size(); i++) {
                WeakReference<OnProgressListener> listener = listeners.get(i);
                OnProgressListener progressListener = listener.get();
                if (progressListener == null) {
                    listeners.remove(i);
                } else {
                    progressListener.onProgress(imageUrl, bytesRead, totalBytes, isDone, exception);
                }
            }
        }
    };

    /**
     * 添加监听器
     *
     * @param progressListener 下载进度监听器
     */
    public static void addProgressListener(OnProgressListener progressListener) {
        if (progressListener == null) return;
        if (findProgressListener(progressListener) == null) {
            listeners.add(new WeakReference<>(progressListener));
        }
    }

    /**
     * 移除监听器
     *
     * @param progressListener 下载监听器
     */
    public static void removeProgressListener(OnProgressListener progressListener) {
        if (progressListener == null) return;
        WeakReference<OnProgressListener> listener = findProgressListener(progressListener);
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    /**
     * 若引用
     *
     * @param listener 监听器
     * @return
     */
    private static WeakReference<OnProgressListener> findProgressListener(OnProgressListener listener) {
        if (listener == null) return null;
        if (listeners == null || listeners.size() == 0) return null;
        for (int i = 0; i < listeners.size(); i++) {
            WeakReference<OnProgressListener> progressListener = listeners.get(i);
            if (progressListener.get() == listener) {
                return progressListener;
            }
        }
        return null;
    }

}

package com.sharkz.images.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.sharkz.images.glide.config.GlideApp;
import com.sharkz.images.glide.config.GlideImageConfig;
import com.sharkz.images.glide.listener.GlideImageListener;
import com.sharkz.images.glide.okhttp.ImageSize;
import com.sharkz.images.glide.listener.OnProgressListener;
import com.sharkz.images.glide.okhttp.ProgressManager;
import com.sharkz.images.glide.tranform.BlurBitmapTransformation;
import com.sharkz.images.glide.tranform.GlideCircleTransformation;
import com.sharkz.images.glide.tranform.RoundBitmapTransformation;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  17:17
 * 描    述 基于Glide再次封装的图片加载器
 * 修订历史：
 * ================================================
 */
public class GlideImage {

    private static GlideImage glideImage;

    private GlideImage() {
        if (GlideImageConfig.placeholderResId < 0) {
            GlideImageConfig.setErrorResId(R.drawable.error);
            GlideImageConfig.setPlaceholderResId(R.drawable.placeholder);
        }
    }

    public static GlideImage getInstance() {
        if (glideImage == null) {
            synchronized (GlideImage.class) {
                if (glideImage == null) glideImage = new GlideImage();
            }
        }
        return glideImage;
    }


    // =============================================================================================
    // =============================================================================================
    // =============================================================================================


    /**
     * 当前 Context 下 Glide暂停加载:
     *
     * @param context 当前调用界面
     */
    public void pauseRequests(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    /**
     * 当前 Context 下 Glide恢复加载:
     *
     * @param context 当前调用界面
     */
    public void resumeRequests(Context context) {
        GlideApp.with(context).resumeRequests();
    }

    /**
     * 取消下载
     * 生命周期：与传入的context对象共生命周期，如果是activity或者fragment，当销毁时，自动取消下载（加载图片）
     *
     * @param context 上下文
     * @param view    显示图片的View
     */
    public void cancel(Context context, View view) {
        GlideApp.with(context).clear(view);
    }


    // =============================================================================================
    // =============================================================================================
    // =============================================================================================


    /**
     * 加载资源图片
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     * DiskCacheStrategy.ALL 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
     * DiskCacheStrategy.NONE 不使用磁盘缓存
     * DiskCacheStrategy.DATA 在资源解码前就将原始数据写入磁盘缓存
     * DiskCacheStrategy.RESOURCE 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
     * DiskCacheStrategy.AUTOMATIC 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。
     */

    /**
     * 取消加载
     *
     * @param activity
     * @param imageView
     */
    public void clear(Activity activity, ImageView imageView) {
        GlideApp.with(activity).clear(imageView);
    }

    public void clear(Context context, ImageView imageView) {
        GlideApp.with(context).clear(imageView);
    }

    public void clear(Fragment fragment, ImageView imageView) {
        GlideApp.with(fragment).clear(imageView);
    }

    /**
     * 暂停加载
     *
     * @param context 上下文
     */
    public void glidePauseRequests(Context context) {
        GlideApp.with(context).pauseRequests();
    }

    public void glidePauseRequests(Activity activity) {
        GlideApp.with(activity).pauseRequests();
    }

    public void glidePauseRequests(Fragment fragment) {
        GlideApp.with(fragment).pauseRequests();
    }

    /**
     * 重新加载
     *
     * @param context 上下文
     */
    public void glideResumeRequests(Context context) {
        GlideApp.with(context).resumeRequests();
    }

    public void glideResumeRequests(Activity activity) {
        GlideApp.with(activity).resumeRequests();
    }

    public void glideResumeRequests(Fragment fragment) {
        GlideApp.with(fragment).resumeRequests();
    }


    // --------------------下面是加载资源图片----------------------------------------------------------
    // --------------------下面是加载资源图片----------------------------------------------------------
    // --------------------下面是加载资源图片----------------------------------------------------------


    /**
     * 加载资源文件
     *
     * @param context   上下文
     * @param resId     id
     * @param imageView into
     */
    public void displayImageInResource(Context context, int resId, ImageView imageView) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public void displayImageInResource(Fragment fragment, int resId, ImageView imageView) {
        GlideApp.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public void displayImageInResource(Activity activity, int resId, ImageView imageView) {
        GlideApp.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 加载资源文件的同时，对图片进行处理
     *
     * @param context
     * @param resId
     * @param imageView
     * @param transformations
     */
    public void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
    }

    public void displayImageInResource(Fragment fragment, int resId, ImageView imageView, BitmapTransformation transformations) {
        GlideApp.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
    }

    public void displayImageInResource(Activity activity, int resId, ImageView imageView, BitmapTransformation transformations) {
        GlideApp.with(activity).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).transform(transformations).into(imageView);
    }


    // --------------------下面是加载网络图片----------------------------------------------------------
    // --------------------下面是加载网络图片----------------------------------------------------------
    // --------------------下面是加载网络图片----------------------------------------------------------


    /**
     * 网络图片请求配置
     *
     * @param placeholderResId 占位图片资源ID
     * @param errorResId       错误显示资源ID
     * @return
     */
    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions().placeholder(placeholderResId).error(errorResId);
    }

    /**
     * 只从缓存中加载图片
     *
     * @param fragment  上下文
     * @param url       图片的地址
     * @param imageView 显示图片的控件
     */
    public void disPlayImageOnlyRetrieveFromCache(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment).load(url).onlyRetrieveFromCache(true).into(imageView);
    }

    public void disPlayImageOnlyRetrieveFromCache(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).onlyRetrieveFromCache(true).into(imageView);
    }

    public void disPlayImageOnlyRetrieveFromCache(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).onlyRetrieveFromCache(true).into(imageView);
    }

    /**
     * 不开启硬件加速
     *
     * @param fragment  上下文
     * @param url       图片的地址
     * @param imageView 显示图片的控件
     */
    public void disPlayImageDisallowHardwareConfig(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment).load(url).disallowHardwareConfig().into(imageView);
        // 第二种方法
        // RequestOptions options = new RequestOptions().disallowHardwareConfig();
        // Glide.with(fragment) .load(url) .apply(options) .into(imageView);
    }

    public void disPlayImageDisallowHardwareConfig(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).disallowHardwareConfig().into(imageView);
    }

    public void disPlayImageDisallowHardwareConfig(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).disallowHardwareConfig().into(imageView);
    }


    /**
     * 加载网络图片 默认缓存策略
     *
     * @param context   上下文
     * @param url       url
     * @param imageView in
     */
    public void displayImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }

    public void displayImage(Activity activity, String url, ImageView imageView) {
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView);
    }


    /**
     * 加载网络图片 是否缓存可控制
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 显示图片的控件
     * @param isCache   是否是缓存 如果是：缓存策略缓存原始数据  不是的话 ：缓存策略DiskCacheStrategy.NONE：什么都不缓存
     */
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        GlideApp.with(context).load(url).skipMemoryCache(isCache).diskCacheStrategy(isCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE).into(imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, boolean isCache) {
        GlideApp.with(fragment).load(url).skipMemoryCache(isCache).diskCacheStrategy(isCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE).into(imageView);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, boolean isCache) {
        GlideApp.with(activity).load(url).skipMemoryCache(isCache).diskCacheStrategy(isCache ? DiskCacheStrategy.AUTOMATIC : DiskCacheStrategy.NONE).into(imageView);
    }

    /**
     * 如果你想确保一个特定的请求跳过磁盘和/或内存缓存（比如，图片验证码 –）
     *
     * @param fragment         上下文
     * @param url              图片的地址
     * @param imageView        显示图片的控件
     * @param skipflag         是否跳过内存缓存
     * @param diskCacheStratey 是否跳过磁盘缓存
     */
    public void disPlayImage(Fragment fragment, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(diskCacheStratey ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC).skipMemoryCache(skipflag).into(imageView);
    }

    public void disPlayImage(Activity activity, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        GlideApp.with(activity).load(url).diskCacheStrategy(diskCacheStratey ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC).skipMemoryCache(skipflag).into(imageView);
    }

    public void disPlayImage(Context context, String url, ImageView imageView, boolean skipflag, boolean diskCacheStratey) {
        GlideApp.with(context).load(url).diskCacheStrategy(diskCacheStratey ? DiskCacheStrategy.NONE : DiskCacheStrategy.AUTOMATIC).skipMemoryCache(skipflag).into(imageView);
    }

    /**
     * 加载失败重新加载
     *
     * @param fragment
     * @param url
     * @param fallbackUrl
     * @param imageView
     */
    public void disPlayImageErrorReload(Fragment fragment, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(fragment).load(url).error(GlideApp.with(fragment).load(fallbackUrl)).into(imageView);
    }

    public void disPlayImageErrorReload(Activity activity, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(activity).load(url).error(GlideApp.with(activity).load(fallbackUrl)).into(imageView);
    }

    public void disPlayImageErrorReload(Context context, String url, String fallbackUrl, ImageView imageView) {
        GlideApp.with(context).load(url).error(GlideApp.with(context).load(fallbackUrl)).into(imageView);
    }


    // -----------------------------下面是加载加载缩略图-----------------------------------------------


    /**
     * thumbnail 方法有一个简化版本，它只需要一个 sizeMultiplier 参数。
     * 如果你只是想为你的加载相同的图片，但尺寸为 View 或 Target 的某个百分比的话特别有用：
     *
     * @param fragment
     * @param url
     * @param thumbnailSize
     * @param imageView
     */
    public void displayImageThumbnail(Fragment fragment, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(fragment).load(url).thumbnail(/*sizeMultiplier=*/ thumbnailSize).into(imageView);
        } else {
            throw new IllegalArgumentException("thumbnailSize 的值必须在0到1之间");
        }
    }

    public void displayImageThumbnail(Activity activity, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(activity).load(url).thumbnail(/*sizeMultiplier=*/ thumbnailSize).into(imageView);
        } else {
            throw new IllegalArgumentException("thumbnailSize 的值必须在0到1之间");
        }
    }

    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (thumbnailSize >= 0.0F && thumbnailSize <= 1.0F) {
            GlideApp.with(context)
                    .load(url)
                    .thumbnail(/*sizeMultiplier=*/ thumbnailSize)
                    .into(imageView);
        } else {
            throw new IllegalArgumentException("thumbnailSize 的值必须在0到1之间");
        }
    }


    /**
     * 先加载缩略图在加载原图
     *
     * @param context       上下文
     * @param url           图片url
     * @param backUrl       缩略图的url
     * @param thumbnailSize 如果需要放大放小的数值  越小，图片越小，低网络的情况，图片越小
     * @param imageView     显示图片的控件
     */
    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (thumbnailSize == 0) {
            GlideApp.with(context).load(url).thumbnail(Glide.with(context).load(backUrl)).into(imageView);
        } else {
            GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .thumbnail(GlideApp.with(context).load(backUrl).override(thumbnailSize)) // API 来强制 Glide 在缩略图请求中加载一个低分辨率图像
                    .into(imageView);
        }
    }

    public void displayImageThumbnail(Activity activity, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (thumbnailSize == 0) {
            GlideApp.with(activity).load(url).thumbnail(Glide.with(activity).load(backUrl)).into(imageView);
        } else {
            GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .thumbnail(GlideApp.with(activity).load(backUrl).override(thumbnailSize))// API 来强制 Glide 在缩略图请求中加载一个低分辨率图像
                    .into(imageView);
        }
    }

    public void displayImageThumbnail(Fragment fragment, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (thumbnailSize == 0) {
            GlideApp.with(fragment).load(url).thumbnail(Glide.with(fragment).load(backUrl)).into(imageView);
        } else {
            GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .thumbnail(GlideApp.with(fragment).load(backUrl).override(thumbnailSize)) // API 来强制 Glide 在缩略图请求中加载一个低分辨率图像
                    .into(imageView);
        }
    }


    // --------------------------下面是加载圆形图片----------------------------------------------------


    /**
     * 加载圆形图的请求
     *
     * @param placeholderResId
     * @param errorResId
     * @return
     */
    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId).transform(new GlideCircleTransformation());
    }

    /**
     * 圆形图片的裁剪
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 显示图片的控件
     * @param defRes    占位图片
     */
    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }

    public void displayCircleImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }

    public void displayCircleImage(Activity fragment, String url, ImageView imageView, int defRes) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(circleRequestOptions(defRes, defRes)).into(imageView);
    }


    // --------------------------下面是加载圆角图片----------------------------------------------------


    /**
     * 圆角图片转换
     *
     * @param placeholderResId
     * @param errorResId
     * @param radius
     * @return
     */
    public RequestOptions roundRequestOptions(int placeholderResId, int errorResId, int radius) {
        return requestOptions(placeholderResId, errorResId)
                .transform(new RoundBitmapTransformation(radius));
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param url       图片的地址
     * @param imageView 显示图片的控件
     * @param defRes    占位图片
     * @param radius    倒圆角的图片 需要传入需要radius  越大的话，倒角越明显
     */
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(roundRequestOptions(defRes, defRes, radius)).into(imageView);
    }

    public void displayRoundImage(Fragment fragment, String url, ImageView imageView, int defRes, int radius) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(roundRequestOptions(defRes, defRes, radius)).into(imageView);
    }

    public void displayRoundImage(Activity activity, String url, ImageView imageView, int defRes, int radius) {
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(roundRequestOptions(defRes, defRes, radius)).into(imageView);
    }


    // -----------------------------下面是加载指定大小的图片-------------------------------------------


    /**
     * 加载网络图片 指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes    placeholder(int resourceId). 设置资源加载过程中的占位Drawable  error(int resourceId).设置load失败时显示的Drawable
     * @param size      override(int width, int height). 重新设置Target的宽高值
     */
    public void displayImage(Context context, String url, ImageView imageView, int defRes, ImageSize size) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes)
                .override(size.getWidth(), size.getHeight()).into(imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, ImageSize size) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes)
                .override(size.getWidth(), size.getHeight()).into(imageView);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, int defRes, ImageSize size) {
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes)
                .override(size.getWidth(), size.getHeight()).into(imageView);
    }


    // --------------------------------下面是可以监听图片加载成功失败回调的------------------------------


    /**
     * 只在需要的地方进行监听 listener 通过自定义的接口回调参数
     *
     * @param context
     * @param url
     * @param imageView
     * @param listener  监听资源加载的请求状态 但不要每次请求都使用新的监听器，要避免不必要的内存申请，
     *                  可以使用单例进行统一的异常监听和处理
     */
    public void displayImage(Context context, final String url, final ImageView imageView, final GlideImageListener.IImageLoaderListener listener) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onLoadingFailed(url, imageView, e);
                // Log.e(TAG, "Load failed", e);//如果关系的话，关系如何失败
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onLoadingComplete(url, imageView);
                return false;
            }
        }).into(imageView);
    }

    public void displayImage(Fragment fragment, final String url, final ImageView imageView, final GlideImageListener.IImageLoaderListener listener) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onLoadingFailed(url, imageView, e);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onLoadingComplete(url, imageView);
                return false;
            }
        }).into(imageView);
    }

    public void displayImage(Context context, final String url, final ImageView imageView, int defRes, final GlideImageListener.IImageLoaderListener listener) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onLoadingFailed(url, imageView, e);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onLoadingComplete(url, imageView);
                return false;
            }
        }).into(imageView);
    }

    public void displayImage(Fragment fragment, final String url, final ImageView imageView, int defRes, final GlideImageListener.IImageLoaderListener listener) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).placeholder(defRes).error(defRes).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                listener.onLoadingFailed(url, imageView, e);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                listener.onLoadingComplete(url, imageView);
                return false;
            }
        }).into(imageView);
    }


    // --------------------------------下面是图片加载进度回调的-----------------------------------------


    public void disPlayImageProgress(Context context, final String url, ImageView imageView, int placeholderResId, int errorResId, final OnProgressListener listener) {
        // 添加进度监听器
        ProgressManager.addProgressListener(listener);
        // 加载图片
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.NONE) //todo 我是为了测试，看到进度条，才把缓存策略设置成这样的，项目中一定不要这样做
                .apply(new RequestOptions().placeholder(placeholderResId).error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }
                }).into(imageView);
    }

    public void disPlayImageProgress(Fragment fragment, final String url, ImageView imageView, int placeholderResId, int errorResId, final OnProgressListener listener) {
        // 添加进度监听器
        ProgressManager.addProgressListener(listener);
        // 加载图片
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.NONE) //todo 我是为了测试，看到进度条，才把缓存策略设置成这样的，项目中一定不要这样做
                .apply(new RequestOptions().placeholder(placeholderResId).error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }
                }).into(imageView);
    }

    public void disPlayImageProgress(Activity activity, final String url, ImageView imageView, int placeholderResId, int errorResId, final OnProgressListener listener) {
        // 添加进度监听器
        ProgressManager.addProgressListener(listener);
        // 加载图片
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.NONE) //todo 我是为了测试，看到进度条，才把缓存策略设置成这样的，项目中一定不要这样做
                .apply(new RequestOptions().placeholder(placeholderResId).error(errorResId))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressManager.removeProgressListener(listener);
                        return false;
                    }
                }).into(imageView);
    }


    // ----------------------------------------下面是加载高斯模糊的------------------------------------


    /**
     * 加载 高斯模糊
     *
     * @param context
     * @param url
     * @param blurRadius 模糊的程度 ，数字越大越模糊
     * @param listener   接口回调需要拿到drawable
     */
    public void displayBlurImage(Context context, String url, int blurRadius, final GlideImageListener.IGetDrawableListener listener) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) {
                    listener.onDrawable(resource);
                }
            }
        });
    }

    /**
     * 不需要关系此模糊图的drawable
     *
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param blurRadius
     */
    public void displayBlurImage(Context context, String url, ImageView imageView, int defRes, int blurRadius) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(defRes, defRes, blurRadius)).into(imageView);
    }

    public void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(resId, resId, blurRadius)).into(imageView);
    }

    public void displayBlurImage(Fragment fragment, String url, ImageView imageView, int defRes, int blurRadius) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(blurRequestOptions(defRes, defRes, blurRadius)).into(imageView);
    }

    private RequestOptions blurRequestOptions(int defRes, int defRes1, int blurRadius) {
        return requestOptions(defRes, defRes1)
                .transform(new BlurBitmapTransformation(blurRadius));
    }

    // ----------------------------------下面是自定义Transform的使用-----------------------------------


    public RequestOptions requestOptionsTransform(int placeholderResId, int errorResId, Transformation transformation) {
        return new RequestOptions().placeholder(placeholderResId).error(errorResId).transform(transformation);
    }

    public RequestOptions requestOptionsTransformation(int placeholderResId, int errorResId, BitmapTransformation bitmapTransformation) {
        return requestOptions(placeholderResId, errorResId).transform(bitmapTransformation);
    }

    /**
     * @param context
     * @param url
     * @param imageView
     * @param defRes
     * @param transformations bitmapTransform 方法设置图片转换
     */
    public void displayImage(Context context, String url, ImageView imageView, int defRes, final BitmapTransformation transformations) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransformation(defRes, defRes, transformations)).into(imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes, BitmapTransformation transformations) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransformation(defRes, defRes, transformations)).into(imageView);
    }

    public void displayImageByTransition(Context context, String url, TransitionOptions transitionOptions, ImageView imageView) {
        if (transitionOptions instanceof DrawableTransitionOptions) {
            GlideApp.with(context).load(url).transition((DrawableTransitionOptions) transitionOptions).into(imageView);
        } else {
            GlideApp.with(context).asBitmap().load(url).transition(transitionOptions).into(imageView);
        }
    }

    public void displayImageByTransition(Activity activity, String url, TransitionOptions transitionOptions, ImageView imageView) {
        if (transitionOptions instanceof DrawableTransitionOptions) {
            GlideApp.with(activity).load(url).transition((DrawableTransitionOptions) transitionOptions).into(imageView);
        } else {
            GlideApp.with(activity).asBitmap().load(url).transition(transitionOptions).into(imageView);
        }
    }

    public void displayImageByTransition(Fragment fragment, String url, TransitionOptions transitionOptions, ImageView imageView) {
        if (transitionOptions instanceof DrawableTransitionOptions) {
            GlideApp.with(fragment).load(url).transition((DrawableTransitionOptions) transitionOptions).into(imageView);
        } else {
            GlideApp.with(fragment).asBitmap().load(url).transition(transitionOptions).into(imageView);
        }
    }

    /**
     * @param fragment
     * @param resId
     * @param imageView
     * @param transformation 需要变换那种图像
     * @param errorResId
     */
    public void displayImageInResourceTransform(Activity fragment, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        GlideApp.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).apply(requestOptionsTransform(errorResId, errorResId, transformation)).into(imageView);
    }

    public void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        GlideApp.with(context).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).apply(requestOptionsTransform(errorResId, errorResId, transformation)).into(imageView);
    }

    public void displayImageInResourceTransform(Fragment fragment, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        GlideApp.with(fragment).load(resId).diskCacheStrategy(DiskCacheStrategy.NONE).apply(requestOptionsTransform(errorResId, errorResId, transformation)).into(imageView);
    }

    public void displayImageByNet(Context context, String url, ImageView imageView, int defRes, Transformation transformation) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransform(defRes, defRes, transformation)).into(imageView);
    }

    public void displayImageByNet(Fragment fragment, String url, ImageView imageView, int defRes, Transformation transformation) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransform(defRes, defRes, transformation)).into(imageView);
    }

    public void displayImageByNet(Activity activity, String url, ImageView imageView, int defRes, Transformation transformation) {
        GlideApp.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).apply(requestOptionsTransform(defRes, defRes, transformation)).into(imageView);
    }


    // ------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用.placeholder()方法在某些情况下会导致图片显示的时候出现图片变形的情况
     * 这是因为Glide默认开启的crossFade动画导致的TransitionDrawable绘制异常
     * 默认为200  时间有点长  ，工程中要修改下，设置一个加载失败和加载中的动画过渡，V4.0的使用的方法
     *
     * @param context   上下文
     * @param url       图片地址
     * @param imageView 显示图片的控件
     * @param defRes    defRes 可以是个new ColorDrawable(Color.BLACK) 也可以是张图片 默认的占位图片
     */
    public void displayImage(Context context, String url, ImageView imageView, int defRes) {
        GlideApp.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .placeholder(defRes).error(defRes).into(imageView);
    }

    public void displayImage(Fragment fragment, String url, ImageView imageView, int defRes) {
        GlideApp.with(fragment).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .placeholder(defRes).error(defRes)
                .into(imageView);
    }

    public void displayImage(Activity activity, String url, ImageView imageView, int defRes) {
        GlideApp.with(activity).load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .placeholder(defRes).error(defRes).into(imageView);
    }


    // ------------------------------------------------------------------------------------------------------------------------


    /**
     * 获取缓存中的图片
     *
     * @param context
     * @param url
     * @param listener
     */
    public void getBitmapFromCache(Context context, String url, final GlideImageListener.IGetBitmapListener listener) {
        GlideApp.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (listener != null) {
                    listener.onBitmap(resource);
                }
            }
        });
    }


    /**
     * 当传入到其他的东西的时候，我要保证图片不变形
     *
     * @param placeholderResId
     * @param errorResId
     * @param transformation
     * @return
     */
    public RequestOptions requestOptionsNoTransform(int placeholderResId, int errorResId, Transformation<Bitmap> transformation) {
        return new RequestOptions().placeholder(placeholderResId).error(errorResId).transform(transformation);
    }


}

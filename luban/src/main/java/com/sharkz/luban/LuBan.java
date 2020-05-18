package com.sharkz.luban;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/18  10:08
 * 描    述 图片压缩框架
 * 修订历史：
 * ================================================
 */
public class LuBan {

    // 方法列表
    // 方法 	                描述
    // load 	            传入原图
    // filter 	            设置开启压缩条件
    // ignoreBy 	        不压缩的阈值，单位为K
    // setFocusAlpha    	设置是否保留透明通道
    // setTargetDir 	    缓存压缩图片路径
    // setCompressListener 	压缩回调接口
    // setRenameListener 	压缩前重命名接口

    // TODO 异步调用
    // Luban内部采用IO线程进行图片压缩，外部调用只需设置好结果监听即可：
    // Luban.with(this)
    //        .load(photos)
    //        .ignoreBy(100)
    //        .setTargetDir(getPath())
    //        .filter(new CompressionPredicate() {
    //          @Override
    //          public boolean apply(String path) {
    //            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
    //          }
    //        })
    //        .setCompressListener(new OnCompressListener() {
    //          @Override
    //          public void onStart() {
    //            // 压缩开始前调用，可以在方法内启动 loading UI
    //          }
    //
    //          @Override
    //          public void onSuccess(File file) {
    //            // 压缩成功后调用，返回压缩后的图片文件
    //          }
    //
    //          @Override
    //          public void onError(Throwable e) {
    //            // 当压缩过程出现问题时调用
    //          }
    //        }).launch();

    // TODO 同步调用
    // 同步方法请尽量避免在主线程调用以免阻塞主线程，下面以rxJava调用为例
    // Flowable.just(photos)
    //    .observeOn(Schedulers.io())
    //    .map(new Function<List<String>, List<File>>() {
    //      @Override public List<File> apply(@NonNull List<String> list) throws Exception {
    //        // 同步方法直接返回压缩后的文件
    //        return Luban.with(MainActivity.this).load(list).get();
    //      }
    //    })
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe();


    // TODO 所有函数调用
    // Luban.with(this) // 上下文
    //                .load(photos)    // 需要压缩的图片
    //                .ignoreBy(100)   // 单位 KB 默认小于100KB 不压缩
    //                .setTargetDir(getPath()) // 压缩问价目标目录
    //                .setFocusAlpha(false) //
    //                .filter(new CompressionPredicate() { // 对于给定的输入路径，返回true或false的函数接口(回调)应该进行压缩。
    //                    @Override
    //                    public boolean apply(String path) {
    //                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
    //                    }
    //                })
    //                .setRenameListener(new OnRenameListener() { // 提供修改压缩图片命名接口
    //                    @Override
    //                    public String rename(String filePath) {
    //                        try {
    //                            MessageDigest md = MessageDigest.getInstance("MD5");
    //                            md.update(filePath.getBytes());
    //                            return new BigInteger(1, md.digest()).toString(32);
    //                        } catch (NoSuchAlgorithmException e) {
    //                            e.printStackTrace();
    //                        }
    //                        return "";
    //                    }
    //                })
    //                .setCompressListener(new OnCompressListener() { // 压缩监听回调
    //                    @Override
    //                    public void onStart() {
    //                        // 开始压缩任务
    //                    }
    //
    //                    @Override
    //                    public void onSuccess(File file) {
    //                        // 压缩成功
    //                        Log.i(TAG, file.getAbsolutePath());
    //                        showResult(originPhotos, file);
    //                    }
    //
    //                    @Override
    //                    public void onError(Throwable e) {
    //                        // 压缩异常
    //                    }
    //                }).launch(); // 开启压缩任务
}

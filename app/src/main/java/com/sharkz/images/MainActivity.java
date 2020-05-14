package com.sharkz.images;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.GlideException;
import com.sharkz.images.glide.GlideImage;
import com.sharkz.images.glide.listener.OnProgressListenerImpl;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  16:51
 * 描    述 当前 Module 图片显示
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView iv = findViewById(R.id.iv);
        String url="http://a2.att.hudong.com/36/48/19300001357258133412489354717.jpg";

        GlideImage.getInstance().disPlayImageProgress(this, url, iv, R.mipmap.ic_launcher, R.mipmap.ic_launcher, new OnProgressListenerImpl(url) {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                Log.i("Shark_TAG", "onProgress: percent="+percent+"---isDone="+isDone+"---exception="+exception);
            }
        });

    }

}

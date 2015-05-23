package cn.dong.demo;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MyApp extends Application {

    private static MyApp instantce;

    public static MyApp getInstance() {
        return instantce;
    }

    @Override
    public void onCreate() {
        instantce = this;
        initStrictMode();
        super.onCreate();
        initImageLoader();
        pathTest();
    }

    /**
     * 初始化StrictMode
     */
    private void initStrictMode() {
        if (Constants.Config.DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll()
                    .penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects().penaltyLog()
                    // .penaltyDeath()
                    .build());
        }
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DisplayImageOptions options =
                new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.empty_photo)
                        .imageScaleType(ImageScaleType.NONE).cacheInMemory(false).cacheOnDisc(true)
                        .build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(this);
        builder.defaultDisplayImageOptions(options);
        builder.threadPriority(Thread.NORM_PRIORITY - 1);
        builder.denyCacheImageMultipleSizesInMemory();
        builder.discCacheFileNameGenerator(new Md5FileNameGenerator());
        builder.tasksProcessingOrder(QueueProcessingType.LIFO);
        builder.writeDebugLogs();
        ImageLoaderConfiguration config = builder.build();
        ImageLoader.getInstance().init(config);
    }

    private void pathTest() {
        String IMG_DIR = getExternalFilesDir(null).getPath();
        Log.d("appliciton", "IMG_DIR = " + IMG_DIR);
    }

}
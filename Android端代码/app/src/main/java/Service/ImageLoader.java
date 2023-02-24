package Service;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.hellofood.R;

public class ImageLoader {

    //无占位图
    public static void Imageloadenoholder(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }
    //圆角头像
    public static void Imageloadecircle(Context context, String url, ImageView imageView){
        RequestOptions options = new RequestOptions().error(R.mipmap.image_load_error).bitmapTransform(new RoundedCorners(30));//图片圆角为30
        Glide.with(context).load(url) //图片地址
                .apply(options)
                .into(imageView);
    }
    //activity
    public static void Imageloadenoholder(Activity activity, String url, ImageView imageView){
        Glide.with(activity).load(url).into(imageView);
    }
    //有占位图
    public static void Imageloade(Context context, String url, ImageView imageView){
//        Glide.with(context)
//                .load(url)
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.mipmap.ic_launcher)
//                .fallback(R.mipmap.ic_launcher_round);
    }
    //图片裁剪
    public static void Imageloadecaijian(Context context, String url, ImageView imageView){
//        Glide.with(context)
//                .load(url)
//                .override(300,100);
//                 .fitCenter()
//                .centerCrop()
    }
    //磁盘缓存
    public static void diskcache(Context context, String url, ImageView imageView){
//        Glide.with(context)
//                .load(url)
//                .skipMemoryCache(true)
//                .diskCacheStrategy( DiskCacheStrategy.NONE )
//                .into(imageView);
    }
}

package com.hxing.hx.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * @Description描述:常用获取手机图片方法
 * @Author作者: hx
 */
public class GetImageUtils {
    /*从相机获取*/
    public static final int GET_IMAGE_BY_CAMERA = 5001;
    /*从图库获取*/
    public static final int GET_IMAGE_FROM_PHONE = 5002;
    /*裁剪图片*/
    public static final int CROP_IMAGE = 5003;
    /*裁剪图片的Uri*/
    public static Uri cropImageUri;
    /*拍照图片的Uri*/
    private static Uri imageUriFromCamera;

    /**
     * 打开照相机
     * @param activity
     */
    public static void openCameraImage(final Activity activity) {
        GetImageUtils.imageUriFromCamera = GetImageUtils
                .createImagePathUri(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                GetImageUtils.imageUriFromCamera);
        activity.startActivityForResult(intent,
                GetImageUtils.GET_IMAGE_BY_CAMERA);
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    private static Uri createImagePathUri(Context context) {
        String filePathOriginal = FileUtil.getPicCache(context)
                + Calendar.getInstance().getTimeInMillis()
                + ".jpg";
        return Uri.fromFile(new File(filePathOriginal));
    }

    /**
     * 打开本地图库
     * @param activity
     */
    public static void openLocalImage(final Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent,
                GetImageUtils.GET_IMAGE_FROM_PHONE);
    }

    /**
     * 裁剪图片
     * @param activity
     * @param srcUri
     */
    public static void cropImage(Activity activity, Uri srcUri) {
        GetImageUtils.cropImageUri = GetImageUtils.createImagePathUri(activity);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(
                Uri.parse("file://"
                        + FileUtil.getFilePathFromUri(activity, srcUri)),
                "image/*");
        intent.putExtra("crop", "true");

        // //////////////////////////////////////////////////////////////
        // 1.宽高和比例都不设置时,裁剪框可以自行调整(比例和大小都可以随意调整)
        // /////////////////////////////////////////////////////////
        // ///
        // 2.只设置裁剪框宽高比(aspect)后,裁剪框比例固定不可调整,只能调整大小
        // //////////////////////////////////////////////////////////////
        // 3.裁剪后生成图片宽高(output)的设置和裁剪框无关,只决定最终生成图片大小
        // //////////////////////////////////////////////////////////////
        // 4.裁剪框宽高比例(aspect)可以和裁剪后生成图片比例(output)不同,此时,
        // 会以裁剪框的宽为准,按照裁剪宽高比例生成一个图片,该图和框选部分可能不同,
        // 不同的情况可能是截取框选的一部分,也可能超出框选部分,向下延伸补足
        // //////////////////////////////////////////////////////////////

        // aspectX aspectY 是裁剪框宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪后生成图片的宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        // return-data为true时,会直接返回bitmap数据,但是大图裁剪时会出现问题,推荐下面为false时的方式
        // return-data为false时,不会返回bitmap,但需要指定一个MediaStore.EXTRA_OUTPUT保存图片uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, GetImageUtils.cropImageUri);
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, CROP_IMAGE);
    }

    public static Uri getImageUri(Intent data, int type) {
        if (type == GET_IMAGE_FROM_PHONE) {
            return data.getData();
        } else {
            return imageUriFromCamera;
        }
    }

    /**
     * 保存图片
     * @param buffer 图片资源的字节数组
     * @param path 保存地址
     */
    public static void saveImage(byte[] buffer, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(buffer);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

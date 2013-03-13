package com.barestodo.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: hp008
 * Date: 13/03/13
 * Time: 07:48
 * To change this template use File | Settings | File Templates.
 */
public class Gravatar {

    public static void setImageContentWithGravatar(ImageView img, String email) throws IOException {
        String stamp= Crypto.toMD5(email);
        String avatarUrl="http://www.gravatar.com/avatar/".concat(stamp).concat("?d=wavatar");
        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(avatarUrl).getContent());
        img.setImageBitmap(bitmap);
    }

}

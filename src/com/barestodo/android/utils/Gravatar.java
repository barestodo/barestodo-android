package com.barestodo.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
        new RetreiveGravatarTask(stamp,img).execute();
    }

    static class RetreiveGravatarTask extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;
        private final String stamp;
        private final ImageView imageView;

        public RetreiveGravatarTask(String stamp,ImageView view)  {
            this.stamp=stamp;
            this.imageView=view;
        }
        protected Bitmap doInBackground(String... urls) {
            try {
                String avatarUrl="http://www.gravatar.com/avatar/".concat(stamp).concat("?d=wavatar");
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(avatarUrl).getContent());
                return bitmap;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }


}

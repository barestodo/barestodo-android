package com.barestodo.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * permet d'interragir avec Gravatar, le site d'avatars
 */
public class Gravatar {

    public static void setImageContentWithGravatar(ImageView img, String email) throws IOException {
        String stamp= Crypto.toMD5(email);
        new RetreiveGravatarTask(stamp,img).execute();
    }

    public static void setImageContentWithGravatar(ImageView img, String email,int size) throws IOException {
        String stamp= Crypto.toMD5(email);
        new RetreiveGravatarTask(stamp,img,size).execute();
    }


    static class RetreiveGravatarTask extends AsyncTask<String, Void, Bitmap> {

        private final String stamp;
        private final ImageView imageView;
        private final int size;

        /**
         * cré une tâche asynchrone pour retrouver une image sur le site gravatar (avec un tailel de 80 par defaut)
         * @param stamp empreinte de l'adresse mail de l'utilisateur dont on souhaite récupérer le profil
         * @param image l'image view dotn il faut setter la source.
         */
        public RetreiveGravatarTask(String stamp,ImageView image)  {
            this(stamp, image,80);
        }
        /**
         * cré une tâche asynchrone pour retrouver une image sur le site gravatar
         * @param stamp empreinte de l'adresse mail de l'utilisateur dont on souhaite récupérer le profil
         * @param image l'image view dotn il faut setter la source.
         * @param size  la taile de l'image (la longueur en px d'un coté du carré)
         *
         */
        public RetreiveGravatarTask(String stamp,ImageView image,int size)  {
            this.stamp=stamp;
            this.imageView=image;
            this.size=size;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                StringBuilder builder=new StringBuilder("http://www.gravatar.com/avatar/").append(stamp);
                builder.append("?d=wavatar").append("&s=").append(size);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(builder.toString()).getContent());
                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }


}

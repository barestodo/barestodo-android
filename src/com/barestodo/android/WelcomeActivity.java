package com.barestodo.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.barestodo.android.security.IdentificationManager;
import com.barestodo.android.service.tasks.AsyncRetrievecurrentUserPseudonymeOperation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;


public class WelcomeActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncRetrievecurrentUserPseudonymeOperation operation=new AsyncRetrievecurrentUserPseudonymeOperation();
        operation.execute();

        setContentView(R.layout.activity_welcome);
        String email=IdentificationManager.INSTANCE.getEmail();
        String stamp=emailMd5Stamp(email);
        String avatarUrl="http://www.gravatar.com/avatar/".concat(stamp).concat("?d=wavatar");
        ImageView img= (ImageView) findViewById(R.id.avatar);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(avatarUrl).getContent());
            img.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String pseudonyme= null;
        try {
            pseudonyme = operation.get();
            knowedUser(pseudonyme);
        } catch (Exception e) {
            firstConnection();
        }

    }

    private void firstConnection() {

    }

    private void knowedUser(String pseudonyme) {
        TextView pseudo= (TextView) findViewById(R.id.pseudoLabel);
        pseudo.setText(pseudonyme);

        EditText pseudoInput= (EditText) findViewById(R.id.pseudoInput);
        pseudoInput.setVisibility(View.INVISIBLE);
    }

    private String emailMd5Stamp(String email) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(email.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
            } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
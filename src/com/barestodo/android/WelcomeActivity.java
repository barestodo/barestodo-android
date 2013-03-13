package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;
import com.barestodo.android.security.IdentificationManager;
import com.barestodo.android.service.tasks.AsyncRetrieveCurrentUserNameOperation;
import com.barestodo.android.service.tasks.AsyncSetCurrentUserNameOperation;
import com.barestodo.android.service.tasks.HttpStatus;
import com.barestodo.android.utils.Crypto;
import com.barestodo.android.utils.Gravatar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.barestodo.android.service.tasks.AsyncRetrieveCurrentUserNameOperation.UserNameReceiver;
import static com.barestodo.android.service.tasks.AsyncSetCurrentUserNameOperation.UserRegistrationReceiver;


public class WelcomeActivity extends Activity implements UserNameReceiver,UserRegistrationReceiver {

    public String userName;


    public void onCreate(Bundle savedInstanceState) {
        try{
                super.onCreate(savedInstanceState);

                AsyncRetrieveCurrentUserNameOperation operation=new AsyncRetrieveCurrentUserNameOperation(this);
                operation.execute();

                setContentView(R.layout.activity_welcome);
                ImageView img= (ImageView) findViewById(R.id.avatar);
                String email=IdentificationManager.INSTANCE.getEmail();
                Gravatar.setImageContentWithGravatar(img, email);

        }catch(Exception e){
            Toast.makeText(WelcomeActivity.this, "désarmement des tobogans",
                    Toast.LENGTH_LONG).show();
        }

    }


    private void formToRegisterNewUser() {
        showFormFields();
        final EditText input= (EditText) findViewById(R.id.pseudoInput);

        ImageButton btnValidePseudonyme= (ImageButton) findViewById(R.id.validatePseudoButton);
        btnValidePseudonyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncSetCurrentUserNameOperation operation = new AsyncSetCurrentUserNameOperation(input.getText().toString(),WelcomeActivity.this);
                operation.execute();
            }
        });
    }

    private void prepareToNextScreen(String pseudonyme) {
        TextView pseudo= (TextView) findViewById(R.id.pseudoLabel);
        pseudo.setVisibility(View.VISIBLE);
        pseudo.setText(pseudonyme);
        hideFormFields();
        SystemClock.sleep(5000);
        gotoCircleActivity();
    }

    private void hideFormFields() {
        findViewById(R.id.validatePseudoButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.pseudoInput).setVisibility(View.INVISIBLE);
    }
    private void showFormFields() {
        findViewById(R.id.validatePseudoButton).setVisibility(View.VISIBLE);
        findViewById(R.id.pseudoInput).setVisibility(View.VISIBLE);
    }


    @Override
    public void receiveUserName(String name) {
        userName=name;
        prepareToNextScreen(userName);
    }

    @Override
    public void onError(HttpStatus status) {
        if(status==HttpStatus.NOT_FOUND){
            formToRegisterNewUser();
        }else {
            Toast.makeText(WelcomeActivity.this,"votre compte n'a pu être retrouvé",
                Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void isRegistered() {
        gotoCircleActivity();
    }

    private void gotoCircleActivity() {
        Intent intent = new Intent(WelcomeActivity.this, CirclesListActivity.class);
        startActivity(intent);
    }
}
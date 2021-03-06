package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.barestodo.android.security.IdentificationManager;
import com.barestodo.android.service.tasks.AsyncRetrieveCurrentUserNameOperation;
import com.barestodo.android.service.tasks.AsyncSetCurrentUserNameOperation;
import com.barestodo.android.service.tasks.HttpStatus;
import com.barestodo.android.utils.Gravatar;

import static com.barestodo.android.R.drawable.detail_button;
import static com.barestodo.android.service.tasks.AsyncRetrieveCurrentUserNameOperation.UserNameReceiver;
import static com.barestodo.android.service.tasks.AsyncSetCurrentUserNameOperation.UserRegistrationReceiver;


public class WelcomeActivity extends Activity implements UserNameReceiver,UserRegistrationReceiver {

    private String userName;
    private Bitmap avatarBitmap;


    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome);
            ImageView img= (ImageView) findViewById(R.id.avatar);
            String email= IdentificationManager.INSTANCE.getEmail();
            if(userName==null){
               AsyncRetrieveCurrentUserNameOperation operation=new AsyncRetrieveCurrentUserNameOperation(this);
               operation.execute();
                avatarBitmap= Gravatar.getSyncGravatarImage(email, 200);
                img.setImageBitmap(avatarBitmap);
            }
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

    private void setLoginLabel(String pseudonyme) {
        hideFormFields();
        TextView pseudo= (TextView) findViewById(R.id.pseudoLabel);
        pseudo.setText(pseudonyme);
        pseudo.setVisibility(View.VISIBLE);
    }

    private void hideFormFields() {
        ImageButton button=(ImageButton)findViewById(R.id.validatePseudoButton);
        button.setImageResource(detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoCircleActivity();
            }
        });
        button.setVisibility(View.VISIBLE);
        findViewById(R.id.pseudoInput).setVisibility(View.INVISIBLE);
    }
    private void showFormFields() {
        findViewById(R.id.validatePseudoButton).setVisibility(View.VISIBLE);
        findViewById(R.id.pseudoInput).setVisibility(View.VISIBLE);
    }


    @Override
    public void receiveUserName(String name) {
        userName=name;
        setLoginLabel(userName);
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
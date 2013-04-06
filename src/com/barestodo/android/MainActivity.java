package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        goToNextPlace();
    }

    private void goToNextPlace() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

}

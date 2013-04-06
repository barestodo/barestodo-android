package com.barestodo.android;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class CircleContentActivity extends TabActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_content);
 
        Resources res = getResources(); 
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec; 
        Intent intent; 
 
		Bundle b = getIntent().getExtras();		
		intent = new Intent().setClass(this, CirclePlacesListActivity.class);
        intent.putExtras(b);
        spec = tabHost
                .newTabSpec("Lieux")
                .setIndicator(null,
                        res.getDrawable(R.drawable.check))
                .setContent(intent);

        tabHost.addTab(spec);
 
        intent = new Intent().setClass(this, CircleMemberActivity.class);
       
        intent.putExtras(b);
        spec = tabHost
                .newTabSpec("Membres")
                .setIndicator(null,
                        res.getDrawable(R.drawable.users))
                .setContent(intent);
        tabHost.addTab(spec);
 
        tabHost.setCurrentTab(0);
 
    }

}

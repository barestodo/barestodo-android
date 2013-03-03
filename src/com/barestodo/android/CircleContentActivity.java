package com.barestodo.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.support.v4.app.NavUtils;

public class CircleContentActivity extends TabActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_content);
 
        Resources res = getResources(); 
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec; 
        Intent intent; 
 
        intent = new Intent().setClass(this, CirclePlacesListActivity.class);
 
        spec = tabHost
                .newTabSpec("Widget")
                .setIndicator("DateTime",
                        res.getDrawable(android.R.drawable.ic_menu_today))
                .setContent(intent);
        tabHost.addTab(spec);
 
        intent = new Intent().setClass(this, CirclePlaceListActivity.class);
        spec = tabHost
                .newTabSpec("Form")
                .setIndicator("Form",
                        res.getDrawable(android.R.drawable.ic_menu_manage))
                .setContent(intent);
        tabHost.addTab(spec);
 
        tabHost.setCurrentTab(0);
 
    }

}

package com.barestodo.android;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;import com.barestodo.android.adapteur.CircleListAdapteur;


public class CirclesListActivity extends Activity {

    private ListView listView;
    private ImageButton addButton;
    private ListAdapter circleListAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles_list);
        listView = (ListView) findViewById(R.id.circlesListView);

        circleListAdapter=new CircleListAdapteur(this);
        listView.setAdapter(circleListAdapter);
        addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);
        listView.refreshDrawableState();
    }
}
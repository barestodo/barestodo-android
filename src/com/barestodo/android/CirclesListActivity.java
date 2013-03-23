package com.barestodo.android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.place.Circle;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.tasks.AsyncCreatePlaceOperation;
import com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncCreatePlaceOperation.*;
import static com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation.CirclesReceiver;


public class CirclesListActivity extends Activity implements CirclesReceiver {

    private static final int RETURN_CIRCLE_ID =1004 ;
    public static final String NEW_CIRCLE_CREATED = "circle.new.id";

    private ListView listView;
    private ImageButton addButton;
    private CircleListAdapteur circleListAdapter;
    private int nbTry=0;

    public void onCreate(Bundle savedInstanceState) {
        retrieveCircles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles_list);
        listView = (ListView) findViewById(R.id.circlesListView);

        circleListAdapter=new CircleListAdapteur();
        listView.setAdapter(circleListAdapter);

        addButton = (ImageButton) findViewById(R.id.createCircleImageButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CirclesListActivity.this,AddCircleActivity.class);
                startActivityForResult(intent, RETURN_CIRCLE_ID);
            }
        });
    }

    private void retrieveCircles() {
        new AsyncRetrieveCirclesOperation(this).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (RETURN_CIRCLE_ID) : {
                if (resultCode == Activity.RESULT_OK) {
                    Circle newCircle = (Circle) data.getSerializableExtra(NEW_CIRCLE_CREATED);
                    circleListAdapter.add(newCircle);
                    circleListAdapter.notifyDataSetInvalidated();
                }
                break;
            }
        }
    }

    @Override
    public void receiveCircles(List<Circle> result) {
        if(!result.isEmpty()){
            findViewById(R.id.tipsAddCircleLabel).setVisibility(View.INVISIBLE);
        }
        circleListAdapter.addAll(result);
        listView.invalidateViews();
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(CirclesListActivity.this, "Problème de communication avec le server",
                Toast.LENGTH_LONG).show();
       if(nbTry<3){
        retrieveCircles();
        nbTry++;
       }
    }

}
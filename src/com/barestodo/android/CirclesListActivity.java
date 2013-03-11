package com.barestodo.android;

import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.place.Circle;
import com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation.CirclesReceiver;


public class CirclesListActivity extends Activity implements CirclesReceiver {

    private ListView listView;
    private ImageButton addButton;
    private CircleListAdapteur circleListAdapter;


    public void onCreate(Bundle savedInstanceState) {
        retrieveCircles();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles_list);
        listView = (ListView) findViewById(R.id.circlesListView);

        circleListAdapter=new CircleListAdapteur();
        listView.setAdapter(circleListAdapter);

        addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);

    }

    private void retrieveCircles() {
        new AsyncRetrieveCirclesOperation(this).execute();
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
        Toast.makeText(CirclesListActivity.this, "Impossible de retrouver vos cercles (nous reéssayons)",
                Toast.LENGTH_LONG).show();
        retrieveCircles();
    }
}
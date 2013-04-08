package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.model.Circle;
import com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation.CirclesReceiver;


public class CirclesListActivity extends Activity implements CirclesReceiver {

    private static final int RETURN_CIRCLE_ID =1004 ;
    public static final String NEW_CIRCLE_CREATED = "circle.new.id";

    private ListView listView;
    private ImageButton addButton;
    private ImageButton refreshButton;
    private CircleListAdapteur circleListAdapter;
    private int nbTry=0;


    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        refreshButton = (ImageButton) findViewById(R.id.refreshCircleListImageButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

    }

    private void refresh() {
        refreshButton.setVisibility(View.INVISIBLE);
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
        refreshButton.setVisibility(View.VISIBLE);
        if(!result.isEmpty()){
            findViewById(R.id.tipsAddCircleLabel).setVisibility(View.INVISIBLE);
            circleListAdapter.set(result);
            listView.invalidateViews();
        }
    }

    @Override
    public void onError(HttpStatus status) {
        refreshButton.setVisibility(View.VISIBLE);
        Toast.makeText(CirclesListActivity.this, "Problème de communication avec le server",
                Toast.LENGTH_LONG).show();

    }

}
package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.adapteur.CirclePlaceListAdapter;
import com.barestodo.android.place.Circle;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.tasks.AsyncRetrievePlacesOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

public class CirclePlacesListActivity extends Activity implements AsyncRetrievePlacesOperation.CirclePlacesReceiver {


	private ImageButton addButton;
	private ListView listView;
	private Circle circle;
	private CirclePlaceListAdapter placeListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		
		circle = (Circle)b.get(CircleListAdapteur.CIRCLE_TO_SHOW);


		initiateActivity();
	}

	@Override
	protected void onResume() {
		try{
			placeListAdapter.notifyDataSetChanged();
			super.onResume();

		}catch(Exception e){
			Toast.makeText(CirclePlacesListActivity.this,e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	private void initAddButton() {
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CirclePlacesListActivity.this,
						AddPlaceToCircleActivity.class);
				startActivity(intent);
			}
		});
	}


	private void initiateActivity(){

        Log.d("Id du cercle", circle.getId().toString());
		placeListAdapter = new CirclePlaceListAdapter();
		new AsyncRetrievePlacesOperation(circle.getId(),this).execute();

		setContentView(R.layout.activity_circle_places_list);

		listView = (ListView) findViewById(R.id.placesListView);
		listView.setAdapter(placeListAdapter);

		addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);
		initAddButton();
	}

    @Override
    public void receivePlaces(List<Place> places) {
        placeListAdapter.addAll(places);
        listView.invalidateViews();
    }

    @Override
    public void onError(HttpStatus status) {
        //TODO toaster une erreur
    }
}

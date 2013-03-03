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

public class CirclePlacesListActivity extends Activity {

	//private IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();

	public ImageButton addButton;
	public ListView listView;
	public Circle circle;



	public CirclePlaceListAdapter placeListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		
		circle = (Circle)b.get(CircleListAdapteur.circleToShow);


		iniateActivity();
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


	private void iniateActivity(){


		Log.d("Id du cercle", circle.getId().toString());
		if (circle != null){
			placeListAdapter = new CirclePlaceListAdapter(circle.getId());
		}

		setContentView(R.layout.activity_circle_places_list);
		/*
        listView = (ListView) findViewById(R.id.listView1);

        initListView();*/
		listView = (ListView) findViewById(R.id.placesListView);
		listView.setAdapter(placeListAdapter);

		addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);
		initAddButton();
	}
}

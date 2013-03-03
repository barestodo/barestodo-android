package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.barestodo.android.adapteur.PlaceListAdapter;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;

public class PlacesListActivity extends Activity {

	private IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();

	public ImageButton addButton;
	public ListView listView;

	public PlaceListAdapter placeListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iniateActivity();
	}

	@Override
	protected void onResume() {
		try{
			placeListAdapter.notifyDataSetChanged();
			super.onResume();

		}catch(Exception e){
			Toast.makeText(PlacesListActivity.this,e.getMessage(),
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
				Intent intent = new Intent(PlacesListActivity.this,
						AddPlaceActivity.class);
				startActivity(intent);
			}
		});
	}


	private void iniateActivity(){

		placeListAdapter = new PlaceListAdapter();

		setContentView(R.layout.activity_places_list);
		/*
        listView = (ListView) findViewById(R.id.listView1);

        initListView();*/
		listView = (ListView) findViewById(R.id.placesListView);
		listView.setAdapter(placeListAdapter);

		addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);
		initAddButton();
	}
}

package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.barestodo.android.Adapter.PlaceListAdapter;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;

public class MainActivity extends Activity {

	IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();

	public Button addButton;
	public ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
        listView = (ListView) findViewById(R.id.listView1);

        initListView();*/
		listView = (ListView) findViewById(R.id.listView1);
		PlaceListAdapter placeListAdapter = new PlaceListAdapter();
		listView.setAdapter(placeListAdapter);


		addButton = (Button) findViewById(R.id.addButton);

		initAddButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	public void initListView(){
		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<Place> adapter = new ArrayAdapter<Place>(this,android.R.layout.simple_list_item_1, android.R.id.text1, placeRepository.getListPlace());

		// Assign adapter to ListView
		listView.setAdapter(adapter); 

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Bundle b = new Bundle();
				Intent intent = new Intent(MainActivity.this,
						PlaceDescriptionActivity.class);
				Place a = (Place)parent.getItemAtPosition(position);
				b.putString("placeId", a.getId());
				intent.putExtras(b); 
				startActivity(intent);
			}
		});

	}

	private void initAddButton() {
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						AddPlaceActivity.class);
				startActivity(intent);
			}
		});
	}
}

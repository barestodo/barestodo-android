package com.barestodo.android;

import java.util.logging.LogRecord;

import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.ListPlace;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Intent;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	IPlaceRepository placeRepository = ListPlace.INSTANCE ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        ListView listView = (ListView) findViewById(R.id.listView1);
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
        	  public void onItemClick(AdapterView<?> parent, View view,
        	    int position, long id) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

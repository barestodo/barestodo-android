package com.barestodo.android;

import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.adapteur.CircleMemberListAdapter;
import com.barestodo.android.place.Circle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class CircleMemberActivity  extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		Bundle b = getIntent().getExtras();
		
		circle = (Circle)b.get(CircleListAdapteur.CIRCLE_TO_SHOW);

		iniateActivity();
		
	}
	
	

	//private IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();

	public ImageButton addButton;
	public ListView listView;
	public Circle circle;



	public CircleMemberListAdapter memberListAdapter;


	protected void onResume() {
		try{
			memberListAdapter.notifyDataSetChanged();
			super.onResume();

		}catch(Exception e){
			Toast.makeText(CircleMemberActivity.this,e.getMessage(),
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
				/*Intent intent = new Intent(CircleMemberActivity.this,
						AddPlaceToCircleActivity.class);
				startActivity(intent);*/
			}
		});
	}



	private void iniateActivity(){
		

		Log.d("Id du cercle", circle.getId().toString());
		if (circle != null){
		
			memberListAdapter = new CircleMemberListAdapter(circle.getId());
		}

		setContentView(R.layout.activity_circle_member);
		/*
        listView = (ListView) findViewById(R.id.listView1);

        initListView();*/
		listView = (ListView) findViewById(R.id.membersListView);
		listView.setAdapter(memberListAdapter);

		addButton = (ImageButton) findViewById(R.id.addMemberImageButton);
		initAddButton();
	}
}



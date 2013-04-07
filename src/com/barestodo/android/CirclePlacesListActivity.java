package com.barestodo.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.adapteur.CirclePlaceListAdapter;
import com.barestodo.android.app.MyApplication;
import com.barestodo.android.model.Circle;
import com.barestodo.android.model.Place;
import com.barestodo.android.service.tasks.AsyncLeaveCircleOperation;
import com.barestodo.android.service.tasks.AsyncRetrievePlacesOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncLeaveCircleOperation.LeaveCaller;
import static com.barestodo.android.service.tasks.AsyncRetrievePlacesOperation.CirclePlacesReceiver;

public class CirclePlacesListActivity extends Activity implements CirclePlacesReceiver,LeaveCaller {


    public static final String NEW_PLACE_CREATED = "list.place.new";
    public static final int RETURN_PLACE_ID=12345;

    private ImageButton addButton;
    private ImageButton leaveCircleButton;

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
            super.onResume();
            new AsyncRetrievePlacesOperation(circle.getId(),this).execute();
            placeListAdapter.notifyDataSetInvalidated();
        }catch(Exception e){
			Toast.makeText(CirclePlacesListActivity.this,e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}





	private void initAddButton() {
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CirclePlacesListActivity.this,AddPlaceToCircleActivity.class);

                intent.putExtra(AddPlaceToCircleActivity.CIRCLE_ID,circle.getId())    ;
                startActivityForResult(intent, RETURN_PLACE_ID);
                
			}
		});
	}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (RETURN_PLACE_ID) : {
                if (resultCode == Activity.RESULT_OK) {
                    Place newPlace = (Place) data.getSerializableExtra(NEW_PLACE_CREATED);
                    placeListAdapter.add(newPlace);
                    placeListAdapter.notifyDataSetInvalidated();
                    placeListAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }

	private void initiateActivity(){

        Log.d("Id du cercle", circle.getId().toString());
		placeListAdapter = new CirclePlaceListAdapter();
		setContentView(R.layout.activity_circle_places_list);

		listView = (ListView) findViewById(R.id.placesListView);
		listView.setAdapter(placeListAdapter);
		

		addButton = (ImageButton) findViewById(R.id.addPlaceImageButton);
		initAddButton();
        leaveCircleButton = (ImageButton) findViewById(R.id.leaveCircleImageButton);
        initLeaveButton();

    }

    private void initLeaveButton() {
        leaveCircleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog leavecircleDialog = new AlertDialog.Builder(v.getContext())
                        .setMessage(MyApplication.getAppContext().getString(R.string.leave_circle_confirmation,circle.getName()))
                        .setTitle(R.string.leave_circle)
                        .setPositiveButton(R.string.deletion_ok, new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AsyncLeaveCircleOperation(CirclePlacesListActivity.this,circle.getId()).execute();
                            }
                        }
                        )
                        .setNegativeButton(R.string.deletion_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();
                leavecircleDialog.show();
            }
        });
    }

    @Override
    public void receivePlaces(List<Place> places) {
        if(places!=null && !places.isEmpty()){
            placeListAdapter.set(places);
            listView.invalidateViews();
            placeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(CirclePlacesListActivity.this,"création interrompue:"+status.getErrorMessage(),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void leaveSuccess() {
        finish();
    }
}

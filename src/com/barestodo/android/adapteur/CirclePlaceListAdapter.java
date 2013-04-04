package com.barestodo.android.adapteur;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.barestodo.android.R;
import com.barestodo.android.model.Place;
import com.barestodo.android.service.tasks.AsyncDeletePlaceOperation;
import com.barestodo.android.service.tasks.AsyncSchedulePlaceOperation;
import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;


public class CirclePlaceListAdapter extends BaseAdapter {

	private List<Place> listPlace = new ArrayList<Place>();

	private String placeId;


	@Override
	public int getCount() {
		return listPlace.size();    // total number of elements in the list
	}

	@Override
	public Object getItem(int i) {
		return listPlace.get(i);
		// single item in the list
	}

	@Override
	public long getItemId(int i) {
		return i;                   // index number
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.circle_place_list_adapter_layout, parent, false);
		}

		final Place place = listPlace.get(index);

		final String placeName = place.getName();
		final String placeLocation = place.getLocation();
		final DateTime placeScheduleDate = place.getScheduleDate();


		TextView textName = (TextView) view.findViewById(R.id.nameText);
		textName.setText(placeName);
		textName.setTypeface(null,Typeface.BOLD);

		TextView textLocation = (TextView) view.findViewById(R.id.locationText);
		textLocation.setText(placeLocation);
		textLocation.setTypeface(null,Typeface.ITALIC);

		if (placeScheduleDate != null){
			TextView textScheduleDate = (TextView) view.findViewById(R.id.scheduleDate);
			PrettyTime p = new PrettyTime();
			textScheduleDate.setText(p.format(placeScheduleDate.toDate()));
			textScheduleDate.setTypeface(null,Typeface.BOLD_ITALIC);
			view.setBackgroundColor(Color.GREEN);
		}

		placeId = place.getId();
		// button click listener
		// this chunk of code will run, if user click the button
		// because, we set the click listener on the button only
		ImageButton button = (ImageButton) view.findViewById(R.id.deleteImageButton);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog deleteDialog = new AlertDialog.Builder(parent.getContext())
				.setMessage(R.string.deletion_confirmation)
				.setTitle(placeName)
				.setPositiveButton(R.string.deletion_ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new AsyncDeletePlaceOperation(placeId).execute();
						listPlace.remove(place);
					}}
						)
						.setNegativeButton(R.string.deletion_cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								dialog.cancel();

							} })
							.create();
				deleteDialog.show();
			}
		});
		
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				final Dialog myDialog = new Dialog(parent.getContext());
                myDialog.setContentView(R.layout.dialog_schedule_place);
                myDialog.setTitle("My Custom Dialog Title");
                TextView placeNameTV=(TextView)myDialog.findViewById(R.id.place_to_schedule_name);
                placeNameTV.setText(placeName);
                TextView placeLocationTV=(TextView)myDialog.findViewById(R.id.place_to_schedule_name);
                placeLocationTV.setText(placeLocation);
                
                final DatePicker scheduleDP = (DatePicker)myDialog.findViewById(R.id.place_to_schedule_datepicker);
                Button confirm_button=(Button)myDialog.findViewById(R.id.button_confirm_place_schedule);
                confirm_button.setOnClickListener(new OnClickListener() {
                    @Override
                     public void onClick(View v) {
                           Toast.makeText(parent.getContext(), "Scheduling de la plce au"+scheduleDP.getDrawingTime(), Toast.LENGTH_SHORT).show();
                           System.out.println(scheduleDP.getYear());
                           System.out.println(scheduleDP.getMonth());
                           System.out.println();
                           DateTime sendDT = new DateTime(scheduleDP.getYear(),scheduleDP.getMonth()+1,scheduleDP.getDayOfMonth(),0,0);
                           
                           
                           
                           new AsyncSchedulePlaceOperation(placeId, sendDT).execute();
                           myDialog.dismiss();
                           
                     }
               });
                
                Button cancel_button=(Button)myDialog.findViewById(R.id.button_cancel_place_schedule);
                cancel_button.setOnClickListener(new OnClickListener() {
                     @Override
                      public void onClick(View v) {
                            // TODO Auto-generated method stub
                            myDialog.cancel();
                      }
                });
                myDialog.show();	
			}
		});
		
		
		return view;
	}

	public void addAll(List<Place> places) {
		listPlace.addAll(places);
	}

	public void set(List<Place> places) {
		listPlace.clear();
		listPlace.addAll(places);
	}

	public void add(Place place) {
		listPlace.add(place);
	}
}


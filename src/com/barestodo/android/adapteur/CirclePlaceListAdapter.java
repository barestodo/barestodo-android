package com.barestodo.android.adapteur;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.barestodo.android.R;
import com.barestodo.android.model.Place;
import com.barestodo.android.service.tasks.AsyncDeletePlaceOperation;
import com.barestodo.android.service.tasks.AsyncSchedulePlaceOperation;
import com.ocpsoft.pretty.time.PrettyTime;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


public class CirclePlaceListAdapter extends BaseAdapter {

	private List<Place> listPlace = new ArrayList<Place>();
	private DateTime sentDT = DateTime.now();
	private DatePickerDialog dialog = null;
	private boolean schedule = false;

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

		final Place place = (Place)getItem(index);

		final String placeName = place.getName();
		final String placeLocation = place.getLocation();
		final DateTime placeScheduleDate = place.getScheduleDate();
		final String placeId = place.getId();


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
						notifyDataSetChanged();
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

		if (place.getScheduleDate() == null){
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {


					if(dialog == null){
						dialog = new DatePickerDialog(parent.getContext(),new DatePickerDialog.OnDateSetListener(){
							public void onDateSet(DatePicker datepicker, int year, int monthOfYear,
									int dayOfMonth) {

								
								if (schedule){
									sentDT = new DateTime(datepicker.getYear(),datepicker.getMonth()+1,datepicker.getDayOfMonth(),0,0);
									datepicker.updateDate(year, monthOfYear, dayOfMonth);
									Log.d("month",Integer.toString(datepicker.getMonth()));
									Toast.makeText(parent.getContext(), "Scheduling de la place "+placeName+"au"+sentDT, Toast.LENGTH_SHORT).show();
									Log.d("sd",sentDT.toString());
									new AsyncSchedulePlaceOperation(placeId, sentDT).execute();
									schedule = false;
									notifyDataSetChanged();
								}
								dialog.hide();
							}
						},sentDT.getYear(),sentDT.getMonthOfYear(),
						sentDT.getDayOfMonth());
					}

					dialog.updateDate(sentDT.getYear(),sentDT.getMonthOfYear(),sentDT.getDayOfMonth());
					dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (which == DialogInterface.BUTTON_NEGATIVE) {
								dialog.cancel();
							}
						}
					});
					dialog.setButton(DialogInterface.BUTTON_POSITIVE, "validate", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (which == DialogInterface.BUTTON_POSITIVE) {
								schedule = true;
								
							}
						}
					});
					dialog.show();
				}
			});
		}
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


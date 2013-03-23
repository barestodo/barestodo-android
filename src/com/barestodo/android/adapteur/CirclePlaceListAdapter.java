package com.barestodo.android.adapteur;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.joda.time.DateTime;
import org.ocpsoft.prettytime.PrettyTime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barestodo.android.PlaceDescriptionActivity;
import com.barestodo.android.R;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.tasks.AsyncDeletePlaceOperation;


public class CirclePlaceListAdapter extends BaseAdapter {

	private List<Place> listPlace = new ArrayList<Place>();
	/*
	private TextView deletionPLaceName;
	private TextView deletionPLaceLocation;

	private Dialog confirmDeletionDialog;

	private Button validateDeletion;
	private Button cancelDeletion;
	 */
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
				/*
				// TODO Auto-generated method stub
				confirmDeletionDialog = new Dialog(parent.getContext());
				confirmDeletionDialog.setContentView(R.layout.confirm_deletion_dialog);
				confirmDeletionDialog.setTitle(R.string.deletion_confirmation);

				deletionPLaceName=(TextView)confirmDeletionDialog.findViewById(R.id.place_to_delate_name);
				deletionPLaceName.setText(placeLocation);

				deletionPLaceLocation=(TextView)confirmDeletionDialog.findViewById(R.id.place_to_delete_location);
				deletionPLaceLocation.setText(placeLocation);

				validateDeletion=(Button)confirmDeletionDialog.findViewById(R.id.button_confirm_place_deletion);
				validateDeletion.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						confirmDeletionDialog.cancel();
					}
				});
				cancelDeletion=(Button)confirmDeletionDialog.findViewById(R.id.button_cancel_place_deletion);
				cancelDeletion.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						confirmDeletionDialog.cancel();
					}
				});

				confirmDeletionDialog.show();*/


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


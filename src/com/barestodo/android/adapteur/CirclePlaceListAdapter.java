package com.barestodo.android.adapteur;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.barestodo.android.R;
import com.barestodo.android.model.Place;
import com.barestodo.android.service.tasks.AsyncDeletePlaceOperation;
import com.barestodo.android.service.tasks.AsyncSchedulePlaceOperation;
import org.joda.time.DateTime;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CirclePlaceListAdapter extends BaseAdapter {

	private List<Place> listPlace = new ArrayList<Place>();

    @Override
	public int getCount() {
		return listPlace.size();    // total number of elements in the list
	}

	@Override
	public Place getItem(int i) {
		return listPlace.get(i);
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

		final Place place = getItem(index);

		final String placeName = place.getName();
		final String placeLocation = place.getLocation();


		TextView textName = (TextView) view.findViewById(R.id.nameText);
		textName.setText(placeName);
		textName.setTypeface(null,Typeface.BOLD);

		TextView textLocation = (TextView) view.findViewById(R.id.locationText);
		textLocation.setText(placeLocation);
		textLocation.setTypeface(null,Typeface.ITALIC);

		if (place.isPlaned()){
            TextView textScheduleDate = (TextView) view.findViewById(R.id.scheduleDate);
            textScheduleDate.setText(place.getRelativeScheduledDateLabel());
            textScheduleDate.setTypeface(null, Typeface.BOLD_ITALIC);
            view.setBackgroundColor(Color.rgb(225,255,226));
        }else{
            initializeShedulerPopUp(view, parent, place);
            view.setBackgroundColor(Color.WHITE);
            TextView textScheduleDate = (TextView) view.findViewById(R.id.scheduleDate);
            textScheduleDate.setText("");
        }


        initializePlaceDeletionConfirmDialog(view, parent, place);


		return view;
	}

    private void initializePlaceDeletionConfirmDialog(View view, final ViewGroup parent, final Place place) {
        ImageButton button = (ImageButton) view.findViewById(R.id.deleteImageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog deleteDialog = new AlertDialog.Builder(parent.getContext())
                .setMessage(R.string.deletion_confirmation)
                .setTitle(place.getName())
                .setPositiveButton(R.string.deletion_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AsyncDeletePlaceOperation(place.getId()).execute();
                        listPlace.remove(place);
                        notifyDataSetChanged();
                    }
                }
                )
                        .setNegativeButton(R.string.deletion_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();

                            }
                        })
                            .create();
                deleteDialog.show();
            }
        });
    }

    public static class OnDateSelectedToDoPlace implements DatePickerDialog.OnDateSetListener {

        private final Place placetoSchedule;

        public OnDateSelectedToDoPlace(Place placeToSchedule){
            this.placetoSchedule=placeToSchedule;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
         }
    }

    private void initializeShedulerPopUp(View view, final ViewGroup parent, final Place place) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DateTime now = DateTime.now();
                final DatePickerDialog dialog = new DatePickerDialog(parent.getContext(),new OnDateSelectedToDoPlace(place), now.getYear(), now.getMonthOfYear()-1, now.getDayOfMonth());
                //dialog.updateDate(place.getScheduleDate().getYear(), place.getScheduleDate().getMonthOfYear(), place.getScheduleDate().getDayOfMonth());
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                        }
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatePickerDialog castedDialog= (DatePickerDialog) dialog;

                        Field mDatePickerField = null;
                        try {
                            mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
                            mDatePickerField.setAccessible(true);
                            DatePicker datePicker = (DatePicker) mDatePickerField.get(dialog);
                            DateTime sendDT = new DateTime(datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth(),20,0);
                            //datePicker.updateDate(year, monthOfYear, dayOfMonth);
                           // Toast.makeText(castedDialog.getWindow().getContext(), "Scheduling de la place " + place.getName() + "au" + sendDT, Toast.LENGTH_SHORT).show();
                            new AsyncSchedulePlaceOperation(place.getId(), sendDT).execute().get();
                            place.setScheduleDate(sendDT);
                            CirclePlaceListAdapter.this.notifyDataSetInvalidated();
                        } catch (NoSuchFieldException e) {
                            Toast.makeText(castedDialog.getWindow().getContext(), "problem occured during datepicker manipulation", Toast.LENGTH_SHORT).show();
                        } catch (IllegalAccessException e) {
                            Toast.makeText(castedDialog.getWindow().getContext(), "problem occured during datepicker manipulation", Toast.LENGTH_SHORT).show();
                        } catch (InterruptedException e) {
                            Toast.makeText(castedDialog.getWindow().getContext(), "problem occured during datepicker manipulation", Toast.LENGTH_SHORT).show();
                        } catch (ExecutionException e) {
                            Toast.makeText(castedDialog.getWindow().getContext(), "problem occured during datepicker manipulation", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

   public void set(List<Place> places) {
		listPlace.clear();
		listPlace.addAll(places);
       notifyDataSetInvalidated();
	}

	public void add(Place place) {
		listPlace.add(place);
	}
	

	
	
	
}


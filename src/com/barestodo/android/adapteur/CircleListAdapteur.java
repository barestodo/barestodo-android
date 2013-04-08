package com.barestodo.android.adapteur;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.barestodo.android.CircleContentActivity;
import com.barestodo.android.R;
import com.barestodo.android.model.Circle;

import java.util.ArrayList;
import java.util.List;

//extends ArrayAdapter<PlaceList>
public class CircleListAdapteur extends BaseAdapter {

    public static final String CIRCLE_TO_SHOW = "circleToShow";

    private List<Circle> circles=new ArrayList<Circle>();



    @Override
    public boolean areAllItemsEnabled() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEnabled(int i) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return circles.size();
    }

    @Override
    public Circle getItem(int i) {
        return circles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.circle_list_adapter_layout, parent, false);
        }

        final Circle circle = circles.get(index);

        TextView placeCount = (TextView) view.findViewById(R.id.placesCount);
        placeCount.setText(String.valueOf(circle.getPlaceCount()));

        TextView groupSize = (TextView) view.findViewById(R.id.groupSize);
        groupSize.setText(String.valueOf(circle.getMemberCount()));

        TextView circleName = (TextView) view.findViewById(R.id.circleName);
        circleName.setText(circle.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(parent.getContext(),	CircleContentActivity.class);
            	Bundle b = new Bundle();
				b.putSerializable(CIRCLE_TO_SHOW,circle);
				intent.putExtras(b); 
				view.getContext().startActivity(intent);
                //Toast.makeText(parent.getContext(), "button clicked: " + place.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return circles.isEmpty();
    }

    public void addAll(List<Circle> circles) {
        this.circles.addAll(circles);
    }
    public void add(Circle circle) {
        this.circles.add(circle);
    }

    public void set(List<Circle> circles) {
        this.circles.clear();
        this.circles.addAll(circles);
    }
}

package com.barestodo.android.adapteur;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.barestodo.android.CircleContentActivity;
import com.barestodo.android.PlaceDescriptionActivity;
import com.barestodo.android.R;
import com.barestodo.android.place.Place;
import com.barestodo.android.place.Circle;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;
import com.barestodo.android.service.tasks.AsyncRetrieveCirclesOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

//extends ArrayAdapter<PlaceList>
public class CircleListAdapteur extends BaseAdapter implements AsyncRetrieveCirclesOperation.HasCircles {

    IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();
    private static final String TAG = CircleListAdapteur.class.getSimpleName();
    public static final String circleToShow = "circleToShow";
    List<Circle> circles=new ArrayList<Circle>();

    public CircleListAdapteur(Context context){
        try {
            circles = new AsyncRetrieveCirclesOperation(this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ExecutionException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
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

        TextView groupSize = (TextView) view.findViewById(R.id.groupSize);
        groupSize.setText("0");
        TextView circleName = (TextView) view.findViewById(R.id.circleName);
        circleName.setText(circle.getName());

        
        
        circleName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent intent = new Intent(parent.getContext(),	CircleContentActivity.class);
            	Bundle b = new Bundle();
				b.putSerializable(circleToShow,circle);
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

    @Override
    public void update(List<Circle> result) {

    }
}

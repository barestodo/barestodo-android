package com.barestodo.android.adapteur;

import java.util.List;

import com.barestodo.android.PlaceDescriptionActivity;
import com.barestodo.android.R;
import com.barestodo.android.place.Member;
import com.barestodo.android.place.Place;
import com.barestodo.android.service.IPlaceRepository;
import com.barestodo.android.service.RepositoryFactory;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CircleMemberListAdapter  extends BaseAdapter {
	
	IPlaceRepository placeRepository = RepositoryFactory.getPlaceRepository();
	private static final String TAG = CircleMemberListAdapter.class.getSimpleName();
	List<Member> listMember;

    public CircleMemberListAdapter(Long circleId) {
        listMember =placeRepository.getListMember(circleId);
        System.out.println(listMember);
    }
    
   
    @Override
    public long getItemId(int i) {
        return i;
    }




	@Override
	public int getCount() {
		return listMember.size();  
	}




	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return listMember.get(i);
	}




	@Override
	public View getView(int index, View view, final ViewGroup parent) {
		if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.circle_member_list_adapter_layout, parent, false);
        }

        final Member member = listMember.get(index);

        TextView textPseudo = (TextView) view.findViewById(R.id.pseudoText);
        textPseudo.setText(member.getPseudo());
        
        ImageButton button = (ImageButton) view.findViewById(R.id.detailImageButton);
        
        // button click listener
        // this chunk of code will run, if user click the button
        // because, we set the click listener on the button only

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	/*Intent intent = new Intent(parent.getContext(),	PlaceDescriptionActivity.class);
            	Bundle b = new Bundle();
				b.putSerializable("placeToShow",member);
				intent.putExtras(b); 
				view.getContext().startActivity(intent);
                //Toast.makeText(parent.getContext(), "button clicked: " + place.getName(), Toast.LENGTH_SHORT).show();*/
            }
        });
        return view;
	}

   

}

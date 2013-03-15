package com.barestodo.android.adapteur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;
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
import com.barestodo.android.utils.Gravatar;

public class CircleMemberListAdapter  extends BaseAdapter {
	
	private static final String TAG = CircleMemberListAdapter.class.getSimpleName();
	List<Member> listMember=new ArrayList<Member>();

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

        ImageView img=(ImageView)view.findViewById(R.id.avatar) ;
        try {
            Gravatar.setImageContentWithGravatar(img, member.getEmail());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
	}


    public void addAll(List<Member> members) {
        listMember.addAll(members);
    }

    public void set(List<Member> members) {
        listMember.clear();
        listMember.addAll(members);
    }
}

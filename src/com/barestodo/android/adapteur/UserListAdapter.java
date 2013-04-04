package com.barestodo.android.adapteur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.barestodo.android.R;
import com.barestodo.android.model.Member;
import com.barestodo.android.service.tasks.AsyncInvitePeopleOnCircleOperation;
import com.barestodo.android.utils.Gravatar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.barestodo.android.service.tasks.AsyncInvitePeopleOnCircleOperation.UserReceiver;

public class UserListAdapter extends BaseAdapter {
	
	private List<Member> listMember=new ArrayList<Member>();
    private final UserReceiver userReceiver;
    private final Long circleId;

    public UserListAdapter(UserReceiver userReceiver,Long circleId){
       this.userReceiver=userReceiver;
        this.circleId=circleId;
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
        textPseudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncInvitePeopleOnCircleOperation(member.getEmail(),circleId,userReceiver).execute() ;
            }
        });
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

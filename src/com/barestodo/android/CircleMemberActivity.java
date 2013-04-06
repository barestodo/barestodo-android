package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.adapteur.CircleMemberListAdapter;
import com.barestodo.android.model.Circle;
import com.barestodo.android.model.Member;
import com.barestodo.android.model.User;
import com.barestodo.android.service.tasks.AsyncRetrieveMembersOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncRetrieveMembersOperation.CircleMembersReceiver;

public class CircleMemberActivity  extends Activity implements CircleMembersReceiver {

    public static final String CIRCLE_TO_INVITE_ON = "circle_to_invite_on";
    public static final String NEW_PEOPLE_INVITED = "new_people_invited";
    private static final int RETURN_MEMBER_ID = 120;


    private ImageButton addButton;
    private ListView listView;
    private Circle circle;

    private CircleMemberListAdapter memberListAdapter=new CircleMemberListAdapter();


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
		circle = (Circle)b.get(CircleListAdapteur.CIRCLE_TO_SHOW);
        iniateActivity();
	}

    @Override
	protected void onResume() {
        new AsyncRetrieveMembersOperation(this,circle.getId()).execute();
		super.onResume();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}



	private void initAddButton() {
		addButton.setOnClickListener(new OnClickListener() {

            @Override
			public void onClick(View v) {
                Intent intent = new Intent(CircleMemberActivity.this,
                InvitePeopleActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(CIRCLE_TO_INVITE_ON,circle);
                intent.putExtra(CIRCLE_TO_INVITE_ON,circle);
                CircleMemberActivity.this.startActivityForResult(intent, RETURN_MEMBER_ID);
            }
		});
	}



	private void iniateActivity(){

        new AsyncRetrieveMembersOperation(this,circle.getId()).execute();
        Log.d("Id du cercle", circle.getId().toString());

		setContentView(R.layout.activity_circle_member);
        listView = (ListView) findViewById(R.id.membersListView);
        listView.setAdapter(memberListAdapter);

        addButton = (ImageButton) findViewById(R.id.addMemberImageButton);
		initAddButton();
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (RETURN_MEMBER_ID) : {
                if (resultCode == Activity.RESULT_OK) {
                    User newPlace = (User) data.getSerializableExtra(CircleMemberActivity.NEW_PEOPLE_INVITED);
                    memberListAdapter.add(newPlace);
                    memberListAdapter.notifyDataSetInvalidated();
                }
                break;
            }
        }
    }

    @Override
    public void receiveMembers(List<Member> members) {
        if(members!=null && !members.isEmpty()){
            memberListAdapter.set(members);
            memberListAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(CircleMemberActivity.this,"vos amis n'ont pu être  retrouvés",
                Toast.LENGTH_LONG).show();
    }
}



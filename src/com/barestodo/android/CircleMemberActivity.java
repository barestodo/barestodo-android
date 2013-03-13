package com.barestodo.android;

import com.barestodo.android.adapteur.CircleListAdapteur;
import com.barestodo.android.adapteur.CircleMemberListAdapter;
import com.barestodo.android.place.Circle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.place.Member;
import com.barestodo.android.service.tasks.AsyncRetrieveMembersOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncRetrieveMembersOperation.CircleMembersReceiver;

public class CircleMemberActivity  extends Activity implements CircleMembersReceiver {



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


	protected void onResume() {
		try{
			memberListAdapter.notifyDataSetChanged();
			super.onResume();

		}catch(Exception e){
			Toast.makeText(CircleMemberActivity.this,e.getMessage(),
					Toast.LENGTH_LONG).show();
		}
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
				/*Intent intent = new Intent(CircleMemberActivity.this,
						AddPlaceToCircleActivity.class);
				startActivity(intent);*/
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
    public void receiveMembers(List<Member> members) {
        memberListAdapter.set(members);
        memberListAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(CircleMemberActivity.this,"vos amis n'ont pu être  retrouvés",
                Toast.LENGTH_LONG).show();
    }
}



package com.barestodo.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.barestodo.android.adapteur.UserListAdapter;
import com.barestodo.android.model.Circle;
import com.barestodo.android.model.Member;
import com.barestodo.android.model.User;
import com.barestodo.android.service.tasks.AsyncInvitePeopleOnCircleOperation;
import com.barestodo.android.service.tasks.AsyncRetrieveAvailableUsersOperation;
import com.barestodo.android.service.tasks.HttpStatus;

import java.util.List;

import static com.barestodo.android.service.tasks.AsyncRetrieveAvailableUsersOperation.MembersReceiver;

/**
 * Permet d'inviter une personne dans un cercle
 */
public class InvitePeopleActivity extends Activity implements MembersReceiver,AsyncInvitePeopleOnCircleOperation.UserReceiver{

    private Circle circle;
    private UserListAdapter userListAdapter;


    @Override
    public void onResume(){
        super.onResume();
        new AsyncRetrieveAvailableUsersOperation(this).execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circle = (Circle) getIntent().getSerializableExtra(CircleMemberActivity.CIRCLE_TO_INVITE_ON);   //savedInstanceState.get(CircleListAdapteur.CIRCLE_TO_SHOW);
        userListAdapter=new UserListAdapter(this,circle.getId());
        setContentView(R.layout.activity_invite_people);
        ListView listView = (ListView) findViewById(R.id.potentialsUsersListView);
        listView.setAdapter(userListAdapter);
    }

    @Override
    public void receiveMembers(List<Member> members) {
        if(members!=null && !members.isEmpty()){
            userListAdapter.set(members);
            userListAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onError(HttpStatus status) {
        Toast.makeText(this, "impossible de récupérer la liste des utilisateurs",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void receiveCompleteUser(User user) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(CircleMemberActivity.NEW_PEOPLE_INVITED,user);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
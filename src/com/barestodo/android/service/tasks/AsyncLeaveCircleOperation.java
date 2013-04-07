package com.barestodo.android.service.tasks;

import android.widget.Toast;
import com.barestodo.android.app.MyApplication;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.io.UnsupportedEncodingException;

import static com.barestodo.android.service.tasks.HttpOperationFactory.getPostOperation;

/**
 * Permet de se désabonner d'un cercle
 */
public class AsyncLeaveCircleOperation extends AbstractAsyncTask<String, Void, Void> {

    public interface LeaveCaller extends OnAsynHttpError{
        void leaveSuccess();
    }

	private Long circleId;
    private LeaveCaller leaveCaller;

	public AsyncLeaveCircleOperation(LeaveCaller caller,Long circleId){
		this.circleId=circleId;
        this.leaveCaller=caller;
	}

	@Override
	Void concreteOperation(String... params) throws Exception {
		HttpPost httpRequest = getPostOperation(constructSafeUrl());
		HttpResponse response = httpClient.execute(httpRequest, localContext);
		checkResponseStatus(response.getStatusLine().getStatusCode());
		return null;
	}

	@Override
	protected void onPostExecute(Void result){
		if(hasFail()){
            Toast.makeText(MyApplication.getAppContext(), "Erreur lors de votre désabonnement:".concat(getRequestStatus().getErrorMessage()),
                    Toast.LENGTH_LONG).show();
		}else {
            leaveCaller.leaveSuccess();
        }
	}

	private String constructSafeUrl() throws UnsupportedEncodingException {
		return new StringBuilder("circle/").append(circleId).append("/leave").toString();
	}
}
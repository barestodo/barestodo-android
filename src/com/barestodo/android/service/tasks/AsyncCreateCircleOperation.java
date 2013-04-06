package com.barestodo.android.service.tasks;

import com.barestodo.android.model.Circle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static com.barestodo.android.service.tasks.HttpOperationFactory.getPutOperation;

/**
 * Crée un cercle
 */
public class AsyncCreateCircleOperation extends AbstractAsyncTask<String, Void, Circle> {


    public interface CircleReceiver extends OnAsynHttpError{
        void receiveCircle(Circle circle);
    }

    private CircleReceiver circleReceiver;
    private final String name;

    public AsyncCreateCircleOperation(String name,CircleReceiver receiver){
        this.circleReceiver =receiver;
        this.name=name;
    }


    @Override
    public Circle concreteOperation(String... params) throws Exception {
        HttpPut httpPut = getPutOperation(constructSafeUrl(name));
        HttpResponse response = httpClient.execute(httpPut, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String json = reader.readLine();
        JSONObject jsonResponse = new JSONObject(json);
        return new Circle(jsonResponse.getLong("id"),jsonResponse.getString("name"));
    }

    @Override
    protected void onPostExecute(Circle result){
        if(hasFail()){
            circleReceiver.onError(getRequestStatus());
        }else{
            circleReceiver.receiveCircle(result);
        }
    }

    private String constructSafeUrl(String name) throws UnsupportedEncodingException {
        String safeUrl="circle/".concat(name).replace(" ", "%20");
        return safeUrl;
    }
}

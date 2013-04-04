package com.barestodo.android.service.tasks;

import com.barestodo.android.model.Place;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static com.barestodo.android.repository.HttpOperationFactory.getPutOperation;

/**
 * Crée une place
 */
public class AsyncCreatePlaceOperation extends AbstractAsyncTask<String, Void, Place> {

    public interface PlaceReceiver extends OnAsynHttpError,Serializable{
        void receivePlace(Place place);
    }

    private Place place;
    private PlaceReceiver placeReceiver;
    private Long circleId;

    public AsyncCreatePlaceOperation(Long circleId,Place place,PlaceReceiver receiver){
        this.place=place;
        this.placeReceiver=receiver;
        this.circleId=circleId;
    }


    @Override
    Place concreteOperation(String... params) throws Exception {
        HttpPut httpPut = getPutOperation(constructSafeUrl(place));
        HttpResponse response = httpClient.execute(httpPut, localContext);
        checkResponseStatus(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
        String json = reader.readLine();
        JSONObject jsonResponse = new JSONObject(json);

        return new Place(jsonResponse.getString("id"),jsonResponse.getString("name"),jsonResponse.getString("location"));
    }

    @Override
    protected void onPostExecute(Place result){
        if(hasFail()){
            placeReceiver.onError(getRequestStatus());
        }else{
            placeReceiver.receivePlace(result);
        }
    }

    private String constructSafeUrl(Place place) throws UnsupportedEncodingException {
        StringBuilder url=new StringBuilder(place.getName())
        .append("/")
        .append(place.getLocation()) ;
        String safeUrl="circle/".concat(String.valueOf(circleId)).concat("/place/").concat(url.toString().replace(" ","%20"));
        return safeUrl;
    }
}      // /rest/circle/:circleId/place/:name/:location

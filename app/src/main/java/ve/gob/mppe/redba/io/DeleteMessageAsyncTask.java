package ve.gob.mppe.redba.io;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ve.gob.mppe.redba.RedBa;
import ve.gob.mppe.redba.activities.DetailActivity;
import ve.gob.mppe.redba.services.RestMessageService;

/**
 * Created by fernando on 27/10/14.
 * @since 27/10/14
 */
public class DeleteMessageAsyncTask extends AsyncTask<Bundle,Integer,Bundle> {

    public static final String TAG = DeleteMessageAsyncTask.class.getSimpleName();
    private Context mContext;
    private String requester;

    public DeleteMessageAsyncTask(Context mContext, String requester){
        this.mContext = mContext;
        this.requester = requester;
    }

    @Override
    protected Bundle doInBackground(Bundle... params) {
        Log.d(TAG, "doInBackground");
        Bundle bundle = params[0];
        String id = bundle.getString(RedBa.KEY_ID);

        Bundle bundleResponse = null;

        try{

            String messageUrl = RedBa.SERVER_URL + "/messages/" + id + RedBa.JSON_EXT;
            Log.d(TAG, "doInBackground. url: " + messageUrl);
            bundleResponse = new Bundle();

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(messageUrl);
            httpDelete.addHeader(RedBa.HTTP_HEADER_CONTENT_TYPE_KEY, RedBa.HTTP_HEADER_CONTENT_TYPE_JSON);

            HttpResponse response = httpClient.execute(httpDelete);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            int responseCode = response.getStatusLine().getStatusCode();

            bundleResponse.putInt(RedBa.HTTP_RESPONSE_CODE_KEY, responseCode);
            bundleResponse.putString(RedBa.HTTP_RESPONSE_KEY, "{}");

        } catch (IOException e) {
            Log.e(TAG, "Error. IOException. Couldn't Delete Message", e);
            bundleResponse = null;
        }

        return bundleResponse;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        JSONObject response;
        if(bundle != null) {
            int responseCode = bundle.getInt(RedBa.HTTP_RESPONSE_CODE_KEY);
            String responseStr = bundle.getString(RedBa.HTTP_RESPONSE_KEY);

            try {
                response = new JSONObject(responseStr);
                Log.d(TAG, "onPostExecute. response code: " + responseCode + " response: " + response.toString(1));
                if (responseCode == 200) {
                    bundle.putBoolean("success", true);
                } else {
                    bundle.putBoolean("success", false);
                    if (responseCode == 401) {
                        String errorCode = response.getJSONObject("error").getString("code");
                        bundle.putString("ERROR_MSG", errorCode);
                    }
                    bundle.putString("ERR_BODY", responseStr);
                }
            } catch (JSONException e) {
                Log.d(TAG, "onPostExecute. Invalid response JSONObject", e);
            }
        } else {
            bundle = new Bundle();
            bundle.putBoolean(RedBa.KEY_SERVER_ERROR, true);
        }

        bundle.putString(RedBa.KEY_ACTION, RestMessageService.ACTION_DELETE_MESSAGE);
        //Enviar resultado a Componente que hizo la llamada al Sevicio
        Intent intent = null;
        try {
            intent = new Intent(mContext, Class.forName(requester));
            intent.putExtra(RedBa.EXTRA_RESULT, bundle);
            mContext.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

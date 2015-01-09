package ve.com.fdefreitas.curso.app.io;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import ve.com.fdefreitas.curso.app.Curso;
import ve.com.fdefreitas.curso.app.services.RestMessageService;

/**
 * Created by fernando on 27/10/14.
 * @since 27/10/14
 */
public class GetMessagesAsyncTask extends AsyncTask<Bundle,Integer,Bundle> {

    public static final String TAG = GetMessagesAsyncTask.class.getSimpleName();
    private static final String MESSAGES_URL = Curso.SERVER_URL + "/messages" + Curso.JSON_EXT;
    private Context mContext;
    private String requester;

    public GetMessagesAsyncTask(Context mContext, String requester){
        this.mContext = mContext;
        this.requester = requester;
    }

    @Override
    protected Bundle doInBackground(Bundle... params) {
        Log.d(TAG, "doInBackground");
        Bundle bundleResponse = null;

        try{
            bundleResponse = new Bundle();

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(MESSAGES_URL);
            httpGet.addHeader(Curso.HTTP_HEADER_CONTENT_TYPE_KEY, Curso.HTTP_HEADER_CONTENT_TYPE_JSON);

            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
            int responseCode = response.getStatusLine().getStatusCode();

            Log.d(TAG, "responseJSON: ");
            try {
                Log.d(TAG, new JSONArray(responseString).toString(1));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "error parsing responseJSON");
            }

            bundleResponse.putInt(Curso.HTTP_RESPONSE_CODE_KEY, responseCode);
            bundleResponse.putString(Curso.HTTP_RESPONSE_KEY, responseString);

        } catch (IOException e) {
            Log.e(TAG, "Error. IOException. Couldn't get messages", e);
            bundleResponse = null;
        }

        return bundleResponse;
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        JSONArray response;
        if(bundle != null) {
            int responseCode = bundle.getInt(Curso.HTTP_RESPONSE_CODE_KEY);
            String responseStr = bundle.getString(Curso.HTTP_RESPONSE_KEY);

            try {
                response = new JSONArray(responseStr);
                Log.d(TAG, "onPostExecute. response code: " + responseCode + " response: " + response.toString(1));
                if (responseCode == 200) {
                    bundle.putBoolean("success", true);
                } else {
                    bundle.putBoolean("success", false);
                    if (responseCode == 401) {
                        bundle.putString("ERROR_MSG", String.valueOf(responseCode));
                    }
                    bundle.putString("ERR_BODY", responseStr);
                }
            } catch (JSONException e) {
                Log.d(TAG, "onPostExecute. Invalid response JSONObject", e);
            }
        } else {
            bundle = new Bundle();
            bundle.putBoolean(Curso.KEY_SERVER_ERROR, true);
        }

        //Enviar resultado a Componente que hizo la llamada al Sevicio
        bundle.putString(Curso.KEY_ACTION, RestMessageService.ACTION_GET_MESSAGES);
        Intent intent = null;
        try {
            intent = new Intent(mContext, Class.forName(requester));
            intent.putExtra(Curso.EXTRA_RESULT, bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

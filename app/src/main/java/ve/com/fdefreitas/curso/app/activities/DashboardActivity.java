package ve.com.fdefreitas.curso.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ve.com.fdefreitas.curso.app.Curso;
import ve.com.fdefreitas.curso.app.R;
import ve.com.fdefreitas.curso.app.adapters.MessagesAdapter;
import ve.com.fdefreitas.curso.app.services.RestMessageService;

public class DashboardActivity extends ActionBarActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private DashboardActivity mActivity;
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (savedInstanceState == null) {

        }

        mActivity = this;

        messagesAdapter = new MessagesAdapter(this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(messagesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject itemSelected = (JSONObject) messagesAdapter.getItem(position);

                Toast.makeText(mActivity, "Item seleccionado: " + itemSelected.toString()
                        , Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putString(Curso.KEY_RESULT, itemSelected.toString());
                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.putExtra(Curso.EXTRA_BUNDLE, bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            Log.d(TAG, "Refresh triggered");
            Intent refreshIntent = new Intent(this, RestMessageService.class);
            Bundle bundle = new Bundle();
            bundle.putString(RestMessageService.KEY_REQUESTER, mActivity.getClass().getName());
            refreshIntent.putExtra(RestMessageService.EXTRA_DATA, bundle);
            refreshIntent.setAction(RestMessageService.ACTION_GET_MESSAGES);
            startService(refreshIntent);
            return true;
        } else if( id == R.id.action_map){
            Intent intent = new Intent(mActivity, MapsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.hasExtra(Curso.EXTRA_RESULT)){
            Bundle bundle = intent.getBundleExtra(Curso.EXTRA_RESULT);
            if(!bundle.getBoolean(Curso.KEY_SERVER_ERROR)){

                if(bundle.getString(Curso.KEY_ACTION).equals(RestMessageService.ACTION_GET_MESSAGES)) {
                    JSONArray jsonMessages = null;
                    try {
                        jsonMessages = new JSONArray(bundle.getString(Curso.HTTP_RESPONSE_KEY));
                        Curso.messages = jsonMessages;
                        messagesAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing server messages");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

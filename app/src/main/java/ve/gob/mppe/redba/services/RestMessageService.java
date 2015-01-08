package ve.gob.mppe.redba.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ve.gob.mppe.redba.RedBa;
import ve.gob.mppe.redba.io.GetMessagesAsyncTask;
import ve.gob.mppe.redba.io.PutMessageAsyncTask;


/**
 * An {@link android.app.IntentService} subclass for handling REST Network Requests
 */
public class RestMessageService extends IntentService {

    private static final String TAG = RestMessageService.class.getSimpleName();
    public static final String ACTION_GET_MESSAGES = "get_messages";
    public static final String ACTION_POST_MESSAGE = "post_message";
    public static final String ACTION_PUT_MESSAGE = "put_message";
    public static final String ACTION_DELETE_MESSAGE = "delete_message";
    public static final String KEY_REQUESTER = "requester";
    public static final String KEY_JSON_STR = "json_str";
    public static final String KEY_ID = "id";
    public static final String EXTRA_DATA = "extra_data";


    public RestMessageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent");
        if (intent != null && intent.getBundleExtra(EXTRA_DATA) != null) {
            final String action = intent.getAction();
            Bundle bundle = intent.getBundleExtra(EXTRA_DATA);
            switch (action) {
                case ACTION_GET_MESSAGES:
                    getMessages(bundle.getString(KEY_REQUESTER));
                    break;
                case ACTION_POST_MESSAGE: {
                    final String requester = intent.getStringExtra(KEY_REQUESTER);
                    final String jsonStr = intent.getStringExtra(KEY_JSON_STR);
                    postMessage(requester, jsonStr);
                    break;
                }
                case ACTION_PUT_MESSAGE: {
                    final String requester = intent.getStringExtra(KEY_REQUESTER);
                    final String jsonStr = intent.getStringExtra(KEY_JSON_STR);
                    final String id = intent.getStringExtra(KEY_ID);
                    putMessage(requester, id, jsonStr);
                    break;
                }
                case ACTION_DELETE_MESSAGE: {
                    final String requester = intent.getStringExtra(KEY_REQUESTER);
                    final String id = intent.getStringExtra(KEY_ID);
                    deleteMessage(requester, id);
                    break;
                }
            }
        } else {
            Log.d(TAG, "onHandleIntent. No EXTRA_DATA");
        }
    }

    /**
     * Request Messages from REST Backend
     */
    private void getMessages(String requester) {
        new GetMessagesAsyncTask(getApplicationContext(), requester).execute();
    }

    /**
     * Handle Update Message on a specific position, if it doesn't exist then create it
     */
    private void putMessage(String requester, String id, String jsonStr) {
        Bundle bundle = new Bundle();
        bundle.putString(RedBa.KEY_DATA, jsonStr);
        bundle.putString(RedBa.KEY_ID, id);
        new PutMessageAsyncTask(getApplicationContext(), requester).execute(bundle);
    }

    /**
     * Handle Post new Message
     */
    private void postMessage(String requester, String jsonStr) {
        String id = String.valueOf(RedBa.messages.length());
        Bundle bundle = new Bundle();
        bundle.putString(RedBa.KEY_DATA, jsonStr);
        bundle.putString(RedBa.KEY_ID, id);
        new PutMessageAsyncTask(getApplicationContext(), requester).execute(bundle);
    }

    /**
     * Handle Delete a Message with specified ID
     */
    private void deleteMessage(String requester, String id) {
        Bundle bundle = new Bundle();
        bundle.putString(RedBa.KEY_ID, id);
        new PutMessageAsyncTask(getApplicationContext(), requester).execute(bundle);
    }
}

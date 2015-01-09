package ve.com.fdefreitas.curso.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fernando on 18/12/14.
 */
public class Curso extends Application{
    private static final String TAG = Curso.class.getSimpleName();
    public static final String SERVER_URL = "https://android-course.firebaseio.com";
    public static final String JSON_EXT = ".json";
    public static final String HTTP_HEADER_CONTENT_TYPE_KEY = "Content-Type";
    public static final String HTTP_HEADER_CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String HTTP_HEADER_AUTHORIZATION_PREFIX = "Bearer ";
    public static final String HTTP_RESPONSE_KEY = "response";
    public static final String HTTP_RESPONSE_CODE_KEY = "response_code";
    public static final String KEY_DATA = "data";
    public static final String KEY_ID = "id";
    public static final String KEY_SERVER_ERROR = "server_error";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_ACTION = "action";
    public static final String KEY_MESSAGE_TITLE = "title";
    public static final String KEY_MESSAGE_AUTHOR = "author";
    public static final String EXTRA_ARGS = "args";
    public static final String EXTRA_RESULT = "result";
    public static final String EXTRA_BUNDLE = "extra_bundle";
    public static final String KEY_RESULT = "result";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor sharedPreferencesEditor;
    public static final String defaultJSONObjectString = "{}";
    public static final String defaultJSONArrayString = "[]";
    private static File appDirectory;
    public static JSONArray messages;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        appDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                , getApplicationContext().getString(R.string.app_name));
    }

    public static void getDeviceInfo(){
        Log.d(TAG, "MANUFACTURER: " + Build.MANUFACTURER);
        Log.d(TAG, "MODEL: " + Build.MODEL);
        Log.d(TAG, "DEVICE: " + Build.DEVICE);
        Log.d(TAG, "OS VERSION.SDK_INT: " + Build.VERSION.SDK_INT);
        Log.d(TAG, "OS VERSION.RELEASE: " + Build.VERSION.RELEASE);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken() && imm.isActive())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void clearSharedPreferences() {
        sharedPreferencesEditor.clear().apply();
    }

    public static void removeKeyFromSharedPreferences(String key) {
        sharedPreferencesEditor.remove(key).apply();
    }

    public static void sharedPrefsPutJSONArray(String key, JSONArray value){
        sharedPreferencesEditor.putString(key, value.toString()).apply();
    }

    public static JSONArray sharedPrefsGetJSONArray(String key){
        String jsonString = sharedPreferences.getString(key, defaultJSONArrayString);
        try {
            return new JSONArray(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static void sharedPrefsPutJSONObject(String key, JSONObject value){
        sharedPreferencesEditor.putString(key, value.toString()).apply();
    }

    public static JSONObject sharedPrefsGetJSONObject(String key){
        String jsonString = sharedPreferences.getString(key, defaultJSONObjectString);
        try {
            return new JSONObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static void sharedPrefsPutString(String key, String value){
        sharedPreferencesEditor.putString(key, value).apply();
    }

    public static String sharedPrefsGetString(String key){
        return sharedPreferences.getString(key, "");
    }

    public static void sharedPrefsPutBoolean(String key, Boolean value){
        sharedPreferencesEditor.putBoolean(key, value).apply();
    }

    public static Boolean sharedPrefsGetBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public static void sharedPrefsPutInt(String key, int value){
        sharedPreferencesEditor.putInt(key, value).apply();
    }

    public static int sharedPrefsGetInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public static Uri getOutputMediaFileUri(String name){
        return Uri.fromFile(getOutputMediaFile(name));
    }

    public static File getOutputMediaFile(String name){
        File mediaStorageDir = new File(getAppDirectory(), name);

        if (!mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "getOutputMediaFile. failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String ext = ".jpg";
        String filename = "nombre_de_archivo" + ext;
        File mediaFile = new File(mediaStorageDir.getPath(), filename);
        return mediaFile;
    }

    /**
     * Returns Public App folder
     */
    public static File getAppDirectory(){
        return appDirectory;
    }
}

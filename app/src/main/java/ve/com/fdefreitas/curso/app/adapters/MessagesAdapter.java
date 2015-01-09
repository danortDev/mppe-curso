package ve.com.fdefreitas.curso.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ve.com.fdefreitas.curso.app.Curso;
import ve.com.fdefreitas.curso.app.R;

/**
 * Created by fernando on 18/12/14.
 * @since 18/12/14
 */
public class MessagesAdapter extends BaseAdapter {
    private static final String TAG = MessagesAdapter.class.getSimpleName();
    private Activity mActivity;
    private JSONArray messages;


    public MessagesAdapter(Activity activity){
        mActivity = activity;
        messages = new JSONArray();
    }

    @Override
    public int getCount() {
        return messages.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return messages.get(position);
        } catch (JSONException e) {
            Log.e(TAG, "getItem. Couldn't get item at position: " + position);
            e.printStackTrace();
            return new JSONObject();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JSONObject item = (JSONObject) getItem(position);
        if (convertView == null) {
            convertView = ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.row_adapter_item, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        TextView text = (TextView) convertView.findViewById(R.id.textView);
        String title;
        try {
            title = item.getString(Curso.KEY_MESSAGE_TITLE);
        } catch (JSONException e) {
            title = "Title Not Available";
            e.printStackTrace();
        }
        text.setText(title);

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        messages = Curso.messages;
        super.notifyDataSetChanged();
    }
}

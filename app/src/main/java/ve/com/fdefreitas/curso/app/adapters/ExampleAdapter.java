package ve.com.fdefreitas.curso.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ve.com.fdefreitas.curso.app.R;

/**
 * Created by fernando on 18/12/14.
 */
public class ExampleAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<String> items;


    public ExampleAdapter(Activity activity){
        mActivity = activity;
        items = new ArrayList<>();
        items.add("Uno");
        items.add("Dos");
        items.add("Tres");
        items.add("Cuatro");
        items.add("Cinco");
        items.add("Seis");
        items.add("Uno");
        items.add("Dos");
        items.add("Tres");
        items.add("Cuatro");
        items.add("Cinco");
        items.add("Seis");
        items.add("Uno");
        items.add("Dos");
        items.add("Tres");
        items.add("Cuatro");
        items.add("Cinco");
        items.add("Seis");
        items.add("Uno");
        items.add("Dos");
        items.add("Tres");
        items.add("Cuatro");
        items.add("Cinco");
        items.add("Seis");
        items.add("Uno");
        items.add("Dos");
        items.add("Tres");
        items.add("Cuatro");
        items.add("Cinco");
        items.add("Seis");
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.row_adapter_item, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        TextView text = (TextView) convertView.findViewById(R.id.textView);
        text.setText((String)getItem(position));

        return convertView;
    }
}

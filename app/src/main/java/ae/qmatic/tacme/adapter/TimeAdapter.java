package ae.qmatic.tacme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ae.qmatic.tacme.R;

/**
 * Created by mdev3 on 8/22/16.
 */
public class TimeAdapter extends BaseAdapter {
    private Context mContext;

    private static LayoutInflater inflater = null;
    private String[] mStrIds;

    public TimeAdapter(Context context, String[] mStrIds) {
        this.mContext = context;
        this.mStrIds = mStrIds;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView tv;

    }

    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) {
        if (index >= mStrIds.length) {
            index = index % mStrIds.length;
        }

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.itemview, null);
        holder.tv = (TextView) rowView.findViewById(R.id.time);


        holder.tv.setText(mStrIds[index]);


        return rowView;
    }


}

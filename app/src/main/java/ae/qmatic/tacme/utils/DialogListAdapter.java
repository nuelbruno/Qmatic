package ae.qmatic.tacme.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.DialogItem;


/**
 * Created by mdev3 on 3/9/16.
 */
public class DialogListAdapter extends BaseAdapter {
    List<DialogItem> itemList;
    public Context context;
    public LayoutInflater inflater;
    public DialogListAdapter(Context context, List<DialogItem> itemList) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder {
        TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.dialog_list_item, null);
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.dialogListItemText);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.txtViewTitle.setText(itemList.get(position).getName());
        return convertView;
    }


}

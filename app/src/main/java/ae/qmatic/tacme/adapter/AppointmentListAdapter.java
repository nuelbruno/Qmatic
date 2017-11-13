package ae.qmatic.tacme.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.AppointmentBookingListModel;

/**
 * Created by mdev3 on 8/31/16.
 */
public class AppointmentListAdapter extends BaseAdapter {
    List<AppointmentBookingListModel> itemList;
    public Activity context;
    public LayoutInflater inflater;
    public AppointmentListAdapter(Activity context,List<AppointmentBookingListModel> itemList) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static class ViewHolder
    {
        ImageView imgViewLogo;
        TextView txtViewservice;
        TextView txtViewstatus;
        TextView txtViewticket;
        TextView txtViewTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;
        if(convertView==null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.booking_list_row, null);
            holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.ImageViewColor);
            holder.txtViewservice = (TextView) convertView.findViewById(R.id.txtViewService);
            holder.txtViewstatus = (TextView) convertView.findViewById(R.id.txtViewStatus);
            holder.txtViewticket = (TextView) convertView.findViewById(R.id.ticketNumber);
            holder.txtViewTime = (TextView) convertView.findViewById(R.id.txtTime);
            holder.txtViewTime.setGravity(Gravity.CENTER);

            convertView.setTag(holder);
        }
        else
            holder=(ViewHolder)convertView.getTag();

        holder.txtViewticket.setVisibility(View.GONE);
        holder.txtViewservice.setText(itemList.get(position).getServiceName());
        holder.txtViewTime.setText(itemList.get(position).getTime());


        switch (Integer.valueOf(itemList.get(position).getStatus())){
            case 0:
                holder.imgViewLogo.setImageResource(R.drawable.ic_booked);
                holder.txtViewstatus.setTextColor(context.getResources().getColor(R.color.buttonfirstColor));
                holder.txtViewstatus.setText("Booked");
                break;
            case 1:
                holder.imgViewLogo.setImageResource(R.drawable.ic_cancelled);
                holder.txtViewstatus.setTextColor(Color.RED);
                holder.txtViewstatus.setText("Cancelled");
                break;
            case 2:
                holder.imgViewLogo.setImageResource(R.drawable.ic_pending);
                holder.txtViewstatus.setTextColor(context.getResources().getColor(R.color.buttonfifthColor));
                holder.txtViewstatus.setText("Pending");
                break;
        }

        return convertView;
    }
}

package ae.qmatic.tacme.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.GetAllBranchesOnMap;
import ae.qmatic.tacme.utils.GPSTracker;

/**
 * Created by nikunjkumar on 8/15/16.
 */
public class BranchAdapter extends BaseAdapter{


    List<GetAllBranchesOnMap> listArrayData;
    Context context;
    GPSTracker gpsTracker;
    LayoutInflater layoutInflater;

    public BranchAdapter(Context con, List<GetAllBranchesOnMap> listArrayData){
        this.context = con;
        this.listArrayData = listArrayData;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gpsTracker = new GPSTracker(context);
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return listArrayData.size();
    }



    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.branch_item_layout, null);
        }

        // initialize controls
        TextView txtViewBranchName = (TextView)convertView.findViewById(R.id.txtViewBranchName);
        ImageView imgDirection = (ImageView)convertView.findViewById(R.id.imgDirection);
        ImageView imgCall = (ImageView)convertView.findViewById(R.id.imgCall);

        txtViewBranchName.setText(listArrayData.get(position).getName());
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0123456789"));
                context.startActivity(callIntent);
            }
        });
        imgDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapURI = "http://maps.google.com/maps?saddr=" + gpsTracker.getLatitude() + ","
                        + gpsTracker.getLongitude() + "+&daddr="
                        + listArrayData.get(position).getLatitude() + ","
                        + listArrayData.get(position).getLongitude()
                        + "&mode=driving";

                Intent branchDirectionIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapURI));
                context.startActivity(branchDirectionIntent);
            }
        });

        return convertView;
    }
}

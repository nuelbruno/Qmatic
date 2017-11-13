package ae.qmatic.tacme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ae.qmatic.tacme.R;

/**
 * Created by nikunjkumar on 8/14/16.
 */
public class AppointmentTimeAdapter extends RecyclerView.Adapter<AppointmentTimeAdapter.MyViewHolder> {
    List<String> list;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);

        }
    }
    public AppointmentTimeAdapter(Context con, List<String> hList){
        context = con;
        this.list = hList;
    }
    @Override
    public int getItemCount() {
        //return list.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recyclerview_item, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        position = position%list.size();
        holder.txtView.setText(list.get(position));
        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Time", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

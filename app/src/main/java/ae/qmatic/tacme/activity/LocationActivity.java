package ae.qmatic.tacme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.adapter.BranchAdapter;
import ae.qmatic.tacme.model.GetAllBranchesOnMap;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.GPSTracker;
import ae.qmatic.tacme.utils.HttpConstants;
import ae.qmatic.tacme.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev3 on 8/10/16.
 */
public class LocationActivity extends AppCompatActivity {
    ListView listviewBranhes;
    GPSTracker gps;
    double currentLatitude ;
    double currentLongitude ;

    //List<ServiceBranchModel> webserviceBranchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_layout);

        ////// access toolbar ////////////////
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.branches));
        ImageView imgRightIcon = (ImageView)toolbarTop.findViewById(R.id.imgRightIcon);

        ////// set map icon and it's listner ////////////////
        imgRightIcon.setImageResource(R.drawable.ic_map_black);
        imgRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(LocationActivity.this,MapActivity.class);
                /*webserviceBranchData = new ArrayList<ServiceNearByModel>();
                mIntent.putParcelableArrayListExtra("branch_data", webserviceBranchData);*/
                startActivity(mIntent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                finish();
            }
        });

        if (NetworkUtils.getInstance(this).isOnline()) {
            listviewBranhes = (ListView)findViewById(R.id.listviewBranhes);
            getLocationDataFromService();
            //getCurrentLocation();
        } else {
            Toast.makeText(LocationActivity.this, "Please connect to internet.", Toast.LENGTH_LONG).show();
        }

    }

    /*private void getCurrentLocation(){
        // create class object
        gps = new GPSTracker(LocationActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

             currentLatitude = gps.getLatitude();
             currentLongitude = gps.getLongitude();

            getLocationDataFromService();

            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + currentLatitude + "\nLong: " + currentLongitude, Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText(LocationActivity.this, "Please enable your device location service.", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void getLocationDataFromService(){
        final ActivityConstant activityConstant = new ActivityConstant();
        activityConstant.showProgressDialog(LocationActivity.this, "Loading...");

        ServiceManager webServiceManager = new ServiceManager();

        //Log.d("Lat: " + currentLatitude, "Long: " + currentLongitude, null);

        Call<List<GetAllBranchesOnMap>> call = webServiceManager.apiService.getAllBranchesMapAndListing(HttpConstants.auth);
        call.enqueue(new Callback<List<GetAllBranchesOnMap>>() {
            @Override
            public void onResponse(Call<List<GetAllBranchesOnMap>> call, Response<List<GetAllBranchesOnMap>> response) {
                System.out.println("Response: " + response.body().toString());
                listviewBranhes.setAdapter(new BranchAdapter(LocationActivity.this, response.body()));
            }
            @Override
            public void onFailure(Call<List<GetAllBranchesOnMap>> call, Throwable t) {
                activityConstant.hideProgressDialog();
                Toast.makeText(LocationActivity.this, "An error occured in getting data.", Toast.LENGTH_SHORT).show();
            }
        });
        activityConstant.hideProgressDialog();
    }
}

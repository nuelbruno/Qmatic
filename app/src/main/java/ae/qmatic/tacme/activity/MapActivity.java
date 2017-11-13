package ae.qmatic.tacme.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ae.qmatic.tacme.R;
import ae.qmatic.tacme.model.DialogItem;
import ae.qmatic.tacme.model.GetAllBranchesOnMap;
import ae.qmatic.tacme.model.RemoteBookingBranchByIDModel;
import ae.qmatic.tacme.networkManager.ServiceManager;
import ae.qmatic.tacme.utils.ActivityConstant;
import ae.qmatic.tacme.utils.DialogUtils;
import ae.qmatic.tacme.utils.GPSTracker;
import ae.qmatic.tacme.utils.HttpConstants;
import ae.qmatic.tacme.utils.MyMarkers;
import ae.qmatic.tacme.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mdev3 on 8/15/16.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, DialogUtils.DialogListner{
    GoogleMap gMap;
    GPSTracker gps;
    double currentLatitude, currentLongitude;
    int Count =0;
    private ArrayList<MyMarkers> mMyMarkersArray = new ArrayList<MyMarkers>();
    private HashMap<Marker, MyMarkers> mMarkersHashMap;
    ServiceManager webServiceManager = new ServiceManager();
    ActivityConstant activityConstant = new ActivityConstant();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_layout);

        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<Marker, MyMarkers>();

        ////// access toolbar ////////////////
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.branches));
        ImageView imgRightIcon = (ImageView)toolbarTop.findViewById(R.id.imgRightIcon);
        ////// set map icon and it's listner ////////////////
        imgRightIcon.setImageResource(R.drawable.ic_list);
        imgRightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MapActivity.this, LocationActivity.class);
                startActivity(mIntent);
                finish();
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        if (NetworkUtils.getInstance(this).isOnline())
            getCurrentLocation();
        else
            Toast.makeText(MapActivity.this, "Please connect to internet.", Toast.LENGTH_LONG).show();
    }

    private void getCurrentLocation(){
        // create class object
        gps = new GPSTracker(MapActivity.this);
        // check if GPS enabled
        if(gps.canGetLocation()){
            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();
            getAllBranches();

        }else{
            Toast.makeText(MapActivity.this, "Please enable your device location service.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllBranches(){
        //Log.d("Lat: " + currentLatitude, "Long: " + currentLongitude, null);
        Call<List<GetAllBranchesOnMap>> call = webServiceManager.apiService.getAllBranchesMapAndListing(HttpConstants.auth);
        call.enqueue(new Callback<List<GetAllBranchesOnMap>>() {
            @Override
            public void onResponse(Call<List<GetAllBranchesOnMap>> call, final Response<List<GetAllBranchesOnMap>> response) {

                for(int i=0; i<response.body().size(); i++){

                    Log.e("LLLLLL" + response.body().get(i).getLatitude(), "LLLLLLL" + response.body().get(i).getLongitude(), null);
                    LatLng l = new LatLng(response.body().get(i).getLatitude(), response.body().get(i).getLongitude());

                    mMyMarkersArray.add(new MyMarkers(response.body().get(i).getId()+"",
                            response.body().get(i).getName(),
                            l,
                            "No Branch Address",
                            response.body().get(i).isBranchOpen(),
                            "Working days",
                            response.body().get(Count).getOpenTime() + " - " + response.body().get(Count).getCloseTime()

                    ));
                    //gMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
                }
                plotMarkers(mMyMarkersArray);
            }
            @Override
            public void onFailure(Call<List<GetAllBranchesOnMap>> call, Throwable t) {
                Toast.makeText(MapActivity.this, "An error occured in getting data.", Toast.LENGTH_SHORT).show();
            }
        });
        //activityConstant.hideProgressDialog();
    }

    @Override
    public void mDialogSelectedType(String mMesage, int i) {}

    @Override
    public void mDialogSelectedItem(DialogItem mDialogItem, int i) {}

    @Override
    public void mDialogSelectedHashMap(HashMap mHashMap) {}

    /////////// CUSTOM INFO ADAPTER CLASS
    /*public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter()
        {
        }

        @Override
        public View getInfoWindow(Marker marker)
        {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker)
        {
            MyMarkers myMarker = mMarkersHashMap.get(marker);
            View v = getLayoutInflater().inflate(R.layout.branch_detail,null);
            v.setLayoutParams(new ViewGroup.LayoutParams(900, 1800));

            TextView txtBranchName = (TextView)v.findViewById(R.id.txtViewBranchName);
            TextView txtviewBranchAddress = (TextView)v.findViewById(R.id.txtviewBranchAddress);
            TextView txtviewBranchCallNo = (TextView)v.findViewById(R.id.txtviewBranchCallNo);
            TextView txtviewBranchStatus = (TextView)v.findViewById(R.id.txtviewBranchStatus);
            TextView txtviewBranchWorkingDays = (TextView)v.findViewById(R.id.txtviewBranchWorkingDays);
            TextView txtviewBranchWorkingTime = (TextView)v.findViewById(R.id.txtviewBranchWorkingTime);

            txtviewBranchAddress.setText("NA");
            txtviewBranchCallNo.setText("NA");
            //txtviewBranchStatus.setText(myMarker.isBranchOpen()?"Open":"Close");
            txtviewBranchWorkingDays.setText("NA");
            txtviewBranchWorkingTime.setText(myMarker.getBranchTiming());

            txtBranchName.setText(myMarker.getBranchName());

            return v;
        }
    }*/
    private void plotMarkers(ArrayList<MyMarkers> markers)
    {
        if(markers.size() > 0)
        {
            for (MyMarkers myMarker : markers)
            {

                // Create user marker with custom icon and other options
                LatLng getLL = myMarker.getLatLong();
                //Log.e("Lat"+getLL.latitude, "Long"+getLL.longitude, null);
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(getLL.latitude, getLL.longitude));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pin));

                Marker currentMarker = gMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                //gMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
                Log.d("Branch Name; ", myMarker.getBranchName());
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(getLL)              // Sets the center of the map to Mountain View
                        .zoom(7)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    activityConstant.showProgressDialog(MapActivity.this, "Getting Branch Details...");
                    MyMarkers myMarker = mMarkersHashMap.get(marker);
                    Log.d("BranchId", ""+myMarker.getBranchId(), null);
                    getBranchDetailByBranchId(myMarker.getBranchId());

                    return true;
                }
            });
        }
    }

    public void getBranchDetailByBranchId(String branchId){

        Call<RemoteBookingBranchByIDModel> call = webServiceManager.apiService.getAllBranchDetailsById(branchId);
        call.enqueue(new Callback<RemoteBookingBranchByIDModel>() {
            @Override
            public void onResponse(Call<RemoteBookingBranchByIDModel> call, final Response<RemoteBookingBranchByIDModel> response) {

                Log.e("Response", "Response" + response.code());
                if (response.body() != null && response.code() == 200) {
                    if (response.isSuccessful()) {

                        HashMap<String,Object> branchData = new HashMap<String, Object>();
                        branchData.put("branch_id", response.body().getId());
                        branchData.put("branch_name", response.body().getName());
                        branchData.put("branch_address", response.body().getParameter().getAddress1());
                        branchData.put("branch_status", true);
                        branchData.put("branch_working_days", "No working days");
                        branchData.put("branch_timing", response.body().getParameter().getOpeningHour() +" - "+response.body().getParameter().getClosingHour());

                        dialogBranchDetail(branchData, response.body().getServiceDefiniionsList());
                    }
                }
            }
            @Override
            public void onFailure(Call<RemoteBookingBranchByIDModel> call, Throwable t) {
                Log.e("ERROR", "Getting Branch Detail:"+t.toString());
                Toast.makeText(MapActivity.this, "An error occured in getting data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialogBranchDetail(final HashMap<String, Object> bData, List<RemoteBookingBranchByIDModel.ServiceDefiniionsListEntity> serviceList){
        DisplayMetrics metrics = new DisplayMetrics();
        MapActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = (int) (metrics.heightPixels * 0.9);
        final Dialog dialogBranchDetail = new Dialog(MapActivity.this);
        dialogBranchDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBranchDetail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBranchDetail.setContentView(R.layout.branch_detail);
        dialogBranchDetail.setCanceledOnTouchOutside(true);
        dialogBranchDetail.setCancelable(true);
        dialogBranchDetail.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height);
        dialogBranchDetail.getWindow().setGravity(Gravity.BOTTOM);

        ////// Access required Textview and set Branch Deatil value on it.
        TextView txtBranchName = (TextView)dialogBranchDetail.findViewById(R.id.txtViewBranchName);
        TextView txtviewBranchAddress = (TextView)dialogBranchDetail.findViewById(R.id.txtviewBranchAddress);
        TextView txtviewBranchCallNo = (TextView)dialogBranchDetail.findViewById(R.id.txtviewBranchCallNo);
        TextView txtviewBranchStatus = (TextView)dialogBranchDetail.findViewById(R.id.txtviewBranchStatus);
        TextView txtviewBranchWorkingDays = (TextView)dialogBranchDetail.findViewById(R.id.txtviewBranchWorkingDays);
        TextView txtviewBranchWorkingTime = (TextView)dialogBranchDetail.findViewById(R.id.txtviewBranchWorkingTime);
        ///// set values
        txtBranchName.setText(bData.get("branch_name").toString());
        txtviewBranchAddress.setText("branch_address");
        txtviewBranchCallNo.setText("NO NUMBER");
        txtviewBranchStatus.setText((Boolean) bData.get("branch_status") ? "Open" : "Close");
        txtviewBranchWorkingDays.setText(bData.get("branch_working_days").toString());
        txtviewBranchWorkingTime.setText(bData.get("branch_timing").toString());

        Button btnRemoteBooking = (Button) dialogBranchDetail.findViewById(R.id.btnRemoteBooking);
        Button btnBookAnAppointment = (Button) dialogBranchDetail.findViewById(R.id.btnBookAnAppointment);
        btnRemoteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intRemoteBooking = new Intent(MapActivity.this, FromLocationRemoteBooking.class);
                intRemoteBooking.putExtra("came_from", 0);
                intRemoteBooking.putExtra("BRANCH_ID",bData.get("branch_id").toString());
                intRemoteBooking.putExtra("BRANCH_NAME",bData.get("branch_name").toString());
                startActivity(intRemoteBooking);
                dialogBranchDetail.hide();
            }
        });

        btnBookAnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intBookAppointment = new Intent(MapActivity.this, FromLocationAppointment.class);
                intBookAppointment.putExtra("came_from", 0);
                startActivity(intBookAppointment);
                dialogBranchDetail.hide();
            }
        });
        addDynamicServicesForThisBranch(dialogBranchDetail, serviceList);
        activityConstant.hideProgressDialog();
        dialogBranchDetail.show();
    }

    public void addDynamicServicesForThisBranch(Dialog dialogBranchDetail, List<RemoteBookingBranchByIDModel.ServiceDefiniionsListEntity> serviceList){

        LinearLayout lLayoutLeft = (LinearLayout)dialogBranchDetail.findViewById(R.id.lLayoutLeft);
        LinearLayout lLayoutRight = (LinearLayout)dialogBranchDetail.findViewById(R.id.lLayoutRight);

        if (serviceList.size() > 0){
            for (int i=0; i<serviceList.size(); i++){
                TextView txtViewServiceName = new TextView(MapActivity.this);
                txtViewServiceName.setText(serviceList.get(i).getExternalName());
                txtViewServiceName.setPadding(15, 5, 5, 5); /// left, top, right, bottom
                txtViewServiceName.setTextColor(Color.BLACK);
                if (i%2 == 0)
                    lLayoutLeft.addView(txtViewServiceName);
                else
                    lLayoutRight.addView(txtViewServiceName);
            }
        }
    }
}

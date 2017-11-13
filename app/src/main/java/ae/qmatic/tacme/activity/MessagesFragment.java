package ae.qmatic.tacme.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

import ae.qmatic.tacme.R;


/**
 * Created by Ravi on 29/07/15.
 */
public class MessagesFragment extends Fragment {
    View rootView;

    GoogleMap mGoogleMap;
    MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            Log.e("Hellop ","sdfdsfs");
            return rootView;

        }
        rootView = inflater.inflate(R.layout.fragment_messages, null);
      //  initMap();
        return rootView;
    }

//    private void initMap() {
//
//
//        mActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mGoogleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
//                    //mGoogleMap.setMyLocationEnabled(true);
//                    mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                    mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
//                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
//                    mGoogleMap.getUiSettings().setCompassEnabled(false);
//                    mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
//                    mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
//                    //                mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
//                // Enable / Disable my location button
//                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
//                // Enable / Disable Compass icon
//                mGoogleMap.getUiSettings().setCompassEnabled(true);
//                // Enable / Disable Rotate gesture
//                mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
//                // Enable / Disable zooming functionality
//                mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
//                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
//                        23.422662, 53.757556), 7));
//
//
//                } catch (Exception ex) {
//                    Log.e("Map", "Map Load exception: " + ex.getMessage());
//                }
//            }
//        });
//    }

}

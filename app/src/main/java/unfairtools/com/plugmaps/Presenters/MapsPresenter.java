package unfairtools.com.plugmaps.Presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.HashMap;

import javax.inject.Inject;

import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Contracts.MapsContract;
import unfairtools.com.plugmaps.MarkerInfo;
import unfairtools.com.plugmaps.Repository;

/**
 * Created by brianroberts on 10/26/16.
 */

public class MapsPresenter implements MapsContract.Presenter, GoogleMap.OnMarkerClickListener {



    @Inject
    public SQLiteDatabase db;

    @Inject
    public Repository repository;



    private GoogleMap googleMap;

    BitmapDescriptor icon;

    private volatile int showId = -1;

    MapsContract.View mapFragment;



    /*
     * Must be called every time a map fragment when it calls onResume();
     * Call deregisterMapFragment when finished.
     */
    public void registerMapFragment(MapsContract.View v){
        this.mapFragment = v;
        repository.registerPresenter(Repository.PresenterType.MAPS_PRESENTER,this);
    }

    /*
     Call deregisterMapFragment when map fragment calls onPause();
     */
    public void deregisterMapFragment(){
        //this.mapFragment = null;
        //this.googleMap = null;
        //repository.deregisterPresenter(Repository.PresenterType.MAPS_PRESENTER);

    }

    HashMap<MarkerInfo,MarkerOptions> markerOptionsMap;


    public void gpsClicked(){

        Log.e("MapsPresenter", "GPS clicked");

    }

    public void receivePoints(HashMap<MarkerInfo,MarkerOptions> mMarkersHashMap){
        for(MarkerInfo m: markerOptionsMap.keySet()) {
            Log.e("MapsPresenter", "Already have " + m.name);
            mMarkersHashMap.remove(m);

        }


        for(MarkerInfo m: mMarkersHashMap.keySet()) {
            googleMap.addMarker(mMarkersHashMap.get(m));
            Log.e("Adding", "MAPPresenter: Adding " + m.name);
        }

        markerOptionsMap.putAll(mMarkersHashMap);

        for(MarkerInfo m: markerOptionsMap.keySet()) {
            Log.e("Adding", "HOLDING CURRENT: MAPPresenter: Adding " + m.name);
        }


    }

    public MapsPresenter(BaseApplication baseApplication){
        markerOptionsMap = new HashMap<MarkerInfo,MarkerOptions>();
        baseApplication.getServicesComponent().inject(this);
    }



    public void sendMapTo(double lat, double longitude) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longitude), 9.0f));
    }


    public boolean onMarkerClick(Marker m){
        m.showInfoWindow();
        return true;
    }

    public void initZoom() {

    }

    private void fillPoints(){
        repository.getPoints(googleMap.getProjection().getVisibleRegion().latLngBounds);
    }



    public void takeMap(GoogleMap gm) {

        this.googleMap = gm;
        if (ContextCompat.checkSelfPermission(mapFragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            this.googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        //this.fillPoints();
//
//        googleMap.setOnMarkerClickListener(this);
//
//        if (ContextCompat.checkSelfPermission(this.view.getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(this.view.getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
//                        PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//            googleMap.setMyLocationEnabled(true);
//
//        } else {
//            ActivityCompat.requestPermissions(this.view.getMainActivity(), new String[] {
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.ACCESS_COARSE_LOCATION },
//                    1);
//        }


//
//
//        view.getMainActivity().replaceSearchBarText();
//
//        InfoObject inf = new InfoObject();
//        inf.name = "Heart O the Hills Campground";
//        inf.ids = new int[]{0};
//        inf.latPoint = 48.0356029d;
//        inf.longPoint = -123.424074d;
//        inf.types = new int[]{0};
//        SQLMethods.addLocationLocal(db,inf);
//        MarkerInfoObject obj = new MarkerInfoObject();
//        obj.id_primary_key = 0; obj.description = "The beautiful Heart O' the Hills Campground";
//        SQLMethods.addLocationInfoLocal(db,obj);
//        LatLng lat = new LatLng(47.51f,-122.35f);
//
//        LatLng lat2 = SQLMethods.getMapLocationLatLng(db);
//        float zoom = SQLMethods.getMapLocationZoom(db);
//
//        Log.e("MapsPresenter", "latLong: " + lat2.latitude + ", " + lat2.longitude + " :: zoom " + zoom);
//
//        //6.833f = zoom default
//
//
        this.googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                MapsPresenter.this.fillPoints();
            }
        });
//
//        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(lat2,zoom));
//


    }
}
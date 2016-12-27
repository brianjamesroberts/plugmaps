package unfairtools.com.plugmaps.Presenters;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.MapsContract;
import unfairtools.com.plugmaps.Repository;

/**
 * Created by brianroberts on 10/26/16.
 */

public class MapsPresenter implements MapsContract.Presenter, GoogleMap.OnMarkerClickListener {


//    @Inject
//    SQLiteDatabase db;
//
//    @Inject
//    Repository repository;

//    @Inject
//    ApiService apiService;

    BaseApplication baseApp;

    MapsContract.View view;

    private GoogleMap googleMap;

    BitmapDescriptor icon;

    private volatile int showId = -1;

    public void receivePoints(HashSet<MarkerOptions> mMarkersHashSet){
        Log.e("Receive points", "Receive pints rec'vd from repo");
    }

    public MapsPresenter(MapsContract.View v, BaseApplication b) {
        view = v;
        baseApp = b;

        Log.e("New", "New MapsPresenter!!!!!");

        //icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_marker);
//
//
//        b.getServicesComponent().inject(this);
//
//
//        Log.e("Repo is", repository.toString());
//        repository.registerPresenter(0,this);
//
//        repository.getPoints();

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

    ;


    public void takeMap(GoogleMap gm) {
          this.googleMap = gm;
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
//        gm.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//
//                SQLMethods.setMapPrefs(db,googleMap.getCameraPosition().target, googleMap.getCameraPosition().zoom);
//                initPoints();
//            }
//        });
//
//        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(lat2,zoom));
//


    }
}
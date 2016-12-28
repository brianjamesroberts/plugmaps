package unfairtools.com.plugmaps;

import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;
import unfairtools.com.plugmaps.UI.MainActivity;


public class MapFragment extends SupportMapFragment implements MapsContract.View {

    @Inject
    public MapsPresenter mapsPresenter;


    @Override
    public void onPause(){
        mapsPresenter.deregisterMapFragment(this);
        super.onPause();
    }

    @Override
    public void onStart(){
        super.onStart();
        mapsPresenter.registerMapFragment(this);
    }

    public void addMarkers(HashMap<MarkerOptions,MarkerInfo> optionsHashMap){
        for(MarkerOptions m : optionsHashMap.keySet()){
            //
        }
    }

    public MainActivity getMainActivity(){
        return (MainActivity)getActivity();
    }

    public FragmentManager getFragmentManager2(){
        return getActivity().getFragmentManager();

    }
    public void zoomLocation(int x, int y, int zoom){

    }


    public MapFragment() {
        // Required empty public constructor
    }



    public static MapFragment newInstanceCustom(){
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
        }


        ((BaseApplication)getActivity().getApplication()).getServicesComponent().inject(this);
        mapsPresenter.initZoom();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.e("Dxxxxxxxxxxxxxxx", "Creating MapFragment view");

        OnMapReadyCallback cb = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapsPresenter.takeMap(googleMap);
            }
        };

        super.getMapAsync(cb);

        View viewMain = super.onCreateView(inflater, container, savedInstanceState);

        return viewMain;
        //return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}

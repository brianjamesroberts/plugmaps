package unfairtools.com.plugmaps;

import android.app.FragmentManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.unfairtools.campsites.base.BaseApplication;
//import com.unfairtools.campsites.contracts.unfairtools.com.plugmaps.MapsContract;
//import com.unfairtools.campsites.dagger.component.DaggerMapsComponent;
//import com.unfairtools.campsites.dagger.module.MapsModule;
//import com.unfairtools.campsites.presenters.MapsPresenter;

import java.util.List;

import javax.inject.Inject;

import unfairtools.com.plugmaps.UI.MainActivity;
//import android.support.v4.app.FragmentManager;


public class MapFragment extends SupportMapFragment {
    //implements
//} unfairtools.com.plugmaps.MapsContract.View {
    // TODO: Rename parameter arguments, choose names that match


    //@Inject
    //public MapsPresenter presenter;

    @Inject
    SQLiteDatabase db;

//    @Inject
//    ApiService apiService;

//    private String map_api_key = PrivateConstants.Google_Map_Key;

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;


    public MainActivity getMainActivity(){
        return (MainActivity)getActivity();
    }

    public FragmentManager getFragmentManager2(){
        return getActivity().getFragmentManager();

    }
    public void zoomLocation(int x, int y, int zoom){

    }

    public void placePoints(List<Integer> points){

    }

    public MapFragment() {
        // Required empty public constructor
    }

//    public static MapFragment newInstance(String param1, String param2) {
//        MapFragment fragment = new MapFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

//    public void setListeners(){
//        this.getMap().setBuildingsEnabled(true);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
        }


//        DaggerMapsComponent.builder()
//                .mapsModule(new MapsModule(this,(BaseApplication)getActivity().getApplication()))
//                //.realmModule(new RealmModule((BaseApplication)getActivity().getApplication()))
//                .build()
//                .inject(this);
//
//        presenter.log("fragment created");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        OnMapReadyCallback cb = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
               // presenter.takeMap(googleMap);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package unfairtools.com.plugmaps;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.HashSet;

import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Base.BaseView;

/**
 * Created by brianroberts on 12/22/16.
 */

public interface MapsContract {

    interface View extends BaseView<Presenter> {
//
//        void placePoints(List<Integer> points);
//
//        void zoomLocation(int x, int y, int zoom);
//
//        MainActivity getMainActivity();
//
//        FragmentManager getFragmentManager2();

        void addMarkers(HashMap<MarkerOptions,MarkerInfo> markerOptionsHashMap);

    }

//    interface MarkerInfoView extends BaseView<MarkerInfoPresenter> {
//        //MainActivity getMainActivity();
//    }
//
//    interface MarkerInfoPresenter extends BasePresenter {
//    }


    interface Presenter extends BasePresenter {

//
//        void sendMapTo(double lat, double longitude);
//        void takeMap(GoogleMap gm);
//        void initZoom();
//        void initPoints();
        void registerMapFragment(MapsContract.View v);
        void receivePoints(HashMap<MarkerOptions,MarkerInfo> markerOptions);
//        void showInfo(SupportMapFragment mapFragment);
//        boolean showMarkerTag(int id);
//        void setShowTagId(int id);



    }
}

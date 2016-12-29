package unfairtools.com.plugmaps.Contracts;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Base.BaseView;
import unfairtools.com.plugmaps.MarkerInfo;

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

        void addMarkers(HashMap<MarkerInfo,MarkerOptions> markerOptionsHashMap);

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
        void deregisterMapFragment(MapsContract.View v);
        void registerMapFragment(MapsContract.View v);
        void receivePoints(HashMap<MarkerInfo,MarkerOptions> markerOptions);
//        void showInfo(SupportMapFragment mapFragment);
//        boolean showMarkerTag(int id);
//        void setShowTagId(int id);



    }
}

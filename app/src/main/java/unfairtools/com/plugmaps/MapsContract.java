package unfairtools.com.plugmaps;

import android.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Base.BaseView;
import unfairtools.com.plugmaps.MainActivity;

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

    }

    interface MarkerInfoView extends BaseView<MarkerInfoPresenter> {
        //MainActivity getMainActivity();
    }

    interface MarkerInfoPresenter extends BasePresenter {
    }

    interface Presenter extends BasePresenter {

//
//        void sendMapTo(double lat, double longitude);
//        void takeMap(GoogleMap gm);
//        void initZoom();
//        void initPoints();
//        void showInfo(SupportMapFragment mapFragment);
//        boolean showMarkerTag(int id);
//        void setShowTagId(int id);



    }
}

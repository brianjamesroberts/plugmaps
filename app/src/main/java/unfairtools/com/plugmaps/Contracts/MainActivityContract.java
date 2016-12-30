package unfairtools.com.plugmaps.Contracts;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Base.BaseView;
import unfairtools.com.plugmaps.MarkerInfo;
import unfairtools.com.plugmaps.UI.MainActivity;

/**
 * Created by newuser on 12/28/16.
 */

public interface MainActivityContract {

    interface View extends BaseView<MainActivityContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        void registerMainActivityPresenter(MainActivityContract.View v);
        void deregisterMainActivityPresenter(MainActivityContract.View v);
    }
}

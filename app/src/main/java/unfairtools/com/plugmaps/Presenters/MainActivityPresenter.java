package unfairtools.com.plugmaps.Presenters;

import javax.inject.Inject;

import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Base.BasePresenter;
import unfairtools.com.plugmaps.Contracts.MainActivityContract;
import unfairtools.com.plugmaps.Contracts.MapsContract;
import unfairtools.com.plugmaps.Repository;
import unfairtools.com.plugmaps.UI.MainActivity;

/**
 * Created by newuser on 12/28/16.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter{

    MainActivityContract.View mainActivity;

    @Inject
    Repository repository;

    private BaseApplication baseApplication;

    public MainActivityPresenter(BaseApplication base){
        this.baseApplication = base;
        base.getServicesComponent().inject(this);
    }



    public void registerMainActivityPresenter(MainActivityContract.View v){
        this.mainActivity = v;
        repository.registerPresenter(Repository.PresenterType.MAIN_ACTIVITY_PRESENTER,this);
    }

    /*
     Call deregisterMapFragment when map fragment calls onPause();
     */
    public void deregisterMainActivityPresenter(MainActivityContract.View v){
        this.mainActivity = null;
        repository.deregisterPresenter(Repository.PresenterType.MAIN_ACTIVITY_PRESENTER);


    }

}

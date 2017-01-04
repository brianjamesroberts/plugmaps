package unfairtools.com.plugmaps.Presenters;

import android.util.Log;

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

    private double random;

    private BaseApplication baseApplication;

    public MainActivity getMainActivity(){
        Log.e("MainActPresenter", "Random: " + random);
        if(mainActivity== null)
            Log.e("MainActPresenter", "VIEW WAS NULLL ");
        return mainActivity.getMainActivity();
    }

    public MainActivityPresenter(BaseApplication base){
        this.baseApplication = base;
        base.getServicesComponent().inject(this);
        this.random = Math.random();
    }


    public void filterPageSelectorClicked(){
        Log.e("MainActivityPresenter", "FilterPageSelectorClicked");
        mainActivity.animateToolbarOpenClose();
    }

    public void registerMainActivityPresenter(MainActivityContract.View v){
        Log.e("MainActivityPresenter", random + "REGISTERING MAIN ACTIVITY WITH PRESENTER!");

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

package unfairtools.com.plugmaps.Dagger.Component;

/**
 * Created by brianroberts on 12/21/16.
 */


import dagger.Component;
import unfairtools.com.plugmaps.ChooseCarFragment;
import unfairtools.com.plugmaps.Dagger.Module.MapPresenterModule;
import unfairtools.com.plugmaps.Dagger.Module.RepoModule;
import unfairtools.com.plugmaps.Dagger.Module.SQLiteModule;
import unfairtools.com.plugmaps.MapFragment;
import unfairtools.com.plugmaps.Repository;
import unfairtools.com.plugmaps.UI.MainActivity;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;


import javax.inject.Singleton;

@Component(modules = {
        SQLiteModule.class,
        RepoModule.class,
        MapPresenterModule.class
})

@Singleton
public interface ServicesComponent {
    //TODO: kill this
    void inject(MainActivity presenter);

    //TODO: kill this
    void inject(ChooseCarFragment fragment);

    //inject presenter
    void inject(MapFragment fragment);

    //inject repo
    void inject(MapsPresenter presenter);

    //inject sqliteDB
    void inject(Repository repository);



}
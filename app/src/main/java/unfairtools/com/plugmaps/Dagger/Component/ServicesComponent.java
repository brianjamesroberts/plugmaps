package unfairtools.com.plugmaps.Dagger.Component;

/**
 * Created by brianroberts on 12/21/16.
 */


import dagger.Component;
import unfairtools.com.plugmaps.Dagger.Module.SQLiteModule;
import unfairtools.com.plugmaps.MainActivity;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;


import javax.inject.Singleton;

@Component(modules = {
        SQLiteModule.class
})

@Singleton
public interface ServicesComponent {
    void inject(MainActivity presenter);
    void inject(MapsPresenter presenter);
//    void inject(MarkerInfoFragmentPresenter presenter);
//    void inject(MarkerInfoCardAdapter adapter);
//    void inject(LoginManager loginManager);


}
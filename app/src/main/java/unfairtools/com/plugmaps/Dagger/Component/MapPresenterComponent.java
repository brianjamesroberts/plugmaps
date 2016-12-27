package unfairtools.com.plugmaps.Dagger.Component;

import javax.inject.Singleton;

import dagger.Component;
import unfairtools.com.plugmaps.Dagger.Module.MapPresenterModule;
import unfairtools.com.plugmaps.MapFragment;

/**
 * Created by brianroberts on 12/27/16.
 */

@Component(modules = {
        MapPresenterModule.class
})


@Singleton
public interface MapPresenterComponent {
    void inject(unfairtools.com.plugmaps.MapFragment mapfragment);
}
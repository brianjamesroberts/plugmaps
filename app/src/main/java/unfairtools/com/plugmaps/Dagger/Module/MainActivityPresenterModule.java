package unfairtools.com.plugmaps.Dagger.Module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Presenters.MainActivityPresenter;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;

/**
 * Created by newuser on 12/28/16.
 */

@Module
public class MainActivityPresenterModule {

        private BaseApplication base;


        public MainActivityPresenterModule(BaseApplication baseApplication) {
            this.base = baseApplication;
        }

        @Provides
        @Singleton
        MainActivityPresenter providePresenter() {
            return new MainActivityPresenter(base);
        }


    }
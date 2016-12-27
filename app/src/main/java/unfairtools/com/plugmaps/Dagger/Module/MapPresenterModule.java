package unfairtools.com.plugmaps.Dagger.Module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import dagger.Module;
import dagger.Provides;
import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.MapsContract;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;

/**
 * Created by brianroberts on 12/27/16.
 */

@Module
public class MapPresenterModule {

    private MapsContract.View view;
    private BaseApplication base;

    public MapPresenterModule(MapsContract.View v, BaseApplication b) {
        view = v;
        base = b;
    }

    @Provides
    MapsPresenter providePresenter() {
        return new MapsPresenter(view,base);
    }


    }
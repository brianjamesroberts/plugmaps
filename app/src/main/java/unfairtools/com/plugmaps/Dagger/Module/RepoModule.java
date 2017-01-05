package unfairtools.com.plugmaps.Dagger.Module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.OpenHelper;
import unfairtools.com.plugmaps.Repository;

/**
 * Created by brianroberts on 12/21/16.
 */

@Module
public class RepoModule {

    private BaseApplication base;

    public RepoModule(BaseApplication baseApplication) {
        base = baseApplication;
    }

    @Provides
    @Singleton
    Repository provideDatabase() {
        return Repository.newInstance(base);
    }
}
package unfairtools.com.plugmaps.Dagger.Module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import unfairtools.com.plugmaps.OpenHelper;
import unfairtools.com.plugmaps.Repository;

/**
 * Created by brianroberts on 12/21/16.
 */

@Module
public class RepoModule {

    private Context context;

    public RepoModule(Context c) {
        context = c;
    }

    @Provides
    @Singleton
    Repository provideDatabase() {
        return Repository.newInstance(context);
    }
}
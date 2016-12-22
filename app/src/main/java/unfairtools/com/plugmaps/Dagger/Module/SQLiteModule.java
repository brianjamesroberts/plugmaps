package unfairtools.com.plugmaps.Dagger.Module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import unfairtools.com.plugmaps.OpenHelper;

/**
 * Created by brianroberts on 12/21/16.
 */

@Module
public class SQLiteModule {

    private Context context;


    public SQLiteModule(Context c) {
        context = c;
    }

    @Provides
    @Singleton
    SQLiteDatabase provideDatabase() {
        return OpenHelper.getInstance(context).getWritableDatabase();
    }
}

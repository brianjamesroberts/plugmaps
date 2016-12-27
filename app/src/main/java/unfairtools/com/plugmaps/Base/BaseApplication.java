package unfairtools.com.plugmaps.Base;

import android.app.Application;

import unfairtools.com.plugmaps.Dagger.Component.DaggerServicesComponent;
import unfairtools.com.plugmaps.Dagger.Component.ServicesComponent;
import unfairtools.com.plugmaps.Dagger.Module.SQLiteModule;

/**
 * Created by brianroberts on 12/21/16.
 */


    public class BaseApplication extends Application {

        private ServicesComponent servicesComponent;



        public void onCreate(){
            servicesComponent =
                    DaggerServicesComponent.builder()
                            .sQLiteModule(new SQLiteModule(this))
                            .build();
        }

        public ServicesComponent getServicesComponent(){
            return servicesComponent;
        }


    }


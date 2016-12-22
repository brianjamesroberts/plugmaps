package unfairtools.com.plugmaps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_searchbar_frame);
        if(fragment == null){
            fragment = ChooseCarFragment.newInstance("","");
        }


        if(!fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_main_searchbar_frame,fragment);
            fragmentTransaction.commit();
        }


        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_map_frame);
        if(mapFragment == null){
            mapFragment = MapFragment.newInstance();
        }


        if(!mapFragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_main_map_frame,mapFragment);
            fragmentTransaction.commit();
        }


    }


}

package unfairtools.com.plugmaps.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import unfairtools.com.plugmaps.R;

public class CarChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_chooser);

//        View view = findViewById(R.id.car_chooser_fragment_container);
//        Animation mLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
//        mLoadAnimation.setDuration(5900);
//        view.startAnimation(mLoadAnimation);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.car_chooser_fragment_container);
        if(fragment == null){
            fragment = ChooseCarFragment.newInstance("","");
        }


        if(!fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.car_chooser_fragment_container,fragment);
            fragmentTransaction.commit();
        }
    }
}

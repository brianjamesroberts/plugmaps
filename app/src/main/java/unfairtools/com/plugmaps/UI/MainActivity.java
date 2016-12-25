package unfairtools.com.plugmaps.UI;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import unfairtools.com.plugmaps.ChooseCarFragment;
import unfairtools.com.plugmaps.MapFragment;
import unfairtools.com.plugmaps.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.map_cointainer);
        if(fragment == null){
            fragment = ChooseCarFragment.newInstance("","");
        }


        if(!fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.map_cointainer,fragment);
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


        ((FloatingActionButton)findViewById(R.id.fab)).setOnClickListener(new FloatingActionButton.OnClickListener(){
            public void onClick(View v){

                Log.e("MainActivity", "CLICK");

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);

        LayoutInflater mInflater= LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.actionbar_searchfield, null);


        toolbar.addView(mCustomView);

        setSupportActionBar(toolbar);

        toolbar.findViewById(R.id.toolbar_display_only_text).setVisibility(View.GONE);
        toolbar.findViewById(R.id.toolbar_recycler_view_tiles).setVisibility(View.GONE);
        toolbar.findViewById(R.id.toolbar_x_button).setBackgroundResource(R.drawable.ic_close_black);
        toolbar.findViewById(R.id.toolbar_x_button).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                MainActivity.this.getToolbarEditText().setText("");
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    onBackPressed();
                }
                else{

                    if (!drawer.isDrawerOpen(GravityCompat.START)) {
                        Log.e("MainActivity","opening drawer");
                        drawer.openDrawer(GravityCompat.START);
                    } else {
                        //nBackPressed();
                    }
                }

                Log.e("MainActivity","Navigation clicked");
            }
        });




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

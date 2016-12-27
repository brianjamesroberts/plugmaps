package unfairtools.com.plugmaps.UI;

import android.content.Context;
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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import unfairtools.com.plugmaps.ChooseCarFragment;
import unfairtools.com.plugmaps.MapFragment;
import unfairtools.com.plugmaps.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

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
            mapFragment = unfairtools.com.plugmaps.MapFragment.newInstanceCustom();
        }


        if(!mapFragment.isAdded()) {
            Log.e("Adding", "Adding new map fragment!");
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


    public ImageButton getClearTextButton(){
        return (ImageButton)findViewById(R.id.toolbar_x_button);
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public RecyclerView getToolbarRecyclerView(){
        return (RecyclerView)findViewById(R.id.toolbar_recycler_view_tiles);
    }

    public TextView getToolbarDisplayOnlyEditText(){
        return (TextView)findViewById(R.id.toolbar_display_only_text);
    }


    public AutoCompleteTextView getToolbarEditText(){
        return (AutoCompleteTextView)toolbar.findViewById(R.id.main_search_bar);
    }

    public FrameLayout getToolbarFrameLayout(){
        return (FrameLayout) findViewById(R.id.searchbar_framelayout);
    }

    public void setToggleHamburgerVisibility(boolean visible){
        if(visible) {
            toggle.setDrawerArrowDrawable(toggle.getDrawerArrowDrawable());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

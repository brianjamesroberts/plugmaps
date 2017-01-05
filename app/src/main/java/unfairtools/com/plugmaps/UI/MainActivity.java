package unfairtools.com.plugmaps.UI;

import android.content.Context;
import android.content.Intent;
import android.service.chooser.ChooserTarget;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import javax.inject.Inject;

import unfairtools.com.plugmaps.Base.BaseApplication;
import unfairtools.com.plugmaps.Contracts.MainActivityContract;
import unfairtools.com.plugmaps.Presenters.MapsPresenter;
import unfairtools.com.plugmaps.UI.MapFragment;
import unfairtools.com.plugmaps.Presenters.MainActivityPresenter;
import unfairtools.com.plugmaps.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainActivityContract.View {


    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private boolean filterWindowOpen = false;


    @Inject
    MainActivityPresenter presenter;

    @Inject
    MapsPresenter mapsPresenter;

    public MainActivity getMainActivity(){
        return MainActivity.this;
    }



    public void swapCarSelectorForMap(){
        Fragment carChooserFragment = getSupportFragmentManager().findFragmentByTag("chooser");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(carChooserFragment==null) {
            carChooserFragment = ChooseCarFragment.newInstance("","");
        }


        ft.replace(R.id.activity_main_map_frame,ChooseCarFragment.newInstance("",""), "map")
                .addToBackStack(null);



        supportPostponeEnterTransition();
        ft.commit();
        setToolbarForward();
        getToolbarDisplayOnlyEditText().setVisibility(View.VISIBLE);
        getToolbarDisplayOnlyEditText().setText("Select your vehicle");
    }

    public void swapMapForCarSelector(){

        animateToolbarOpenClose(true);
        getSupportFragmentManager().popBackStack();
        setToolbarBase();

    }

    //add a back button, get rid of filter button
    public void setToolbarForward(){
       getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getMainActivity().getSupportActionBar().setHomeButtonEnabled(true);
      getMainActivity().getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        animateToolbarOpenClose(false);
        getToolbarDisplayOnlyEditText().setVisibility(View.VISIBLE);
        findViewById(R.id.toolbar_filter_button).setVisibility(View.GONE);
        findViewById(R.id.main_search_bar_autocomplete_text).setVisibility(View.GONE);
    }

    //basic filtering/map toolbar
    public void setToolbarBase(){
//        toggle.setDrawerIndicatorEnabled(true);
//
        getMainActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
////        getMainActivity().getSupportActionBar().setHomeButtonEnabled(true);
//        //getMainActivity().getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
//        getMainActivity().getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toggle.syncState();
        animateToolbarOpenClose(true);
        Log.e("SetToolbarBase","Setting toolbar base");
        getToolbarDisplayOnlyEditText().setVisibility(View.GONE);
        findViewById(R.id.toolbar_filter_button).setVisibility(View.VISIBLE);
        findViewById(R.id.main_search_bar_autocomplete_text).setVisibility(View.VISIBLE);

    }

    public void animateToolbarOpenClose(){
        Log.e("MainActivity", "TODO: animate toolbar open close");
        animateToolbarOpenClose(!filterWindowOpen);
        filterWindowOpen = !filterWindowOpen;


    }


    public void animateToolbarOpenClose(boolean open){
        Log.e("MainActivity", "TODO: animate toolbar open close");
        if(!open) {
            ((LinearLayout) findViewById(R.id.searchbar_framelayout)).getLayoutParams().height = 150;
            findViewById(R.id.toolbar_filter_options).setVisibility(View.GONE);
        }else{
            ((LinearLayout) findViewById(R.id.searchbar_framelayout)).getLayoutParams().height = 600;
            findViewById(R.id.toolbar_filter_options).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.searchbar_framelayout).requestLayout();
        findViewById(R.id.toolbar_filter_options).requestLayout();
        //filterWindowOpen = !filterWindowOpen;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BaseApplication)getApplication()).getServicesComponent().inject(this);

        Fragment mapFragment = getSupportFragmentManager().findFragmentByTag("map");
        if(mapFragment == null){
            mapFragment = MapFragment.newInstanceCustom();
        }

        if(!mapFragment.isAdded()) {
            Log.e("Adding", "Adding new map fragment!");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.activity_main_map_frame,mapFragment,"map");
            fragmentTransaction.commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);

        LayoutInflater mInflater= LayoutInflater.from(this);

        final View mCustomView = mInflater.inflate(R.layout.actionbar_searchfield, null);

       // FrameLayout frame = (FrameLayout) findViewById(R.id.searchbar_framelayout);




        toolbar.addView(mCustomView);


        ((ImageButton)mCustomView.findViewById(R.id.toolbar_filter_button)).setOnClickListener(new ImageButton.OnClickListener(){
            public void onClick(View v){
                presenter.filterPageSelectorClicked();
            }
        });

        ((ImageButton)findViewById(R.id.image_button_select_car))
                .setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                presenter.swapCarSelectorForMap();


            }
        });


        setSupportActionBar(toolbar);

        toolbar.findViewById(R.id.toolbar_display_only_text).setVisibility(View.GONE);
        toolbar.findViewById(R.id.toolbar_x_button).setBackgroundResource(R.drawable.ic_close_black);
        toolbar.findViewById(R.id.toolbar_x_button).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

//        ActionBarDrawerToggle
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    onBackPressed();
                    //swapMapForCarSelector();
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
    public void onBackPressed(){
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
            finish();
        else
            swapMapForCarSelector();
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

//    public RecyclerView getToolbarRecyclerView(){
//        return (RecyclerView)findViewById(R.id.toolbar_recycler_view_tiles);
//    }

    public TextView getToolbarDisplayOnlyEditText(){
        return (TextView)findViewById(R.id.toolbar_display_only_text);
    }


    public AutoCompleteTextView getToolbarEditText(){
        return (AutoCompleteTextView)findViewById(R.id.main_search_bar_autocomplete_text);
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
    public void onPause(){
       // presenter.deregisterMainActivityPresenter(this);
        super.onPause();
    }

    @Override
    public void onStart(){
        super.onStart();
        presenter.registerMainActivityPresenter(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

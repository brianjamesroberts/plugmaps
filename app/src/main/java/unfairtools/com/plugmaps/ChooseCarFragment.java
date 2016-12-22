package unfairtools.com.plugmaps;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import unfairtools.com.plugmaps.Constants.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChooseCarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChooseCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseCarFragment extends Fragment {
    public static int numBrands;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private OnFragmentInteractionListener mListener;

    public ChooseCarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseCarFragment newInstance(String param1, String param2) {
        ChooseCarFragment fragment = new ChooseCarFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//        }
    }


    private int mScreenWidth;
    private int mCellWidth;
    //privatevalues int mOffset;

    //**    Constants
    private final static int mManufacturerTile_Width = 80;
    //**  ^ Constants ^
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_choose_car, container, false);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mScreenWidth = displaymetrics.widthPixels;


        mCellWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mManufacturerTile_Width, getResources()
                .getDisplayMetrics());

        //mOffset = (this.mScreenWidth / 2) - (mCellWidth / 2);


        Log.e("SW","Screen width is " +  mScreenWidth);
        Repository repository = new Repository(this.getActivity().getApplication());



        final RecyclerView manufacturerRecycler = (RecyclerView) view.findViewById(R.id.manufacturer_recycler);



        //TODO: Implement this bad boy!
        RecyclerView makeRecycler = (RecyclerView) view.findViewById(R.id.make_recycler);

        final LinearLayoutManager mLLM = new LinearLayoutManager(getActivity().getApplication(),LinearLayoutManager.HORIZONTAL,false);
        manufacturerRecycler.setLayoutManager(mLLM);
        manufacturerRecycler.setAdapter(new ChooseCarFragment.CarAdapter(CarPickerType.MANUFACTURER, repository.getModelData(CarPickerType.MANUFACTURER), manufacturerRecycler, getResources().getDisplayMetrics()));
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(manufacturerRecycler);



        //this commented section skews the entire recyclerview's screen with setPolyToPoly call on Matrix

//        ViewTreeObserver viewTreeObserver = manufacturerRecycler.getViewTreeObserver();
//        if (viewTreeObserver.isAlive()) {
//            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    manufacturerRecycler.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    int viewWidth = manufacturerRecycler.getWidth();
//                    int viewHeight = manufacturerRecycler.getHeight();
//
//
//
//                    Matrix m = new Matrix();
//                    int w = viewWidth;
//                    int h = viewHeight ;
//                    Log.e("Width, height:" , viewWidth + ", "+ viewHeight);
//                    final float DELTAX = w * 0.15f;
//                    float[] src = {
//                            0, 0, w, 0, w, h, 0, h
//                    };
//                    float[] dst = {
//                            0, 0, w, 0, w - DELTAX, h, DELTAX, h
//                    };
//                    //m.setSinCos(.5f,1.5f);
//                    m.setPolyToPoly(src, 0, dst, 0, 4);
//
//                    MyAnimation animation = new MyAnimation(m);
//                    animation.setDuration(0);
//                    animation.setFillAfter(true);
//                    manufacturerRecycler.setAnimation(animation);
//                }
//            });
//        }

        manufacturerRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int offset = recyclerView.computeHorizontalScrollOffset();
                int extent = recyclerView.computeHorizontalScrollExtent();
                int range = recyclerView.computeHorizontalScrollRange();

                int percentage = (int)(100.0 * offset / (float)(range - extent));
                int mWidth = mLLM.findViewByPosition(mLLM.findFirstCompletelyVisibleItemPosition()).getWidth();

                int actualItem = (((offset)/mWidth) + ((extent+mCellWidth)/2)/mWidth);

//                  Log.i("RecyclerView", "scroll percentage: "+ percentage + "%");
//                  Log.i("layoutmanager","children: " + mLLM.findFirstCompletelyVisibleItemPosition());
//                  Log.i("REC","offest" + offset);
//                  Log.i("REC","extent" + extent);
//                  Log.i("REC","range" + range);
//                  Log.i("Centered item: ", "Centered item: " + (((offset/mWidth) + (extent/2)/mWidth))%5);
//                  Log.i("Centered actual item: ", "" + ((offset/mWidth) + (extent/2)/mWidth));


                for (int i =  mLLM.findFirstVisibleItemPosition(); i <= mLLM.findLastVisibleItemPosition(); i++){

                    //Log.e("Viewholder Pos", "" + i + ": " + vh.getPosition());

                    ChooseCarFragment.ModelMakeHolder vh = (ChooseCarFragment.ModelMakeHolder)recyclerView.findViewHolderForAdapterPosition(i);

                    double leftPercent =  (((double)(i+1)*mCellWidth)-((double)offset)) / ((double)extent);
                    double oneHalfImageTileWidth = (((double)mCellWidth)/((double)extent))/2f;

                    vh.updateMatrix(leftPercent,oneHalfImageTileWidth);


                    if(i==actualItem) { //its the selected item (in the middle of the recycler)
                        vh.image.setBackgroundColor(Color.argb(100,0,100,20));
                    }else {
                        vh.image.setBackgroundColor(Color.argb(100,119, 121, 130));
                    }
                }

            }
        });


        return view;
    }

    public enum CarPickerType {
        MANUFACTURER,MODEL
    }

    public static class MakeModelData{
        public Drawable img;
        public String stringInfo;
    }

    private static class CarAdapter extends RecyclerView.Adapter<ChooseCarFragment.ModelMakeHolder>{

        private ArrayList<MakeModelData> data;
        private CarPickerType type;
        private RecyclerView recycler;
        DisplayMetrics displayMetrics;


        public CarAdapter(CarPickerType adapterType, ArrayList<MakeModelData> dat, RecyclerView recyclerView, DisplayMetrics displayMetrics1){
            this.type = adapterType;
            this.data = dat;
            this.recycler = recyclerView;
            this.displayMetrics = displayMetrics1;

        }

        @Override
        public ChooseCarFragment.ModelMakeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.car_image_text_tile, parent, false);

            ChooseCarFragment.ModelMakeHolder imageTile = new ChooseCarFragment.ModelMakeHolder(view,displayMetrics);
            return imageTile;
        }



        @Override
        public void onBindViewHolder(ChooseCarFragment.ModelMakeHolder holder, int position) {

            holder.image.setImageDrawable(data.get(position).img);
            holder.textView.setText(data.get(position).stringInfo);

            holder.image.setTag(position);
            holder.image.setOnClickListener(new View.OnClickListener (){


                @Override
                public void onClick(View view) {
                    Log.e("Click","CLICK" + (int)view.getTag() + " offset ; " +(recycler.getWidth()));
                    Log.e("Width", "Width/2: " + Constants.dpToInt(Constants.CarHolderWidth/2,displayMetrics)*2);
                    ((LinearLayoutManager)recycler.getLayoutManager()).
                            scrollToPositionWithOffset((int)view.getTag(),
                                    (recycler.getWidth())/2 - Constants.dpToInt(Constants.CarHolderWidth,displayMetrics)/2);
                }
            });
        }


        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static class ModelMakeHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        public TextView textView;
        public Matrix defaultMatrix;
        public DisplayMetrics displayMetrics;
        public FrameLayout layout;

        public static float bigFactor = .37f;
        public static float smallFactor = .15f;

        public ModelMakeHolder(View itemView, DisplayMetrics displayM) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.make_model_image);
            this.textView = (TextView) itemView.findViewById(R.id.make_model_text);
            this.defaultMatrix = image.getImageMatrix();
            this.displayMetrics = displayM;
            this.layout = (FrameLayout) itemView.findViewById(R.id.brand_holder_layout);
        }

        public void updateMatrix(double screenRightPos, double oneViewPercentageHalf){

            //  Log.e("Updating", "updateMatrix(" + screenRightPos + "), oneViewPercentageHalf(" + oneViewPercentageHalf + ")");


            Matrix m = new Matrix();
            if(screenRightPos < (.50 +  oneViewPercentageHalf)){
                //this view's centre is left of midpoint of the recyclerview
                //(rectangle should appear smaller on its left side)
                //Log.e("View", "View is on left");

                int h = image.getHeight();
                int w = image.getWidth();



                double midViewPos = screenRightPos -  oneViewPercentageHalf;
                double screenLeftFactor = Math.min(1d,midViewPos/(.5d) + oneViewPercentageHalf*2d);


                int spacer2Width =Math.max(0,(int)(screenLeftFactor * Constants.dpToInt(17,displayMetrics)));
                int spacer1Width = Constants.dpToInt(34,displayMetrics) - spacer2Width;

                ViewGroup.MarginLayoutParams params  = (ViewGroup.MarginLayoutParams)image.getLayoutParams();
                params.setMargins(spacer1Width,0,spacer2Width,0);

                float shrinkFactorBig = (1f-(float)screenLeftFactor)*bigFactor*h;
                float shrinkFactorSmall = (1f-(float)screenLeftFactor)*smallFactor*h;

                float[] src = {
                        0, 0, w, 0, w, h, 0, h
                };
                float[] dst = {
                        0 + (shrinkFactorSmall*(.15f*shrinkFactorSmall))*3 , 0 + shrinkFactorBig, //BL
                        w, 0 + shrinkFactorSmall, //BR
                        w, h - shrinkFactorSmall, //TR
                        0 + (shrinkFactorSmall*(.15f*shrinkFactorSmall))*3, h - shrinkFactorBig //TL
                };


                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);

            }else if((screenRightPos - oneViewPercentageHalf) > .50d){
                //this view's centre is right of midpoint of the recyclerview
                //(rectangle should appear smaller on its right side)
                //Log.e("View", "View is on right");



                int h = image.getHeight();
                int w = image.getWidth();


                //midpoint(centre) of this view % (0 to 1 value)
                double midViewPos = screenRightPos - oneViewPercentageHalf;
                double screenLeftFactor = Math.min(1d,1-((midViewPos-.5d))/(.5d) + oneViewPercentageHalf*2d);



                //spacers compact views together (and towards centre) as they spread towards the left and right edges of the recyclerview.
                int spacer1Width =Math.max(0,(int)(screenLeftFactor * Constants.dpToInt(17,displayMetrics)));
                int spacer2Width = Constants.dpToInt(34,displayMetrics) - spacer1Width;


                ViewGroup.MarginLayoutParams params  = (ViewGroup.MarginLayoutParams)image.getLayoutParams();
                params.setMargins(spacer1Width,0,spacer2Width,0);



                float shrinkFactorBig = (1f-(float)screenLeftFactor)*bigFactor*h;
                float shrinkFactorSmall = (1f-(float)screenLeftFactor)*smallFactor*h;

                float[] src = {
                        w, 0,0, 0, 0, h,w, h
                };

                float[] dst = {
                        w-(shrinkFactorSmall*(.15f*shrinkFactorSmall))*3, shrinkFactorBig,
                        0, shrinkFactorSmall,
                        0, h-shrinkFactorSmall,
                        w-(shrinkFactorSmall*(.15f*shrinkFactorSmall))*3, h - shrinkFactorBig
                };


                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);


                //return;
            }else{
                //perfect centre!


                image.setImageMatrix(defaultMatrix);
                m = image.getImageMatrix();
                m.reset();
                image.setImageMatrix(new Matrix());

                ViewGroup.MarginLayoutParams params  = (ViewGroup.MarginLayoutParams)image.getLayoutParams();
                params.setMargins(Constants.dpToInt(17,displayMetrics),0,Constants.dpToInt(17,displayMetrics),0);
                Log.e("View", "View is in MIDDLE!!!)");
            }


            //causes console warning... maybe add an onLayoutChangedListener and update from there?
            image.requestLayout();
//
        }





    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static class MyAnimation extends Animation {
        private Matrix matrix;

        public MyAnimation(Matrix matrix) {
            this.matrix = matrix;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            t.getMatrix().set(matrix);
        }
    }

}

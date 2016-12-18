package unfairtools.com.plugmaps;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseCarActivity extends AppCompatActivity {


    Repository repository;
    private int mScreenWidth;
    private int mCellWidth;
    private int mOffset;

    public static int ManufacturerTile_Width=80;
    public static int numBrands=0;

    public enum CarPickerType {
        MANUFACTURER,MODEL
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.mScreenWidth = displaymetrics.widthPixels;


        mCellWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ManufacturerTile_Width, getResources()
                .getDisplayMetrics());

        mOffset = (this.mScreenWidth / 2) - (mCellWidth / 2);


        Log.e("SW","Screen width is " +  mScreenWidth);
        Repository repository = new Repository(this.getApplication());




        final RecyclerView manufacturerRecycler =(RecyclerView) findViewById(R.id.manufacturer_recycler);



        RecyclerView makeRecycler = (RecyclerView) findViewById(R.id.make_recycler);

        final LinearLayoutManager mLLM = new LinearLayoutManager(getApplication(),LinearLayoutManager.HORIZONTAL,false);
        manufacturerRecycler.setLayoutManager(mLLM);
        manufacturerRecycler.setAdapter(new ChooseCarActivity.CarAdapter(CarPickerType.MANUFACTURER, repository.getModelData(CarPickerType.MANUFACTURER), manufacturerRecycler, getResources().getDisplayMetrics()));
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(manufacturerRecycler);


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
//                    m.setSinCos(.5f,1.5f);
//                    //m.setPolyToPoly(src, 0, dst, 0, 4);
//
//                    MyAnimation animation = new MyAnimation(m);
//                    animation.setDuration(0);
//                    animation.setFillAfter(true);
//                    //manufacturerRecycler.setAnimation(animation);
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
//
                int actualItem = (((offset)/mWidth) + ((extent+mCellWidth)/2)/mWidth);

                Log.i("RecyclerView", "scroll percentage: "+ percentage + "%");
                //                Log.e("layoutmanager","children: " + mLLM.findFirstCompletelyVisibleItemPosition());
//                Log.i("REC","offest" + offset);
//                Log.i("REC","extent" + extent);
//                Log.i("REC","range" + range);
//                Log.i("Centered item: ", "Centered item: " + (((offset/mWidth) + (extent/2)/mWidth))%5);
                //Log.i("Centered actual item: ", "" + ((offset/mWidth) + (extent/2)/mWidth));


                ModelMakeHolder m = (ModelMakeHolder)manufacturerRecycler.getChildViewHolder(mLLM.getChildAt(mLLM.getChildCount()/2));
               // Log.e("Visible children:", "visible children " + mLLM.getChildCount());





                for (int i =  mLLM.findFirstVisibleItemPosition(); i <= mLLM.findLastVisibleItemPosition(); i++){




                    ModelMakeHolder vh = (ModelMakeHolder)recyclerView.findViewHolderForAdapterPosition(i);






                    double leftPercent =  (((double)(i+1)*mCellWidth)-((double)offset)) / ((double)extent);
                    double oneHalfImageTileWidth = (((double)mCellWidth)/((double)extent))/2f;

                    vh.updateMatrix(leftPercent,oneHalfImageTileWidth);


//                    Log.e(" i", "i(" + i + ")*mCellWidth(" + mCellWidth+")) - offset("+offset+"))/extent("+extent+")");
//                    Log.e("equals", "equals " + ((i*mCellWidth) - extent)/mScreenWidth);

                    //vh.updateMatrix(0d,0d);
                    Log.e("Viewholder Pos", "" + i + ": " + vh.getPosition());

                    //vh.image.setBackgroundColor(Color.argb(100,0,100,20));
                    if(i==actualItem) {
                        vh.image.setBackgroundColor(Color.argb(100,0,100,20));

//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(170, 170);
//                        vh.image.setLayoutParams(layoutParams);
//                        vh.image.requestLayout();


                    }else {
//                        if(i<actualItem) {
//                            Matrix matrix = new Matrix();
//                            matrix.postSkew(0.5f, 0.5f, 0f, 0f);
//                            vh.image.setImageMatrix(matrix);
//                        }else{
//                            Matrix matrix = new Matrix();
//                            matrix.postSkew(-0.5f, 0.5f, 0f, 0f);
//                            vh.image.setImageMatrix(matrix);
//                        }
                        vh.image.setBackgroundColor(Color.argb(100,119, 121, 130));


//
//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
//                        vh.image.setLayoutParams(layoutParams);
//                        vh.image.requestLayout();


                    }



                    //vh.imageView.setTranslationY( computedParalaxOffset ); // assuming ViewHolder has a imageView field on which you want to apply the parallax effect
                }

                //m.image.requestLayout();

            }
        });

    }

    public static class MakeModelData{
        public Drawable img;
        public String stringInfo;

    }

    private static class CarAdapter extends RecyclerView.Adapter<ModelMakeHolder>{

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
        public ModelMakeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.car_image_text_tile, parent, false);

            ChooseCarActivity.ModelMakeHolder imageTile = new ChooseCarActivity.ModelMakeHolder(view,displayMetrics);
            return imageTile;
        }



        @Override
        public void onBindViewHolder(ModelMakeHolder holder, int position) {

            //holder.image.setBackground(data.get(position).img);
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

//            holder.image.setScaleType(ImageView.ScaleType.MATRIX);
//            Matrix imageMatrix = holder.image.getImageMatrix();
//            imageMatrix.setSkew(.25f,0f, 0.5f, 0.5f);
//            //imageMatrix.setTranslate(holder.getLayoutPosition()*5,holder.getLayoutPosition()*5);
//            holder.image.setImageMatrix(imageMatrix);



        }


        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static class ModelMakeHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView textView;
        public Matrix defaultMatrix;
        public DisplayMetrics displayMetrics;
        public Space spacer1;
        public Space spacer2;

        public static float bigFactor = .37f;
        public static float smallFactor = .15f;

        public ModelMakeHolder(View itemView, DisplayMetrics displayM) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.make_model_image);
            textView = (TextView) itemView.findViewById(R.id.make_model_text);
            defaultMatrix = image.getImageMatrix();
            this.displayMetrics = displayM;
            this.spacer1 = (Space) itemView.findViewById(R.id.space1);
            this.spacer2 = (Space) itemView.findViewById(R.id.space2);
        }

        public void updateMatrix(double screenRightPos, double oneViewPercentageHalf){

          //  Log.e("Updating", "updateMatrix(" + screenRightPos + "), oneVIewPercentageHalf(" + oneViewPercentageHalf + ")");
           // image.setImageMatrix(defaultMatrix);
            Matrix m = new Matrix();
                    // image.getImageMatrix();
//            m.reset();

            if(screenRightPos < (.50 +  oneViewPercentageHalf)){
                //rectangle smaller left side

                //m.preRotate((float)screenRightPos * 1000f);

                int h = image.getHeight();
                int w = image.getWidth();



                double midViewPos = screenRightPos -  oneViewPercentageHalf;
                double screenLeftFactor = Math.min(1d,midViewPos/(.5d) + oneViewPercentageHalf*2d);

                double screenLeftPos = screenRightPos - ((double) 2)*oneViewPercentageHalf;




                int spacer1Width =Math.max(0,(int)(screenLeftFactor * Constants.dpToInt(17,displayMetrics)));

                int spacer2Width = Constants.dpToInt(34,displayMetrics) - spacer1Width;

//                Log.e("Spacer factor %", "Spacer factor %: " + screenLeftFactor);
  //              Log.e("Spacer", "Spacer1 width: " + spacer1Width);
                spacer2.getLayoutParams().width = spacer1Width;
                spacer1.getLayoutParams().width = spacer2Width;

                spacer2.requestLayout();
                spacer1.requestLayout();
                image.requestLayout();




                float shrinkFactorBig = (1f-(float)screenLeftFactor)*bigFactor*h;
                float shrinkFactorSmall = (1f-(float)screenLeftFactor)*smallFactor*h;
              //  Log.e("Blah","big,small , width: " + shrinkFactorBig+ ", " + shrinkFactorSmall + ", " + w);

                float[] src = {
                        0, 0, w, 0, w, h, 0, h
                };
                float[] dst = {
                        0 + shrinkFactorSmall*3 , 0 + shrinkFactorBig, //BL
                        w, 0 + shrinkFactorSmall, //BR
                        w, h - shrinkFactorSmall, //TR
                        0 + shrinkFactorSmall*3, h - shrinkFactorBig //TL
                };

//                for(float f2 : dst)
//                    Log.e("dst", "dst: " + f2);
                //m.reset();
                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);
                //Log.e("View", "View is on left");
                return;



            }else if((screenRightPos - oneViewPercentageHalf) > .50d){
                //m.preSkew(-.1f,.1f);
                //m.preRotate((float)screenRightPos * 1000f);

                double screenLeftPos = screenRightPos - (double) 2*oneViewPercentageHalf;
                //Log.e("Double", "Scren left pos  " + screenLeftPos);



                //screenRightPos = (screenRightPos + i - .5d)/(.5d + 3d*i);

                int h = image.getHeight();
                int w = image.getWidth();


                double midViewPos = screenRightPos - oneViewPercentageHalf;
                //Log.e("mid", "midview pos:" + midViewPos);
                double screenLeftFactor = Math.min(1d,1-((midViewPos-.5d))/(.5d) + oneViewPercentageHalf*2d);





                int spacer1Width =Math.max(0,(int)(screenLeftFactor * Constants.dpToInt(17,displayMetrics)));


                int spacer2Width = Constants.dpToInt(34,displayMetrics) - spacer1Width;

//                Log.e("Spacer factor %", "Spacer factor %: " + screenLeftFactor);
//                Log.e("Spacer", "Spacer1, spacer2 width: " + spacer1Width + ", " + spacer2Width);
                spacer1.getLayoutParams().width = spacer1Width;
                spacer2.getLayoutParams().width = spacer2Width;

//                spacer1.getLayoutParams().width = 0;
//                spacer2.getLayoutParams().width = 0;

                spacer2.requestLayout();
                spacer1.requestLayout();
                image.requestLayout();


                float shrinkFactorBig = (1f-(float)screenLeftFactor)*bigFactor*h;
                float shrinkFactorSmall = (1f-(float)screenLeftFactor)*smallFactor*h;
                //Log.e("Blah","big,small , width: " + shrinkFactorBig+ ", " + shrinkFactorSmall + ", " + w);

                float[] src = {
                         w, 0,0, 0, 0, h,w, h
                };

                float[] dst = {
                        w-shrinkFactorSmall*3, shrinkFactorBig,
                        0, shrinkFactorSmall,
                        0, h-shrinkFactorSmall,
                        w-shrinkFactorSmall*3, h - shrinkFactorBig
                };


                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);

                //we're to the left of middle
                //Log.e("View", "View is on right");
                return;
            }else{
                image.setImageMatrix(defaultMatrix);
                m = image.getImageMatrix();
                m.reset();
                image.setImageMatrix(new Matrix());
                image.requestLayout();

                spacer1.getLayoutParams().width = Constants.dpToInt(17,displayMetrics);
                spacer2.getLayoutParams().width = Constants.dpToInt(17,displayMetrics);
                spacer1.requestLayout();
                spacer2.requestLayout();
                //dead center, do nothing.
                Log.e("View", "View is in MIDDLE!!!)");
            }

            //image.setImageMatrix(m);
//            image.invalidate();
//            image.requestLayout();





        }





    }

}

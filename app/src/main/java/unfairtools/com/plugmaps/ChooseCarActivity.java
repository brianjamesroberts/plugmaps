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
import android.widget.ImageView;
import android.widget.LinearLayout;
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


        ViewTreeObserver viewTreeObserver = manufacturerRecycler.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    manufacturerRecycler.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int viewWidth = manufacturerRecycler.getWidth();
                    int viewHeight = manufacturerRecycler.getHeight();



                    Matrix m = new Matrix();
                    int w = viewWidth;
                    int h = viewHeight ;
                    Log.e("Width, height:" , viewWidth + ", "+ viewHeight);
                    final float DELTAX = w * 0.15f;
                    float[] src = {
                            0, 0, w, 0, w, h, 0, h
                    };
                    float[] dst = {
                            0, 0, w, 0, w - DELTAX, h, DELTAX, h
                    };
                    m.setSinCos(.5f,1.5f);
                    //m.setPolyToPoly(src, 0, dst, 0, 4);

                    MyAnimation animation = new MyAnimation(m);
                    animation.setDuration(0);
                    animation.setFillAfter(true);
                    //manufacturerRecycler.setAnimation(animation);
                }
            });
        }

        manufacturerRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int offset = recyclerView.computeHorizontalScrollOffset();
                int extent = recyclerView.computeHorizontalScrollExtent();
                int range = recyclerView.computeHorizontalScrollRange();

                int percentage = (int)(100.0 * offset / (float)(range - extent));
                Log.e("layoutmanager","children: " + mLLM.findFirstCompletelyVisibleItemPosition());
                int mWidth = mLLM.findViewByPosition(mLLM.findFirstCompletelyVisibleItemPosition()).getWidth();
                Log.i("RecyclerView", "scroll percentage: "+ percentage + "%");
                Log.i("REC","offest" + offset);
                Log.i("REC","extent" + extent);
                Log.i("REC","range" + range);
//                Log.i("Centered item: ", "Centered item: " + (((offset/mWidth) + (extent/2)/mWidth))%5);
                int actualItem = ((offset/mWidth) + (extent/2)/mWidth);
                //Log.i("Centered actual item: ", "" + ((offset/mWidth) + (extent/2)/mWidth));


                ModelMakeHolder m = (ModelMakeHolder)manufacturerRecycler.getChildViewHolder(mLLM.getChildAt(mLLM.getChildCount()/2));
               // Log.e("Visible children:", "visible children " + mLLM.getChildCount());









                for (int i =  mLLM.findFirstVisibleItemPosition(); i <= mLLM.findLastVisibleItemPosition(); i++){

                    //recyclerView.findViewHolderForAdapterPosition(i).getAdapterPosition();

                    // i is the adapter position


                    ModelMakeHolder vh = (ModelMakeHolder)recyclerView.findViewHolderForAdapterPosition(i);


                    //pos = - BrandTile_extent




double leftPercent =  (((double)(i+1)*mCellWidth)-((double)offset)) / ((double)extent);
                    double oneHalfImageTileWidth = (((double)mCellWidth)/((double)extent))/2f;
                    vh.updateMatrix(leftPercent,oneHalfImageTileWidth);


//                    Log.e(" i", "i(" + i + ")*mCellWidth(" + mCellWidth+")) - offset("+offset+"))/extent("+extent+")");
//                    Log.e("equals", "equals " + ((i*mCellWidth) - extent)/mScreenWidth);

                    //vh.updateMatrix(0d,0d);
                    Log.e("Viewholder Pos", "" + i + ": " + vh.getPosition());

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
                        vh.image.setBackgroundColor(Color.rgb(255,255,255));


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

            ChooseCarActivity.ModelMakeHolder imageTile = new ChooseCarActivity.ModelMakeHolder(view);
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

        public ModelMakeHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.make_model_image);
            textView = (TextView) itemView.findViewById(R.id.make_model_text);
            defaultMatrix = image.getImageMatrix();
        }

        public void updateMatrix(double screenRightPos, double oneViewPercentageHalf){

            Log.e("Updating", "updateMatrix(" + screenRightPos + "), oneVIewPercentageHalf(" + oneViewPercentageHalf + ")");
            image.setImageMatrix(defaultMatrix);
            Matrix m = image.getImageMatrix();
            m.reset();

            if(screenRightPos < (.45d +  oneViewPercentageHalf)){
                //rectangle smaller left side

                //m.preRotate((float)screenRightPos * 1000f);

                double screenLeftPos = screenRightPos - (double) 2*oneViewPercentageHalf;
                Log.e("Double", "Scren left pos  " + screenLeftPos);


                double f = 0.25d;
                int h2 = 105;
                double i = oneViewPercentageHalf;

                screenLeftPos = (screenLeftPos + 2d*i)/(.5d + 3d*i);
                screenRightPos = (screenRightPos + 2d*i)/(.5d + 3d*i);

                float leftFactor = (float)(f * h2 * (1d-screenLeftPos));
                float rightFactor = (float)(f * h2 * (1d-screenRightPos));



                float moveInFactor = (float)((1d-(screenLeftPos+i))*f*6d*h2);






               // Log.e("math","L(0,1) R(0,1) to L(" + bottomLeft + "," + topLeft + ") R(" + bottomRight + ", " + topRight + ")");

                int h = image.getHeight();
                int w = image.getWidth();

                float[] src = {
                        0, 0, w, 0, w, h, 0, h
                };
                float[] dst = {
                        0 + moveInFactor, 0 + leftFactor, //BL
                        w, 0 + rightFactor, //BR
                        w, h - rightFactor, //TR
                        0 + moveInFactor, h - leftFactor //TL
                };

                for(float fs: src){
                    Log.e("logging", "src:" + fs);
                }
                for(float fs: dst){
                    Log.e("logging",  "dst:" + fs);
                }
                //m.setSinCos(.5f,1.5f);
                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);
                Log.e("View", "View is on left");
                return;



            }else if(screenRightPos > (.55d - oneViewPercentageHalf)){
                //m.preSkew(-.1f,.1f);
                //m.preRotate((float)screenRightPos * 1000f);

                double screenLeftPos = screenRightPos - (double) 2*oneViewPercentageHalf;
                Log.e("Double", "Scren left pos  " + screenLeftPos);


                double f = 0.25d;
                int h2 = 105;
                double i = oneViewPercentageHalf;

                screenLeftPos = (screenLeftPos + i - .5d)/(.5d + 3d*i);
                screenRightPos = (screenRightPos + i - .5d)/(.5d + 3d*i);

                float leftFactor = (float)(f * h2 * (screenLeftPos));
                float rightFactor = (float)(f * h2 * (screenRightPos));



                float moveInFactor = (float)((screenRightPos)*f*2d*h2);






                // Log.e("math","L(0,1) R(0,1) to L(" + bottomLeft + "," + topLeft + ") R(" + bottomRight + ", " + topRight + ")");

                int h = image.getHeight();
                int w = image.getWidth();

                float[] src = {
                        0, 0, w, 0, w, h, 0, h
                };
                float[] dst = {
                        0 , 0 + leftFactor, //BL
                        w - moveInFactor, 0 + rightFactor, //BR
                        w - moveInFactor, h - rightFactor, //TR
                        0, h - leftFactor //TL
                };

                for(float fs: src){
                    Log.e("logging", "src:" + fs);
                }
                for(float fs: dst){
                    Log.e("logging",  "dst:" + fs);
                }
                //m.setSinCos(.5f,1.5f);
                m.setPolyToPoly(src, 0, dst, 0, 4);
                MyAnimation animation = new MyAnimation(m);
                animation.setDuration(0);
                animation.setFillAfter(true);
                image.clearAnimation();
                image.setAnimation(animation);


                //we're to the left of middle
                Log.e("View", "View is on right");
                return;
            }else{
                image.setImageMatrix(defaultMatrix);
                m = image.getImageMatrix();
                m.reset();
                image.setImageMatrix(m);

                //dead center, do nothing.
                Log.e("View", "View is in middle)");
            }

            //image.setImageMatrix(m);
//            image.invalidate();
//            image.requestLayout();





        }





    }

}

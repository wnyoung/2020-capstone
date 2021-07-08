package com.example.ui_home_1105;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.BaseBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.BaseModel;
import org.eazegraph.lib.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyBarChart extends BaseBarChart {
    private static final String LOG_TAG = MyBarChart.class.getSimpleName();

    private List<MyBarModel>  mData;

    private Paint           mValuePaint;
    private int             mValueDistance = (int) Utils.dpToPx(3);

    public MyBarChart(Context context) {
        super(context);
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BarChart,
                0, 0
        );

        try {

        } finally {
            // release the TypedArray so that it can be reused.
            a.recycle();
        }

        initializeGraph();
    }

    /**
     * Adds a new {@link org.eazegraph.lib.models.BarModel} to the BarChart.
     * @param _Bar The BarModel which will be added to the chart.
     */
    public void addBar(MyBarModel _Bar) {
        Log.i("calendar", "Start adBar: ");
        if(mData.size() ==0){
            Log.i("calendar", "getLegendLabel: " + _Bar.getLegendLabel());
            mData.add(_Bar);
        }else {
            int cnt=0;
            for (MyBarModel i : mData) {
                Log.i("calendar", "getLegendLabel: " + i.getLegendLabel());

                if (i.getLegendLabel().equals(_Bar.getLegendLabel())) {
                    Log.i("calendar", "getLegendLabel2: " + i.getLegendLabel());
                    i.setValue(i.getValue() + _Bar.getValue());
                    Log.i("calendar", "getValue: " + i.getValue());
                    cnt++;
                }


            }
            if (cnt == 0) {
                Log.i("calendar", "adBar: " + _Bar.getLegendLabel());
                mData.add(_Bar);
            }
        }
        //mData.add(_Bar);
        onDataChanged();
    }

    /**
     * Adds a new list of {@link org.eazegraph.lib.models.BarModel} to the BarChart.
     * @param _List The BarModel list which will be added to the chart.
     */
    public void addBarList(List<MyBarModel> _List) {
        mData = _List;
        onDataChanged();
    }

    /**
     * Returns the data which is currently present in the chart.
     * @return The currently used data.
     */
    @Override
    public List<MyBarModel> getData() {
        return mData;
    }

    /**
     * Resets and clears the data object.
     */
    @Override
    public void clearChart() {
        mData.clear();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            performClick();
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is the main entry point after the graph has been inflated. Used to initialize the graph
     * and its corresponding members.
     */
    @Override
    protected void initializeGraph() {
        super.initializeGraph();
        mData = new ArrayList<>();

        mValuePaint = new Paint(mLegendPaint);
        mValuePaint.setTextAlign(Paint.Align.CENTER);

        if(this.isInEditMode()) {
            addBar(new MyBarModel(2.3f));
            addBar(new MyBarModel(2.f));
            addBar(new MyBarModel(3.3f));
            addBar(new MyBarModel(1.1f));
            addBar(new MyBarModel(2.7f));
            addBar(new MyBarModel(2.3f));
            addBar(new MyBarModel(2.f));
            addBar(new MyBarModel(3.3f));
            addBar(new MyBarModel(1.1f));
            addBar(new MyBarModel(2.7f));
        }
    }

    /**
     * Should be called after new data is inserted. Will be automatically called, when the view dimensions
     * has changed.
     */
    @Override
    protected void onDataChanged() {
        calculateBarPositions(mData.size());
        super.onDataChanged();
    }

    /**
     * Calculates the bar boundaries based on the bar width and bar margin.
     * @param _Width    Calculated bar width
     * @param _Margin   Calculated bar margin
     */
    protected void calculateBounds(float _Width, float _Margin) {
        float maxValue = 0;
        int   last     = 0;

        for (MyBarModel model : mData) {
            if(model.getValue() > maxValue) {
                maxValue = model.getValue();
            }
        }

        int valuePadding = mShowValues ? (int) mValuePaint.getTextSize() + mValueDistance : 0;

        float heightMultiplier = (mGraphHeight - valuePadding) / maxValue;

        for (MyBarModel model : mData) {
            float height = model.getValue() * heightMultiplier;
            last += _Margin / 2;
            model.setBarBounds(new RectF(last, mGraphHeight - height, last + _Width, mGraphHeight));
            model.setLegendBounds(new RectF(last, 0, last + _Width, mLegendHeight));
            last += _Width + (_Margin / 2);
        }

        Utils.calculateLegendInformation(mData, 0, mContentRect.width(), mLegendPaint);
    }

    /**
     * Callback method for drawing the bars in the child classes.
     * @param _Canvas The canvas object of the graph view.
     */
    protected void drawBars(Canvas _Canvas) {

        for (MyBarModel model : mData) {
            RectF bounds = model.getBarBounds();
            mGraphPaint.setColor(model.getColor());

            _Canvas.drawRect(
                    bounds.left,
                    bounds.bottom - (bounds.height() * mRevealValue),
                    bounds.right,
                    bounds.bottom, mGraphPaint);

            int answer = (int) model.getValue();
            int sec = (answer) % 60;
            int min = (answer) / 60 % 60;
            int hour = (answer) / 3600 ;
            String result;
            if (hour == 0) {
                if (min == 0) {
                    result = String.format("%dS", sec);
                } else {
                    result = String.format("%dM", min);
                }
            } else {
                result = String.format("%dH %dM", hour, min);
            }

            if (mShowValues) {
                _Canvas.drawText(result, model.getLegendBounds().centerX(),
                        bounds.bottom - (bounds.height() * mRevealValue) - mValueDistance, mValuePaint);
            }
        }
    }

    /**
     * Returns the list of data sets which hold the information about the legend boundaries and text.
     * @return List of BaseModel data sets.
     */
    @Override
    protected List<? extends BaseModel> getLegendData() {
        return mData;
    }

    @Override
    protected List<RectF> getBarBounds() {
        ArrayList<RectF> bounds = new ArrayList<RectF>();
        for (MyBarModel model : mData) {
            bounds.add(model.getBarBounds());
        }
        return bounds;
    }
}

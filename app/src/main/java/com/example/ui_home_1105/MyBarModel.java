package com.example.ui_home_1105;

import android.graphics.Rect;
import android.graphics.RectF;

import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.BaseModel;

public class MyBarModel extends BaseModel implements Comparable {

    public MyBarModel(String _legendLabel, float _value, int _color) {
        super(_legendLabel);
        mLegendLabel = _legendLabel;
        mValue = _value;
        mColor = _color;
    }

    public MyBarModel(float _value, int _color) {
        super("" + _value);
        mLegendLabel ="" + _value;
        mValue = _value;
        mColor = _color;
    }

    public MyBarModel(float _value) {
        super("" + _value);
        mLegendLabel ="" + _value;
        mValue = _value;
        mColor = 0xFFFF0000;
    }
    public String getLegendLabel() {
        return mLegendLabel;
    }

    public void setLegendLabel(String _legendLabel) {
        mLegendLabel = _legendLabel;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float _value) {
        mValue = _value;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int _color) {
        mColor = _color;
    }

    public RectF getBarBounds() {
        return mBarBounds;
    }

    public void setBarBounds(RectF _bounds) {
        mBarBounds = _bounds;
    }

    public boolean isShowValue() {
        return mShowValue;
    }

    public void setShowValue(boolean _showValue) {
        mShowValue = _showValue;
    }

    public Rect getValueBounds() {
        return mValueBounds;
    }

    public void setValueBounds(Rect _valueBounds) {
        mValueBounds = _valueBounds;
    }

    @Override
    public int compareTo(Object o) {
        BarModel bar = (BarModel) o;
        if (this.mValue > bar.getValue()) {
            return 1;
        }
        else if (this.mValue == bar.getValue()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    /**
     * legendLabel of the bar.
     */
    private String mLegendLabel;

    /**
     * Value of the bar.
     */
    private float mValue;

    /**
     * Color in which the bar will be drawn.
     */
    private int mColor;

    /**
     * Bar boundaries.
     */
    private RectF mBarBounds;

    private boolean mShowValue = false;

    private Rect mValueBounds = new Rect();
}
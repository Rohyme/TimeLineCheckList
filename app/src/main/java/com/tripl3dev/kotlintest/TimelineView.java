package com.tripl3dev.kotlintest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimelineView extends View{

    private Drawable mMarker;
    private Drawable mStartLine;
    private Drawable mEndLine;
    private int mMarkerSize;
    private int mLineSize;
    private int mLineOrientation;
    private int mLinePadding;
    private boolean mMarkerInCenter;

    private Rect mBounds;
    private Context mContext;
    private Drawable mCheckedMarker;
    private Drawable mUnCheckedMarker;
    private int mMarkerStatus;

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mMarkerSize = dpToPx(20, mContext);
        mLineSize = dpToPx(2, mContext);
        mLineOrientation = 1;
        mLinePadding = 0;
        mMarkerInCenter = true;


        if (mMarker == null) {
            mMarker = mContext.getResources().getDrawable(R.drawable.ic_marker);
        }

        if (mStartLine == null && mEndLine == null) {
            mStartLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.darker_gray));
            mEndLine = new ColorDrawable(mContext.getResources().getColor(android.R.color.darker_gray));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        // Width and height to determine the final view through a systematic approach to decision-making
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
        initDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // When the view is displayed when the callback
        // Positioning Drawable coordinates, then draw
        initDrawable();
    }


    private void initDrawable() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();

        int width = getWidth();// Width of current custom view
        int height = getHeight();

        int cWidth = width - pLeft - pRight;// Circle width
        int cHeight = height - pTop - pBottom;

        int markSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));


        switch (mMarkerStatus) {
            case Constants.ViewStatus.CHECKED:
                mMarker = mContext.getResources().getDrawable(R.drawable.ic_checked);
                break;
            case Constants.ViewStatus.HEADER:
                mMarker = mContext.getResources().getDrawable(R.drawable.ic_marker);
                break;
            case Constants.ViewStatus.UNCHECKED:
                mMarker = mContext.getResources().getDrawable(R.drawable.ic_uncheck);
                break;
        }

        if (mMarkerInCenter) { //Marker in center is true
            if (mMarker != null) {
                mMarker.setBounds((width / 2) - (markSize / 2), (height / 2) - (markSize / 2), (width / 2) + (markSize / 2), (height / 2) + (markSize / 2));
                mBounds = mMarker.getBounds();
            }
        } else { //Marker in center is false
            if (mMarker != null) {
                mMarker.setBounds(pLeft, pTop, pLeft + markSize, pTop + markSize);
                mBounds = mMarker.getBounds();
            }
        }



        int centerX = mBounds.centerX();
        int lineLeft = centerX - (mLineSize >> 1);

        if (mLineOrientation == 0) {

            //Horizontal Line
            if (mStartLine != null) {
                mStartLine.setBounds(0, pTop + (mBounds.height() / 2), mBounds.left - mLinePadding, (mBounds.height() / 2) + pTop + mLineSize);
            }

            if (mEndLine != null) {
                mEndLine.setBounds(mBounds.right + mLinePadding, pTop + (mBounds.height() / 2), width, (mBounds.height() / 2) + pTop + mLineSize);
            }
        } else {

            //Vertical Line
            if (mStartLine != null) {
                mStartLine.setBounds(lineLeft, 0, mLineSize + lineLeft, mBounds.top - mLinePadding);
            }

            if (mEndLine != null) {
                mEndLine.setBounds(lineLeft, mBounds.bottom + mLinePadding, mLineSize + lineLeft, height);
            }
        }
    }


    public int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    public int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public void setMarkerStatus(int markerStatus){
        this.mMarkerStatus = markerStatus ;
        initDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMarker != null) {
            mMarker.draw(canvas);
        }

        if (mStartLine != null) {
            mStartLine.draw(canvas);
        }

        if (mEndLine != null) {
            mEndLine.draw(canvas);
        }
    }

    /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     */
    public void setMarker(Drawable marker) {
        mMarker = marker;
        initDrawable();
    }

    /**
     * Sets marker.
     *
     * @param marker will set marker drawable to timeline
     * @param color  with a color
     */
    public void setMarker(Drawable marker, int color) {
        mMarker = marker;
        mMarker.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        initDrawable();
    }

    /**
     * Sets marker color.
     *
     * @param color the color
     */
    public void setMarkerColor(int color) {
        mMarker.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        initDrawable();
    }

    /**
     * Sets start line.
     *
     * @param color    the color
     * @param viewType the view type
     */
    public void setStartLine(int color, int viewType) {
        mStartLine = new ColorDrawable(color);
        initLine(viewType);
    }

    /**
     * Sets end line.
     *
     * @param color    the color
     * @param viewType the view type
     */
    public void setEndLine(int color, int viewType) {
        mEndLine = new ColorDrawable(color);
        initLine(viewType);
    }

    /**
     * Sets marker size.
     *
     * @param markerSize the marker size
     */
    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
        initDrawable();
    }

    /**
     * Sets line size.
     *
     * @param lineSize the line size
     */
    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
        initDrawable();
    }

    /**
     * Sets line padding
     *
     * @param padding the line padding
     */
    public void setLinePadding(int padding) {
        mLinePadding = padding;
        initDrawable();
    }

    private void setStartLine(Drawable startLine) {
        mStartLine = startLine;
        initDrawable();
    }

    private void setEndLine(Drawable endLine) {
        mEndLine = endLine;
        initDrawable();
    }

    /**
     * Init line.
     *
     * @param viewType the view type
     */
    public void initLine(int viewType) {
        if (viewType == Constants.LineType.BEGIN) {
            setStartLine(null);
        } else if (viewType == Constants.LineType.END) {
            setEndLine(null);
        } else if (viewType == Constants.LineType.ONLYONE) {
            setStartLine(null);
            setEndLine(null);
        }
        initDrawable();
    }

    /**
     * Gets timeline view type.
     *
     * @param position   the position
     * @param total_size the total size
     * @return the time line view type
     */
    public static int getTimeLineViewType(int position, int total_size) {
        if (total_size == 1) {
            return Constants.LineType.ONLYONE;
        } else if (position == 0) {
            return Constants.LineType.BEGIN;
        } else if (position == total_size - 1) {
            return Constants.LineType.END;
        } else {
            return Constants.LineType.NORMAL;
        }
    }
}
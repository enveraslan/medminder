package com.aae.medminder.components.calendar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aae.medminder.R;
import com.aae.medminder.components.calendar.model.DateModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Enver ASLAN on 16/12/2020.
 */
public class CustomHorizontalCalendar extends RelativeLayout {
    private RecyclerView mRecyclerView;
    private HorizontalDateAdapter mHorizontalDateAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mTodayTextView;
    private TextView mMonthAndDateTextView;
    private List<DateModel> mInputDates;
    private int mCenterChildPosition = -1;
    private LayoutClickListener mLayoutClickListener;
    private OnHorizontalDateSelectListener mOnHorizontalDateSelectListener;
    protected int mBgResourceId;
    protected int mSelectedBgResourceId;
    protected int mTextColorResourceId;
    protected int mSelectedTextColorResourceId;
    private int mScrollSpeed;


    private int mNumberOfDays = -1;
    private Locale mLocale = null;
    private DateFormatSymbols mDateFormatSymbols = null;
    private Date mStartDate = new Date();
    private Date mCurrentDate = new Date();

    public void setOnDateSelectListener(OnHorizontalDateSelectListener onHorizontalDateSelectListener) {
        mOnHorizontalDateSelectListener = onHorizontalDateSelectListener;
    }

    protected void setLayoutClickListener(LayoutClickListener layoutClickListener) {
        mLayoutClickListener = layoutClickListener;
        if (mHorizontalDateAdapter != null)
            mHorizontalDateAdapter.setOnHorizontalDateSelectListener(new OnHorizontalDateSelectListener() {
                @Override
                public void onDateClick(DateModel dateModel) {
                    setMonthAndYear(dateModel);
                    mCurrentDate = dateModel.date;
                    if (mOnHorizontalDateSelectListener != null){
                        mOnHorizontalDateSelectListener.onDateClick(dateModel);
                    }
                }
            });
    }

    public CustomHorizontalCalendar(Context context) {
        super(context);
        init(context, null);
    }

    public CustomHorizontalCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomHorizontalCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_horizontal_calendar, this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTodayTextView = findViewById(R.id.todayTextView);
        mMonthAndDateTextView = findViewById(R.id.monthAndDateTextView);
        setLocale(Locale.getDefault());
        if (attrs == null) return;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomHorizontalCalendar, 0, 0);
        mScrollSpeed = typedArray.getInteger(R.styleable.CustomHorizontalCalendar_setScrollSpeed, 30);
        mNumberOfDays = typedArray.getInteger(R.styleable.CustomHorizontalCalendar_numOfDays, 60);
        String label = "Today";
        mBgResourceId = typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setBgColor, R.drawable.rect_dark_gray);
        mTextColorResourceId = typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setTextColor, R.color.dark_gray);
        mSelectedBgResourceId = typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setSelectedBgColor, R.color.colorCinnebar);
        mSelectedTextColorResourceId = typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setSelectedTextColor, R.color.white);
        setCalender(mNumberOfDays);
        setLabel(label);
        if (typedArray.hasValue(R.styleable.CustomHorizontalCalendar_setLabelColor))
            mTodayTextView.setTextColor(ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setLabelColor, R.color.colorAlabaster)));
        mMonthAndDateTextView.setTextColor(ContextCompat.getColor(context, typedArray.getResourceId(R.styleable.CustomHorizontalCalendar_setMonthColor, R.color.colorAlabaster)));
        if (typedArray.hasValue(R.styleable.CustomHorizontalCalendar_setLabelTextSize))
            mTodayTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInteger(R.styleable.CustomHorizontalCalendar_setLabelTextSize, 13));
        if (typedArray.hasValue(R.styleable.CustomHorizontalCalendar_setMonthTextSize))
            mMonthAndDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInteger(R.styleable.CustomHorizontalCalendar_setMonthTextSize, 15));
        if (typedArray.hasValue(R.styleable.CustomHorizontalCalendar_setLabelFontStyle))
            mTodayTextView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), typedArray.getString(R.styleable.CustomHorizontalCalendar_setLabelFontStyle)));
        if (typedArray.hasValue(R.styleable.CustomHorizontalCalendar_setMonthFontStyle))
            mMonthAndDateTextView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), typedArray.getString(R.styleable.CustomHorizontalCalendar_setMonthFontStyle)));
    }

    private void setCalender(int noOfDays){
        setCalender(noOfDays, mStartDate);
    }

    private void setCalender(int noOfDays, Date date) {
        if (noOfDays < 1)
            return;

        Calendar calendar = Calendar.getInstance();
        //Date date = new Date();
        mInputDates = new ArrayList<>();
        mInputDates.clear();

        for (int index = 0; index < noOfDays; index++) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, index);
            DateModel dateModel = new DateModel();
            dateModel.day = calendar.get(Calendar.DAY_OF_MONTH) + "";
            dateModel.dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
            dateModel.month = getMonth(calendar.get(Calendar.MONTH));
            dateModel.year = calendar.get(Calendar.YEAR) + "";
            dateModel.date = calendar.getTime();
            mInputDates.add(dateModel);
        }
        setData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setData() {
        if (mHorizontalDateAdapter == null)
            mHorizontalDateAdapter = new HorizontalDateAdapter();
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mHorizontalDateAdapter.setData(mInputDates, this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mHorizontalDateAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        final SnapHelper snapHelper = new LinearSnapHelper();

        //Caused by: java.lang.IllegalStateException: An instance of OnFlingListener already set.
        mRecyclerView.setOnFlingListener(null);

        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setHorizontalScrollBarEnabled(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getLayoutManager() != null) {
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    if (centerView != null)
                        mCenterChildPosition = recyclerView.getLayoutManager().getPosition(centerView);
                }
                if (mLayoutClickListener != null)
                    mLayoutClickListener.onLayoutClick(mCenterChildPosition);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() != null) {
                    View centerView = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    if (centerView != null)
                        mCenterChildPosition = recyclerView.getLayoutManager().getPosition(centerView);
                }
                if (mLayoutClickListener != null)
                    mLayoutClickListener.onLayoutClick(mCenterChildPosition);

            }
        });

        final Handler handler = new Handler();
        final Runnable longPressedLeft = new Runnable() {
            public void run() {
                mRecyclerView.scrollBy(-mScrollSpeed, 0);
                handler.post(this);
            }
        };


        final Runnable longPressedRight = new Runnable() {
            public void run() {
                mRecyclerView.scrollBy(mScrollSpeed, 0);
                handler.post(this);
            }
        };


    }

    protected interface LayoutClickListener {
        void onLayoutClick(int position);
    }

    public void setLocale(Locale locale){
        this.mLocale = locale;
        mDateFormatSymbols = new DateFormatSymbols(this.mLocale);
        setCalender(mNumberOfDays);
    }

    public void setStartDate(Date date){
        this.mStartDate = date;
        setCalender(mNumberOfDays);
    }

    public void selectDate(Date date){
        //some bugs happens in the call of this method, for example, if it is an initial or final date, where it can not be centralized
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mStartDate);
        calendar.add(Calendar.DAY_OF_MONTH, mNumberOfDays);
        Date endDate = calendar.getTime();

        boolean dateExists = date.after(mStartDate) && date.before(endDate);

        if (dateExists == false)
            throw new ArrayIndexOutOfBoundsException("The date does not exist");

        long diff = date.getTime() - mStartDate.getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        diffDays = diffDays - 2;

        mCenterChildPosition = (int) diffDays;
        mRecyclerView.scrollToPosition((int) diffDays);
        if (mLayoutClickListener != null)
            mLayoutClickListener.onLayoutClick(mCenterChildPosition);

        mHorizontalDateAdapter.notifyItemChanged((int) diffDays + 1);
    }

    private String getDayOfWeek(int dayOfWeek) {
        String[] weekdays = mDateFormatSymbols.getShortWeekdays();
        return toTitleCase(weekdays[dayOfWeek]);
    }

    private String getMonth(int month) {
        String[] monthNames = mDateFormatSymbols.getMonths();
        return toTitleCase(monthNames[month]);
    }

    public Date getCurrentDate(){
        return mCurrentDate;
    }

    //format to title ex: (abril to Abril)
    private static String toTitleCase(String input) {
        input = input.toLowerCase();
        char c =  input.charAt(0);
        String s = new String("" + c);
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    private void setMonthAndYear(DateModel dateModel) {
        String monthAndYear = "";
        if (dateModel != null)
            monthAndYear = dateModel.month + " " + dateModel.year;
        mMonthAndDateTextView.setText(monthAndYear);
    }

    public void setLabel(String label) {
        mTodayTextView.setText(label);
    }



}



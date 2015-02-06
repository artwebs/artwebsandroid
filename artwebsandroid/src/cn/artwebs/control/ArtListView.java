package cn.artwebs.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import cn.artwebs.R;

import java.util.Date;

/**
 * Created by rsmac on 15/2/6.
 */

public class ArtListView extends ListView implements AbsListView.OnScrollListener,View.OnClickListener {
    // 释放状态
    public final static int RELEASE_To_REFRESH = 0;

    // 下拉到刷新状态
    public final static int PULL_To_REFRESH = 1;

    // 正在刷新
    public final static int REFRESHING = 2;

    // 刷新完成
    public final static int DONE = 3;

    //上拉加载更多
    //普通状态
    public final static int MORE_DONE = 4;

    //刷新状态
    public final static int MORE_REFRESHING = 5;



    private final static int RATIO = 3;

    // 刷新箭头的宽度
    private static final int ARROW_IMAGE_WIDTH = 70;

    // 刷新箭头的高度
    private static final int ARROW_IMAGE_HEIGHT = 50;

    private LayoutInflater inflater;

    // head xml
    private LinearLayout headView;

    private TextView tipsTextview;

    private TextView lastUpdatedTextView;

    private ImageView arrowImageView;

    private ProgressBar progressBar;

    // foot xml
    private RelativeLayout footView;

    private TextView mLoadMoreTextView;

    private RelativeLayout mLoadMoreView;

    private LinearLayout mLoadingView;

    private RotateAnimation animation;

    private RotateAnimation reverseAnimation;

    private boolean isRecored;

    // private int headContentWidth;

    private int headContentHeight;

    private int startY;

    private int firstItemIndex;

    private int state;

    private int loadingMoreState;

    private boolean isBack;

    private OnRefreshListener refreshListener;

    private boolean isRefreshable;

    public static volatile boolean isPullRefresh = true;

    public ArtListView(Context context)
    {
        super(context);
        init(context);
    }

    public ArtListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化ListView
     *
     * @param context
     */
    private void init(Context context)
    {
        // setCacheColorHint(context.getResources().getColor(R.color.transparent));
        inflater = LayoutInflater.from(context);
        headView = (LinearLayout) inflater
                .inflate(R.layout.artlistview_head, null);
        arrowImageView = (ImageView) headView
                .findViewById(R.id.head_arrowImageView);
        progressBar = (ProgressBar) headView
                .findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView
                .findViewById(R.id.head_lastUpdatedTextView);

        footView = (RelativeLayout) inflater.inflate(R.layout.footer, null);

        mLoadMoreTextView = (TextView) footView.findViewById(R.id.load_more_tv);
        mLoadMoreView = (RelativeLayout) footView
                .findViewById(R.id.load_more_view);

        mLoadingView = (LinearLayout) footView
                .findViewById(R.id.loading_layout);

        mLoadMoreView.setOnClickListener(this);

        addFooterView(footView);

        // 设定箭头大小
        arrowImageView.setMinimumWidth(ARROW_IMAGE_WIDTH);
        arrowImageView.setMinimumHeight(ARROW_IMAGE_HEIGHT);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        // headContentWidth = headView.getMeasuredWidth();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();
        addHeaderView(headView, null, false);
        setOnScrollListener(this);

        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;
        isRefreshable = false;

        loadingMoreState = MORE_DONE;

    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2,
                         int arg3)
    {
        firstItemIndex = firstVisiableItem;
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1)
    {
    }

    public boolean onTouchEvent(MotionEvent event)
    {

        if (!isRefreshable)
        {
            return super.onTouchEvent(event);
        }

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (firstItemIndex == 0 && !isRecored)
                {
                    isRecored = true;
                    startY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (state != REFRESHING)
                {
                    /*
                     * if (state == DONE) { }
                     */
                    if (state == PULL_To_REFRESH)
                    {
                        state = DONE;
                        changeHeaderViewByState();
                    }
                    if (state == RELEASE_To_REFRESH)
                    {
                        state = REFRESHING;
                        changeHeaderViewByState();
                        isPullRefresh = true;
                        onRefresh();
                    }
                }
                isRecored = false;
                isBack = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) event.getY();
                if (!isRecored && firstItemIndex == 0)
                {
                    isRecored = true;
                    startY = tempY;
                }
                if (state != REFRESHING && isRecored)
                {
                    if (state == RELEASE_To_REFRESH)
                    {
                        setSelection(0);
                        if (((tempY - startY) / RATIO < headContentHeight)
                                && (tempY - startY) > 0)
                        {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                        }
                        else if (tempY - startY <= 0)
                        {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == PULL_To_REFRESH)
                    {
                        setSelection(0);
                        if ((tempY - startY) / RATIO >= headContentHeight)
                        {
                            state = RELEASE_To_REFRESH;
                            isBack = true;
                            changeHeaderViewByState();
                        }
                        else if (tempY - startY <= 0)
                        {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == DONE)
                    {
                        if (tempY - startY > 0)
                        {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                        }
                    }
                    if (state == PULL_To_REFRESH)
                    {
                        headView.setPadding(0, -1 * headContentHeight
                                + (tempY - startY) / RATIO, 0, 0);
                    }
                    if (state == RELEASE_To_REFRESH)
                    {
                        headView.setPadding(0, (tempY - startY) / RATIO
                                - headContentHeight, 0, 0);
                    }
                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 根据状�?改变下拉刷新的显�?
     */
    private void changeHeaderViewByState()
    {
        switch (state)
        {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);
                tipsTextview.setText(R.string.release_refresh);
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                tipsTextview.setVisibility(View.VISIBLE);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                if (isBack)
                {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);
                    tipsTextview.setText(R.string.dropdown_refresh);
                }
                else
                {
                    tipsTextview.setText(R.string.dropdown_refresh);
                }
                break;
            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                tipsTextview.setText(R.string.loading);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.drawable.arrow);
                tipsTextview.setText(R.string.load_complete);
                lastUpdatedTextView.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void setStateWithShow(int state)
    {
        this.state = state;
        changeHeaderViewByState();
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener)
    {
        this.refreshListener = refreshListener;

        isRefreshable = true;
    }

    public interface OnRefreshListener
    {
        void onRefresh();
        void onLoadMore();
    }

    public void onRefreshComplete()
    {
        state = DONE;
        lastUpdatedTextView.setText(R.string.last_update);
        String temp = lastUpdatedTextView.getText().toString();
        lastUpdatedTextView.setText(temp + new Date().toLocaleString());
        changeHeaderViewByState();
    }

    private void onRefresh()
    {
        if (refreshListener != null)
        {
            refreshListener.onRefresh();
        }
    }

    private void measureView(View child)
    {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null)
        {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec = 0;
        if (lpHeight > 0)
        {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        }
        else
        {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter)
    {
        lastUpdatedTextView.setText("" + new Date().toLocaleString());
        super.setAdapter(adapter);
    }


    private void updateLoadMoreViewState(int state) {
        switch (state) {
            // 普通状态
            case MORE_DONE:
                mLoadingView.setVisibility(View.GONE);
                mLoadMoreTextView.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setText("查看更多");
                break;
            // 加载中状态
            case MORE_REFRESHING:
                mLoadingView.setVisibility(View.VISIBLE);
                mLoadMoreTextView.setVisibility(View.GONE);
                break;


            default:
                break;
        }
        loadingMoreState = state;
    }

    public void onLoadMoreComplete() {
        updateLoadMoreViewState(ArtListView.MORE_DONE);

    }

    @Override
    public void onClick(View v)
    {

        if (refreshListener != null
                && loadingMoreState == ArtListView.MORE_DONE) {
            updateLoadMoreViewState(ArtListView.MORE_REFRESHING);
            isPullRefresh = false;
            refreshListener.onLoadMore();// 对外提供方法加载更多.
        }

    }
}

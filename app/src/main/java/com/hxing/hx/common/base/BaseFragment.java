package com.hxing.hx.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public String TAG = getClass().getSimpleName();
    protected boolean isVisible;//当前Fragment是否可见
    public BaseActivity mActivity;
    private View rootView;
    private Context mContext;

    /* 指定layout */
    public abstract int getLayoutId();

    /* 初始化界面 */
    public abstract void initView(View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        mContext = container.getContext();
        initView(rootView);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment可见
     */
    protected void onVisible() {
        lazyLoad();//
    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoad();

    /**
     * fragment不可见
     */
    protected void onInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }
}


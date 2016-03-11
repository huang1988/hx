package com.hxing.hx.common.utils.viewHolder;

import android.util.SparseArray;
import android.view.View;

/**
 * @Description描述:通用ViewHolder
 * @Author作者: hx
 */
public class ViewHolder {
    /**
     * 获取item的childView
     * @param id childView的id
     * @return 返回childView对象
     */
    public static <T extends View> T get(View view, int id) {
        SparseArray viewHolder = (SparseArray) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray();
            view.setTag(viewHolder);
        }
        View childView = (View) viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T)childView;
    }
}

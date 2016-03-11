package com.hxing.hx.common.utils.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @Description描述:通用的RecyclerViewHolder
 * @Author作者: hx
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> array;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        array = new SparseArray<>();
    }

    /**
     * 获取item的childView
     * @param id childView的id
     * @return 返回childView对象
     */
    public <T extends View> T getView(int id){
        if (array.get(id)==null){
            array.put(id,itemView.findViewById(id));
        }
        return (T) array.get(id);
    }
}

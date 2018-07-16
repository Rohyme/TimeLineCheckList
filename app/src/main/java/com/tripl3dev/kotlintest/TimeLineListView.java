package com.tripl3dev.kotlintest;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.tripl3dev.luffyyview.baseAdapter.BaseListAdapter;
import com.tripl3dev.luffyyview.baseAdapter.MainHolderInterface;

import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class TimeLineListView extends RecyclerView {
    Context mContext;
    private BaseListAdapter<TimeLineModelI> mAdapter;
    private ArrayList<TimeLineModelI> list = new ArrayList<>();
    public final int HEADER_TYPE = -13;
    public final int CHECKED_TYPE = -12;
    public final int UNCHECKED_TYPE = -11;
    int latestItemCheckedIndex = -10;
    OnStatusChangedCB onStatusListener;


    SparseArray<TimeLineModelI> latestCheckMap = new SparseArray<>();

    public TimeLineListView(Context context) {
        super(context);
        this.mContext = context;
        init();

    }


    public TimeLineListView(Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public TimeLineListView(Context context, @android.support.annotation.Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }


    public void setOnStatusListener(OnStatusChangedCB onStatusListener) {
        this.onStatusListener = onStatusListener;
    }

    public void setLists(LinkedHashMap<TimeLineModelI, ArrayList<TimeLineModelI>> map) {
        this.list.clear();
        Iterator<Map.Entry<TimeLineModelI, ArrayList<TimeLineModelI>>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<TimeLineModelI, ArrayList<TimeLineModelI>> item = it.next();
            list.add(item.getKey());
            list.addAll(item.getValue());
        }
        mAdapter.updateList();
    }

    void init() {

        mAdapter = new BaseListAdapter(new MainHolderInterface<TimeLineModelI>() {
            @Override
            public void getViewData(final ViewHolder viewHolder, final TimeLineModelI t, final int i) {
                View v = viewHolder.itemView;
                TextView title = v.findViewById(R.id.title);
                TimelineView timelineView = v.findViewById(R.id.timeLineItem);
                title.setText(t.getTitle());
                if (viewHolder.getItemViewType() == HEADER_TYPE) {
                    timelineView.setMarkerStatus(Constants.ViewStatus.HEADER);
                    viewHolder.itemView.setClickable(false);
                    if (onStatusListener != null)
                        onStatusListener.onStatusChanged(v, t, Constants.ViewStatus.HEADER);
                } else {
                    if (viewHolder.getItemViewType() == CHECKED_TYPE) {
                    viewHolder.itemView.setClickable(true);
                    timelineView.setMarkerStatus(Constants.ViewStatus.CHECKED);
                    if (onStatusListener != null)
                        onStatusListener.onStatusChanged(v, list.get(i), Constants.ViewStatus.CHECKED);
                } else {
                    viewHolder.itemView.setClickable(true);
                    if (onStatusListener != null)
                        onStatusListener.onStatusChanged(v, latestCheckMap.get(list.get(i).type()), Constants.ViewStatus.UNCHECKED);
                    timelineView.setMarkerStatus(Constants.ViewStatus.UNCHECKED);
                    viewHolder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list.get(i).setChecked(true);
                            if (latestCheckMap.get(list.get(i).type()) != null) {
                                list.get(list.indexOf(latestCheckMap.get(list.get(i).type()))).setChecked(false);
                                latestCheckMap.put(list.get(i).type(), list.get(i));
                                mAdapter.notifyItemChanged(latestItemCheckedIndex);
                            } else {
                                latestCheckMap.put(list.get(i).type(), list.get(i));
                            }
                            mAdapter.notifyItemChanged(i);
                        }
                    });
                }
            }
            }

            @Nullable
            @Override
            public ArrayList getListCopy() {
                return list;
            }

            @Override
            public int getView(int i) {

                return R.layout.item_layout;
            }


            @Override
            public int getItemViewType(int i) {
                if (list.get(i).isHeader()) {
                    return HEADER_TYPE;
                } else {
                    if (list.get(i).isChecked()) {
                        return CHECKED_TYPE;
                    } else {
                        return UNCHECKED_TYPE;
                    }
                }
            }
        }, mContext);
        this.setLayoutManager(new LinearLayoutManager(mContext));
        this.setHasFixedSize(true);
        this.setAdapter(mAdapter);
    }

    public SparseArray<TimeLineModelI> getCheckedList() {
        return latestCheckMap;
    }

    public static <C> List<C> getArrayList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        List<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }


    interface OnStatusChangedCB {
        void onStatusChanged(View v , TimeLineModelI item , @Constants.ViewStatus int status);
    }

}

package com.app.customerapp.listner;

import android.widget.AbsListView;

/**
 * Created by umesh on 19/1/16.
 */
public interface IOrderDetailHolder {
    void adjustScroll(int scrollHeight);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}

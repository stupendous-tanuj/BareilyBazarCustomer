package com.app.bareillybazarcustomer.utils.parallaxutils;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StickyUtils {

    private static final int COUNT_ITEMS = 500;

    public static void populateListView(ListView listView) {
        String[] elements = new String[COUNT_ITEMS];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = "row " + i;
        }

        listView.setAdapter(new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, elements));
    }
}

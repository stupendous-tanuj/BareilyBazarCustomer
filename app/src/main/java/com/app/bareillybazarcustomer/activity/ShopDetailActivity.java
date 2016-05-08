package com.app.bareillybazarcustomer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bareillybazarcustomer.R;
import com.app.bareillybazarcustomer.constant.AppConstant;
import com.app.bareillybazarcustomer.fragment.ShopDetailFragment;
import com.app.bareillybazarcustomer.fragment.SubHolderFragment;
import com.app.bareillybazarcustomer.listner.ScrollTabHolder;
import com.app.bareillybazarcustomer.utils.PagerSlidingTabStrip;

public class ShopDetailActivity extends BaseActivity implements ScrollTabHolder {

    private View mHeader;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private Toolbar toolbar;
    private String shopCategoryName;
    private ImageView ivshopDetil;
    private String shopCategoryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        initUI();
        setToolBar();
        getIntentData();
        setDimensScrolling();
        setViewPager();

    }

    private void initUI() {
        mHeader = findViewById(R.id.header);
        ivshopDetil = (ImageView) findViewById(R.id.iv_shop_detil);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mPagerSlidingTabStrip.setIndicatorColor(Color.TRANSPARENT);
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    // fetch from home screen

    private void getIntentData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            shopCategoryName = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_NAME);
            shopCategoryImage = getIntent().getExtras().getString(AppConstant.BUNDLE_KEY.SHOP_CATEGORY_IMGE);
            imageLoader.displayImage(shopCategoryImage, ivshopDetil, imageOptions);

        }
    }

    private void setDimensScrolling() {
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp_250);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp_300);
        mMinHeaderTranslation = -mMinHeaderHeight;
    }

    private void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView shopDTitle = (TextView) toolbar.findViewById(R.id.tv_main_title);
        shopDTitle.setText("Shop Detail");

        //   toolbar.inflateMenu(R.menu.menu_sub_activity);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
                ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
                currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);

            int i = Math.max(-scrollY, mMinHeaderTranslation + (int) getResources().getDimension(R.dimen.dp_50));
            mHeader.setTranslationY(i);
            if (i >= -((int) getResources().getDimension(R.dimen.dp_30))) {
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            } else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.color_base));
            }
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    private int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();
        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }
        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }


    private class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private final String[] TITLES = {shopCategoryName};
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            SubHolderFragment fragment = (SubHolderFragment) ShopDetailFragment.newInstance(position, shopCategoryName);

            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }

            return fragment;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }
}

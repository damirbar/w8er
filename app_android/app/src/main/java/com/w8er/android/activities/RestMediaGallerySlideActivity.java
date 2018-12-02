package com.w8er.android.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.w8er.android.R;
import com.w8er.android.model.Pictures;
import com.w8er.android.model.Response;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import net.alhazmy13.mediagallery.library.activity.adapter.CustomViewPager;
import net.alhazmy13.mediagallery.library.activity.adapter.HorizontalListAdapters;
import net.alhazmy13.mediagallery.library.activity.adapter.ViewPagerAdapter;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * The type Media gallery activity.
 */
public class RestMediaGallerySlideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, HorizontalListAdapters.OnImgClick {
    private CustomViewPager mViewPager;
    private RecyclerView imagesHorizontalList;
    private HorizontalListAdapters hAdapter;
    private ServerResponse mServerResponse;
    private CompositeSubscription mSubscriptions;
    private RetrofitRequests mRetrofitRequests;
    private ArrayList<String> images;
    private int pos;
    private Toolbar mToolbar;
    private ArrayList<Pictures> picsId;
    private boolean admin;
    private String restId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.w8er.android.R.layout.activity_gallery_slide);
        mSubscriptions = new CompositeSubscription();
        mServerResponse = new ServerResponse(findViewById(R.id.mainLayout));
        mRetrofitRequests = new RetrofitRequests(this);

        initViews();
        if (!getData()) {
            finish();
        }
        initImages();
    }

    private void initImages() {
        mViewPager.setAdapter(new ViewPagerAdapter(this, images, mToolbar, imagesHorizontalList));
        hAdapter = new HorizontalListAdapters(this, images, this,0);
        imagesHorizontalList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imagesHorizontalList.setAdapter(hAdapter);
        hAdapter.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(this);
        hAdapter.setSelectedItem(pos);
        mViewPager.setCurrentItem(pos);
        }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar_media_gallery);
        setSupportActionBar(mToolbar);
        ImageButton buttonBack = findViewById(com.w8er.android.R.id.image_Button_back);
        buttonBack.setOnClickListener(view -> onBackPressed());
        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        imagesHorizontalList = (RecyclerView) findViewById(R.id.imagesHorizontalList);
    }

    private boolean getData() {
        if (getIntent().getExtras() != null) {
            images = getIntent().getExtras().getStringArrayList("images");
            pos = getIntent().getExtras().getInt("pos");
            picsId = getIntent().getExtras().getParcelableArrayList("picsId");
            admin = getIntent().getExtras().getBoolean("admin");
            restId = getIntent().getExtras().getString("restId");

            return true;
        } else
            return false;
    }

    private void removePic() {

        int index = mViewPager.getCurrentItem();

        Pictures picture = new Pictures();
        picture.setId(picsId.get(index).getId());
        removePicProcess(restId, picture);

    }

    private void removePicProcess(String restId, Pictures picture) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().removePic(restId, picture)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, i -> mServerResponse.handleError(i)));
    }

    private void handleResponse(Response response) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.w8er.android.R.menu.menu_rest_image, menu);
//        if (admin)
//            menu.findItem(R.id.action_add_image).setVisible(true);
//        else
//            menu.findItem(R.id.action_add_image).setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_remove) {
            removePic();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        imagesHorizontalList.smoothScrollToPosition(position);
        hAdapter.setSelectedItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(int pos) {
        mViewPager.setCurrentItem(pos, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}

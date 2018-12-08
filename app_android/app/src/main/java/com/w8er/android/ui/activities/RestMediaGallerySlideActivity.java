package com.w8er.android.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.w8er.android.R;
import com.w8er.android.adapters.HorizontalListAdapters;
import com.w8er.android.adapters.ViewPagerAdapter;
import com.w8er.android.model.Pictures;
import com.w8er.android.model.Response;
import com.w8er.android.network.RetrofitRequests;
import com.w8er.android.network.ServerResponse;

import net.alhazmy13.mediagallery.library.activity.adapter.CustomViewPager;

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
    private int pos;
    private Toolbar mToolbar;
    private ArrayList<Pictures> picsId;
    private boolean admin;
    private String restId;
    private KProgressHUD hud;

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
        mViewPager.setAdapter( new ViewPagerAdapter(this, picsId, mToolbar, imagesHorizontalList));
        hAdapter = new HorizontalListAdapters(this, picsId, this,0);
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
            pos = getIntent().getExtras().getInt("pos");
            picsId = getIntent().getExtras().getParcelableArrayList("picsId");
            admin = getIntent().getExtras().getBoolean("admin");
            restId = getIntent().getExtras().getString("restId");

            return true;
        } else
            return false;
    }

    private void removePic() {

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();

        pos = mViewPager.getCurrentItem();

        Pictures picture = new Pictures();
        picture.setId(picsId.get(pos).getId());
        removePicProcess(restId, picture);

    }

    private void removePicProcess(String restId, Pictures picture) {
        mSubscriptions.add(mRetrofitRequests.getTokenRetrofit().removePic(restId, picture)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseRemovePic, this::handleErrorRemovePic));
    }

    private void handleResponseRemovePic(Response response) {
        finish();
        hud.dismiss();
    }

    private void handleErrorRemovePic(Throwable error) {
        hud.dismiss();
        mServerResponse.handleError(error);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (admin)
            getMenuInflater().inflate(com.w8er.android.R.menu.menu_rest_image, menu);
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

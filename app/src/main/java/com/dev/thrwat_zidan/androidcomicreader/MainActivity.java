package com.dev.thrwat_zidan.androidcomicreader;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.thrwat_zidan.androidcomicreader.Adapter.MyComicAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Adapter.MySliderAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Model.Banner;
import com.dev.thrwat_zidan.androidcomicreader.Model.Comic;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.IComicAPI;
import com.dev.thrwat_zidan.androidcomicreader.Service.PicassoImageLoadingService;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class MainActivity extends AppCompatActivity {
    private Slider slider;
    private IComicAPI myAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recycler_comin;
    private TextView txt_comic;
    private SwipeRefreshLayout sweper;
    private ImageView btn_filter;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAPI = Common.getAPI();
        initViews();

        sweper.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark);

        sweper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    fechBanner();
                    fetchComic();
                } else {
                    Toast.makeText(MainActivity.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sweper.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    fechBanner();
                    fetchComic();
                } else {
                    Toast.makeText(MainActivity.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initViews() {

        slider = findViewById(R.id.baner_slider);
        recycler_comin = findViewById(R.id.recycler_comic);
        txt_comic = findViewById(R.id.txt_comic);
        sweper = findViewById(R.id.sweper);
        btn_filter = findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CategoryFilterActivity.class));
            }
        });


        recycler_comin.setHasFixedSize(true);
        recycler_comin.setLayoutManager(new GridLayoutManager(this, 2));
        Slider.init(new PicassoImageLoadingService());
    }

    private void fetchComic() {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait.....")
                .setCancelable(false)
                .build();
        if (!sweper.isRefreshing())
            dialog.show();

        compositeDisposable.add(myAPI.getComicList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        recycler_comin.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                        txt_comic.setText(new StringBuilder("NEW COMIC(").append(comics.size()).append(")"));

                        if (!sweper.isRefreshing())
                        dialog.dismiss();
                        sweper.setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!sweper.isRefreshing())
                            dialog.dismiss();
                        sweper.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "Error while loading comices", Toast.LENGTH_SHORT).show();

                    }
                }));

    }

    private void fechBanner() {
        compositeDisposable.add(myAPI.getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Banner>>() {
                    @Override
                    public void accept(List<Banner> banners) throws Exception {
                        slider.setAdapter(new MySliderAdapter(banners));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Error while loading", Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}

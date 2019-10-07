package com.dev.thrwat_zidan.androidcomicreader;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.thrwat_zidan.androidcomicreader.Adapter.MyViewPagerAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Model.Link;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.IComicAPI;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewDetail extends AppCompatActivity implements View.OnClickListener {
    private IComicAPI myApi;
    private ViewPager pager;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView txt_chapter_name;
    private View back, next;
    private SwipeRefreshLayout sweper;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        myApi = Common.getAPI();
        initViews();

        sweper.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark);

        sweper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    feachLinks(Common.selected_chapter.getID());
                } else {
                    Toast.makeText(ViewDetail.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sweper.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    feachLinks(Common.selected_chapter.getID());
                } else {
                    Toast.makeText(ViewDetail.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initViews() {
        pager = findViewById(R.id.view_pager);
        txt_chapter_name = findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);
        sweper = findViewById(R.id.sweper);


        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.chapter_next:
                fetchLinksWithbNext();
                break;
            case R.id.chapter_back:

                fetchLinksWithbBack();

                break;
        }
    }

    private void fetchLinksWithbNext() {
        if (Common.chapter_index == Common.chapterList.size() - 1) {

            Toast.makeText(this, "you are reading laast chapter", Toast.LENGTH_SHORT).show();
        } else {
            Common.chapter_index++;
            feachLinks(Common.chapterList.get(Common.chapter_index).getID());
        }
    }

    private void fetchLinksWithbBack() {
        if (Common.chapter_index == 0) {

            Toast.makeText(this, "you are reading first chapter", Toast.LENGTH_SHORT).show();
        } else {
            Common.chapter_index--;
            feachLinks(Common.chapterList.get(Common.chapter_index).getID());
        }
    }

    private void feachLinks(int id) {

        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait.....")
                .setCancelable(false)
                .build();
        if (!sweper.isRefreshing())
            dialog.show();

        compositeDisposable.add(myApi.getImageList(String.valueOf(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Link>>() {
                    @Override
                    public void accept(List<Link> links) throws Exception {
                        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), links);
                        pager.setAdapter(adapter);
                        txt_chapter_name.setText(Common.formatString(Common.selected_chapter.getName()));

                        //Create Book FlipPage

                        BookFlipPageTransformer flipPageTransformer = new BookFlipPageTransformer();
                        flipPageTransformer.setScaleAmountPercent(10f);

                        pager.setPageTransformer(true, flipPageTransformer);
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
                        Toast.makeText(ViewDetail.this, "this chapter is being translating", Toast.LENGTH_SHORT).show();

                    }
                }));

    }
}

package com.dev.thrwat_zidan.androidcomicreader;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.thrwat_zidan.androidcomicreader.Adapter.MyChapterAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Adapter.MyNewChapterAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Model.Chapter;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.IComicAPI;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapterActivity extends AppCompatActivity {

    private IComicAPI myAPI;
    private RecyclerView recycler_chapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView txt_chapter;
    private SwipeRefreshLayout sweper;


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        //initAPI
        myAPI = Common.getAPI();
        initViews();

        sweper.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark);

        sweper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    feachChapter(Common.selected_comic.getID());
                } else {
                    Toast.makeText(ChapterActivity.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sweper.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    feachChapter(Common.selected_comic.getID());
                } else {
                    Toast.makeText(ChapterActivity.this, "Can not connect to INTERNET", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selected_comic.getName());
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recycler_chapter = findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(manager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this, manager.getOrientation()));


        txt_chapter = findViewById(R.id.txt_chapter);
        sweper = findViewById(R.id.sweper);


    }

    private void feachChapter(int id) {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait.....")
                .setCancelable(false)
                .build();
        if (!sweper.isRefreshing())
            dialog.show();

        compositeDisposable.add(myAPI.getChapterList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Chapter>>() {
                    @Override
                    public void accept(List<Chapter> chapters) throws Exception {
                        Common.chapterList = chapters; // save chapter to back,next

                        recycler_chapter.setAdapter(new MyChapterAdapter(ChapterActivity.this, chapters));
                        for (Chapter chap:chapters){
                        Log.i("CHAPTER_NAMES", chap.getName());

                        }
                        txt_chapter.setText(new StringBuilder("CHAPTER (").append(chapters.size()).append(")"));
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
                        Toast.makeText(ChapterActivity.this, "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                }));
    }
}

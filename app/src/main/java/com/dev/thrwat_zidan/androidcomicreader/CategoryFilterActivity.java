package com.dev.thrwat_zidan.androidcomicreader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.thrwat_zidan.androidcomicreader.Adapter.MultiableChooseAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Adapter.MyComicAdapter;
import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Model.Category;
import com.dev.thrwat_zidan.androidcomicreader.Model.Comic;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.IComicAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryFilterActivity extends AppCompatActivity {
    private Button btn_filter,btn_search;
    private IComicAPI myAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recycler_filter;
    private Dialog dialog;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_filter);
        myAPI = Common.getAPI();
        initViews();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                featchCategory(); // Load all category from server
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });
    }



    private void initViews() {
        dialog = new Dialog(CategoryFilterActivity.this);
        btn_filter = findViewById(R.id.btn_filter);
        btn_search = findViewById(R.id.btn_search);
        recycler_filter = findViewById(R.id.recycler_filter);
        recycler_filter.setHasFixedSize(true);
        recycler_filter.setLayoutManager(new GridLayoutManager(this, 2));

    }


    private void featchCategory() {
        compositeDisposable.add(myAPI.getCategoryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Category>>() {
                    @Override
                    public void accept(List<Category> categories) throws Exception {
                        Common.categories = categories;
                        for (Category cat: categories){
                            Log.i("FILTER_AC", cat.getName());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(CategoryFilterActivity.this, "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));


    }


    private void showOptionDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryFilterActivity.this);
        builder.setTitle("Select Category");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_options, null);

        RecyclerView recycler_options = view.findViewById(R.id.recycler_options);

        recycler_options.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_options.setLayoutManager(manager);

        final MultiableChooseAdapter adapter = new MultiableChooseAdapter(CategoryFilterActivity.this, Common.categories);
        recycler_options.setAdapter(adapter);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Filter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                fetchFilterCategory(adapter.getFilterArray());

            }
        });
        builder.show();
    }

    private void fetchFilterCategory(String data) {

        compositeDisposable.add(myAPI.getFilteredComic(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {
                        recycler_filter.setVisibility(View.VISIBLE);
                        recycler_filter.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        recycler_filter.setVisibility(View.INVISIBLE);

                        Toast.makeText(CategoryFilterActivity.this, "No Comic found", Toast.LENGTH_SHORT).show();
                    }
                }));

    }


    private void showSearchDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CategoryFilterActivity.this);
        builder.setTitle("Search Comic ");

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_search, null);

        final EditText edt_search = view.findViewById(R.id.edt_search);
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                featchSearchComic(edt_search.getText().toString());
            }
        });
        builder.show();
    }

    private void featchSearchComic(String search) {


        compositeDisposable.add(myAPI.getsearchedComic(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(List<Comic> comics) throws Exception {

                        recycler_filter.setVisibility(View.VISIBLE);
                        recycler_filter.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        recycler_filter.setVisibility(View.INVISIBLE);

                        Toast.makeText(CategoryFilterActivity.this, "No Comic found", Toast.LENGTH_SHORT).show();
                    }
                }));

    }

}

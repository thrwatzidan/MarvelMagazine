package com.dev.thrwat_zidan.androidcomicreader.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Interface.IRecyclerOnClick;
import com.dev.thrwat_zidan.androidcomicreader.Model.Chapter;
import com.dev.thrwat_zidan.androidcomicreader.R;
import com.dev.thrwat_zidan.androidcomicreader.ViewDetail;
import com.dev.thrwat_zidan.androidcomicreader.ViewHolder.MyChapterViewHolder;

import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterViewHolder> {
    private Context context;
    private List<Chapter> chapterList;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyChapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item, viewGroup, false);
        return new MyChapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyChapterViewHolder holder, int i) {

   holder.txt_chapter_number.setText(new StringBuilder(chapterList.get(i).getName()));

//        Log.i("CHAPTER_AD", chapterList.get(i).getName());

        Common.selected_chapter = chapterList.get(i);
        Common.chapter_index = i;
        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_chapter = chapterList.get(position);
                Common.chapter_index = position;
                context.startActivity(new Intent(context, ViewDetail.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
}

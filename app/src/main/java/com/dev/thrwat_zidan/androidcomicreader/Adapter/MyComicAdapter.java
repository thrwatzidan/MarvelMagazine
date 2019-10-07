package com.dev.thrwat_zidan.androidcomicreader.Adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.thrwat_zidan.androidcomicreader.ChapterActivity;
import com.dev.thrwat_zidan.androidcomicreader.Common.Common;
import com.dev.thrwat_zidan.androidcomicreader.Interface.IRecyclerOnClick;
import com.dev.thrwat_zidan.androidcomicreader.Model.Comic;
import com.dev.thrwat_zidan.androidcomicreader.R;
import com.dev.thrwat_zidan.androidcomicreader.ViewHolder.ComicViewHOlder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicAdapter extends RecyclerView.Adapter<ComicViewHOlder> {

    private Context context;
    private List<Comic> comicList;


    public MyComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
    }

    @NonNull
    @Override
    public ComicViewHOlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.comic_layout, viewGroup, false);
        return new ComicViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComicViewHOlder holder, final int i) {

        Comic comic = comicList.get(i);

        Picasso.get().load(comic.getImage()).into(holder.image_view);
        holder.txt_manga_name.setText(comic.getName());

        holder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
               // context.startActivity(new Intent(context, ChapterActivity.class));
                Intent intent = new Intent(context, ChapterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Common.selected_comic = comicList.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }
}

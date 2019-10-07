package com.dev.thrwat_zidan.androidcomicreader.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.thrwat_zidan.androidcomicreader.Interface.IRecyclerOnClick;
import com.dev.thrwat_zidan.androidcomicreader.R;

public class ComicViewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView image_view;
    public TextView txt_manga_name;
    IRecyclerOnClick iRecyclerOnClick;

    public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
        this.iRecyclerOnClick = iRecyclerOnClick;
    }
    public ComicViewHOlder(@NonNull View itemView) {
        super(itemView);
        txt_manga_name = itemView.findViewById(R.id.txt_manga_name);
        image_view = itemView.findViewById(R.id.image_view);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iRecyclerOnClick.onClick(v,getAdapterPosition());
    }
}

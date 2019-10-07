package com.dev.thrwat_zidan.androidcomicreader.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dev.thrwat_zidan.androidcomicreader.Interface.IRecyclerOnClick;
import com.dev.thrwat_zidan.androidcomicreader.R;

public class MyChapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_chapter_number;
    IRecyclerOnClick iRecyclerOnClick;

    public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
        this.iRecyclerOnClick = iRecyclerOnClick;
    }

    public MyChapterViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_chapter_number = itemView.findViewById(R.id.txt_chap_number);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        iRecyclerOnClick.onClick(v, getAdapterPosition());
    }
}

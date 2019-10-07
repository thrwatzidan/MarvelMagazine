package com.dev.thrwat_zidan.androidcomicreader.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.thrwat_zidan.androidcomicreader.Interface.IRecyclerOnClick;
import com.dev.thrwat_zidan.androidcomicreader.Model.Chapter;
import com.dev.thrwat_zidan.androidcomicreader.R;

import java.util.List;

public class MyNewChapterAdapter extends RecyclerView.Adapter<MyNewChapterAdapter.MyViewHolder> {
    private Context context;
    private List<Chapter> chapterList;

    public MyNewChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        holder.txt_chapter_number.setText(chapterList.get(i).getName());
        Log.i("CHAP_SIZE", String.valueOf(chapterList.size()));
        Log.i("CHAP_AD", String.valueOf(chapterList.get(i).getName()));
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView txt_chapter_number;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chapter_number = itemView.findViewById(R.id.txt_chap_number);
        }

        @Override
        public void onClick(View v) {
         iRecyclerOnClick.onClick(v,getAdapterPosition());
        }
    }
}

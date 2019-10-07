package com.dev.thrwat_zidan.androidcomicreader.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.dev.thrwat_zidan.androidcomicreader.Model.Category;
import com.dev.thrwat_zidan.androidcomicreader.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MultiableChooseAdapter extends RecyclerView.Adapter<MultiableChooseAdapter.MultiableChoosViewHolder> {

    private Context context;
    private List<Category> categoryList;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();
    private List<Category> selected_Category = new ArrayList<>();

    public MultiableChooseAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MultiableChoosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.check_item_layout, viewGroup, false);
        return new MultiableChoosViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull MultiableChoosViewHolder holder, int i) {

        holder.check_option.setText(categoryList.get(i).getName());
        holder.check_option.setChecked(itemStateArray.get(i));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public String getFilterArray() {

        List<Integer> id_selected = new ArrayList<>();
        for (Category category:selected_Category)
            id_selected.add(category.getID());
        return new Gson().toJson(id_selected);

    }


    public class MultiableChoosViewHolder extends RecyclerView.ViewHolder {

        CheckBox check_option;

        public MultiableChoosViewHolder(@NonNull View itemView) {
            super(itemView);

            check_option = itemView.findViewById(R.id.check_option);
            check_option.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int adapterPosition = getAdapterPosition();
                    itemStateArray.put(adapterPosition, isChecked);

                    if (isChecked) {
                        selected_Category.add(categoryList.get(adapterPosition));
                    }else{
                        selected_Category.remove(categoryList.get(adapterPosition));
                    }
                }
            });
        }
    }

}

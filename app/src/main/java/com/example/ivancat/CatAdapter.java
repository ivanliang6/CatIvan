package com.example.ivancat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder>{
    List<Cat> catList ;
    private Context context;
    public CatAdapter(Context context,List<Cat> catList) {
        this.context = context;
        this.catList = catList;
    }



    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new CatHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
        final Cat item = catList.get(position);
        holder.textView.setText(item.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CatInfoActivity.class);
                intent.putExtra("cat",item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }
    class CatHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CatHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);

        }
    }
}

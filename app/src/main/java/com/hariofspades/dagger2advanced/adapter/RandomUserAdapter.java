package com.hariofspades.dagger2advanced.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hariofspades.dagger2advanced.R;
import com.hariofspades.dagger2advanced.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.hariofspades.dagger2advanced.presenter.MainPresenter;

/**
 * Created by Hari on 20/11/17.
 */

public class RandomUserAdapter extends RecyclerView.Adapter<RandomUserAdapter.RandomUserViewHolder> {

    private List<Result> resultList = new ArrayList<>();
    @Inject
    MainPresenter mMainPresenter;

    @Override
    public RandomUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_random_user,
                parent, false);
        return new RandomUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RandomUserViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.textView.setText(String.format("%s %s", result.getName().getFirst(),
                result.getName().getLast()));
        Picasso.with(holder.imageView.getContext())
                .load(result.getPicture().getLarge())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* holder.imageView.getContext()
                        .startActivity(new Intent(holder.imageView.getContext(), SecondActivity.class));*/
                mMainPresenter.callJoinApi("abc@gmail.com","Abc@123");
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void setItems(List<Result> results) {
        resultList = results;
        notifyDataSetChanged();
    }

    public class RandomUserViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public RandomUserViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
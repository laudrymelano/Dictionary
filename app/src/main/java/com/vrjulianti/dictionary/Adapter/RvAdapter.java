package com.vrjulianti.dictionary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.vrjulianti.dictionary.Class.Dictionary;
import com.vrjulianti.dictionary.R;
import com.vrjulianti.dictionary.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.SearchViewHolder>{

    private ArrayList<Dictionary> dict_list = new ArrayList<>();
    private static Dictionary dictionary;

    public static Dictionary getDictionary()
    {
        return dictionary;
    }

    public static void setDictionary(Dictionary dictionary)
    {
        RvAdapter.dictionary = dictionary;
    }

    public ArrayList<Dictionary> getKamus_list()
    {
        return dict_list;
    }

    public void setKamus_list(ArrayList<Dictionary> kamus_list)
    {
        this.dict_list = kamus_list;
    }

    public void replaceAll(ArrayList<Dictionary> dictionaries)
    {
        dict_list = dictionaries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kata, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position)
    {
        holder.bind_kamus(dict_list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return dict_list.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.dict_word)
        TextView detail_word;

        @BindView(R.id.dict_translate)
        TextView detail_translate;

        SearchViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind_kamus(final Dictionary dictionary)
        {
            detail_word.setText(dictionary.getWord());
            detail_translate.setText(dictionary.getTranslate());

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    setDictionary(dictionary);
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

    }
}

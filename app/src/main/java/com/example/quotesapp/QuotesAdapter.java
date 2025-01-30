package com.example.quotesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.ArrayList;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {
    ArrayList<Quote> arrayList;
    Context context;
    OnLikeClickListener onLikeClickListener;

    public QuotesAdapter(Context context, ArrayList<Quote> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.explore_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.quote.setText(arrayList.get(holder.getAbsoluteAdapterPosition()).getQuote());
        holder.author.setText(MessageFormat.format("- {0}", arrayList.get(holder.getAbsoluteAdapterPosition()).getAuthor()));

        if (new QuotesDatabaseManager(context).quoteExists(arrayList.get(holder.getAbsoluteAdapterPosition()).getQuote())) {
            holder.favouriteBtn.setIconResource(R.drawable.round_favourite_filled_24);
        } else {
            holder.favouriteBtn.setIconResource(R.drawable.round_favourite_outline_24);
        }

        holder.favouriteBtn.setOnClickListener(view -> {
            if (new QuotesDatabaseManager(context).quoteExists(arrayList.get(holder.getAbsoluteAdapterPosition()).getQuote())) {
                holder.favouriteBtn.setIconResource(R.drawable.round_favourite_outline_24);
            } else {
                holder.favouriteBtn.setIconResource(R.drawable.round_favourite_filled_24);
            }
            onLikeClickListener.like(holder.getAbsoluteAdapterPosition(), holder.itemView, arrayList.get(holder.getAbsoluteAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quote, author;
        MaterialButton favouriteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.list_item_quote);
            author = itemView.findViewById(R.id.list_item_author);
            favouriteBtn = itemView.findViewById(R.id.list_item_favourite_btn);
        }
    }

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        this.onLikeClickListener = onLikeClickListener;
    }

    public interface OnLikeClickListener {
        void like(int pos, View view, Quote quote);
    }
}
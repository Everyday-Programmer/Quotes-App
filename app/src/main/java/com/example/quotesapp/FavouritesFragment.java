package com.example.quotesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quotesapp.databinding.FragmentFavouritesBinding;

import java.util.ArrayList;

public class FavouritesFragment extends Fragment {
    FragmentFavouritesBinding binding;

    public FavouritesFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false);

        QuotesDatabaseManager quotesDatabaseManager = new QuotesDatabaseManager(requireContext());
        ArrayList<Quote> arrayList = quotesDatabaseManager.getAllQuotes();

        if (arrayList.isEmpty()) {
            binding.emptyTV.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.emptyTV.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }

        QuotesAdapter quotesAdapter = new QuotesAdapter(requireContext(), arrayList);
        binding.recyclerView.setAdapter(quotesAdapter);

        quotesAdapter.setOnLikeClickListener(new QuotesAdapter.OnLikeClickListener() {
            @Override
            public void like(int pos, View view, Quote quote) {
                quotesDatabaseManager.removeQuote(quote.getQuote());
                quotesAdapter.notifyItemRemoved(pos);
                arrayList.remove(pos);

                if (arrayList.isEmpty()) {
                    binding.emptyTV.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                }

                Toast.makeText(requireContext(), "Quote removed from favourites", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}
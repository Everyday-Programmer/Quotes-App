package com.example.quotesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotesapp.databinding.FragmentExploreBinding;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ExploreFragment extends Fragment {
    FragmentExploreBinding binding;
    ArrayList<Quote> arrayList;
    QuotesAdapter quotesAdapter;
    Skeleton skeleton;

    public ExploreFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();
        quotesAdapter = new QuotesAdapter(getActivity(), arrayList);
        binding.recyclerView.setAdapter(quotesAdapter);

        skeleton = SkeletonLayoutUtils.applySkeleton(binding.recyclerView, R.layout.explore_list_item, 10);
        skeleton.showSkeleton();

        fetchQuotes();

        return binding.getRoot();
    }

    private void fetchQuotes() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://zenquotes.io/api/quotes")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    try {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                skeleton.showOriginal();
                            }
                        });
                        JSONArray jsonArray = new JSONArray(response.body().string());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            if (jsonObject.getString("a").equals("null")) {
                                continue;
                            }

                            String quote = jsonObject.getString("q");
                            String author = jsonObject.getString("a");

                            Quote quote1 = new Quote(quote, author);
                            arrayList.add(quote1);

                            requireActivity().runOnUiThread(() -> {
                                quotesAdapter.notifyItemInserted(arrayList.size() - 1);
                                quotesAdapter.setOnLikeClickListener(new QuotesAdapter.OnLikeClickListener() {
                                    @Override
                                    public void like(int pos, View view, Quote quote) {
                                        QuotesDatabaseManager quotesDatabaseManager = new QuotesDatabaseManager(getActivity());
                                        if (!quotesDatabaseManager.quoteExists(quote.getQuote())) {
                                            quotesDatabaseManager.addLikedQuote(quote.getQuote(), quote.getAuthor());
                                            Snackbar.make(requireContext(), view, "Quote added to liked quotes", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            quotesDatabaseManager.removeQuote(quote.getQuote());
                                            Snackbar.make(requireContext(), view, "Quote removed from liked quotes", Snackbar.LENGTH_SHORT).show();
                                        }
                                        quotesDatabaseManager.close();
                                    }
                                });
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
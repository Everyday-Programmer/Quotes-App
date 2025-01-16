package com.example.quotesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quotesapp.databinding.FragmentExploreBinding;

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

    public ExploreFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);

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
                        ArrayList<Quote> arrayList = new ArrayList<>();
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

                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> {
                                    QuotesAdapter quotesAdapter = new QuotesAdapter(getActivity(), arrayList);
                                    binding.recyclerView.setAdapter(quotesAdapter);

                                    quotesAdapter.setOnLikeClickListener(new QuotesAdapter.OnLikeClickListener() {
                                        @Override
                                        public void like(Quote quote) {

                                        }
                                    });
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
package com.example.quotesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.quotesapp.databinding.FragmentHomeBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    boolean imageLoaded = false;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchQuote();

        binding.quoteToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (binding.quoteToggleGroup.getCheckedButtonId() == binding.textQuoteBtn.getId()) {
                    if (binding.quoteTV.getText().toString().isEmpty()) {
                        fetchQuote();
                    }
                    binding.textQuote.setVisibility(View.VISIBLE);
                    binding.imageQuote.setVisibility(View.GONE);
                    binding.refreshQuoteBtn.setVisibility(View.GONE);
                } else {
                    if (!imageLoaded) {
                        fetchImageQuote();
                    }
                    binding.textQuote.setVisibility(View.GONE);
                    binding.imageQuote.setVisibility(View.VISIBLE);
                    binding.refreshQuoteBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.refreshQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchImageQuote();
            }
        });
    }

    private void fetchQuote() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://zenquotes.io/api/today")
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
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String quote = jsonObject.getString("q");
                        String author = jsonObject.getString("a");

                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> {
                                binding.quoteTV.setText(quote);
                                binding.authorTV.append(author);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void fetchImageQuote() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://zenquotes.io/api/image")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.body() != null) {
                    String imageUrl = response.request().url().toString();
                    if (getActivity() != null) {
                        imageLoaded = true;
                        getActivity().runOnUiThread(() -> Glide.with(HomeFragment.this)
                                .load(imageUrl)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(binding.imageQuote));
                    }
                }
            }
        });
    }
}
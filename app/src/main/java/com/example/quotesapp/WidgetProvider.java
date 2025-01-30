package com.example.quotesapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WidgetProvider extends AppWidgetProvider {
    private static final String QUOTE_API_URL = "https://zenquotes.io/api/today";
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

        fetchQuote(views, appWidgetManager, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void fetchQuote(RemoteViews views, AppWidgetManager appWidgetManager, int appWidgetId) {
        Request request = new Request.Builder()
                .url(QUOTE_API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                views.setTextViewText(R.id.quoteTV, "Failed to load quote.");
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.body() != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String quote = jsonObject.getString("q");
                        String author = "- " +  jsonObject.getString("a");

                        views.setTextViewText(R.id.quoteTV, quote);
                        views.setTextViewText(R.id.authorTV, author);
                        appWidgetManager.updateAppWidget(appWidgetId, views);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
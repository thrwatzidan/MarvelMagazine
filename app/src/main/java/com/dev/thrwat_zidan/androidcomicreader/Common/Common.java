package com.dev.thrwat_zidan.androidcomicreader.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.dev.thrwat_zidan.androidcomicreader.Model.Category;
import com.dev.thrwat_zidan.androidcomicreader.Model.Chapter;
import com.dev.thrwat_zidan.androidcomicreader.Model.Comic;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.IComicAPI;
import com.dev.thrwat_zidan.androidcomicreader.Retofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static Comic selected_comic;
    public static Chapter selected_chapter;
    public static int chapter_index = -1;
    public static List<Chapter> chapterList = new ArrayList<>();
    public static List<Category> categories=new ArrayList<>();

    public static IComicAPI getAPI() {
        return RetrofitClient.getInstance().create(IComicAPI.class);


    }

    public static String formatString(String name) {
        StringBuilder builder = new StringBuilder(name.length() > 15 ? name.substring(0, 15) + "...." : name);

        return builder.toString();
    }

    public static boolean isConnectedToInternet(Context baseContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) baseContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {

                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }
}

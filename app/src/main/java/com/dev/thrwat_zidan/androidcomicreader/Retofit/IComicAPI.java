package com.dev.thrwat_zidan.androidcomicreader.Retofit;

import com.dev.thrwat_zidan.androidcomicreader.Model.Banner;
import com.dev.thrwat_zidan.androidcomicreader.Model.Category;
import com.dev.thrwat_zidan.androidcomicreader.Model.Chapter;
import com.dev.thrwat_zidan.androidcomicreader.Model.Comic;
import com.dev.thrwat_zidan.androidcomicreader.Model.Link;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IComicAPI {

    @GET("banner")
    Observable<List<Banner>> getBannerList();


    @GET("comic")
    Observable<List<Comic>> getComicList();


    @GET("chapter/{mangaid}")
    Observable<List<Chapter>> getChapterList(@Path("mangaid")int mangaid);


    @GET("links/{chapterid}")
    Observable<List<Link>> getImageList(@Path("chapterid")String chapterid);


    @GET("categories")
    Observable<List<Category>> getCategoryList();


    @POST("filter")
    @FormUrlEncoded
    Observable<List<Comic>> getFilteredComic(@Field("data") String data);

    @POST("search")
    @FormUrlEncoded
    Observable<List<Comic>> getsearchedComic(@Field("search") String search);
}

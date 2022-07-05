package com.komputerkit.ukomde_afa;

import android.widget.TextView;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMenuInterface {

    @GET("api/menu")
    Call<GetDaataMenu> getMenu();

    @GET("api/menu")
Call<GetDaataMenu> getMenu2();

    @GET("api/kategori")
    Call<GetDaataMenu> getKategori();

   @DELETE("api/menu/{id}")
    Call<dataMenu> PostputDel(@Path("id") Integer id);

    @Multipart
    @POST("api/menu")
    Call<GetDaataMenu> addMahasiswa(
                                @Part("menu") RequestBody menu1,
                                @Part("kode") RequestBody kode1,
                                @Part("harga") RequestBody harga1,
                                @Part("email") RequestBody email1,
                                @Part("deskripsi") RequestBody deskripsi1,
                                    @Part("nama_toko") RequestBody namatoko,
                                @Part MultipartBody.Part gambar);

    @Multipart
    @POST("api/menu/{id}")
    Call<GetDaataMenu> postUpdateHeros(
            @Path("id") Integer id,

            @Part("menu") RequestBody menu1,
            @Part("kode") RequestBody kode1,
            @Part("harga") RequestBody harga1,
            @Part("email") RequestBody email1,
            @Part("deskripsi") RequestBody deskripsi1,
            @Part("nama_toko") RequestBody namatoko,
            @Part MultipartBody.Part gambar);


    @Multipart
    @POST("api/menu/{id}")
    Call<GetDaataMenu> postUpdateHerosNoiMAGE(
            @Path("id") Integer id,

            @Part("menu") RequestBody menu1,
            @Part("kode") RequestBody kode1,
            @Part("harga") RequestBody harga1,
            @Part("email") RequestBody email1,
            @Part("deskripsi") RequestBody deskripsi1,
            @Part("nama_toko") RequestBody namatoko
            );

    @GET("api/history")
    Call<GetDataHistory> getHistory(

    );

    @POST("api/history1/{id}")
    Call<GetDataHistory> getUPH1(
            @Path("id") Integer id

    );

    @POST("api/history2/{id}")
    Call<GetDataHistory> getUPH2(
            @Path("id") Integer id

    );

    @POST("api/show")
    Call<GetDaataMenu> getQuery(
            @Query("search") String query
    );

    @Multipart
    @POST("api/history")
    Call<GetDataHistory> createH(

            @Part("id_user") RequestBody iduser,
            @Part("nama_user") RequestBody idkategori1,
            @Part("kode_barang") RequestBody menu1,
            @Part("alamat") RequestBody kode1,
            @Part("telp") RequestBody harga1,
            @Part("nama_menu") RequestBody gambary,
            @Part("kode_transaksi") RequestBody kodetr,
            @Part("harga") RequestBody harga,
            @Part("nama_toko") RequestBody namatoko,
            @Part("total") RequestBody total,
            @Part("jumlah") RequestBody jumlah,
            @Part("keterangan") RequestBody keterangan,
            @Part("tanggal") RequestBody tanggal,

            @Part("gambar") RequestBody deskripsi1);

 @DELETE("api/history/{id}")
 Call<DataHistory> hapusgistoryuser(@Path("id") Integer id);





}



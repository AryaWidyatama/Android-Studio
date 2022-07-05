package com.suhailahnfsella.fudum;

/**
 * Created by root on 2/3/17.
 */

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/menu")
    Call<GetMenu> getMenu();

    @GET("/api/user")
    Call<GetUser> getUser();

    @GET("/api/user2")
    Call<GetUser> getUser2();

    @POST("/api/tokouser/{id}")
    Call<TokoModel> getTokoUser(@Path("id") Integer id);

    @POST("/api/menureq/{request}")
    Call<Menu> getMenuReq(@Path("request") String request);

    @POST("/api/register")
    Call<GetDaftar> daftarUser(@Body DaftarModel daftarModel);

    @POST("/api/registeradm")
    Call<GetDaftar> daftarAdmin(@Body DaftarModel daftarModel);

    @POST("/api/login")
    Call<GetLogin> loginUser(@Body LoginModel loginModel);

    @POST("/api/loginadm")
    Call<GetLogin> loginAdmin(@Body LoginModel loginModel);

    @Multipart
    @POST("/api/toko")
    Call<GetToko> insertToko(@Part("idtoko") RequestBody idtoko,
                             @Part("namatoko") RequestBody namatoko,
                          @Part MultipartBody.Part fototoko,
                            @Part("alamattoko") RequestBody alamattoko,
                            @Part("kecamatan") RequestBody kecamatan,
                            @Part("kabupaten") RequestBody kabupaten,
                            @Part("tahuntoko") RequestBody tahuntoko,
                            @Part("sosmed") RequestBody sosmed,
                            @Part("whatsapp") RequestBody whatsapp,
                            @Part("email") RequestBody email);

    @Multipart
    @POST("/api/menu")
    Call<GetMenu> insertProduk(@Part("idkategori") RequestBody idkategori,
                               @Part("idtoko") RequestBody idtoko,
                               @Part("kategori") RequestBody kategori,
                               @Part("produk") RequestBody produk,
                               @Part("kodeproduk") RequestBody kodeproduk,
                               @Part("stok") RequestBody stok,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part MultipartBody.Part gambar,
                               @Part("harga") RequestBody harga,
                               @Part("namatoko") RequestBody namatoko,
                               @Part("fototoko") RequestBody fototoko,
                               @Part("tahunusaha") RequestBody tahuntoko,
                               @Part("alamattoko") RequestBody alamattoko,
                               @Part("kecamatan") RequestBody kecamatan,
                               @Part("kabupaten") RequestBody kabupaten,
                               @Part("sosmed") RequestBody sosmed,
                               @Part("whatsapp") RequestBody whatsapp,
                               @Part("email") RequestBody email);

    @Multipart
    @POST("/api/menu/{id}")
    Call<GetMenu> updateProduk(@Path("id") Integer idmenu,
                               @Part("idkategori") RequestBody idkategori,
                               @Part("kategori") RequestBody kategori,
                               @Part("produk") RequestBody produk,
                               @Part("kodeproduk") RequestBody kodeproduk,
                               @Part("stok") RequestBody stok,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part MultipartBody.Part gambar,
                               @Part("harga") RequestBody harga);

    @Multipart
    @POST("/api/menu/{id}")
    Call<GetMenu> updateProduk2(@Path("id") Integer idmenu,
                               @Part("idkategori") RequestBody idkategori,
                               @Part("kategori") RequestBody kategori,
                               @Part("produk") RequestBody produk,
                               @Part("kodeproduk") RequestBody kodeproduk,
                               @Part("stok") RequestBody stok,
                               @Part("deskripsi") RequestBody deskripsi,
                               @Part("harga") RequestBody harga);

    @Multipart
    @POST("/api/menu2/{id}")
    Call<GetMenu> updateProduk12(@Path("id") Integer idmenu,
                                @Part("idkategori") RequestBody idkategori,
                                @Part("kategori") RequestBody kategori,
                                @Part("produk") RequestBody produk,
                                @Part("kodeproduk") RequestBody kodeproduk,
                                @Part("stok") RequestBody stok,
                                @Part("deskripsi") RequestBody deskripsi,
                                 @Part MultipartBody.Part gambar,
                                @Part("harga") RequestBody harga);

    @Multipart
    @POST("/api/menu2/{id}")
    Call<GetMenu> updateProduk22(@Path("id") Integer idmenu,
                                @Part("idkategori") RequestBody idkategori,
                                @Part("kategori") RequestBody kategori,
                                @Part("produk") RequestBody produk,
                                @Part("kodeproduk") RequestBody kodeproduk,
                                @Part("stok") RequestBody stok,
                                @Part("deskripsi") RequestBody deskripsi,
                                @Part("harga") RequestBody harga);

    @POST("/api/loginpass")
    Call<GetLupaPwEmail> loginEmail(@Body LupaPwEmailModel lupaPwEmailModel);

    @POST("/api/user/{id}")
    Call<GetLupaPwPassword> NewPassword(@Path("id") Integer id,
                                     @Body LupaPwPasswordModel lupaPwPasswordModel);

    @Multipart
    @POST("/api/user2/{id}")
    Call<GetUser> addMahasiswa(@Path("id") Integer id,
                                    @Part("namapanjang") RequestBody namapanjang,
                                    @Part("username") RequestBody username,
                                    @Part("password") RequestBody password,
                                    @Part MultipartBody.Part gambar);

    @Multipart
    @POST("/api/user2/{id}")
    Call<GetUser> addMahasiswanoimage(@Path("id") Integer id,
                               @Part("namapanjang") RequestBody namapanjang,
                               @Part("username") RequestBody username,
                               @Part("password") RequestBody password);

    @Multipart
    @POST("/api/user3/{id}")
    Call<GetUser> updateStatus(@Path("id") Integer id,
                               @Part("status") RequestBody status);


    @POST("/api/show")
    Call<GetMenu> getQuery(@Query("search") String query);

    @DELETE("api/menu/{id}")
    Call<GetMenu> DeleteProduk(@Path("id") Integer id);

    @GET("/api/history")
    Call<GetDataHistory> getHistory(
    );

    @Multipart
    @POST("/api/history")
    Call<GetDataHistory> createH(
            @Part("iduser") RequestBody iduser,
            @Part("idtoko") RequestBody idtoko,
            @Part("namapenerima") RequestBody namapenerima,
            @Part("alamatpenerima") RequestBody alamatpenerima,
            @Part("kodeproduk") RequestBody kodeproduk,
            @Part("jumlahpesanan") RequestBody jumlahpesanan,
            @Part("via") RequestBody via);

    @POST("/api/history1/{id}")
    Call<GetDataHistory> UpdateHistory1(@Path("id") Integer id);

    @POST("/api/history2/{id}")
    Call<GetDataHistory> UpdateHistory2(@Path("id") Integer id);

    @Multipart
    @POST("/api/history3/{id}")
    Call<GetDataHistory> UpdateHistory3(@Path("id") Integer id,
                                        @Part MultipartBody.Part bukti);

//    @FormUrlEncoded
//    @POST("kontak")
//    Call<PostPutDelKontak> postKontak(@Field("nama") String nama,
//                                      @Field("nomor") String nomor);
//    @FormUrlEncoded
//    @PUT("kontak")
//    Call<PostPutDelKontak> putKontak(@Field("id") String id,
//                                     @Field("nama") String nama,
//                                     @Field("nomor") String nomor);
//    @FormUrlEncoded
//    @HTTP(method = "DELETE", path = "kontak", hasBody = true)
//    Call<PostPutDelKontak> deleteKontak(@Field("id") String id);
}

package com.komputerkit.komen;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/apikoperasi/public/api/menu")
    Call<GetMenu> getMenu();


    @GET("/apikoperasi/public/api/jasa")
    Call<GetJasaModel> getJasa();




    @GET("/apikoperasi/public/api/barang")
    Call<GetBarang> getBarang();

//    @GET("/api/barang")
//    Call<GetBarang> getBarang();

    @POST("/apikoperasi/public/api/updatestatusakun2/{id}")
    Call<GetSiswa> UpdateUser(@Path("id") Integer id);
//    @GET("/api/user")
//    Call<GetUser> getUser();
//
//    @GET("/api/user2")
//    Call<GetUser> getUser2();
//
//    @POST("/api/tokouser/{id}")
//    Call<TokoModel> getTokoUser(@Path("id") Integer id);
//
//    @POST("/api/menureq/{request}")
//    Call<Menu> getMenuReq(@Path("request") String request);

    @POST("/apikoperasi/public/api/registersiswa")
    Call<GetSiswa> daftarUser(@Body SiswaModel daftarModel);



    @POST("/apikoperasi/public/api/registerguru")
    Call<GetSiswa> daftarguru(@Body SiswaModel daftarModel);

    @POST("/apikoperasi/public/api/akunawal")
    Call<GetAkunAwal> akunawal(@Body AkunAwal daftarModel);

//    @POST("/api/registeradm")
//    Call<GetDaftar> daftarAdmin(@Body DaftarModel daftarModel);

    @POST("/apikoperasi/public/api/loginsiswa")
    Call<GetSiswa> loginUser(@Body SiswaModel siswaModel);

//    @POST("/api/loginadm")
//    Call<GetLogin> loginAdmin(@Body LoginModel loginModel);

//    @Multipart
//    @POST("/api/toko")
//    Call<GetToko> insertToko(@Part("idtoko") RequestBody idtoko,
//                             @Part("namatoko") RequestBody namatoko,
//                             @Part MultipartBody.Part fototoko,
//                             @Part("alamattoko") RequestBody alamattoko,
//                             @Part("kecamatan") RequestBody kecamatan,
//                             @Part("kabupaten") RequestBody kabupaten,
//                             @Part("tahuntoko") RequestBody tahuntoko,
//                             @Part("sosmed") RequestBody sosmed,
//                             @Part("whatsapp") RequestBody whatsapp,
//                             @Part("email") RequestBody email);
//
//    @Multipart
//    @POST("/api/menu")
//    Call<GetMenu> insertProduk(@Part("idkategori") RequestBody idkategori,
//                               @Part("idtoko") RequestBody idtoko,
//                               @Part("kategori") RequestBody kategori,
//                               @Part("produk") RequestBody produk,
//                               @Part("kodeproduk") RequestBody kodeproduk,
//                               @Part("stok") RequestBody stok,
//                               @Part("deskripsi") RequestBody deskripsi,
//                               @Part MultipartBody.Part gambar,
//                               @Part("harga") RequestBody harga,
//                               @Part("namatoko") RequestBody namatoko,
//                               @Part("fototoko") RequestBody fototoko,
//                               @Part("tahunusaha") RequestBody tahuntoko,
//                               @Part("alamattoko") RequestBody alamattoko,
//                               @Part("kecamatan") RequestBody kecamatan,
//                               @Part("kabupaten") RequestBody kabupaten,
//                               @Part("sosmed") RequestBody sosmed,
//                               @Part("whatsapp") RequestBody whatsapp,
//                               @Part("email") RequestBody email);
//
//    @Multipart
//    @POST("/api/menu/{id}")
//    Call<GetMenu> updateProduk(@Path("id") Integer idmenu,
//                               @Part("idkategori") RequestBody idkategori,
//                               @Part("kategori") RequestBody kategori,
//                               @Part("produk") RequestBody produk,
//                               @Part("kodeproduk") RequestBody kodeproduk,
//                               @Part("stok") RequestBody stok,
//                               @Part("deskripsi") RequestBody deskripsi,
//                               @Part MultipartBody.Part gambar,
//                               @Part("harga") RequestBody harga);
//
//    @Multipart
//    @POST("/api/menu/{id}")
//    Call<GetMenu> updateProduk2(@Path("id") Integer idmenu,
//                                @Part("idkategori") RequestBody idkategori,
//                                @Part("kategori") RequestBody kategori,
//                                @Part("produk") RequestBody produk,
//                                @Part("kodeproduk") RequestBody kodeproduk,
//                                @Part("stok") RequestBody stok,
//                                @Part("deskripsi") RequestBody deskripsi,
//                                @Part("harga") RequestBody harga);
//
//    @Multipart
//    @POST("/api/menu2/{id}")
//    Call<GetMenu> updateProduk12(@Path("id") Integer idmenu,
//                                 @Part("idkategori") RequestBody idkategori,
//                                 @Part("kategori") RequestBody kategori,
//                                 @Part("produk") RequestBody produk,
//                                 @Part("kodeproduk") RequestBody kodeproduk,
//                                 @Part("stok") RequestBody stok,
//                                 @Part("deskripsi") RequestBody deskripsi,
//                                 @Part MultipartBody.Part gambar,
//                                 @Part("harga") RequestBody harga);
//
//    @Multipart
//    @POST("/api/menu2/{id}")
//    Call<GetMenu> updateProduk22(@Path("id") Integer idmenu,
//                                 @Part("idkategori") RequestBody idkategori,
//                                 @Part("kategori") RequestBody kategori,
//                                 @Part("produk") RequestBody produk,
//                                 @Part("kodeproduk") RequestBody kodeproduk,
//                                 @Part("stok") RequestBody stok,
//                                 @Part("deskripsi") RequestBody deskripsi,
//                                 @Part("harga") RequestBody harga);
//
    @POST("/apikoperasi/public/api/loginpassiswa")
    Call<GetSiswa> loginEmail(@Body SiswaModel lupaPwEmailModel);
//
    @POST("/apikoperasi/public/api/newpwsiswa/{id}")
    Call<GetNewPw> NewPassword(@Path("id") Integer id,
                                        @Body NewPw lupaPwPasswordModel);

    @POST("/apikoperasi/public/api/updatestok/{id}")
    Call<GetBarang> updatestok(@Path("id") Integer id,

                               @Body Barang lupaPwPasswordModel);

    @POST("/apikoperasi/public/api/pesanupdatepg/{id}")
    Call<GetNewDataPengguna> NewDataPesan(@Path("id") Integer id,
                               @Body NewDataPesan lupaPwPasswordModel);
//


    @Multipart
    @POST("/apikoperasi/public/api/updatesiswa/{id}")
    Call<GetSiswa> addMahasiswa(@Path("id") Integer id,
                                @Part("NamaLengkap") RequestBody NamaLengkap,
                               @Part("Username") RequestBody Username,
                               @Part("Email") RequestBody Email,
                               @Part("Kelas") RequestBody Kelas,
                                @Part("Jurusan") RequestBody Jurusan,
                                @Part("NoTelp") RequestBody NoTelp,
                                @Part("Password") RequestBody Password,
                               @Part MultipartBody.Part gambar);


    @Multipart
    @POST("/apikoperasi/public/api/updateguru/{id}")
    Call<GetSiswa> updateguru(@Path("id") Integer id,
                                @Part("NamaLengkap") RequestBody NamaLengkap,
                                @Part("Username") RequestBody Username,
                                @Part("Email") RequestBody Email,
                                @Part("Kelas") RequestBody Kelas,
                                @Part("Jurusan") RequestBody Jurusan,
                                @Part("NoTelp") RequestBody NoTelp,
                                @Part("Password") RequestBody Password,
                                @Part MultipartBody.Part gambar);


    @Multipart
    @POST("/apikoperasi/public/api/verifsiswa")
    Call<GetVerifAkun> verifakun( @Part("NamaLengkap") RequestBody NamaLengkap,
                                  @Part("idsiswa") RequestBody idsiswa,
                                @Part("Email") RequestBody Email,
                              @Part MultipartBody.Part gambar,
                              @Part("NoTelp") RequestBody NoTelp,
                                @Part("Kelas") RequestBody Kelas,
                                @Part("Jurusan") RequestBody Jurusan,
                                @Part("Username") RequestBody Username
                               );

//    @Multipart
//    @POST("/api/verifsiswa")
//    Call<GetVerifAkun> verifakun( @Part("NamaLengkap") RequestBody NamaLengkap,
//                                  @Part("idsiswa") RequestBody idsiswa,
//                                  @Part("Email") RequestBody Email,
//                                  @Part MultipartBody.Part gambar,
//                                  @Part("NoTelp") RequestBody NoTelp,
//                                  @Part("Kelas") RequestBody Kelas,
//                                  @Part("Jurusan") RequestBody Jurusan,
//                                  @Part("Username") RequestBody Username
//    );


    @Multipart
    @POST("/apikoperasi/public/api/updatesiswa/{id}")
    Call<GetSiswa> addMahasiswanoimage(@Path("id") Integer id,
                                       @Part("NamaLengkap") RequestBody NamaLengkap,
                                       @Part("Username") RequestBody Username,
                                       @Part("Email") RequestBody Email,
                                       @Part("Kelas") RequestBody Kelas,
                                       @Part("Jurusan") RequestBody Jurusan,
                                       @Part("NoTelp") RequestBody NoTelp,
                                       @Part("Password") RequestBody Password);

    @Multipart
    @POST("/apikoperasi/public/api/updateguru/{id}")
    Call<GetSiswa> updategurunoimage(@Path("id") Integer id,
                                       @Part("NamaLengkap") RequestBody NamaLengkap,
                                       @Part("Username") RequestBody Username,
                                       @Part("Email") RequestBody Email,
                                       @Part("Kelas") RequestBody Kelas,
                                       @Part("Jurusan") RequestBody Jurusan,
                                       @Part("NoTelp") RequestBody NoTelp,
                                       @Part("Password") RequestBody Password);
//
//    @Multipart
//    @POST("/api/user3/{id}")
//    Call<GetUser> updateStatus(@Path("id") Integer id,
//                               @Part("status") RequestBody status);
//
//
    @POST("/apikoperasi/public/api/showbrg1")
    Call<GetBarang> getQuery(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbrg2")
    Call<GetBarang> getQuery1(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbrg3")
    Call<GetBarang> makanan(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbrg4")
    Call<GetBarang> minuman(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbrghome")
    Call<GetBarang> getQueryhome(@Query("search") String query);

    @POST("/apikoperasi/public/api/showuserproses")
    Call<GetPesananModel> getProsesuser(@Query("search") String query);
    @POST("/apikoperasi/public/api/showuserterima")
    Call<GetPesananModel> getTerima(@Query("search") String query);
    @POST("/apikoperasi/public/api/showusertolak")
    Call<GetPesananModel> getTolak(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbrg1a")
    Call<GetBarang> showatribut();

    @POST("/apikoperasi/public/api/showbrg2a")
    Call<GetBarang> showatk();

    @POST("/apikoperasi/public/api/showbrg3a")
    Call<GetBarang> showmakanan();

    @POST("/apikoperasi/public/api/showbrg4a")
    Call<GetBarang> showminuman();



    @POST("/apikoperasi/public/api/showmm1")
    Call<GetJasaModel> showmm1();

    @POST("/apikoperasi/public/api/showrpl1")
    Call<GetJasaModel> showrpl();

    @POST("/apikoperasi/public/api/showak1")
    Call<GetJasaModel> showak1();

    @POST("/apikoperasi/public/api/showpbk1")
    Call<GetJasaModel> showpbk();

    @POST("/apikoperasi/public/api/showotkp1")
    Call<GetJasaModel> showotkp1();

    @POST("/apikoperasi/public/api/showbdp1")
    Call<GetJasaModel> showbdp();


    @POST("/apikoperasi/public/api/showmm")
    Call<GetJasaModel> mm(@Query("search") String query);

    @POST("/apikoperasi/public/api/showrpl")
    Call<GetJasaModel> rpl(@Query("search") String query);

    @POST("/apikoperasi/public/api/showak")
    Call<GetJasaModel> ak(@Query("search") String query);

    @POST("/apikoperasi/public/api/showpbk")
    Call<GetJasaModel> pbk(@Query("search") String query);

    @POST("/apikoperasi/public/api/showotkp")
    Call<GetJasaModel> otkp(@Query("search") String query);

    @POST("/apikoperasi/public/api/showbdp")
    Call<GetJasaModel> bdp(@Query("search") String query);


//
//    @DELETE("api/menu/{id}")
//    Call<GetMenu> DeleteProduk(@Path("id") Integer id);
//
    @GET("/apikoperasi/public/api/pesan")
    Call<GetPesananModel> getHistory(
    );
//

    @Multipart
    @Headers({ "Cache-Control: no-cache"})
    @POST("/apikoperasi/public/api/pesan")
    Call<GetPesananModel> createH(
            @Part("idSiswa") RequestBody idSiswa,
            @Part("Namabarang") RequestBody Namabarang,
            @Part("harga") RequestBody harga,
            @Part("FotoBarang") RequestBody FotoBarang,
            @Part("NamaPengguna") RequestBody NamaPengguna,
            @Part("Tujuan") RequestBody Tujuan,
            @Part("KodePesanan") RequestBody KodePesanan,
            @Part("KelasPengguna") RequestBody KelasPengguna,
            @Part("JurusanPengguna") RequestBody JurusanPengguna,
            @Part("TanggalTransaksi") RequestBody TanggalTransaksi,
            @Part("DeskripsiPesanan") RequestBody DeskripsiPesanan,
            @Part("jumlahPesanan") RequestBody jumlahPesanan,
            @Part("KodeBarang") RequestBody KodeBarang,
            @Part("Opsi") RequestBody Opsi,
            @Part("JumlahHarga") RequestBody JumlahHarga
            );
//
//    @POST("/api/history1/{id}")
//    Call<GetDataHistory> UpdateHistory1(@Path("id") Integer id);
//
//    @POST("/api/history2/{id}")
//    Call<GetDataHistory> UpdateHistory2(@Path("id") Integer id);
//
//    @Multipart
//    @POST("/api/history3/{id}")
//    Call<GetDataHistory> UpdateHistory3(@Path("id") Integer id,
//                                        @Part MultipartBody.Part bukti);

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

package com.komputerkit.ukomde_afa;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface LoginRegInterfaace {

    @POST("/ukom/api-lumen/public/api/login")
    Call<GetLogin> loginReg(@Body Login login);

    @POST("/ukom/api-lumen/public/api/loginpass")
    Call<GetLoginEmail> loginEmail(@Body LoginEmail loginEmail);

    @POST("/ukom/api-lumen/public/api/register")
    Call<GetRegister> register(@Body RegisterModel registerModel);

    @Multipart
    @POST("/ukom/api-lumen/public/api/register")
    Call<GetRegister> reggambar(@Part("level") RequestBody emailr,
                                    @Part("password") RequestBody passwordr,
                                    @Part("email") RequestBody levelr,
                                    @Part("relasi") RequestBody relasir,
                                    @Part MultipartBody.Part gambar);

    @Multipart
    @POST("/ukom/api-lumen/public/api/register")
    Call<GetRegister> regnogambar(@Part("level") RequestBody emailr,
                                @Part("password") RequestBody passwordr,
                                @Part("email") RequestBody levelr,
                                @Part("relasi") RequestBody relasir);

    @POST("/ukom/api-lumen/public/api/user/{id}")
    Call<GetNewPassword> NewPassword(@Path("id") Integer id,
                              @Body NewPPassword newPPassword);

    @DELETE("/ukom/api-lumen/public/api/menu/{id}")
    Call<dataMenu> PostputDel(@Path("id") Integer id);

    @Multipart
    @POST("/ukom/api-lumen/public/api/userupdate/{id}")
    Call<GetRegister> ubahreg(
            @Path("id") Integer id,
            @Part("level") RequestBody emailr,
                                @Part("password") RequestBody passwordr,
                                @Part("email") RequestBody levelr,
                                @Part("relasi") RequestBody relasir,
                                @Part MultipartBody.Part gambar);

    @Multipart
    @POST("/ukom/api-lumen/public/api/userupdate/{id}")
    Call<GetRegister> ubahregnogambar(
            @Path("id") Integer id,
            @Part("level") RequestBody emailr,
                                  @Part("password") RequestBody passwordr,
                                  @Part("email") RequestBody levelr,
                                  @Part("relasi") RequestBody relasir);




}

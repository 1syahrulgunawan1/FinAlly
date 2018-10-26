package javasign.net.FinAlly.api;

import javasign.net.FinAlly.models.AccountResponse;
import javasign.net.FinAlly.models.CashFlowsResponse;
import javasign.net.FinAlly.models.CashFlowsUpdates;
import javasign.net.FinAlly.models.LoginInternetbangkingResponse;
import javasign.net.FinAlly.models.LoginResponse;
import javasign.net.FinAlly.models.PasswordResponse;
import javasign.net.FinAlly.models.PromoResponse;
import javasign.net.FinAlly.models.SycnResponse;
import javasign.net.FinAlly.models.VendorResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @FormUrlEncoded
    @POST("user/auth")
    Call<LoginResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("user/auth")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("user/forgot")
    Call<PasswordResponse> updatePassword(
            @Field("email") String email,
            @Field("new") String newpassword,
            @Field("confirm") String confirmpassword
    );

    @FormUrlEncoded
    @POST("account/create")
    Call<LoginInternetbangkingResponse> createAccount(
            @Field("vendor_id") int Vendor_id,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("cashflows/update/{cashflow_id}")
    Call<CashFlowsUpdates> updatepaid(
            @Path("cashflow_id") int cashflow_id,
            @Field("status") String status
    );


    @GET("accounts")
    Call<AccountResponse> getAccounts();

    @GET("vendors")
    Call<VendorResponse> getVendor();

    @GET("promos/{vendor_id}")
    Call<PromoResponse> getPromo(
            @Path("vendor_id") int id
    );

    @GET("cashflows/product/{id_product}")
    Call<CashFlowsResponse> getCashflows(
            @Path("id_product") int id
    );

    @GET("account/sync/{account_id}")
    Call<SycnResponse> getSycn(
            @Path("account_id") int id
    );

}

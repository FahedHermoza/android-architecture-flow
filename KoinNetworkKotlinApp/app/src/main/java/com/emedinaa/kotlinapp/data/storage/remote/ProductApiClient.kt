package com.emedinaa.kotlinapp.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
/*
object ProductApiClient {

    private const val API_BASE_URL = "https://api.backendless.com/"
    private var servicesApiInterface: ServicesApiInterface? = null

    fun build(): ServicesApiInterface? {
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java
        )

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    fun getInstance() = this

    interface ServicesApiInterface {

        //Backendless
        //7FBB8DC0-4C21-0178-FF76-367F7D30DC00/E5214A86-653A-529C-FF73-95B4DD4F8C00/users/login
        @POST("/{applicationid}/{restapikey}/users/login")
        suspend fun logInBL(@Path("applicationid") appID:String,
                    @Path("restapikey") restApiKey:String,
                    @Body raw: LogInRaw
        ): Response<LogInResponse>

        //https://api.backendless.com/10D59C7B-6F06-9680-FF6F-D14965C63800/033EA8E0-7219-480B-B934-3F176D26DA7F/data/Product
        @GET("/{applicationid}/{restapikey}/data/Product")
        suspend fun products(@Path("applicationid") appID:String,
                  @Path("restapikey") restApiKEY:String,
                  @HeaderMap headers:Map<String,String>?): Response<List<ProductDTO>>

        //Create Note
        //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>
        @POST("/{applicationid}/{restapikey}/data/Product")
        suspend fun addProduct(@Path("applicationid") appID:String,
                            @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                            @Body raw: ProductRaw
        ): Response<ProductResponse>

        //Delete Note
        //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>/<object-id>
        @DELETE("/{applicationid}/{restapikey}/data/Product/{objectId}")
        suspend fun deleteProduct(@Path("applicationid") appID:String,
                               @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                               @Path("objectId") objectId:String?): Response<DeleteResponse>

        //https://api.backendless.com/<application-id>/<REST-api-key>/data/bulk/<table-name>?where=<where cláusula>
        @Headers("Content-Type: application/json")
        @DELETE("/{applicationid}/{restapikey}/data/bulk/Product")
        suspend fun deleteProducts(@Path("applicationid") appID: String,
                                   @Path("restapikey") restApiKEY: String, @HeaderMap headers: Map<String, String>?,
                                   @Query("where") where: String?): Response<Int>

        //Update Note
        //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>/<object-id>
        @PUT("/{applicationid}/{restapikey}/data/Product/{objectId}")
        suspend fun updateProduct(@Path("applicationid") appID:String,
                       @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                       @Path("objectId")objectId:String?,@Body raw: ProductRaw?): Response<ProductResponse>

    }
}*/

interface ProductApiClient {

    //Backendless
    //7FBB8DC0-4C21-0178-FF76-367F7D30DC00/E5214A86-653A-529C-FF73-95B4DD4F8C00/users/login
    @POST("/{applicationid}/{restapikey}/users/login")
    suspend fun logInBL(@Path("applicationid") appID:String,
                        @Path("restapikey") restApiKey:String,
                        @Body raw: LogInRaw
    ): Response<LogInResponse>

    //https://api.backendless.com/10D59C7B-6F06-9680-FF6F-D14965C63800/033EA8E0-7219-480B-B934-3F176D26DA7F/data/Product
    @GET("/{applicationid}/{restapikey}/data/Product")
    suspend fun products(@Path("applicationid") appID:String,
                         @Path("restapikey") restApiKEY:String,
                         @HeaderMap headers:Map<String,String>?): Response<List<ProductDTO>>

    //Create Note
    //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>
    @POST("/{applicationid}/{restapikey}/data/Product")
    suspend fun addProduct(@Path("applicationid") appID:String,
                           @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                           @Body raw: ProductRaw
    ): Response<ProductResponse>

    //Delete Note
    //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>/<object-id>
    @DELETE("/{applicationid}/{restapikey}/data/Product/{objectId}")
    suspend fun deleteProduct(@Path("applicationid") appID:String,
                              @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                              @Path("objectId") objectId:String?): Response<DeleteResponse>

    //https://api.backendless.com/<application-id>/<REST-api-key>/data/bulk/<table-name>?where=<where cláusula>
    @Headers("Content-Type: application/json")
    @DELETE("/{applicationid}/{restapikey}/data/bulk/Product")
    suspend fun deleteProducts(@Path("applicationid") appID: String,
                               @Path("restapikey") restApiKEY: String, @HeaderMap headers: Map<String, String>?,
                               @Query("where") where: String?): Response<Int>

    //Update Note
    //https://api.backendless.com/<application-id>/<REST-api-key>/data/<table-name>/<object-id>
    @PUT("/{applicationid}/{restapikey}/data/Product/{objectId}")
    suspend fun updateProduct(@Path("applicationid") appID:String,
                              @Path("restapikey") restApiKEY:String,@HeaderMap headers:Map<String,String>?,
                              @Path("objectId")objectId:String?,@Body raw: ProductRaw?): Response<ProductResponse>

}
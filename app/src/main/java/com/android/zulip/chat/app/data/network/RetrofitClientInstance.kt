package com.android.zulip.chat.app.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private const val BASE_URL = "https://setjy.zulipchat.com/api/v1/"
    private const val AUTH_HEADER = "Authorization"
    private const val AUTH_TYPE = "Basic"
    private const val API_KEY = "kWmO8XDVkBqe0wfIjsZG8debmYYoetxp"
    private const val EMAIL = "swed197@mail.ru"

    fun getInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()

    private fun authInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(AUTH_HEADER, "$AUTH_TYPE $EMAIL:$API_KEY")
                .build()
        )
    }

    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor())
        .build()
}
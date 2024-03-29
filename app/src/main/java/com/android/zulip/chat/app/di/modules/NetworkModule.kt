package com.android.zulip.chat.app.di.modules

import com.android.zulip.chat.app.data.network.ZulipApi
import dagger.Module
import dagger.Provides
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://setjy.zulipchat.com/api/v1/"
        private const val AUTH_HEADER = "Authorization"
        private const val AUTH_TYPE = "Basic"
        private const val API_KEY = "kWmO8XDVkBqe0wfIjsZG8debmYYoetxp"
        private const val EMAIL = "swed197@mail.ru"
    }

    @Provides
    @Singleton
    fun getInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun authInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(AUTH_HEADER, Credentials.basic(EMAIL, API_KEY))
                .build()
        )
    }

    @Provides
    @Singleton
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ZulipApi = retrofit.create(ZulipApi::class.java)

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}
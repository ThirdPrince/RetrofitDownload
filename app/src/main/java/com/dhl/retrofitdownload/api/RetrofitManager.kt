package com.dhl.retrofitdownload.api

import com.dhl.rcymvvm.utils.EndPoints
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitManager
 * @author dhl
 *
 */
object RetrofitManager {

    private const val BASEURL = EndPoints.BASE_URL


    val imageService: ImageService by lazy { retrofit.create(ImageService::class.java) }
    /**
     * 懒加载
     */
    private val retrofit: Retrofit by lazy {
        buildRetrofit(BASEURL, buildHttpClient())
    }

    /**
     * 构建自己的OKHttp
     */
    private fun buildHttpClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)


    }

    /**
     * 构建 Retrofit
     */
    private fun buildRetrofit(baseUrl: String, build: OkHttpClient.Builder): Retrofit {

        val client = build.build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }
}
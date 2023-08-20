package com.dhl.retrofitdownload.api

import android.webkit.DownloadListener
import com.dhl.rcymvvm.utils.EndPoints
import com.dhl.retrofitdownload.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming

/**
 * image API
 */
interface ImageService {

     @Streaming
     @GET(EndPoints.IMAGE)
     suspend fun  getImage(): Response<ResponseBody>

     @Streaming
     @GET(EndPoints.IMAGE2)
     suspend fun getImage2(): Response<ResponseBody>



}
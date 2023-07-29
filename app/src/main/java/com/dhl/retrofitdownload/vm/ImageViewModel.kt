package com.dhl.retrofitdownload.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.ToastUtils
import com.dhl.retrofitdownload.api.RetrofitManager
import com.dhl.retrofitdownload.model.DownloadState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

/**
 * 下载图片测试
 * @author dhl
 */
class ImageViewModel :ViewModel() {

    /**
     * LiveData
     */
    private val _downloadLiveData = MutableLiveData<DownloadState>()
    private val downloadLiveData :LiveData<DownloadState>
        get() = _downloadLiveData

    init {


    }

    /**
     * 异常信息
     */
    private val exception = CoroutineExceptionHandler { _, throwable ->
        _downloadLiveData.value = DownloadState.Failed(throwable.message)
    }

    fun getImage( image:String):LiveData<DownloadState>{
        viewModelScope.launch(exception) {
            val targetFile = File(image)
            _downloadLiveData.postValue(DownloadState.Downloading(0))
            var response = RetrofitManager.imageService.getImage()
            if(response.isSuccessful) {
                response.body()?.let { saveFile(it, targetFile) }
            } else{
                _downloadLiveData.postValue(DownloadState.Failed(response.errorBody().toString()))
            }

        }
        return downloadLiveData
    }





    /**
     * 封装saveFile
     */
    private suspend fun saveFile(response:ResponseBody, targetFile:File) = withContext(Dispatchers.IO){
        val inputStream = response.byteStream()
        val outputStream = FileOutputStream(targetFile)
        val contentLength: Long = response.contentLength()
        var len = 0
        var sum = 0
        val bytes = ByteArray(1024 * 12)
        while (inputStream.read(bytes).also { len = it } != -1) {
            sum += len
            outputStream.write(bytes, 0, len)
            outputStream.flush()
            val sumLen = sum
            // 模拟耗时
            delay(50)
            _downloadLiveData.postValue(DownloadState.Downloading((sumLen * 1.0f / contentLength * 100).toInt()))
        }
        targetFile.setExecutable(true, false)
        targetFile.setReadable(true, false)
        targetFile.setWritable(true, false)
        _downloadLiveData.postValue(DownloadState.DownloadFinish(targetFile.absolutePath))
    }
}
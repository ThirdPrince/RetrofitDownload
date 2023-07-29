package com.dhl.retrofitdownload.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhl.retrofitdownload.api.RetrofitManager
import com.dhl.retrofitdownload.model.DownloadState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

/**
 * 下载图片测试 Flow
 * @author dhl
 */
class ImageViewModelByFlow : ViewModel() {



    private val _dataFlow = MutableStateFlow<DownloadState>(DownloadState.Downloading(0))
    private val dataFlow: StateFlow<DownloadState> = _dataFlow.asStateFlow()

    /**
     * 异常信息
     */
    private val exception = CoroutineExceptionHandler { _, throwable ->

        viewModelScope.launch {
            _dataFlow.emit(DownloadState.Failed(throwable.message))
        }

    }

    suspend fun getImage2(image: String): Flow<DownloadState> {
        viewModelScope.launch(exception){
            val targetFile = File(image)
            _dataFlow.emit(DownloadState.Downloading(0))

            var responseBody = RetrofitManager.imageService.getImage2()
            if(responseBody.isSuccessful){
                responseBody.body()?.saveFile(targetFile)
            }else{
                _dataFlow.emit((DownloadState.Failed(responseBody.errorBody().toString())))

            }

        }
        return dataFlow


    }



    /**
     * save file
     */
    private suspend fun ResponseBody.saveFile(targetFile: File) = withContext(Dispatchers.IO){
        val inputStream = byteStream()
        val outputStream = FileOutputStream(targetFile)
        val contentLength: Long = contentLength()
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
            _dataFlow.emit(DownloadState.Downloading((sumLen * 1.0f / contentLength * 100).toInt()))
        }
        targetFile.setExecutable(true, false)
        targetFile.setReadable(true, false)
        targetFile.setWritable(true, false)
        _dataFlow.emit(DownloadState.DownloadFinish(targetFile.absolutePath))
    }


}
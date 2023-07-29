package com.dhl.retrofitdownload.model

/**
 * 下载状态
 * @author dhl
 */
sealed class DownloadState{
    data class Downloading(val progress: Int) : DownloadState()
    data class DownloadFinish(val filePath:String):DownloadState()
    data class Failed(val error: String? = null) : DownloadState()
}

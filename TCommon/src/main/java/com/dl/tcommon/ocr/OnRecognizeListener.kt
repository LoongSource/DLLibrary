package com.dl.tcommon.ocr

/**
 * @Author: DL
 * @Date: 2025/5/28 16:16
 * @Description: 接口回调
 */
interface OnRecognizeListener {
    fun onSuccess(visionText: String, data: IDImageTextRecognizerModel)
    fun onError()
}
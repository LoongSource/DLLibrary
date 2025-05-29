package com.dl.tcommon.ocr

/**
 * @Author: DL
 * @Date: 2025/5/28 16:16
 * @Description: 接口回调
 */
interface OnRecognizeListener {
    /**
     * 成功回调
     * @param visionText 返回的ocr识别的原数据
     * @param data 解析出的数据对象
     */
    fun onSuccess(visionText: String, data: IDImageTextRecognizerModel)
    fun onError()
}
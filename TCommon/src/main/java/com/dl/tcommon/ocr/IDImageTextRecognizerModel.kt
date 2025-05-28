package com.dl.tcommon.ocr

/**
 * @Author: DL
 * @Date: 2025/5/28 16:15
 * @Description: 模型数据格式
 */
data class IDImageTextRecognizerModel(
    var nationality: String = "",
    var chineseName: String = "",
    var englishName: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var idNumber: String = "",
    var sex: String = "M",
    var birthday: String = "N/A",
    var idExpiryDate: String = "N/A"
)

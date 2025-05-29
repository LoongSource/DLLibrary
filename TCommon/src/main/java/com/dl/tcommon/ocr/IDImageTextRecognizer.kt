package com.dl.tcommon.ocr

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.text.TextUtils
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import java.io.IOException
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 * @Author: DL
 * @Date: 2025/5/28 16:17
 * @Description:
 */
object IDImageTextRecognizer {
    private var recognizer: TextRecognizer =
        TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())

    /**
     * 证件图像解析 基于 media.Image 对象创建 InputImage 对象（例如从设备的相机捕获图片时）
     * @param image image
     * @param rotation 图片旋转角度
     * @param idType 证件类型
     * @param listener 处理回调
     */
    fun IDImageProcess(
        image: Image,
        rotation: Int = 0,
        idType: IDType,
        listener: OnRecognizeListener
    ) {
        val inputImage = InputImage.fromMediaImage(image, rotation)
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                when (idType.name) {
                    IDType.NULL.name -> {
                        listener.onSuccess(visionText.text, null)
                    }

                    else -> {
                        val data = parse(visionText.text, idType)
                        listener.onSuccess(visionText.text, data)
                    }
                }
            }
            .addOnFailureListener { e ->
                listener.onError()
            }
    }

    /**
     * 证件图像解析 基于 Bitmap 对象创建 InputImage 对象
     * @param bitmap bitmap
     * @param rotation 图片旋转角度
     * @param idType 证件类型
     * @param listener 处理回调
     */
    fun IDImageProcess(
        bitmap: Bitmap,
        rotation: Int = 0,
        idType: IDType,
        listener: OnRecognizeListener
    ) {
        val inputImage = InputImage.fromBitmap(bitmap, rotation)
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                when (idType.name) {
                    IDType.NULL.name -> {
                        listener.onSuccess(visionText.text, null)
                    }

                    else -> {
                        val data = parse(visionText.text, idType)
                        listener.onSuccess(visionText.text, data)
                    }
                }
            }
            .addOnFailureListener { e ->
                listener.onError()
            }
    }

    /**
     * 证件图像解析 基于文件 URI 创建 InputImage 对象
     * @param uri Uri
     * @param context 应用上下文
     * @param idType 证件类型
     * @param listener 处理回调
     */
    fun IDImageProcess(
        context: Context,
        uri: Uri,
        idType: IDType,
        listener: OnRecognizeListener
    ) {
        val inputImage: InputImage
        try {
            inputImage = InputImage.fromFilePath(context, uri)
            recognizer.process(inputImage)
                .addOnSuccessListener { visionText ->
                    when (idType.name) {
                        IDType.NULL.name -> {
                            listener.onSuccess(visionText.text, null)
                        }

                        else -> {
                            val data = parse(visionText.text, idType)
                            listener.onSuccess(visionText.text, data)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    listener.onError()
                }
        } catch (e: IOException) {
            e.printStackTrace()
            listener.onError()
        }

    }

    /**
     * 证件图像解析 基于 ByteBuffer 或 ByteArray 创建 InputImage 对象
     * @param byteBuffer 缓冲区
     * @param width 宽度
     * @param height 高度
     * @param rotation 旋转角度、
     * @param format 图片格式
     * @param idType 证件类型
     * @param listener 处理回调
     */
    fun IDImageProcess(
        byteBuffer: ByteBuffer,
        width: Int,
        height: Int,
        rotation: Int = 0,
        @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
        idType: IDType,
        listener: OnRecognizeListener
    ) {
        val inputImage: InputImage =
            InputImage.fromByteBuffer(byteBuffer, width, height, rotation, format)
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                when (idType.name) {
                    IDType.NULL.name -> {
                        listener.onSuccess(visionText.text, null)
                    }

                    else -> {
                        val data = parse(visionText.text, idType)
                        listener.onSuccess(visionText.text, data)
                    }
                }
            }
            .addOnFailureListener { e ->
                listener.onError()
            }
    }

    /**
     * 证件图像解析 基于 ByteBuffer 或 ByteArray 创建 InputImage 对象
     * @param byteArray byte数组
     * @param width 宽度
     * @param height 高度
     * @param rotation 旋转角度、
     * @param format 图片格式
     * @param idType 证件类型
     * @param listener 处理回调
     */
    fun IDImageProcess(
        byteArray: ByteArray,
        width: Int,
        height: Int,
        rotation: Int = 0,
        @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
        idType: IDType,
        listener: OnRecognizeListener
    ) {
        val inputImage: InputImage =
            InputImage.fromByteArray(byteArray, width, height, rotation, format)
        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                when (idType.name) {
                    IDType.NULL.name -> {
                        listener.onSuccess(visionText.text, null)
                    }

                    else -> {
                        val data = parse(visionText.text, idType)
                        listener.onSuccess(visionText.text, data)
                    }
                }
            }
            .addOnFailureListener { e ->
                listener.onError()
            }
    }


    /**
     * @param ocrText ocr提取的字符串
     * @param idType 类型
     */
    private fun parse(ocrText: String, idType: IDType): IDImageTextRecognizerModel {
        return when (idType.name) {
            IDType.CHINA_EEP.name -> {
                EEPHelper.parse(ocrText)
            }

            IDType.PASSPORT.name -> {
                PassportHelper.parse(ocrText)
            }

            IDType.HK_ID_NEW.name -> {
                HKIDHelper.parse(ocrText)
            }

            IDType.HK_ID_OLD.name -> {
                HKIDHelper.parse(ocrText)
            }

            IDType.MACAU_ID_NEW.name -> {
                MacauIDHelper.parse(ocrText, type = 1)
            }

            IDType.MACAU_ID_OLD.name -> {
                MacauIDHelper.parse(ocrText, type = 2)
            }

            else -> {
                IDImageTextRecognizerModel()
            }
        }
    }

}

/**
 * 港澳通行证解析
 */
private object EEPHelper {
    //解析
    fun parse(ocrText: String): IDImageTextRecognizerModel {
        val lines = ocrText.replace("港澳通行证", "").split("\n")
        val recognizerModel = IDImageTextRecognizerModel()
        // 国籍
        recognizerModel.nationality = "CHN"
        // 解析中文姓名（兼容OCR错误）
        val chineseName = lines.find { it.matches(Regex("[\u4e00-\u9fa5]{2,4}")) } ?: ""
        recognizerModel.chineseName = chineseName
        // 解析英文姓名（全大写+逗号格式）
        val enNameRegex1 = Pattern.compile("^[A-Z][A-Z ]+,[A-Z ]+\$")
        val enNameRegex2 = Pattern.compile("^[A-Z][A-Z ]+，[A-Z ]+\$")
        val englishName =
            lines.find { enNameRegex1.matcher(it).matches() || enNameRegex2.matcher(it).matches() }
                ?: ""
        if (!TextUtils.isEmpty(englishName)) {
            recognizerModel.englishName = englishName
            var englishList = englishName.split(",")
            if (englishList.isEmpty()) englishList = englishName.split("，")
            recognizerModel.firstName = englishList.last()
            recognizerModel.lastName = englishList.first()
        }
        // 提取证件号
        val idNumberRegex = Pattern.compile("^C[A-Z0-9]{1}[0-9]{7}\$")
        var eepID = lines.find { idNumberRegex.matcher(it).matches() } ?: ""
        if (eepID.length > 12) {
            eepID = eepID.substring(3, 12).replace("I", "1").replace("O", "0")
        }
        recognizerModel.idNumber = eepID
        // 4. 提取出生日期/有效期
        val datePattern = "\\d{4}[.-]\\d{2}[.-]\\d{2}".toRegex()
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val dateList = datePattern.findAll(ocrText)
            .map { sdf.parse(it.value) }
            .toList()
        recognizerModel.birthday = dateList.minByOrNull { it.time }?.let { sdf.format(it) } ?: "N/A"
        recognizerModel.idExpiryDate =
            dateList.maxByOrNull { it.time }?.let { sdf.format(it) } ?: "N/A"
        // 提取性别（男/女）
        val genderFilter = lines.filter { it == "男" || it == "女" }
        if (genderFilter.isNotEmpty())
            recognizerModel.sex = if (genderFilter[0] == "男") "M" else "F"
        return recognizerModel
    }

}

/**
 * 护照解析
 */
private object PassportHelper {

    fun parse(ocrText: String): IDImageTextRecognizerModel {
        val lines = ocrText.replace(" ", "").replace("中华人民共和国", "").split("\n")
        val recognizerModel = IDImageTextRecognizerModel()
        //正则筛选
        val filterRegex1 = Pattern.compile("^[A-Z0-9<]{44}\$")
        val filter =
            lines.filter {
                filterRegex1.matcher(it).find() || (it.length > 20 && it.contains("<<<"))
            }
        if (filter.isEmpty()) {
            return recognizerModel
        }
        //解析中文姓名（兼容OCR错误）
        var cnName = lines.find { it.matches(Regex("[\u4e00-\u9fa5]{2,4}")) } ?: ""
        recognizerModel.chineseName = cnName
        if (filter.size > 1) {
            val mrzLine1 = filter[0].trim()
            val mrzLine2 = filter[1].trim()
            //国籍
            recognizerModel.nationality = parseNationality(mrzLine2)
            //英文名
            val engNameList = parseEnglishName(mrzLine1)
            recognizerModel.firstName = engNameList[1]
            recognizerModel.lastName = engNameList[0]
            recognizerModel.englishName = "${engNameList[1]},${engNameList[0]}".trim()
            //证件号
            recognizerModel.idNumber = parseDocumentNumber(mrzLine2)
            //出生日期
            recognizerModel.birthday = parseBirthDate(mrzLine2)
            //有效日期
            recognizerModel.idExpiryDate = parseExpiryDate(mrzLine2)
            //性别
            recognizerModel.sex = parseGender(mrzLine2)
        }
        return recognizerModel
    }

    private fun parseEnglishName(mrzLine1: String): List<String> {
        val parts = mrzLine1.split("<<").filter { it.isNotBlank() }
        val surname = parts.getOrNull(0)?.replace("<", "") ?: ""
        val givenName = parts.getOrNull(1)?.replace("<", "") ?: ""
        val strings = mutableListOf(surname, givenName)
        return strings
    }

    private fun parseDocumentNumber(line2: String): String {
        val cleaned = line2.replace(" ", "")
        return cleaned.take(9).trimEnd('<').replace("I", "1").replace("O", "0")
    }

    private fun parseNationality(line2: String): String {
        val cleaned = line2.replace(" ", "")
        return cleaned.substring(10, 13)
    }

    private fun parseBirthDate(line2: String): String {
        val cleaned = line2.replace(" ", "")
        return parseYYMMDD(cleaned.substring(13, 19))
    }

    private fun parseGender(line2: String): String {
        val cleaned = line2.replace(" ", "")
        return cleaned.substring(20, 21)
    }

    private fun parseExpiryDate(line2: String): String {
        val cleaned = line2.replace(" ", "")
        return parseYYMMDD(cleaned.substring(21, 27))
    }

    private fun parseYYMMDD(yymmdd: String): String {
        if (yymmdd.length != 6) return "Invalid date"
        val year =
            if (yymmdd.take(2).toInt() <= 21) "20${yymmdd.take(2)}" else "19${yymmdd.take(2)}"
        val month = yymmdd.substring(2, 4)
        val day = yymmdd.substring(4, 6)
        return "$year-$month-$day"
    }
}

private object HKIDHelper {

    fun parse(ocrText: String): IDImageTextRecognizerModel {
        val lines = ocrText.replace(" ", "").split("\n")
        val recognizerModel = IDImageTextRecognizerModel()
        //中文名
        recognizerModel.chineseName = parseChineseName(lines)
        //国籍
        recognizerModel.nationality = "HKG"
        //英文名
        val engName = parseEnglishName(lines)
        if (!TextUtils.isEmpty(engName)) {
            var engList = engName.split(",")
            if (engList.size < 2) {
                engList = engName.split("，")
            }
            if (engList.size < 2) {
                engList = engName.split("-")
            }
            if (engList.size > 1) {
                recognizerModel.firstName = engList[1]
                recognizerModel.lastName = engList[0]
            }
        }
        recognizerModel.englishName = engName
        //证件号
        recognizerModel.idNumber = parseIDNumber(lines)
        //出生日期
        recognizerModel.birthday = parseBirthDate(ocrText)
        //有效日期
        recognizerModel.idExpiryDate = parseExpiryDate(ocrText)
        //性别
        recognizerModel.sex = parseGender(lines)
        return recognizerModel
    }

    private fun parseIDNumber(lines: List<String>): String {
        return lines.firstOrNull {
            it.replace(" ", "").matches(Regex("^[A-Z]{1,2}\\d{6}\\(?[A-Z0-9]\\)?\$"))
        }?.replace(" ", "") ?: ""
    }

    private fun parseGender(lines: List<String>): String {
        val genderLine = lines.find { it.contains("女F") || it.contains("男M") }
        return genderLine?.let {
            if (it.contains("女")) "F" else "M"
        } ?: "M"
    }

    private fun parseChineseName(lines: List<String>): String {
        val cnName = lines.find {
            it.length < 5 && it.replace("n", "").replace("h", "")
                .matches(Regex("^[\u4e00-\u9fa5]{2,4}$"))
        } ?: ""
        return if (!TextUtils.isEmpty(cnName)) {
            cnName.replace("n", "").replace("h", "")
        } else {
            ""
        }
    }

    private fun parseEnglishName(lines: List<String>): String {
        val regex1 = "^[A-Z][A-Za-z ]+,[A-Za-z ]+\$"
        val regex2 = "^[A-Z][A-Za-z ]+，[A-Za-z ]+\$"
        val regex3 = "^[A-Z][A-Za-z ]+-[A-Za-z ]+\$"
        var engName = lines.find { it.matches(Regex(regex1)) } ?: ""
        if (TextUtils.isEmpty(engName)) {
            engName = lines.find { it.matches(Regex(regex2)) } ?: ""
        }
        if (TextUtils.isEmpty(engName)) {
            engName = lines.find { it.matches(Regex(regex3)) } ?: ""
        }
        return engName
    }

    private fun parseBirthDate(rawText: String): String {
        val birthStr = Regex("\\d{2}-\\d{2}-\\d{4}").find(rawText)?.value ?: ""
        return birthStr.replace("O", "0").replace("I", "1")
    }

    private fun parseExpiryDate(rawText: String): String {
        return Regex("\\d{2}-\\d{2}-\\d{2}").findAll(rawText).last().value.let {
            val year =
                if (it.substring(6).toInt() <= 21)
                    "20${it.substring(6)}"
                else
                    "19${it.substring(6)}"
            "${it.substring(0, 2)}-${it.substring(3, 5)}-$year"
        }
    }

}

private object MacauIDHelper {
    private const val TAG = "MacauIDHelper"

    /**
     * @param type 1:新版证件  2：老版证件
     */
    fun parse(ocrText: String, type: Int = 1): IDImageTextRecognizerModel {
        val lines = ocrText.replace(" ", "").split("\n")
        val recognizerModel = IDImageTextRecognizerModel()
        // 1. 提取中文姓名（姓氏 + 名字）
        recognizerModel.chineseName =
            if (type == 1) {
                parseNewChineseName(ocrText)
            } else {
                parseOldChineseName(ocrText)
            }
        // 2. 提取葡萄牙文姓名（Apelido + Nome）
        if (type == 1) {
            val stringList = parseNewEnglishName(ocrText)
            recognizerModel.englishName = "${stringList[0]},${stringList[1]}"
            recognizerModel.firstName = stringList[1]
            recognizerModel.lastName = stringList[0]
        } else {
            val englishName = parseOldEnglishName(ocrText)
            recognizerModel.englishName = englishName
            if (!TextUtils.isEmpty(englishName)) {
                var split = englishName.split(",")
                if (split.size < 2) split = englishName.split("，")
                recognizerModel.firstName = split[1]
                recognizerModel.lastName = split[0]
            }
        }

        // 3. 提取证件号（格式：7位数字 + (校验码)）
        val idPattern = Pattern.compile("\\d{7}\\(\\d\\)")
        val idMatcher = idPattern.matcher(ocrText)
        recognizerModel.idNumber = if (idMatcher.find()) idMatcher.group() else "N/A"

        // 4. 提取出生日期（格式 dd-MM-yyyy）
        val pattern = Pattern.compile("^[0-9]{2}[-.]{1}[0-9]{2}[-.]{1}[0-9]{4}\$")
        val dateList = lines.filter { pattern.matcher(it).find() }
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val parsedDates = dateList.map { sdf.parse(it) }

        recognizerModel.birthday =
            parsedDates.minByOrNull { it.time }?.let { sdf.format(it) } ?: "N/A"

        // 5. 提取有效期（格式 dd-MM-yyyy）
        recognizerModel.idExpiryDate =
            parsedDates.maxByOrNull { it.time }?.let { sdf.format(it) } ?: "N/A"

        // 6. 提取性别（男/女）
        val genderPattern = Pattern.compile("[男女]")
        val genderMatcher = genderPattern.matcher(ocrText)
        recognizerModel.sex = if (genderMatcher.find()) {
            if (genderMatcher.group().contains("男")) "M" else "F"
        } else {
            val genderFilter =
                lines.filter { it.length <= 4 && (it.contains("F") || it.contains("M")) }
            if (genderFilter.isEmpty())
                "N/A"
            else {
                if (genderFilter[0].contains("M")) "M" else "F"
            }
        }
        recognizerModel.nationality = "MAC"

        return recognizerModel
    }

    private fun parseNewChineseName(ocrText: String): String {
        val lines = ocrText.replace("澳門永久性居民身份證", "").split("\n")
        var surname = ""
        var givenName = ""
        val chinesePattern = Regex("^[\u4e00-\u9fa5]+\$")
        lines.forEach { line ->
            chinesePattern.find(line)?.let {
                when {
                    line.startsWith("Name") -> givenName = it.value
                    it.value.length == 1 -> surname = it.value
                    else -> if (givenName.isEmpty()) givenName = it.value
                }
            }
        }
        return "$surname,$givenName"
    }

    fun parseOldChineseName(ocrText: String): String {
        val chineseName = Regex("""([\u4e00-\u9fa5]+)[,\n]?([\u4e00-\u9fa5]+)""")
            .find(ocrText)?.value ?: ""
        return chineseName
    }

    fun parseNewEnglishName(ocrText: String): List<String> {
        val lines = ocrText.split("\n")
        val surname =
            Regex("(?:Apelido|Apial\\s*ido)\\s*([A-Z]{2,})").find(ocrText)?.groupValues?.get(1)
                ?.trim()
                ?: ""
        val givenName = lines.firstOrNull { it.matches(Regex("[A-Z]+\\s[A-Z]+")) } ?: ""
        return mutableListOf(surname, givenName)
    }

    fun parseOldEnglishName(ocrText: String): String {
        // 优化正则表达式匹配模式
        val namePattern1 = "^[A-Z][A-Za-z ]+，[A-Za-z ]+$".toRegex(RegexOption.MULTILINE)
        val namePattern2 = "^[A-Z][A-Za-z ]+,[A-Za-z ]+$".toRegex(RegexOption.MULTILINE)
        val namePattern3 = "^[A-Z][A-Za-z ]+-[A-Za-z ]+$".toRegex(RegexOption.MULTILINE)
        // 按行处理文本
        return ocrText.lineSequence()
            .map { it.trim() }
            .filter { line ->
                // 排除明显非姓名的行
                line.length in 5..30 &&
                        !line.contains(Regex("\\d")) && // 排除包含数字的行
                        (namePattern1.matches(line) || namePattern2.matches(line) || namePattern3.matches(
                            line
                        ))
            }
            .firstOrNull()
            ?.replace(Regex("\\s+"), " ")  // 标准化空格
            ?: ""
    }
}
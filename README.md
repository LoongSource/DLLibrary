# LoongSource Android Common OCR

## Download

Gradle:

```groovy

implementation("com.github.LoongSource:DLLibrary:1.0.5")
```

## About

    封装了Google ML Kit 文字识别，并添加了香港、澳门、港澳通行证、护照等证件的图片解析，并返回IDImageTextRecognizerModel对象

## APIs

* ### IDImageTextRecognizer 主要方法

```

IDImageProcess          :根据传入的图片解析出数据
```

* ### IDType 卡包类型

```
IDType.CHINA_EEP       :港澳通行证
IDType.PASSPORT        :护照
IDType.HK_ID_NEW       :香港新版身份证
IDType.HK_ID_OLD       :香港旧版身份证
IDType.MACAU_ID_NEW    :澳门新版身份证
IDType.MACAU_ID_OLD    :澳门旧版身份证
```

* ### IDImageTextRecognizerModel 返回的数据模型

```
nationality            :国籍
chineseName            :中文名
englishName            :英文名
firstName              :英文名-名字
lastName               :英文名-姓氏
idNumber               :证件ID
sex                    :性别（F:女 M:男）
birthday               :出生日期
idExpiryDate           :证件有效日期
```

* ### OnRecognizeListener

```
onSuccess              :成功回调
onError                :失败回调
```

## 使用方法

* ### Kotlin 使用

```
IDImageTextRecognizer.IDImageProcess(
   image: Image,
   rotation: Int = 0,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.IDImageProcess(
   bitmap: Bitmap,
   rotation: Int = 0,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.IDImageProcess(
   context: Context,
   uri: Uri,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.IDImageProcess(
   byteBuffer: ByteBuffer,
   width: Int,
   height: Int,
   rotation: Int = 0,
   @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.IDImageProcess(
   byteArray: ByteArray,
   width: Int,
   height: Int,
   rotation: Int = 0,
   @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
   idType: IDType,
   listener: OnRecognizeListener
)
```

* ### Java 使用

```
IDImageTextRecognizer.INSTANCE.IDImageProcess(
   image: Image,
   rotation: Int = 0,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.INSTANCE.IDImageProcess(
   bitmap: Bitmap,
   rotation: Int = 0,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.INSTANCE.IDImageProcess(
   context: Context,
   uri: Uri,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.INSTANCE.IDImageProcess(
   byteBuffer: ByteBuffer,
   width: Int,
   height: Int,
   rotation: Int = 0,
   @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
   idType: IDType,
   listener: OnRecognizeListener
)
IDImageTextRecognizer.INSTANCE.IDImageProcess(
   byteArray: ByteArray,
   width: Int,
   height: Int,
   rotation: Int = 0,
   @InputImage.ImageFormat format: Int = InputImage.IMAGE_FORMAT_NV21,
   idType: IDType,
   listener: OnRecognizeListener
)
```





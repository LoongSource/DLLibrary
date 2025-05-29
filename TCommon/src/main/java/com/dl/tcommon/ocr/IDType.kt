package com.dl.tcommon.ocr

/**
 * @Author: DL
 * @Date: 2025/5/28 16:13
 * @Description: 卡包类型
 */
enum class IDType {
    CHINA_EEP,//港澳通行证
    PASSPORT,//护照
    HK_ID_NEW,//香港新版身份证
    HK_ID_OLD,//香港旧版身份证
    MACAU_ID_NEW,//澳门新版身份证
    MACAU_ID_OLD,//澳门旧版身份证
    NULL//未知卡包
}
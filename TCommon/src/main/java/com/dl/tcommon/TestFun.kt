package com.dl.tcommon

import android.content.Context
import android.widget.Toast

/**
 * @Author: DL
 * @Date: 2025/5/27 17:33
 * @Description:
 */
object TestFun {
    fun test(context: Context): String {
        Toast.makeText(context,"调用Kotlin代码成功",Toast.LENGTH_SHORT).show()
        return "测试test"
    }
}
package com.gaoxianglong.filepersistencetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = load()
        if (data.isNotEmpty()) {
            editText.setText(data)
            editText.setSelection(data.length) // 将光标移到数据最后的位置
        }
    }

    /**
     * 在应用被回收或者关闭时
     * 将输入框中的数据保存下来
     */
    override fun onDestroy() {
        super.onDestroy()
        val inputText = editText.text.toString()
        save(inputText)
    }

    /**
     * 保存数据到文件
     */
    private fun save(inputText:String) {
        try {
            val output = openFileOutput("data", Context.MODE_PRIVATE) // 第一个参数是文件名，第二个参数是保存模式，默认的就是这种，还有一种是MODE_APPEND
            val writer = BufferedWriter(OutputStreamWriter(output)) // 创建一个BufferedWriter用于向文件中写入内容
            // use函数能够在使用完后关闭外层的流
            writer.use {
                it.write(inputText) // 写入
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 读取文件中的数据
     */
    fun load():String{
        val sb = StringBuilder()
        try {
            val input = openFileInput("data") // 文件名字
            val reader = BufferedReader(InputStreamReader(input)) // 创建一个BufferedReader用于读取文件内容
            // use函数能够在使用完后关闭外层的流
            reader.use {
                // forEachLine一行一行的读取
                reader.forEachLine {
                    sb.append(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}

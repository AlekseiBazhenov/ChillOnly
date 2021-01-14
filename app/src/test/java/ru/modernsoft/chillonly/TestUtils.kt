package ru.modernsoft.chillonly

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream


object TestUtils {

    fun getJsonString(fileName: String): String {
        return try {
            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            val input: InputStream = this::class.java.classLoader.getResourceAsStream(fileName)
            val bytes = ByteArray(input.available())
            input.read(bytes, 0, bytes.size)
            return String(bytes)
        } catch (e: IOException) {
            ""
        }
    }

    inline fun <reified T : Any> String?.fromJson(): T? = this?.let {
        val type = object : TypeToken<T>() {}.type
        Gson().fromJson(this, type)
    }


}
package com.frontmatic.scrobbleview.data

import androidx.room.TypeConverter
import com.frontmatic.scrobbleview.data.model.Image

class DatabaseConverter {

    private val separator = ","

    private val imageSeparator = "||"
    private val imageInnerSeparator = "---"

    @TypeConverter
    fun convertListToStrings(list: List<String>): String {
        return list.joinToString(separator)
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }

    @TypeConverter
    fun convertListToImages(list: List<Image>): String {
        return list.joinToString(imageSeparator) {
            "${it.size}${imageInnerSeparator}${it.url}"
        }
    }

    @TypeConverter
    fun convertStringToImages(string: String): List<Image> {
        return string.split(imageSeparator).map {
            val (size, url) = it.split(imageInnerSeparator)
            Image(size, url)
        }
    }

}
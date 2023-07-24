package com.frontmatic.scrobbleview.data

import androidx.room.TypeConverter
import com.frontmatic.scrobbleview.data.model.Image
import com.frontmatic.scrobbleview.data.model.Registered
import com.frontmatic.scrobbleview.data.model.TopTrackArtist
import com.frontmatic.scrobbleview.data.model.TrackAlbum
import com.frontmatic.scrobbleview.data.model.TrackArtist
import com.frontmatic.scrobbleview.data.model.TrackDate

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

    @TypeConverter
    fun convertArtisToString(artist: TrackArtist): String {
        return "${artist.name}${imageInnerSeparator}${artist.mbid}"
    }

    @TypeConverter
    fun convertStringToArtist(string: String): TrackArtist {
        val (name, mbid) = string.split(imageInnerSeparator)
        return TrackArtist(name, mbid)
    }

    @TypeConverter
    fun convertTopTrackArtistToString(artist: TopTrackArtist): String {
        return "${artist.name}${imageInnerSeparator}${artist.mbid}${imageInnerSeparator}${artist.url}"
    }

    @TypeConverter
    fun convertStringToTopTrackArtist(string: String): TopTrackArtist {
        val (name, mbid, url) = string.split(imageInnerSeparator)
        return TopTrackArtist(name, mbid, url)
    }

    @TypeConverter
    fun convertAlbumToString(album: TrackAlbum): String {
        return "${album.name}${imageInnerSeparator}${album.mbid}"
    }

    @TypeConverter
    fun convertStringToAlbum(string: String): TrackAlbum {
        val (name, mbid) = string.split(imageInnerSeparator)
        return TrackAlbum(name, mbid)
    }

    @TypeConverter
    fun convertDateToString(date: TrackDate?): String {
        return if (date != null) "${date.uts}${imageInnerSeparator}${date.text}" else ""
    }

    @TypeConverter
    fun convertStringToDate(string: String): TrackDate? {
        if (string.isEmpty()) {
            return null
        }
        val (uts, text) = string.split(imageInnerSeparator)
        return TrackDate(uts.toLong(), text)
    }

    @TypeConverter
    fun convertRegisteredToString(registered: Registered): String {
        return "${registered.unixtime}${imageInnerSeparator}${registered.text}"
    }

    @TypeConverter
    fun convertStringToRegistered(string: String): Registered {
        val (unixtime, text) = string.split(imageInnerSeparator)
        return Registered(unixtime.toLong(), text)
    }

}
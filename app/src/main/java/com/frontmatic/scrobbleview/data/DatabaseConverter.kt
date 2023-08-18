package com.frontmatic.scrobbleview.data

import androidx.room.TypeConverter
import com.frontmatic.scrobbleview.data.model.Artist
import com.frontmatic.scrobbleview.data.model.Image
import com.frontmatic.scrobbleview.data.model.Registered
import com.frontmatic.scrobbleview.data.model.Streamable
import com.frontmatic.scrobbleview.data.model.TrackAlbum
import com.frontmatic.scrobbleview.data.model.TrackArtist
import com.frontmatic.scrobbleview.data.model.TrackDate
import com.frontmatic.scrobbleview.data.model.TrackInfoAlbum
import com.frontmatic.scrobbleview.data.model.TrackTag
import com.frontmatic.scrobbleview.data.model.Wiki

class DatabaseConverter {

//    private val separator = ","


    private val separator = "||"
    private val innerSeparator = "---"

//    @TypeConverter
//    fun convertListToStrings(list: List<String>): String {
//        return list.joinToString(separator)
//    }
//
//    @TypeConverter
//    fun convertStringToList(string: String): List<String> {
//        return string.split(separator)
//    }

    @TypeConverter
    fun convertListToImages(list: List<Image>): String {
        return list.joinToString(separator) {
            "${it.size}${innerSeparator}${it.url}"
        }
    }

    @TypeConverter
    fun convertStringToImages(string: String): List<Image> {
        return string.split(separator).map {
            val (size, url) = it.split(innerSeparator)
            Image(
                size=size,
                url=url
            )
        }
    }

    @TypeConverter
    fun convertTrackArtistToString(artist: TrackArtist): String {
        return "${artist.name}${innerSeparator}${artist.mbid}"
    }

    @TypeConverter
    fun convertStringToTrackArtist(string: String): TrackArtist {
        val (name, mbid) = string.split(innerSeparator)
        return TrackArtist(
            name=name,
            mbid=mbid
        )
    }

    @TypeConverter
    fun convertTopTrackArtistToString(artist: Artist): String {
        return "${artist.name}${innerSeparator}${artist.mbid}${innerSeparator}${artist.url}"
    }

    @TypeConverter
    fun convertStringToTopTrackArtist(string: String): Artist {
        val (name, mbid, url) = string.split(innerSeparator)
        return Artist(
            name=name,
            mbid=mbid,
            url=url
        )
    }

    @TypeConverter
    fun convertAlbumToString(album: TrackAlbum): String {
        return "${album.name}${innerSeparator}${album.mbid}"
    }

    @TypeConverter
    fun convertStringToAlbum(string: String): TrackAlbum {
        val (name, mbid) = string.split(innerSeparator)
        return TrackAlbum(
            name=name,
            mbid=mbid
        )
    }

    @TypeConverter
    fun convertTrackInfoAlbumToString(album: TrackInfoAlbum): String {
        return "${album.artist}${innerSeparator}${convertListToImages(album.image)}${innerSeparator}${album.mbid}${innerSeparator}${album.title}${innerSeparator}${album.url}"
    }

    @TypeConverter
    fun convertStringToTrackInfoAlbum(string: String): TrackInfoAlbum {
        val (artist, images, mbid, title, url) = string.split(innerSeparator)
        return TrackInfoAlbum(
            artist=artist,
            image=convertStringToImages(images),
            mbid=mbid,
            title=title,
            url=url
        )
    }

    @TypeConverter
    fun convertWikiToString(wiki: Wiki): String {
        return "${wiki.content}${innerSeparator}${wiki.published}${innerSeparator}${wiki.summary}"
    }


    @TypeConverter
    fun convertStringToWiki(string: String): Wiki {
        val (content, published, summary) = string.split(innerSeparator)
        return Wiki(
            content=content,
            published=published,
            summary=summary
        )
    }

    @TypeConverter
    fun convertTrackTagsToString(tags: List<TrackTag>): String {
        return tags.joinToString(separator) {
            "${it.name}${innerSeparator}${it.url}"
        }
    }

    @TypeConverter
    fun convertStringToTrackTags(string: String): List<TrackTag> {
        return string.split(separator).map {
            val (name, url) = it.split(innerSeparator)
            TrackTag(
                name=name,
                url=url
            )
        }
    }

    @TypeConverter
    fun convertStreamableToString(streamable: Streamable): String {
        return "${streamable.fulltrack}${innerSeparator}${streamable.text}"
    }
    @TypeConverter
    fun convertStringToStreamable(string: String): Streamable {
        val (fulltrack, text) = string.split(innerSeparator)
        return Streamable(
            fulltrack=fulltrack,
            text=text
        )
    }

    @TypeConverter
    fun convertDateToString(date: TrackDate?): String {
        return if (date != null) "${date.uts}${innerSeparator}${date.text}" else ""
    }

    @TypeConverter
    fun convertStringToDate(string: String): TrackDate? {
        if (string.isEmpty()) {
            return null
        }
        val (uts, text) = string.split(innerSeparator)
        return TrackDate(
            uts=uts.toLong(),
            text=text
        )
    }

    @TypeConverter
    fun convertRegisteredToString(registered: Registered): String {
        return "${registered.unixtime}${innerSeparator}${registered.text}"
    }

    @TypeConverter
    fun convertStringToRegistered(string: String): Registered {
        val (unixtime, text) = string.split(innerSeparator)
        return Registered(
            unixtime=unixtime.toLong(),
            text=text
        )
    }

}
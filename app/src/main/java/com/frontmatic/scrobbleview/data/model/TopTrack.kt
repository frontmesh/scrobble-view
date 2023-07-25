package com.frontmatic.scrobbleview.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
@Entity(tableName = "top_tracks")
data class TopTrack(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String,
    val name: String,
    val duration: Int,
    val playcount: Int,
    val image: List<Image>,

    val period: RequestPeriod,

    val artist: TopTrackArtist
) {
    val durationString: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val duration = Duration.ofSeconds(duration.toLong())
            return "${duration.toMinutes()}m ${duration.seconds}s"
        }

    val largeImage: String
        get() = image.find { it.size == ImageSize.LARGE.value }?.url ?: ""
}


data class TopTrackArtist(
    val name: String,
    val mbid: String,
    val url: String
)

# scrobble-view

Scrobble-View (development title) is last.fm music tracking and browsing client application. 
It is designed to be a simple and easy to use application for tracking your music listening habits.
It is also designed to be a simple and easy to use application for browsing your music library.


## Features

- [x] Browse your recently listened tracks
- [x] Browse your top tracks by period
- [ ] Browse your top artists by period
- [x] View your profile
- [x] View your friends
- [ ] View your loved tracks
- [ ] Play selected tracks on spotify
- [ ] Play selected tracks on youtube

## API Keys
Last.fm API key is required to run this application. You can get one [here](https://www.last.fm/api/account/create).
You can set your API key in `secrets.properties` file.

## Architecture and libraries
-Dependency Injection: [Dagger Hilt](https://dagger.dev/hilt/)
-UI Toolkit: [Jetpack Compose](https://developer.android.com/jetpack/compose)
-Material Design 3: [Material3](https://developer.android.com/jetpack/compose/material)
-Image Loading: [Coil](https://github.com/coil-kt/coil)
-Navigation: [Compose Destinations](https://github.com/raamcosta/compose-destinations)
-Pagination: [Jetpack Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
-SQLite: [Jetpack Room](https://developer.android.com/topic/libraries/architecture/room)
-HTTP Client: [Retrofit](https://square.github.io/retrofit/)


## Development notes

During development of this app I have stumbled upon a lot of similar mobile apps utilizing the last.fm api. 
I have decided to list them here as a reference for myself and others. Let me know if you are the owner of any of these apps and want me to remove it from the list.
Also let me know if you want to have your app added to the list (for some reason :))

- https://github.com/nrubin29/finale - Finale - Dart/Flutter
- https://github.com/Sh4dowSoul/Compose-Scrobbler - Compose Scrobbler - Kotlin/Compose
- https://github.com/mataku/SunsetScrob - SunsetScrob - Kotlin/Compose
- https://github.com/kawaiiDango/pScrobbler - pScrobbler - Java/Kotlin 


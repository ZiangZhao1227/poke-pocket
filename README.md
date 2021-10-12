# PokePocket [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
PokePocket application is based on the Kotlin language and will call the Pokemon API to display information about all the pokemon. Each pokemon's background color based on the dominant color of the pokemon in a particular view. We are also implementing a map feature where users can catch pokemon by collecting pokemon balls on the map. We will also develop an AR feature where users can interact with the poke balls on the map.

## If you want to run this app
- You need to generate your own Google API Key in order to use the Google Maps feature, all you need to do is go to [for Google API Key](https://console.cloud.google.com/home), log in or create a Gmail account and follow the instructions there.
- Also you need to install Google Play Services, you can find it by going to SDK Manager and selecting SDK tools in Android Studio, it will show the list of tools that u can install, scroll to find it.

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct lightweight http API response and handling error responses.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [TransformationLayout](https://github.com/skydoves/transformationlayout) - implementing transformation motion animations.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Custom Views
  - [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView, fully customizable with animations.


## Architecture
PokePocket is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## App preview
<img src="https://user-images.githubusercontent.com/56063269/136838912-a928a71d-96be-4206-a37f-1901294eff58.png" width="300"> <img src="https://user-images.githubusercontent.com/56063269/136839892-dde5036c-f454-42c3-9848-2787afaaf81d.png" width="300"> <img src="https://user-images.githubusercontent.com/56063269/136839960-827fa4eb-1f53-41fa-ac77-dbf70c8ae21a.png" width="300"> <img src="https://user-images.githubusercontent.com/56063269/136841299-11a5ec19-6b64-432f-bbc3-534766736274.png" width="300">

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

PokePocket using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.

# License
```xml
Designed and developed by 2021 Ziang Zhao and Maksim Pasnitsenko.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.






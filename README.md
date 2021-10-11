# PokePocket [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
PokePocket application is based on the Kotlin language and will call the Pokemon API to display information about all the pokemon. Each pokemon's background color based on the dominant color of the pokemon in a particular view. We are also implementing a map feature where users can catch pokemon by collecting pokemon balls on the map. We will also develop an AR feature where users can interact with the poke balls on the map.
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

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

PokePocket using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.



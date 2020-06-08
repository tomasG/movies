# movies
MoviesApp

Este proyecto utiliza
    - MVVM
    - LiveData
    - ViewModel
    - Koin para DI
    - Rxjava
    - Retrofit
    - OkHttp para cache y para incrustar el API_KEY de la libreria de peliculas
    - YoutubePlayerFragment
    - Picasso para las imagenes
    - Mockito
    - ...

El unico feature que quedo pendiente fue el de el lazy loading de los elementos paginados
- Para concluir este es necesario:
   -- Agregar un listener al recycler view
   -- Tan pronto se muestre el ultimo item, pedir la carga de la siguiente pagina
   -- Actualizar el adapter con los nuevos registros (No borrar los existentes)
   -- Notificar al adapter que hay nuevos registros



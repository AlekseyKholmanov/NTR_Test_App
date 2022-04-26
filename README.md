# NTRApp

## Stack:
- Single Activity
- MVVM | MVI (with one state for each Screen)
- Koin for DI
- Network: GSON, Retrofit, OkHttp
- Coroutine for async work
- Navigation : Android Navigation

## Comments:
- For List with data, I used a way to have one adapter which can work with any item type. I implemented it with default component but it is similar to library AdapterDelegates or other.
Also i think that this patter it is the best patter for screens with multiple data, because it is easy to add new type of data to screens


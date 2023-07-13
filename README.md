
# Weather App

## Build tools & versions used
- Gradle: 7.4.1
- Compile/Target SDK: 33
- Min SDK: 24
- Android Studio Version: 2022.1.1 Patch 1

## Steps to run the app
1. git clone
2. Open project in Android Studio
3. Sync Gradle and build project
4. Create "secrets.properties" in the root folder of the project.  Add `OPEN_WEATHER_API_KEY` in the following format: `OPEN_WEATHER_API_KEY = "YOUR_OPEN_WEATHER_API_KEY"`
5. Run project on Android emulator or Android device

## Area of focus
I chose to prioritize the architecture and structure of my application by implementing the MVVM design pattern.   
Employing MVVM architecture in this instance results in an app that is more maintainable, extensible, and easier to test. It provides a clear separation of concerns, enables easier unit testing, and facilitates code reuse across multiple views and screens.

## Enhancements
I would have liked to improve the user interface of the app had I been given more time. I would like to add more weather data on the screen.

##  Dependencies used
These libraries are widely used in the developers community and being continuously maintained and improved. Also, these libraries provide great Kotlin support which makes it easy to use for a Kotlin heavy app
- [Moshi](https://github.com/square/moshi) 1.14.0
- [Retrofit](https://square.github.io/retrofit/) 2.9.0
- [Glide](https://github.com/bumptech/glide) 4.13.2
- [Mockito](https://github.com/mockito/mockito) 4.1.0
- [Hilt](https://dagger.dev/hilt/) 2.44

## Other
I enjoyed working on this take-home assessment.  Thanks for reviewing and I look forward to next steps.

## App In Action
| <img src = "https://github.com/manalighare19/Weather-App/assets/43833000/96db3b25-6c4b-4551-9c2c-82cfcbe06e18" width = "200" height = "400" /> | <img src = "https://github.com/manalighare19/Weather-App/assets/43833000/97c090a0-d9c0-4b79-8d6d-06cdaeaeeb1a" width = "200" height = "400" /> |
|--|--|

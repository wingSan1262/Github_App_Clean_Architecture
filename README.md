# Github_App_Clean_Architecture
this is a github user search app with Clean-ish Architecture (if you may) and other modern App stack
Use SuspendRetrofit, UseCase pattern, MVVM, Dagger with scoping, LiveData along with Event, Navigation Graph,
Single Activity (yet), Coroutine, and Other

## Common Setting
SDK 21 until 31
Native Android Kotlin

## a bit clean arch
basically this App use 3 layer as whole
data, domain, ui
data : where you put your API and other data fetcher thing
Domain : is where the usecases, the data needs to be processed for app side, data mapper etc
UI : Basically cover all other package except the Data and Domain layer, as it's name main function to accomodate the DATA and DOMAIN to show it to USER

## Dagger Construction
the Dagger actually follow the Hilt convention for scoping component to 3 type
Application, Activity and Fragment
Which Component that you decide should stay from the app start and terminated, you go for the AplicationScoped Module
same if the object stay during activity or fragment use the corresponding scope

## Live Data Pattern
the Usecase was basically wrapping the between API and converting it to live data
Basically the pattern was this
Data (API, DB API interface) -> UseCase (call api and post it live data) -> ViewModel (instantiate UseCase and obtain the live data) -> View (subscribe livedata, and invoke api through live data)

## TODO
the API service was not abstracted and grouped to section, still in same class in client



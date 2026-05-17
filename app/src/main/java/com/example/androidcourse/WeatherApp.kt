    package com.example.androidcourse
    
    import android.app.Application
import com.example.androidcourse.di.AppComponent
import com.example.androidcourse.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
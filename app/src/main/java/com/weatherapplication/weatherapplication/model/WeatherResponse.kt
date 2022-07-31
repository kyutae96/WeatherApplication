package com.weatherapplication.weatherapplication.model

import com.google.gson.annotations.SerializedName


data class ResponseModel(
    val day : String? = null,
    val icon : String? = null,
    val weather : String? = null,
    val max : String? = null,
    val min : String? = null
)

class WeatherResponse(){
    @SerializedName("cod") var cod: String? = null
    @SerializedName("message") var message: String? = null
    @SerializedName("cnt") var cnt: String? = null
    @SerializedName("list") var list = ArrayList<List>()
}

class List {
    @SerializedName("dt") var dt: String? = null
    @SerializedName("main") var main: Main? = null
    @SerializedName("weather") var weather = ArrayList<Weather>()
    @SerializedName("clouds") var clouds: Clouds? = null
    @SerializedName("wind") var wind : Wind? = null
    @SerializedName("visibility") var visibility : String? = null
    @SerializedName("pop") var pop : String? = null
    @SerializedName("sys") var sys : Sys? = null
    @SerializedName("dt_txt") var dt_txt : String? = null
}

class Main {
    @SerializedName("temp")
    var temp: Float = 0.toFloat()
    @SerializedName("humidity")
    var humidity: Float = 0.toFloat()
    @SerializedName("pressure")
    var pressure: Float = 0.toFloat()
    @SerializedName("temp_min")
    var temp_min: Float = 0.toFloat()
    @SerializedName("temp_max")
    var temp_max: Float = 0.toFloat()

}
class Weather {
    @SerializedName("main")
    var main: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("icon")
    var icon: String? = null
}
class Clouds {
    @SerializedName("all")
    var all: Float = 0.toFloat()
}

class Wind {
    @SerializedName("speed")
    var speed: Float = 0.toFloat()
    @SerializedName("deg")
    var deg: Float = 0.toFloat()
}

class Sys {
    @SerializedName("pod")
    var pod: String? = null
}
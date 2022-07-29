package com.weatherapplication.weatherapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weatherapplication.weatherapplication.builder.RetrofitBuilder
import com.weatherapplication.weatherapplication.databinding.ActivityMainBinding
import com.weatherapplication.weatherapplication.databinding.ItemWeatherBinding
import com.weatherapplication.weatherapplication.model.ResponseModel
import com.weatherapplication.weatherapplication.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var weathermodel: ArrayList<ResponseModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        WeatherRetrofit()


    }


    private fun WeatherRetrofit() {
        //서울:37, 126
        //런던 : 위도: 51.5072, 경도: -0.1275
        //시카고 : 위도: 41.8379, 경도: -87.6828
        val lat = "37"
        val lng = "126"
        val api_key = getString(R.string.weather_api)
        RetrofitBuilder.api.getWeather(lat, lng, api_key).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
//                when (response.code()) {
//                    200 -> {
                val duplicate = response.body()
                val max = duplicate!!.main!!.temp_max - 273.15
                val min = duplicate!!.main!!.temp_min - 273.15
                val average = duplicate!!.main!!.temp - 273.15
                val icon = duplicate.weather[0].icon
                val day = duplicate.dt
                val country = duplicate.sys!!.country

                val Timestamp: Long = day!!.toLong()
                val timeD = Date(Timestamp * 1000)
                val sdf = SimpleDateFormat("MM월 dd일")
                val Time: String = sdf.format(timeD)

                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")
                println(duplicate)
                println(max)
                println(min)
                println(average)
                println(icon)
                println(day)
                println(country)
                println(Time)
                println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")

                val recyclerView = binding.seoulView
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = RecyclerViewAdapter(weathermodel)
                val adapter = recyclerView.adapter
                adapter?.notifyDataSetChanged()
//                    }
//                    else -> {
//                        println("error")
//                    }
//                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })

    }

    inner class RecyclerViewAdapter(var weathermodel: ArrayList<ResponseModel>) :
        RecyclerView.Adapter<CustomViewHolder>() {

        override fun getItemCount(): Int {
            return weathermodel.size
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view = ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CustomViewHolder(view)
        }


        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {


        }
    }

    inner class CustomViewHolder(itemView: ItemWeatherBinding) :
        RecyclerView.ViewHolder(itemView.root) {


    }
}
package com.weatherapplication.weatherapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                when (response.code()) {
                    200 -> {
                        val duplicate = response.body()
                        weathermodel.clear()
                        for (ds in 0 until duplicate!!.list.size) {
                            val day = duplicate!!.list[ds].dt
                            val icon = duplicate!!.list[ds].weather[0].icon
                            val description = duplicate!!.list[ds].weather[0].description
                            val temp_max = duplicate!!.list[ds].main!!.temp_max - 273.15
                            val temp_min = duplicate!!.list[ds].main!!.temp_min - 273.15

                            val Timestamp: Long = day!!.toLong()
                            val timeD = Date(Timestamp * 1000)
                            val sdf = SimpleDateFormat("MM월 dd일")
                            val Time: String = sdf.format(timeD)
                            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")
                            println(Time)
                            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")
                            val data = ResponseModel(
                                Time,
                                icon,
                                description,
                                temp_max.toString(),
                                temp_min.toString()
                            )
                            weathermodel.add(data)

                            val recyclerView = binding.seoulView
                            recyclerView.let {
                                it.layoutManager = LinearLayoutManager(this@MainActivity)
                                it.adapter = RecyclerViewAdapter(weathermodel)
                                val adapter = it.adapter
                                adapter?.notifyDataSetChanged()
                            }
//                            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//                            recyclerView.adapter = RecyclerViewAdapter(weathermodel)
//                            val adapter = recyclerView.adapter
//                            adapter?.notifyDataSetChanged()
                        }

                        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")
                        println(response)
                        println(duplicate)
                        println(duplicate!!.list[0].dt)
                        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@2")

                    }
                    else -> {
                        println("error")
                    }
                }

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

            holder.day.text = weathermodel[position].day

            Glide.with(this@MainActivity)
                .load(weathermodel[position].icon)
                .into(holder.icon)
            holder.description.text = weathermodel[position].weather
            holder.max.text = weathermodel[position].max
            holder.min.text = weathermodel[position].min

        }
    }

    inner class CustomViewHolder(itemView: ItemWeatherBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val day: TextView = itemView.itemDay
        val icon: ImageView = itemView.itemIcon
        val description: TextView = itemView.itemDescription
        val max: TextView = itemView.itemMax
        val min: TextView = itemView.itemMin

    }
}
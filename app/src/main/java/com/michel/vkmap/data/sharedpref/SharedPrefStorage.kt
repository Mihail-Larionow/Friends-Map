package com.michel.vkmap.data.sharedpref

import android.content.Context
import android.util.Log
import com.michel.vkmap.domain.models.LocationDataModel

class SharedPrefStorage(context: Context) {

    companion object{
        private const val LATITUDE_KEY = "latitude"
        private const val LONGITUDE_KEY = "longitude"
        private const val DATE_KEY = "date"
        private const val SHARED_PREFS_NAME = "location"
    }

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveLocation(data: LocationDataModel) {
        sharedPreferences.edit().putFloat(LATITUDE_KEY, data.latitude.toFloat()).apply()
        sharedPreferences.edit().putFloat(LONGITUDE_KEY, data.longitude.toFloat()).apply()
        sharedPreferences.edit().putLong(DATE_KEY, data.date).apply()
        Log.i("VKMAP", "SharedPref saved ${data.latitude} ${data.longitude} ${data.date}")

    }

    fun getLocation(): LocationDataModel {
        val latitude = sharedPreferences.getFloat(LATITUDE_KEY, -1f).toDouble()
        val longitude = sharedPreferences.getFloat(LONGITUDE_KEY, -1f).toDouble()
        Log.i("VKMAP", "SharedPref returned $latitude $longitude")
        return LocationDataModel(latitude = latitude, longitude = longitude)
    }
}
package com.michel.vkmap.data.sharedpref

import android.content.Context
import android.util.Log
import com.michel.vkmap.data.models.LocationModel

class SharedPrefStorage(context: Context) {

    companion object{
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"
        private const val SHARED_PREFS_NAME = "location"
    }

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveLocation(userLocation: LocationModel) {
        sharedPreferences.edit().putFloat(KEY_LATITUDE, userLocation.latitude.toFloat()).apply()
        sharedPreferences.edit().putFloat(KEY_LONGITUDE, userLocation.longitude.toFloat()).apply()
        Log.i("VKMAP", "SharedPref saved " + userLocation.latitude + " " + userLocation.longitude)

    }

    fun getLocation(): LocationModel {
        val latitude = sharedPreferences.getFloat(KEY_LATITUDE, -1f).toDouble()
        val longitude = sharedPreferences.getFloat(KEY_LONGITUDE, -1f).toDouble()
        Log.i("VKMAP", "SharedPref returned " + latitude + " " + longitude)
        return LocationModel(latitude = latitude, longitude = longitude)
    }
}
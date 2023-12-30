package com.michel.vkmap.data.storage.sharedpref

import android.content.Context
import android.util.Log
import com.michel.vkmap.data.storage.IUserStorage
import com.michel.vkmap.data.storage.models.LocationModel

private const val KEY_LATITUDE = "latitude"
private const val KEY_LONGITUDE = "longitude"
private const val SHARED_PREFS_NAME = "location"

class SharedPrefUserStorage(context: Context): IUserStorage {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveLocation(userLocation: LocationModel) {
        sharedPreferences.edit().putFloat(KEY_LATITUDE, userLocation.latitude).apply()
        sharedPreferences.edit().putFloat(KEY_LONGITUDE, userLocation.longitude).apply()
        Log.d("VKMAP", "SharedPref saveLocation()")
    }

    override fun getLocation(): LocationModel {
        val latitude = sharedPreferences.getFloat(KEY_LATITUDE, -1f)
        val longitude = sharedPreferences.getFloat(KEY_LATITUDE, -1f)
        Log.d("VKMAP", "SharedPref getLocation() " + latitude + " " + longitude)
        return LocationModel(latitude = latitude, longitude = longitude)
    }
}
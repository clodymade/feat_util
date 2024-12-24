/**
 * File: HiPreferencesManager.kt
 *
 * Description: This utility class manages the SharedPreferences for storing and retrieving data
 *              in a persistent storage across app sessions. It provides methods to save and load
 *              values using keys.
 *
 * Author: netcanis
 * Created: 2024-12-24
 *
 * License: MIT
 */

package com.mwkg.util

import android.content.Context

/**
 * Manager for handling SharedPreferences operations, including saving and loading values.
 * It uses a predefined `HiAppPreferences` name for storing the data.
 */
object HiPreferencesManager {

    // The name of the SharedPreferences file used for storing data
    private const val PREFERENCES_NAME = "HiAppPreferences"

    /**
     * Loads a value from SharedPreferences.
     *
     * @param context The context used to access the SharedPreferences.
     * @param key The key associated with the value to retrieve.
     * @param defaultValue The default value to return if the key does not exist (default is an empty string).
     * @return The stored value if it exists, or the default value if not.
     */
    fun load(context: Context, key: String, defaultValue: String = ""): String {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * Saves a value to SharedPreferences.
     *
     * @param context The context used to access the SharedPreferences.
     * @param key The key to associate with the value.
     * @param value The value to store in SharedPreferences.
     */
    fun save(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply() // Asynchronous saving
        }
    }
}

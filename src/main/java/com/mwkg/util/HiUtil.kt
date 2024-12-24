/**
 * File: HiUtil.kt
 *
 * Description: This utility module provides a set of extension functions for `Context` and other common utilities.
 *              It includes functions for loading and saving data using SharedPreferences, managing permissions,
 *              sanitizing strings, converting JSON to maps or lists, and more.
 *              These utilities aim to simplify common operations and improve code readability.
 *
 * Author: netcanis
 * Created: 2024-12-24
 *
 * License: MIT
 */

package com.mwkg.util

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * Extension function to load data from SharedPreferences.
 *
 * @param key The key of the data to retrieve.
 * @param defaultValue The default value to return if the key does not exist (default is an empty string).
 * @return The stored value if it exists, or the default value if not.
 *
 * Example usage:
 * ```
 * val savedValue = context.hiLoad("user_name", "defaultUser")
 * ```
 */
fun Context.hiLoad(key: String, defaultValue: String = ""): String {
    return HiPreferencesManager.load(this, key, defaultValue)
}

/**
 * Extension function to save data to SharedPreferences.
 *
 * @param key The key of the data to save.
 * @param value The value to store.
 *
 * Example usage:
 * ```
 * context.hiSave("user_name", "JohnDoe")
 * ```
 */
fun Context.hiSave(key: String, value: String) {
    HiPreferencesManager.save(this, key, value)
}

/**
 * Extension function to request permissions.
 *
 * @param permissions The list of permissions to request.
 * @param requestCode The request code for the permission request (default is 0).
 */
fun Context.hiRequestPermissions(permissions: Array<String>, requestCode: Int) {
    HiPermissionManager.requestPermissions(this, permissions, requestCode)
}

/**
 * Extension function to check if the required permissions have been granted.
 *
 * @param permissions The list of permissions to check.
 * @return `true` if all the permissions are granted, otherwise `false`.
 */
fun Context.hiHasPermissions(permissions: Array<String>): Boolean {
    return HiPermissionManager.hasPermissions(this, permissions)
}

/**
 * Removes control characters from the string.
 *
 * @return The sanitized string with control characters removed.
 */
fun String.hiSanitize(): String {
    return this.replace(Regex("\\p{Cntrl}"), "") // Remove control characters
}

/**
 * Converts a ByteArray to a string and removes control characters.
 *
 * @param charset The charset to use for conversion (default is UTF-8).
 * @return The sanitized string with control characters removed.
 */
fun ByteArray.hiSanitizeToString(charset: Charset = Charset.forName("UTF-8")): String {
    return String(this, charset)
        .hiSanitize() // Remove control characters and sanitize the string
}

/**
 * Converts the object to a pretty-formatted JSON string.
 *
 * @return A string representation of the JSON object or array, formatted with indentation.
 */
fun Any.hiToPrettyJsonString(): String {
    return when (this) {
        is JSONObject -> this.toString(4) // Convert JSONObject to string with 4-space indentation
        is JSONArray -> this.toString(4) // Convert JSONArray to string with 4-space indentation
        else -> this.toString()          // For other types, just call toString()
    }
}

/**
 * Converts an object (String, JSONObject, JSONArray, or primitives) to a Map, List, or original value.
 *
 * @receiver Any - The input object to be converted.
 * @return A Map<String, Any>, List<Any>, or the original value.
 *
 * Example usage:
 * // Test with JSON string - JSONObject format
 * val jsonObject = JSONObject("""
 *     {
 *         "key1": "value1",
 *         "key2": {
 *             "nestedKey1": "nestedValue1",
 *             "nestedKey2": 123
 *         },
 *         "key3": [1, 2, 3, {"innerKey": "innerValue"}]
 *     }
 * """)
 * val jsonArray = JSONArray("""
 *     [
 *         {"keyA": "valueA", "keyB": 456},
 *         [10, 20, 30],
 *         "simpleValue"
 *     ]
 * """)
 * val jsonString = """
 * println(jsonObject.hiToMapOrList())       // Processing JSONObject
 * println(jsonArray.hiToMapOrList())        // Processing JSONArray
 * println(jsonString.hiToMapOrList())       // Processing JSON-formatted String
 * println(nonJsonString.hiToMapOrList())    // Processing regular String
 * val resultMap = resultJsonString.hiToMapOrList() as? Map<String, String> ?: emptyMap()
 */
fun Any.hiToMapOrList(): Any {
    return when (this) {
        is JSONObject -> {
            val map = mutableMapOf<String, Any>()
            this.keys().forEach { key ->
                val value = this[key]
                map[key] = value.hiToMapOrList() // Recursively call for nested structures
            }
            map
        }
        is JSONArray -> {
            (0 until this.length()).map { index ->
                this[index].hiToMapOrList() // Recursively call for nested structures
            }
        }
        is String -> {
            try {
                when {
                    this.trim().startsWith("{") -> JSONObject(this).hiToMapOrList() // Parse JSON object
                    this.trim().startsWith("[") -> JSONArray(this).hiToMapOrList() // Parse JSON array
                    else -> this // Return as-is if not JSON
                }
            } catch (e: Exception) {
                this // Return as-is if parsing fails
            }
        }
        else -> this // Return as-is for other types
    }
}

/**
 * Converts a JSONObject to a Map<String, Any>.
 *
 * @receiver JSONObject - The JSON object to be converted.
 * @return A Map<String, Any> representing the JSON object.
 *         Nested JSONObject and JSONArray are recursively converted.
 */
fun JSONObject.hiToMap(): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    this.keys().forEach { key ->
        val value = this[key]
        map[key] = when (value) {
            is JSONObject -> value.hiToMap() // Recursively convert nested JSONObject
            is JSONArray -> value.hiToList() // Recursively convert nested JSONArray
            else -> value // Other types are added as-is
        }
    }
    return map
}

/**
 * Converts a JSONArray to a List<Any>.
 *
 * @receiver JSONArray - The JSON array to be converted.
 * @return A List<Any> representing the JSON array.
 *         Nested JSONObject and JSONArray are recursively converted.
 */
fun JSONArray.hiToList(): List<Any> {
    return (0 until this.length()).map { index ->
        val value = this[index]
        when (value) {
            is JSONObject -> value.hiToMap() // Recursively convert nested JSONObject
            is JSONArray -> value.hiToList() // Recursively convert nested JSONArray
            else -> value // Other types are added as-is
        }
    }
}
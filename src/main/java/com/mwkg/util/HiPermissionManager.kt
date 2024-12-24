/**
 * File: HiPermissionManager.kt
 *
 * Description: This manager handles the permission requests for the app. It provides functions to merge
 *              multiple permission types, request permissions, and check if the required permissions
 *              have been granted. The class supports different permission types such as camera, storage, BLE, etc.
 *
 * Author: netcanis
 * Created: 2024-12-24
 *
 * License: MIT
 */

package com.mwkg.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Manager for handling permission requests and checks.
 * Provides utility functions to request and verify permissions for various tasks, including Bluetooth, Camera, Storage, etc.
 */
object HiPermissionManager {

    /**
     * Merges multiple permission types into a single list, removing duplicates.
     *
     * This function is useful when different permission types (like BLE and Beacon) require similar permissions.
     * It combines the permissions and removes duplicates before returning the final array.
     *
     * @param types The permission types (HiPermissionType) to be merged.
     * @return A deduplicated array of required permissions (Array<String>).
     *
     * Example usage:
     * ```
     * val permissions = HiPermissionManager.getMergedPermissions(HiPermissionType.BLE, HiPermissionType.BEACON)
     * if (!HiPermissionManager.hasPermissions(activity, permissions)) {
     *      HiPermissionManager.requestPermissions(activity, permissions, HiGlobal.PermissionReqCodes.BLE_BEACON)
     *      return
     * }
     * ```
     */
    fun getMergedPermissions(vararg types: HiPermissionType): Array<String> {
        return types.flatMap { it.requiredPermissions().asList() }
            .distinct()
            .toTypedArray()
    }

    /**
     * Requests the specified permissions.
     *
     * @param context The context from which the request is made (should be an Activity).
     * @param permissions The list of permissions to request.
     * @param requestCode The request code for the permission request.
     */
    fun requestPermissions(context: Context, permissions: Array<String>, requestCode: Int) {
        if (context is Activity) {
            ActivityCompat.requestPermissions(context, permissions, requestCode)
        } else {
            throw IllegalArgumentException("Context must be an Activity.")
        }
    }

    /**
     * Checks whether all the specified permissions have been granted.
     *
     * @param context The context used to check permissions.
     * @param permissions The list of permissions to check.
     * @return `true` if all permissions are granted, otherwise `false`.
     */
    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        return permissions.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
}

/**
 * Enum class defining different permission types.
 * Each type returns the required permissions associated with it.
 */
enum class HiPermissionType {
    PUSH,       // Push notifications permission
    CAMERA,     // Camera permission
    STORAGE,    // Storage permission
    PHONE,      // Phone state permission
    SMS,        // SMS permission
    LOCATION,   // Location permission
    BLE,        // Bluetooth permission
    BEACON,     // Beacon permission
    RECORD,     // Microphone permission (audio recording)
    CONTACTS;   // Contacts permission

    /**
     * Returns the required permissions for each permission type.
     *
     * @return An array of required permissions (Array<String>).
     */
    fun requiredPermissions(): Array<String> {
        return when (this) {
            PUSH -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                arrayOf()
            }
            CAMERA -> arrayOf(Manifest.permission.CAMERA)
            STORAGE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            } else {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            }
            PHONE -> arrayOf(Manifest.permission.READ_PHONE_STATE)
            SMS -> arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS)
            LOCATION -> arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            BLE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            } else {
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            BEACON -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } else {
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            RECORD -> arrayOf(Manifest.permission.RECORD_AUDIO)
            CONTACTS -> arrayOf(Manifest.permission.READ_CONTACTS)
        }
    }
}
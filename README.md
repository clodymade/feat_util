# **feat_util**

A **feature module** for utility functions and extensions used across the app.

---

## **Overview**

`feat_util` is an Android module that provides a set of utility functions and extension methods to simplify common tasks such as:
- SharedPreferences management for loading and saving data.
- Permission handling for requesting and checking required permissions.
- String sanitization and JSON conversion utilities.
- Extensions for managing device resources such as camera, location, and Bluetooth.

This module helps standardize the app’s utility operations, making the codebase cleaner and more maintainable.

---

## **Features**

- ✅ **SharedPreferences Management**: Easily load and save data using SharedPreferences.
- ✅ **Permission Handling**: Simplify permission requests and checks for different permission types (e.g., CAMERA, STORAGE, BLE).
- ✅ **String Sanitization**: Remove unwanted control characters from strings.
- ✅ **JSON Utilities**: Convert JSON strings, objects, and arrays to Maps and Lists.
- ✅ **Modular Design**: Lightweight and easy-to-integrate utility module.

---

## **Requirements**

| Requirement        | Minimum Version         |
|--------------------|-------------------------|
| **Android OS**     | 11 (API 30)             |
| **Kotlin**         | 1.9.24                  |
| **Android Studio** | Hedgehog                |
| **Gradle**         | 8.7                     |

---

## **Setup**

### **1. Add feat_util to Your Project via JitPack**

To include `feat_util` via **JitPack**, follow these steps:

1. Add JitPack to your project-level `build.gradle` file:

    ```gradle
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
    ```

2. Add `feat_util` to your module-level `build.gradle` file:

    ```gradle
    dependencies {
        implementation 'com.github.clodymade:feat_util:1.0.3'
    }
    ```

3. Sync your project.

---

## **Usage**

### **1. SharedPreferences - Load and Save Data**

To save and load values using SharedPreferences:

```kotlin
// Save a value
HiPreferences.save(context, "username", "JohnDoe")

// Load a value with a default
val username = HiPreferences.load(context, "username", "DefaultUser")
```

### **2. Requesting Permissions**

To request permissions using the utility:

```kotlin
val blePermissions = HiPermissionType.BLE.requiredPermissions()
if (!context.hasPermissions(blePermissions)) {
    context.requestPermissions(blePermissions, PermissionReqCodes.BLE)
} else {
    // BLE scanning logic here
}
```

### **3. JSON Conversion**

To convert JSON data to Map or List:

```kotlin
val jsonObject = JSONObject("""
    {
        "key1": "value1",
        "key2": {"nestedKey": "nestedValue"}
    }
""")

val resultMap = jsonObject.toMap()
println(resultMap) // Prints a Map
```

### **4. String Sanitization**

To sanitize a string by removing control characters:

```kotlin
val sanitizedString = "StringWith\u0001ControlChars".sanitize()
```

---

## **License**

feat_util is available under the MIT License. See the LICENSE file for details.

---

## **Contributing**

Contributions are welcome! To contribute:

1. Fork this repository.
2. Create a feature branch:
```
git checkout -b feature/your-feature
```
3. Commit your changes:
```
git commit -m "Add feature: description"
```
4. Push to the branch:
```
git push origin feature/your-feature
```
5. Submit a Pull Request.

---

## **Author**

### **netcanis**
iOS GitHub: https://github.com/netcanis
Android GitHub: https://github.com/clodymade

---


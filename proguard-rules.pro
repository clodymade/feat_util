# Keep all public classes and methods in the feat_ble package
-keep public class com.mwkg.util.** { *; }

# Keep annotation information
-keepattributes *Annotation*

# Preserve method signatures for reflection
-keepattributes Signature, MethodParameters, EnclosingMethod, InnerClasses

# Preserve Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    private void readObjectNoData();
}

# --- Rules for java.lang.invoke package and StringConcatFactory ---

# Suppress warnings for missing StringConcatFactory
-dontwarn java.lang.invoke.StringConcatFactory

# Keep all classes from java.lang.invoke package
-keep class java.lang.invoke.** { *; }

# Explicitly keep StringConcatFactory
#-keep class java.lang.invoke.StringConcatFactory { *; }

# --- Specific ProGuard rules for feat_ble classes ---

# Preserve the HiPermission class (including fields and methods)
-keep class com.mwkg.util.HiPermission { *; }

# Preserve the HiPreferences class (including fields and methods)
-keep class com.mwkg.util.HiPreferences { *; }

# Preserve extension functions in the HiToolkit package
-keepclassmembers class com.mwkg.util.HiToolkit {
    public *;
}

# --- Additional generic rules for safety ---

# Keep Parcelable implementations
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Keep Data Binding generated classes
-keep class androidx.databinding.** { *; }
-keepclassmembers class androidx.databinding.** { *; }

# Keep Jetpack Compose-related classes
-keep class androidx.compose.** { *; }
-keep class kotlin.Unit { *; }

# Keep coroutines-related classes
-keep class kotlinx.coroutines.** { *; }

# Preserve the HiPermissionType (Enum) class
#-keepclassmembers enum com.mwkg.util.HiPermissionType {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

# --- Additional Settings ---

# Include default ProGuard rules for Android optimization
#-include proguard-android-optimize.txt

# Generate mapping file for debugging and verification
-printmapping mapping.txt

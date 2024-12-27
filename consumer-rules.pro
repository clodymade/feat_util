# --- Preserve Public API ---

# Keep all public classes, methods, and fields in the library
# This ensures that consumers can access the public API of the library
-keep class com.mwkg.util.** {
    public *;
}

# Preserve public enum values for HiPermissionType
#-keepclassmembers enum com.mwkg.util.HiPermissionType {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

# --- Reflection Safety ---

# Keep all classes that might be referenced via reflection
-keep class com.mwkg.util.** { *; }

# Preserve method parameter names (useful for reflection or debugging)
-keepattributes *Annotation*, Signature, MethodParameters

# --- Serialization Safety ---

# Preserve Serializable classes and their members
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    private void readObjectNoData();
}

# --- Extension Functions ---

# Keep Kotlin extension functions so that consumers can use them
-keepclassmembers class com.mwkg.util.** {
    public *;
}

# --- Additional Considerations ---

# If you use annotations in the library, preserve them
-keepattributes *Annotation*

# If consumers may use reflection to access internal members, preserve enclosing method info
-keepattributes EnclosingMethod
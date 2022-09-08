package co.touchlab.kampkit

actual typealias RunWith = org.junit.runner.RunWith
actual typealias Runner = org.junit.runner.Runner
actual typealias AndroidJUnit4 = androidx.test.ext.junit.runners.AndroidJUnit4

// Our expect declaration has fewer parameters than the actual Config annotation
// If we try to match the expect declaration exactly, we run into problems because the `application` parameter has
// a default value `DefaultApplication::class` which we can't write an actual declaration for because it's
// package-private
@Suppress("NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS")
actual typealias Config = org.robolectric.annotation.Config

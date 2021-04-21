package buildsrc

// TODO: 1/10/21 clean up dependencies and apply correct versions
object Dependencies {

    const val otpview = "com.github.mukeshsolanki:android-otpview-pinview:2.1.2"
    const val spinkit = "com.github.ybq:Android-SpinKit:1.4.1"

    object Kotlin {
        private const val version = "1.4.31"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val serializationGradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$version"
    }

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha14"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val ktx = "androidx.core:core-ktx:1.3.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"
        const val material = "com.google.android.material:material:1.2.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-beta01"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    }

    object Coroutines {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"
    }

    object OkHttp {
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"
    }

    object Retrofit {
        const val serialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.7.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    }

    object Hilt {
        private const val version = "2.33-beta"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hilt = "com.google.dagger:hilt-android:$version"
        const val androidKapt = "com.google.dagger:hilt-android-compiler:$version"
        const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha01"
        const val coreKapt = "androidx.hilt:hilt-compiler:1.0.0-alpha01"
    }

    object Logging {
        const val slf4j = "org.slf4j:slf4j-api:1.7.25"
        const val logbackAndroid = "com.github.tony19:logback-android:2.0.0"
    }

    object Test {
        const val junit = "junit:junit:4.12"
        const val logbackJvm = "ch.qos.logback:logback-classic:1.2.3"
    }
}


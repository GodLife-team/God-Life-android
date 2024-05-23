plugins {
    id("god_life.android.feature")
}

android {
    namespace = "com.godlife.setting_page"

    buildFeatures {
        buildConfig = true
    }


}

dependencies {

    implementation(projects.feature.navigator)

    implementation(projects.core.model)
    implementation(projects.core.designsystem)

    implementation(libs.androidx.junit.ktx)
}
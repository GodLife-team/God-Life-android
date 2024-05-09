package com.godlife.network.retrofit

import androidx.tracing.trace
import com.godlife.network.NetworkDataSource
import com.godlife.network.model.NetworkUserQuery
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

interface RetrofitNetworkApi {

    /**
     * ## getUserInfo()
     * 서버에 저장된 User 정보를 받아오기 위함
     *
     *
     */

    @POST("databases/{id}/query")
    suspend fun getUserInfo(
        @Path("id") id: String?,
    ): NetworkUserQuery


}


@Singleton
internal class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : NetworkDataSource {

    private val networkApi = trace("RetrofitNetwork") {
        Retrofit.Builder()
            .baseUrl("BASE_URL_HERE")
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitNetworkApi::class.java)
    }

    override suspend fun getUserInfo(
        //remoteErrorEmitter: RemoteErrorEmitter,
        id: String
    ): NetworkUserQuery? =
        networkApi.getUserInfo(id = id)


}
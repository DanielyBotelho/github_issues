package com.murua.githubissues.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.murua.githubissues.core.network.BuildConfig
import com.murua.githubissues.core.network.IssuesNetworkDataSource
import com.murua.githubissues.core.network.model.Issue
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = BuildConfig.BACKEND_URL

private interface GithubIssuesApi {
    @GET(value = "/repos/JetBrains/kotlin/issues")
    suspend fun getIssues(): List<Issue>
}

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : IssuesNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(GithubIssuesApi::class.java)

    override suspend fun getIssues(): List<Issue> =
        networkApi.getIssues()
}
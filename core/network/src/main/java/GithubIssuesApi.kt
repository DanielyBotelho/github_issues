import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import model.Issue
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://api.github.com"

private interface GithubIssuesApi {
    @GET(value = "/repos/JetBrains/kotlin/issues")
    suspend fun getIssues(): ApiResponse<List<Issue>>
}

@Serializable
private data class ApiResponse<T>(
    val data: T,
)

@Singleton
class NetworkBuilder @Inject constructor(
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
        networkApi.getIssues().data
}


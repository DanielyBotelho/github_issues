import kotlinx.coroutines.flow.Flow
import model.Issue

interface IssuesNetworkDataSource {
    suspend fun getIssues(): List<Issue>
}
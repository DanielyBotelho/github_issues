package com.murua.githubissues.feature.issues

import app.cash.turbine.test
import com.murua.githubissues.core.domain.GetIssuesUseCase
import com.murua.githubissues.feature.issues.home.IssuesUiState
import com.murua.githubissues.feature.issues.home.IssuesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import model.IssueItem
import model.State
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class IssuesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val issuesUseCase: GetIssuesUseCase = mockk<GetIssuesUseCase>()
    private lateinit var viewModel: IssuesViewModel

    @Before
    fun setup() {
        viewModel = IssuesViewModel(
            issuesUseCase = issuesUseCase
        )
    }

    @Test
    fun getIssues_whenSuccess_shouldReturnsListOfIssues() = runTest {
        val mockApiResult = listOf(IssueItem.mock(), IssueItem.mock())
        coEvery { issuesUseCase.invoke() } returns flowOf(mockApiResult)

        viewModel.getIssues()

        viewModel.issuesState.test {
            val successState = awaitItem()
            assertTrue(successState is IssuesUiState.Success)
            assertEquals(mockApiResult, successState.data)
        }
    }

    @Test
    fun getIssues_whenLoading_shouldEmitLoadingState() = runTest {
        coEvery { issuesUseCase.invoke() } returns flow {
            delay(500)
            emit(emptyList())
        }

        viewModel.getIssues()

        viewModel.issuesState.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is IssuesUiState.Loading)
        }
    }

    @Test
    fun getIssues_whenError_shouldEmitErrorState() = runTest {
        val errorMessage = "Simulated error message"

        coEvery { issuesUseCase.invoke() } returns flow {
            throw RuntimeException(errorMessage)
        }

        viewModel.getIssues()

        viewModel.issuesState.test {
            val errorState = awaitItem()
            assertTrue(errorState is IssuesUiState.Error)
            assertEquals(errorMessage, errorState.error)
        }
    }

}

private fun IssueItem.Companion.mock() =
    IssueItem(
        1,
        "avatar/url",
        "2023-12-06T14:34:59Z",
        "Título do Issue",
        "Descrição do Issue",
        "//:url",
        State.OPEN
    )

package com.murua.githubissues.core.common.result

import app.cash.turbine.test
import com.murua.githubissues.core.common.ApiResult
import com.murua.githubissues.core.common.asResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class ApiResultTest {

    @Test
    fun apiErrorFlow_shouldEmitErrorWithCorrectMessage() = runTest {
        flow {
            emit(1)
            throw Exception("Error message")
        }
            .asResult()
            .test {
                assertEquals(ApiResult.Loading, awaitItem())
                assertEquals(ApiResult.Success(1), awaitItem())

                when (val errorResult = awaitItem()) {
                    is ApiResult.Error -> assertEquals(
                        "Error message",
                        errorResult.exception?.message,
                    )

                    ApiResult.Loading,
                    is ApiResult.Success,
                    -> throw IllegalStateException(
                        "The flow should have emitted an Error Result",
                    )
                }

                awaitComplete()
            }
    }

    @Test
    fun apiFlow_shouldEmitSuccessStateAfterLoading() {
        runTest {
            flow {
                emit(1)
            }
                .asResult()
                .test {
                    assertEquals(ApiResult.Loading, awaitItem())
                    assertEquals(ApiResult.Success(1), awaitItem())
                    awaitComplete()
                }
        }
    }
}
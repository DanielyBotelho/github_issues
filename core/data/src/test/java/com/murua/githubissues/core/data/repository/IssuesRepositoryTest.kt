@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.murua.githubissues.core.data.repository

import app.cash.turbine.test
import com.murua.githubissues.core.data.model.mock
import com.murua.githubissues.core.network.IssuesNetworkDataSource
import com.murua.githubissues.core.network.model.Issue
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import model.asDataModel
import org.junit.Before
import org.junit.Test
import repository.IssuesRepository
import repository.IssuesRepositoryImpl
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class IssuesRepositoryTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var subject: IssuesRepository

    private lateinit var networkDataSource: IssuesNetworkDataSource

    @Before
    fun setup() {
        networkDataSource = mockk<IssuesNetworkDataSource>()

        subject = IssuesRepositoryImpl(
            testDispatcher,
            networkDataSource = networkDataSource
        )
    }

    @Test
    fun whenGetIssuesIsCalled_shouldReturnsSuccess() =
        testScope.runTest {
            val mockNetworkData = listOf(Issue.mock(), Issue.mock())
            coEvery { networkDataSource.getIssues() } returns mockNetworkData

            subject.getIssues().test {
                val successResult = awaitItem()

                val expectedIds = mockNetworkData.map { it.asDataModel().id }
                val actualIds = successResult.map { it.id }
                assertEquals(expectedIds, actualIds, "Ids do not match")

                val expectedStates = mockNetworkData.map { it.asDataModel().state }
                val actualStates = successResult.map { it.state }
                assertEquals(expectedStates, actualStates, "States do not match")

                val expectedAvatarUrls = mockNetworkData.map { it.asDataModel().avatarUrl }
                val actualAvatarUrls = successResult.map { it.avatarUrl }
                assertEquals(expectedAvatarUrls, actualAvatarUrls, "Avatar URLs do not match")

                awaitComplete()
            }
        }
}
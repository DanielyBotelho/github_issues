package com.murua.githubissues.feature.issues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murua.githubissues.core.common.ApiResult
import com.murua.githubissues.core.domain.GetIssuesUseCase
import com.murua.githubissues.feature.issues.IssuesUiState.Default
import com.murua.githubissues.feature.issues.IssuesUiState.Error
import com.murua.githubissues.feature.issues.IssuesUiState.Loading
import com.murua.githubissues.feature.issues.IssuesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import model.IssueItem
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val issuesUseCase: GetIssuesUseCase
) : ViewModel() {

    private val _issuesState = MutableStateFlow<IssuesUiState>(Default)
    val issuesState: StateFlow<IssuesUiState>
        get() = _issuesState.asStateFlow()

    fun getIssues() {
        println("getIssues $_issuesState")
        viewModelScope.launch {
            println("launch $_issuesState")
            _issuesState.emit(Loading)

            issuesUseCase().map {
                println("${it}")
                when (it) {
                    is ApiResult.Success -> {
                        println("${it.data}")
                        _issuesState.emit(Success(it.data))
                    }
                    is ApiResult.Error -> {
                        _issuesState.emit(Error(it.exception?.message ?: ""))
                    }
                    is ApiResult.Loading -> _issuesState.emit(Loading)
                }
            }.collect()
        }
    }
}

sealed interface IssuesUiState {
    data object Loading : IssuesUiState
    data object Default : IssuesUiState
    data class Success(val data: List<IssueItem>) : IssuesUiState
    data class Error(val error: String) : IssuesUiState
}
package com.murua.githubissues.feature.issues

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.IssueItem

class SharedViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _sharedState = MutableStateFlow<IssueItem?>(null)
    val sharedState = _sharedState.asStateFlow()

    fun updateState(data: IssueItem) {
        _sharedState.value = data
    }

    override fun onCleared() {
        super.onCleared()
        _sharedState.value = null
    }
}
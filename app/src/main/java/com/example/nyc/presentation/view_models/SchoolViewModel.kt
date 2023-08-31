package com.example.nyc.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyc.domain.models.SchoolModel
import com.example.nyc.domain.utils.Logger
import com.example.nyc.domain.use_cases.FetchSchoolsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private val schoolsUseCase: FetchSchoolsUseCase
) : ViewModel() {
    private val TAG = SchoolViewModel::class.java.simpleName


    private var viewState = ViewState()
    private val _viewStateFlow: MutableStateFlow<ViewState> = MutableStateFlow(viewState)
    val viewStateFlow: StateFlow<ViewState> = _viewStateFlow.asStateFlow()

    private var _viewActionFlow: MutableSharedFlow<ViewAction> = MutableSharedFlow()
    val viewActionFlow: SharedFlow<ViewAction> = _viewActionFlow.asSharedFlow()

    data class ViewState(
        var schoolsList: List<SchoolModel> = emptyList()
    )

    sealed class Action {
        data class FetchSchoolsList(var schoolsList: List<SchoolModel>) : Action()
    }

    sealed class ViewAction {
        data class ShowLoader(val isLoaderShowing: Boolean) : ViewAction()
        data class MoveToDetailActivity(val schoolId: String) : ViewAction()
    }

    @Synchronized
    private fun dispatch(action: Action) {
        Logger.e(TAG, "Dispatching Action $action")
        val updatedViewState = reduce(action)
        viewState = updatedViewState

        _viewStateFlow.update {
            updatedViewState
        }
    }

    private fun reduce(action: Action): ViewState {
        return when (action) {
            is Action.FetchSchoolsList -> {
                viewState.copy(
                    schoolsList = action.schoolsList,
                )
            }
        }
    }

    fun fetchSchoolsList() {
        viewModelScope.launch {
            dispatch(Action.FetchSchoolsList(schoolsUseCase.fetchSchoolsList()))
            _viewActionFlow.emit(ViewAction.ShowLoader(isLoaderShowing = false))
        }
    }

    fun onSchoolItemClicked(index: Int) {
        viewModelScope.launch {
            _viewActionFlow.emit(ViewAction.MoveToDetailActivity(viewState.schoolsList[index].schoolId))
        }
    }
}
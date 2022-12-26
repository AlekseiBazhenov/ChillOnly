package ru.modernsoft.chillonly.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.modernsoft.chillonly.business.use_cases.LoadStationsUseCase


class StationsViewModelFactory(
    private val useCase: LoadStationsUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StationsViewModel(useCase) as T
    }
}


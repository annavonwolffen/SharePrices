package com.annevonwolffen.shareprices.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annevonwolffen.shareprices.domain.StocksInteractor
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SimpleStocksViewModel @Inject constructor(interactor: StocksInteractor) : ViewModel() {

    val stocksFlow = interactor.stocksFlow
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
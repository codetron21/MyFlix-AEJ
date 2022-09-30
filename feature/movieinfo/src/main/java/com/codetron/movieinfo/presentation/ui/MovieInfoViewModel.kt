package com.codetron.movieinfo.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.shared.delegates.AddOrRemoveWatchlistDelegates
import com.catnip.shared.delegates.AddOrRemoveWatchlistDelegatesImpl

class MovieInfoViewModel : ViewModel(),
    AddOrRemoveWatchlistDelegates by AddOrRemoveWatchlistDelegatesImpl() {

    init {
        init(viewModelScope)
    }

}
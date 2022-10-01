package com.codetron.detail.presentation.ui.detailmovie

import androidx.lifecycle.ViewModel
import com.catnip.shared.delegates.AddOrRemoveWatchlistDelegates
import com.catnip.shared.delegates.AddOrRemoveWatchlistDelegatesImpl
import com.codetron.detail.domain.GetDetailMovieUseCase

class DetailMovieViewModel(
    private val useCase: GetDetailMovieUseCase
) : ViewModel(),
    AddOrRemoveWatchlistDelegates by AddOrRemoveWatchlistDelegatesImpl() {


}
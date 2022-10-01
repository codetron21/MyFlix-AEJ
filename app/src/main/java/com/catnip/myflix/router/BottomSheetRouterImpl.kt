package com.catnip.myflix.router

import com.catnip.core.listener.BottomSheetApi
import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.shared.router.BottomSheetRouter
import com.codetron.movieinfo.presentation.ui.MovieInfoBottomSheet

class BottomSheetRouterImpl : BottomSheetRouter {
    override fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetApi {
        return MovieInfoBottomSheet(movieViewParam)
    }
}
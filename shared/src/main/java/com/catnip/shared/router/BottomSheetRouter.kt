package com.catnip.shared.router

import com.catnip.core.listener.BottomSheetApi
import com.catnip.shared.data.model.viewparam.MovieViewParam

interface BottomSheetRouter {
    fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetApi
}
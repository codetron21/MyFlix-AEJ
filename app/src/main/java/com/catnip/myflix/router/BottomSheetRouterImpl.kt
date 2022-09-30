package com.catnip.myflix.router

import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.shared.router.BottomSheetRouter
import com.codetron.movieinfo.presentation.ui.MovieInfoBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetRouterImpl : BottomSheetRouter {
    override fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetDialogFragment {
        return MovieInfoBottomSheet(movieViewParam)
    }
}
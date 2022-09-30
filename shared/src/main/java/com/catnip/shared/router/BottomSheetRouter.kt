package com.catnip.shared.router

import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface BottomSheetRouter {
    fun createMovieInfoBottomSheet(movieViewParam: MovieViewParam): BottomSheetDialogFragment
}
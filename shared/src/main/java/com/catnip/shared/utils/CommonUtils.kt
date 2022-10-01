package com.catnip.shared.utils

import android.content.Context
import android.content.Intent
import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.styling.ProjectDrawable

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object CommonUtils {

    fun getAdditionalMovieInfo(movieViewParam: MovieViewParam): String {
        val unit = 60
        val releaseDate = movieViewParam.releaseDate
        val rating = movieViewParam.filmRate
        val runtime = movieViewParam.runtime
        val hour = runtime / unit
        val minute = runtime % unit

        if (runtime % 2 < 0) {
            return "$releaseDate \u2022 $rating"
        }

        val strRuntime = when {
            (hour > 0 && minute > 0) -> "${hour}h ${minute}m"
            (hour > 0) -> "${hour}h"
            (minute > 0) -> "${minute}m"
            else -> "0m"
        }

        return "$releaseDate \u2022 $rating \u2022 $strRuntime"
    }

    fun getWatchlistIcon(isUserWatchlist: Boolean): Int {
        return if (isUserWatchlist) ProjectDrawable.ic_check else ProjectDrawable.ic_add
    }

    fun shareFilm(context: Context, movieViewParam: MovieViewParam) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Watch this ! ${movieViewParam.title} ${movieViewParam.posterUrl}"
            )
            type = "text/plain"
        }, null)
        context.startActivity(shareIntent)
    }
}
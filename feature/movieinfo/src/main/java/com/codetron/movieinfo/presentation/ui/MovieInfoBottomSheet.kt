package com.codetron.movieinfo.presentation.ui

import android.widget.Toast
import androidx.lifecycle.ViewModel
import coil.load
import com.catnip.core.base.BaseBottomSheetDialog
import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.shared.router.ActivityRouter
import com.catnip.shared.utils.MovieAttributeUtils
import com.catnip.shared.utils.ext.subscribe
import com.catnip.styling.databinding.BottomSheetMovieInfoBinding
import com.codetron.movieinfo.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieInfoBottomSheet(
    private val movieViewParam: MovieViewParam
) : BaseBottomSheetDialog<BottomSheetMovieInfoBinding, ViewModel>(
    BottomSheetMovieInfoBinding::inflate
) {

    override val viewModel by viewModel<MovieInfoViewModel>()

    private val activityRouter by inject<ActivityRouter>()

    override fun initView() {
        setData()
        setActionListeners()
    }

    override fun observeData() {
        viewModel.getWatchlistResult().observe(this) {
            it.subscribe(
                doOnSuccess = {
                    dismiss()
                    Toast.makeText(
                        requireContext(),
                        if (it.payload?.isUserWatchlist == true)
                            getString(R.string.text_add_watchlist_success)
                        else
                            getString(R.string.text_remove_watchlist_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }, doOnError = {
                    dismiss()
                })
        }
    }

    private fun setData() = with(binding) {
        ivPoster.load(movieViewParam.posterUrl)
        tvMovieTitle.text = movieViewParam.title
        tvShortDesc.text = movieViewParam.overview
        tvAdditionalInfo.text = getString(
            R.string.format_additional_info,
            movieViewParam.releaseDate,
            movieViewParam.filmRate,
            MovieAttributeUtils.formatRuntime(movieViewParam.runtime),
        )
    }

    private fun setActionListeners() = with(binding) {
        ivClose.setOnClickListener {
            dismiss()
        }

        llPlayMovie.setOnClickListener {
            Toast.makeText(requireContext(), "play-movie", Toast.LENGTH_SHORT).show()
        }

        llMyList.setOnClickListener {
            viewModel.addOrRemoveWatchlist(movieViewParam)
        }

        llShare.setOnClickListener {
            Toast.makeText(requireContext(), "share-movie", Toast.LENGTH_SHORT).show()
        }

        tvDetailMovie.setOnClickListener {
            Toast.makeText(requireContext(), "detail-movie", Toast.LENGTH_SHORT).show()
        }
    }

}
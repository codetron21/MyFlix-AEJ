package com.codetron.movieinfo.presentation.ui

import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import coil.load
import com.catnip.core.base.BaseBottomSheetDialog
import com.catnip.core.listener.NotifyListener
import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.shared.router.ActivityRouter
import com.catnip.shared.utils.CommonUtils
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
    private var myWatchListClicked: Boolean = false
    private var notifyListener: NotifyListener<Boolean>? = null

    override fun initView() {
        setData()
        setActionListeners()
    }

    override fun display(fm: FragmentManager, tag: String?) {
        show(fm, tag)
    }

    override fun hide() {
        dismiss()
    }

    override fun onExit(notifyListener: NotifyListener<Boolean>) {
        this.notifyListener = notifyListener
    }

    override fun onCancel(dialog: DialogInterface) {
        notifyListener?.runNotify(myWatchListClicked)
        super.onCancel(dialog)
    }

    override fun observeData() {
        viewModel.getWatchlistResult().observe(this) { resources ->
            resources.subscribe(
                doOnSuccess = {
                    val message = getString(
                        if (it.payload?.isUserWatchlist == true)
                            R.string.text_add_watchlist_success
                        else R.string.text_remove_watchlist_success
                    )

                    showToastMessage(message)
                    binding.ivWatchlist.setImageResource(CommonUtils.getWatchlistIcon(movieViewParam.isUserWatchlist))
                }, doOnError = {
                    showToastMessage(it.message.orEmpty())
                })
        }
    }

    private fun setData() = with(binding) {
        ivPoster.load(movieViewParam.posterUrl)
        tvMovieTitle.text = movieViewParam.title
        tvShortDesc.text = movieViewParam.overview
        tvAdditionalInfo.text = CommonUtils.getAdditionalMovieInfo(movieViewParam)
        ivWatchlist.setImageResource(CommonUtils.getWatchlistIcon(movieViewParam.isUserWatchlist))
    }

    private fun setActionListeners() = with(binding) {
        ivClose.setOnClickListener {
            notifyListener?.runNotify(myWatchListClicked)
            hide()
        }

        llPlayMovie.setOnClickListener {
            // todo: play movie
        }

        llMyList.setOnClickListener {
            myWatchListClicked = true
            viewModel.addOrRemoveWatchlist(movieViewParam)
        }

        llShare.setOnClickListener {
            CommonUtils.shareFilm(requireContext(), movieViewParam)
        }

        tvDetailMovie.setOnClickListener {
            // todo: detail movie
        }
    }

}
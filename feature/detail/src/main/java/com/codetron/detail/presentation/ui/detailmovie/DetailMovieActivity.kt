package com.codetron.detail.presentation.ui.detailmovie

import androidx.activity.viewModels
import com.catnip.core.base.BaseActivity
import com.codetron.detail.databinding.ActivityDetailMovieBinding

class DetailMovieActivity:BaseActivity<ActivityDetailMovieBinding, DetailMovieViewModel>(
    ActivityDetailMovieBinding::inflate
) {

    override val viewModel by viewModels<DetailMovieViewModel>()

    override fun initView() {

    }
}
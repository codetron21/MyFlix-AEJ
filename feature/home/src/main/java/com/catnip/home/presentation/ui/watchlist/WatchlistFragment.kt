package com.catnip.home.presentation.ui.watchlist

import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.catnip.core.base.BaseFragment
import com.catnip.home.R
import com.catnip.home.databinding.FragmentWatchlistBinding
import com.catnip.home.presentation.adapter.MovieAdapter
import com.catnip.home.presentation.ui.home.HomeViewModel
import com.catnip.shared.utils.ext.subscribe
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WatchlistFragment : BaseFragment<FragmentWatchlistBinding, HomeViewModel>(
    FragmentWatchlistBinding::inflate
), SwipeRefreshLayout.OnRefreshListener {

    override val viewModel: HomeViewModel by sharedViewModel()

    private val watchlistAdapter by lazy { MovieAdapter(true) {} }

    override fun initView() {
        setupRecyclerView()
        registerActionListener()
        initData()
    }

    override fun onDestroyView() {
        unregisterActionListener()
        super.onDestroyView()
    }

    override fun observeData() {
        super.observeData()
        viewModel.watchlistResult.observe(viewLifecycleOwner) {
            it.subscribe(
                doOnSuccess = { result ->
                    showLoading(false)
                    showEmptyMessage(false)
                    result.payload?.let { data ->
                        watchlistAdapter.setItems(data)
                    }
                },
                doOnLoading = {
                    showLoading(true)
                    showEmptyMessage(false)
                },
                doOnError = { error ->
                    showLoading(false)
                    showEmptyMessage(false)
                    error.exception?.let { e -> showError(true, e) }
                },
                doOnEmpty = {
                    showLoading(false)
                    showEmptyMessage(true)
                }
            )
        }
    }

    private fun registerActionListener() = with(binding) {
        srlWatchlist.setOnRefreshListener(this@WatchlistFragment)
    }

    private fun unregisterActionListener() = with(binding) {
        srlWatchlist.setOnRefreshListener(null)
    }

    private fun setupRecyclerView() = with(binding.rvWatchlist) {
        adapter = watchlistAdapter
        layoutManager = GridLayoutManager(requireContext(), LIST_SPAN_COUNT)
    }

    private fun showLoading(isShowLoading: Boolean) = with(binding) {
        pbWatchlist.isVisible = isShowLoading && srlWatchlist.isRefreshing.not()
        srlWatchlist.isRefreshing = isShowLoading && pbWatchlist.isVisible.not()
    }

    private fun showEmptyMessage(
        isShowMessage: Boolean,
        @StringRes messageRes: Int = R.string.text_empty_watchlist
    ) = with(binding.tvErrorWatchlist) {
        isVisible = isShowMessage
        text = getString(messageRes)
    }

    private fun initData() {
        viewModel.fetchWatchlist()
    }

    companion object {
        private const val LIST_SPAN_COUNT = 3
    }

    override fun onRefresh() {
        viewModel.fetchWatchlist()
        watchlistAdapter.setItems(emptyList())
    }

}
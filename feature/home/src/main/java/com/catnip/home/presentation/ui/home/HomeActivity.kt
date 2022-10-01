package com.catnip.home.presentation.ui.home


import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.catnip.core.base.BaseActivity
import com.catnip.core.listener.BottomSheetApi
import com.catnip.core.listener.NotifyListener
import com.catnip.home.R
import com.catnip.home.databinding.ActivityHomeBinding
import com.catnip.home.presentation.ui.homefeeds.HomeFeedsFragment
import com.catnip.home.presentation.ui.watchlist.WatchlistFragment
import com.catnip.shared.data.model.viewparam.MovieViewParam
import com.catnip.shared.router.BottomSheetRouter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeViewModel>(ActivityHomeBinding::inflate),
    NotifyListener<MovieViewParam> {

    private val homeFeedsFragment = HomeFeedsFragment()
    private val watchListFragment = WatchlistFragment()
    private var activeFragment: Fragment = homeFeedsFragment
    private var bottomSheetDialog: BottomSheetApi? = null
    private val dataRefreshListeners = hashSetOf<OnDataRefreshListener>()

    private val bottomSheetRouter by inject<BottomSheetRouter>()

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {
        setupFragment()
    }

    override fun onStop() {
        bottomSheetDialog?.hide()
        super.onStop()
    }

    override fun onDestroy() {
        dataRefreshListeners.clear()
        super.onDestroy()
    }

    override fun runNotify(data: MovieViewParam?) {
        data?.let {
            bottomSheetRouter.createMovieInfoBottomSheet(it).run {
                bottomSheetDialog = this
                display(supportFragmentManager, null)
                onExit(object : NotifyListener<Boolean> {
                    override fun runNotify(data: Boolean?) {
                        if (data == null || !data) return
                        dataRefreshListeners.forEach(OnDataRefreshListener::refresh)
                    }
                })
            }
        }
    }

    private fun setupFragment() {
        dataRefreshListeners.addAll(listOf(homeFeedsFragment, watchListFragment))
        // delete all fragment in fragment manager first
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        // add fragment to fragment manager
        supportFragmentManager.beginTransaction().apply {
            add(binding.container.id, homeFeedsFragment)
            add(binding.container.id, watchListFragment)
            hide(watchListFragment)
        }.commit()
        // set click menu for changing fragment
        binding.bottomNavView.setOnItemSelectedListener {
            bottomSheetDialog?.hide()

            when (it.itemId) {
                R.id.home -> {
                    showFragment(homeFeedsFragment)
                    true
                }
                else -> {
                    showFragment(watchListFragment)
                    true
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()

        activeFragment = fragment
    }
}
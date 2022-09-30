package com.catnip.myflix.router

import android.content.Context
import android.content.Intent
import com.catnip.home.presentation.ui.home.HomeActivity
import com.catnip.login.presentation.ui.LoginActivity
import com.catnip.register.presentation.ui.RegisterActivity
import com.catnip.shared.router.ActivityRouter

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ActivityRouterImpl : ActivityRouter {
    override fun loginActivity(context: Context): Intent {
        return LoginActivity.createIntent(context)
    }

    override fun homeActivity(context: Context): Intent {
        return HomeActivity.createIntent(context)
    }

    override fun registerActivity(context: Context): Intent {
        return RegisterActivity.createIntent(context)
    }
}
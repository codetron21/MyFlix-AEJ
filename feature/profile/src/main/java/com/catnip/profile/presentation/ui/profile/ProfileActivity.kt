package com.catnip.profile.presentation.ui.profile

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import com.catnip.core.base.BaseActivity
import com.catnip.core.exception.FieldErrorException
import com.catnip.profile.R
import com.catnip.profile.constants.ProfileConstants
import com.catnip.profile.databinding.ActivityProfileBinding
import com.catnip.shared.router.ActivityRouter
import com.catnip.shared.utils.ext.subscribe
import com.catnip.shared.utils.textdrawable.ColorGenerator
import com.catnip.shared.utils.textdrawable.TextDrawable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>(
    ActivityProfileBinding::inflate
) {

    override val viewModel: ProfileViewModel by viewModel()

    private val activityRouter: ActivityRouter by inject()

    override fun initView() {
        initData()
        setupActionListeners()
    }

    override fun observeData() {
        viewModel.updateUserResult.observe(this) {
            resetField()
            it.subscribe(
                doOnSuccess = { viewRes ->
                    binding.pbLoading.isVisible = false
                    val username = viewRes.payload?.username.toString()
                    setUsername(username)
                    loadPhotoUser(username)
                    Toast.makeText(this, R.string.success_update_profile, Toast.LENGTH_SHORT).show()
                },
                doOnError = { viewRes ->
                    binding.pbLoading.isVisible = false
                    binding.pbLoading.isVisible = false
                    if (viewRes.exception is FieldErrorException) {
                        handleFieldError(viewRes.exception as FieldErrorException)
                    } else {
                        viewRes.exception?.let { e -> showError(true, e) }
                    }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
            )
        }

        viewModel.logoutResult.observe(this) {
            it.subscribe(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    intentLogout()
                },
                doOnError = { viewRes ->
                    viewRes.exception?.let { e -> showError(true, e) }
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                },
            )
        }

        viewModel.currentUserResult.observe(this) {
            it.subscribe(doOnSuccess = { result ->
                val username = result.payload?.username.toString()
                setUsername(username)
                loadPhotoUser(username)
            })
        }
    }

    private fun setUsername(username:String){
        binding.etUsername.setText(username)
    }

    private fun loadPhotoUser(username: String) {
        binding.ivAvatarUser.setImageDrawable(
            TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(
                    username[0].toString(),
                    ColorGenerator.MATERIAL.randomColor
                )
        )
    }

    private fun initData() {
        viewModel.getCurrentUser()
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == ProfileConstants.FIELD_USERNAME) {
                binding.etUsername.error = getString(errorField.second)
            }
        }
    }

    private fun resetField() {
        binding.tilUsername.isErrorEnabled = false
    }

    private fun setupActionListeners() = with(binding) {
        btnUpdate.setOnClickListener {
            val username = etUsername.text.toString().trim()
            viewModel.updateProfile(username)
        }

        btnLogout.setOnClickListener { viewModel.logout() }
    }

    private fun intentLogout() {
        startActivity(
            activityRouter.loginActivity(this).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        finish()
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, ProfileActivity::class.java)
    }

}
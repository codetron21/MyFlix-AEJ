package com.catnip.core.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.catnip.core.listener.BottomSheetApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<B : ViewBinding, VM : ViewModel>(
    private val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BottomSheetDialogFragment(), BottomSheetApi {

    protected lateinit var binding: B
    protected abstract val viewModel: VM?

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = bindingFactory(layoutInflater, null, false)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)

        initView()
        observeData()

        return dialog
    }

    abstract fun initView()

    open fun observeData() {}

    open fun showToastMessage(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            requireContext(),
            message,
            duration
        ).show()
    }

}
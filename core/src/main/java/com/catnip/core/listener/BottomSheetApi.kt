package com.catnip.core.listener

import androidx.fragment.app.FragmentManager

interface BottomSheetApi : ExistListener<Boolean> {
    fun display(fm: FragmentManager, tag: String?)
    fun hide()
}
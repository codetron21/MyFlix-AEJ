package com.catnip.home.presentation.ui.home

interface ItemActionClickListener<T> {
    fun onItemClick(data: T?)
}
package com.catnip.core.listener

interface ExistListener<T> {
    fun onExit(notifyListener: NotifyListener<T>)
}
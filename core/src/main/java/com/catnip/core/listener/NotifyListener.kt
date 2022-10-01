package com.catnip.core.listener

interface NotifyListener<T> {
    fun runNotify(data: T?)
}
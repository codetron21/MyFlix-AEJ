package com.catnip.shared.utils

object MovieAttributeUtils {

    fun formatRuntime(runtime: Int): String {
        val unit = 60
        val hour = runtime / unit
        val minute = runtime % unit

        return when {
            (hour > 0 && minute > 0) -> "${hour}h ${minute}m"
            (hour > 0) -> "${hour}h"
            (minute > 0) -> "${minute}m"
            else -> "0m"
        }
    }

}
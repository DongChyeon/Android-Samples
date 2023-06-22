package com.example.composelazylayoutsample.ui.graph

import androidx.annotation.DrawableRes
import com.example.composelazylayoutsample.R

enum class Screen(
    val route: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
) {
    HOME(
        "home-route",
        R.drawable.ic_round_home_24,
        R.drawable.ic_outline_home_24
    ),
    NOTIFICATION(
        "notification-route",
        R.drawable.ic_round_notifications_24,
        R.drawable.ic_outline_notifications_24
    ),
    CHAT(
        "chat-route",
        R.drawable.ic_round_chat_bubble_24,
        R.drawable.ic_outline_chat_bubble_outline_24
    ),
    BOOKMARK(
        "bookmark-route",
        R.drawable.ic_round_bookmark_24,
        R.drawable.ic_outline_bookmark_border_24
    ),
    PROFILE(
        "profile-route",
        R.drawable.ic_round_person_24,
        R.drawable.ic_outline_person_outline_24
    );

    companion object {
        const val MAIN_GRAPH = "main-graph"
    }
}
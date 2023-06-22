package com.example.composelazylayoutsample.ui.graph

import android.view.animation.AnticipateOvershootInterpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composelazylayoutsample.ui.theme.Main


val BOTTOMBAR_HORIZONTAL_PADDING = 16.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(
    navController: NavHostController,
    bottomNavItems: Array<Screen> = Screen.values(),
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val configuration = LocalConfiguration.current

    var indicatorXoffset by remember { mutableStateOf(0.dp) }
    val animatedIndicatorXOffset = animateDpAsState(
        targetValue = indicatorXoffset,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 0,
            easing = {
                AnticipateOvershootInterpolator(2f).getInterpolation(it)
            }
        )
    )

    var dotsXoffset by remember { mutableStateOf(0.dp) }
    val animatedDotsXOffset = animateDpAsState(
        targetValue = dotsXoffset,
        animationSpec = tween(
            durationMillis = 700,
            delayMillis = 0,
        )
    )


    manageSelectedIdx(
        navBackStackEntry = navBackStackEntry,
        updateSelecetedIdx = {
            val xOffset =
                ((configuration.screenWidthDp.dp - BOTTOMBAR_HORIZONTAL_PADDING) / bottomNavItems.size * it) +
                        (configuration.screenWidthDp.dp - BOTTOMBAR_HORIZONTAL_PADDING) / 8
            indicatorXoffset = xOffset
            dotsXoffset = xOffset
        }
    )

    Box {
        Row(
            modifier = Modifier
                .padding(
                    start = BOTTOMBAR_HORIZONTAL_PADDING / 2,
                    bottom = 30.dp,
                    end = BOTTOMBAR_HORIZONTAL_PADDING / 2
                )
                .height(66.dp)
                .background(
                    Main,
                    shape = RoundedCornerShape(20.dp)
                ),
        ) {
            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                        ) {
                            if (!isSelected) {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.MAIN_GRAPH) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                ) {
                    AnimatedContent(
                        modifier = Modifier.align(Alignment.Center),
                        targetState = isSelected,
                        transitionSpec = {
                            return@AnimatedContent if (targetState) {
                                scaleIn(
                                    initialScale = 1.22f,
                                    animationSpec = tween(
                                        durationMillis = 220,
                                        delayMillis = 90
                                    )
                                ) with scaleOut(
                                    animationSpec = tween(
                                        durationMillis = 220,
                                        delayMillis = 40
                                    )
                                )
                            } else {
                                // No Animation
                                fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 0,
                                        delayMillis = 0
                                    )
                                ) with fadeOut(
                                    animationSpec = tween(durationMillis = 0)
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = (if (isSelected) screen.selectedIcon else screen.unSelectedIcon),
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }


        Canvas(
            modifier = Modifier
                .offset(
                    x = animatedIndicatorXOffset.value,
                    y = 0.dp
                )
                .align(Alignment.TopStart)
        ) {
            // 둥근 삼각형
            val triangleHeight = 15.dp.toPx()
            val triangleWidth = 50.dp.toPx()
            val trianglePath = Path().apply {
                moveTo(center.x - triangleWidth, center.y)
                lineTo(center.x - triangleWidth / 3, center.y)
                lineTo(center.x, center.y + triangleHeight)
                lineTo(center.x + triangleWidth / 3, center.y)
                lineTo(center.x + triangleWidth, center.y)
                close()
            }
            drawIntoCanvas {
                it.drawOutline(
                    outline = Outline.Generic(trianglePath),
                    paint = Paint().apply {
                        color = Color.White
                        style = PaintingStyle.Fill
                        pathEffect = PathEffect.cornerPathEffect(20f)
                    }
                )
            }
        }

        Canvas(
            modifier = Modifier
                .offset(
                    x = animatedDotsXOffset.value,
                    y = (-2).dp
                )
                .align(Alignment.TopStart)
        ) {
            drawCircle(
                color = Main,
                radius = 15f,
                center = center
            )
        }

    }
}

@Composable
private fun manageSelectedIdx(
    navBackStackEntry: NavBackStackEntry?,
    updateSelecetedIdx: (Int) -> Unit,
) {
    LaunchedEffect(navBackStackEntry) {
        updateSelecetedIdx(
            Screen.values()
                .indexOfFirst { it.route == navBackStackEntry?.destination?.route }
        )
    }
}
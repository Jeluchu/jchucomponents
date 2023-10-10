package com.jeluchu.composer.core.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.jeluchu.composer.core.utils.DestinationsIds
import com.jeluchu.composer.core.utils.NavigationIds
import com.jeluchu.composer.features.bottons.view.ButtonsView
import com.jeluchu.composer.features.dashboard.view.MainView
import com.jeluchu.composer.features.dividers.view.DividersView
import com.jeluchu.composer.features.lists.view.LazyGridsView
import com.jeluchu.composer.features.progress.view.ProgressView
import com.jeluchu.composer.features.toolbars.view.ToolbarsView
import com.jeluchu.jchucomponents.ui.composables.button.FloatingButtonPreview
import com.jeluchu.jchucomponents.ui.composables.progress.IconProgressbarPreview
import com.jeluchu.jchucomponents.ui.composables.progress.LinearProgressbarPreview
import com.jeluchu.jchucomponents.ui.composables.toolbars.CenterToolbarActionsPreview
import com.jeluchu.jchucomponents.ui.composables.toolbars.ToolbarActionsPreview
import com.jeluchu.jchucomponents.ui.foundation.lists.LazyStaticGridPreview

fun NavGraphBuilder.dashboardNav(nav: Destinations) {
    composable(Feature.DASHBOARD.nav) {
        MainView { id ->
            when (id) {
                DestinationsIds.buttons -> nav.goToButtons()
                DestinationsIds.progress -> nav.goToProgress()
                DestinationsIds.lazyGrids -> nav.goToLazyGrids()
                DestinationsIds.dividers -> nav.goToDividers()
                DestinationsIds.toolbars -> nav.goToToolbars()
            }
        }
    }
}

fun NavGraphBuilder.buttonsNav(nav: Destinations) {
    navigation(
        startDestination = Feature.BUTTONS.route,
        route = NavigationIds.buttons
    ) {
        composable(Feature.BUTTONS.nav) {
            ButtonsView { id ->
                when (id) {
                    DestinationsIds.floatingButton -> nav.goToFloatingButtons()
                }
            }
        }

        composable(Feature.FLOATING_BUTTONS.nav) {
            FloatingButtonPreview()
        }
    }
}

fun NavGraphBuilder.progressNav(nav: Destinations) {
    navigation(
        startDestination = Feature.PROGRESS.route,
        route = NavigationIds.progress
    ) {
        composable(Feature.PROGRESS.nav) {
            ProgressView { id ->
                when (id) {
                    DestinationsIds.linearProgress -> nav.goToLinearProgress()
                    DestinationsIds.iconProgress -> nav.goToIconProgress()
                }
            }
        }

        composable(Feature.LINEAR_PROGRESS.nav) {
            LinearProgressbarPreview()
        }

        composable(Feature.ICON_PROGRESS.nav) {
            IconProgressbarPreview()
        }
    }
}

fun NavGraphBuilder.lazyGridsNav(nav: Destinations) {
    navigation(
        startDestination = Feature.LAZY_GRIDS.route,
        route = NavigationIds.lazyGrids
    ) {
        composable(Feature.LAZY_GRIDS.nav) {
            LazyGridsView { id ->
                when (id) {
                    DestinationsIds.lazyStaticGrids -> nav.goToLazyStaticGrid()
                }
            }
        }

        composable(Feature.LAZY_STATIC_GRIDS.nav) {
            LazyStaticGridPreview()
        }
    }
}

fun NavGraphBuilder.dividersNav(nav: Destinations) {
    navigation(
        startDestination = Feature.DIVIDERS.route,
        route = NavigationIds.dividers
    ) {
        composable(Feature.DIVIDERS.nav) {
            DividersView()
        }
    }
}

fun NavGraphBuilder.toolbarsNav(nav: Destinations) {
    navigation(
        startDestination = Feature.TOOLBARS.route,
        route = NavigationIds.toolbars
    ) {
        composable(Feature.TOOLBARS.nav) {
            ToolbarsView { id ->
                when (id) {
                    DestinationsIds.simpleToolbars -> nav.goToSimpleToolbars()
                    DestinationsIds.centerToolbars -> nav.goToCenterToolbars()
                    DestinationsIds.largeToolbars -> nav.goToLargeToolbars()
                    DestinationsIds.back -> nav.goBack(it)
                }
            }
        }

        composable(Feature.SIMPLE_TOOLBARS.nav) {
            ToolbarActionsPreview()
        }

        composable(Feature.CENTER_TOOBARS.nav) {
            CenterToolbarActionsPreview()
        }
    }
}
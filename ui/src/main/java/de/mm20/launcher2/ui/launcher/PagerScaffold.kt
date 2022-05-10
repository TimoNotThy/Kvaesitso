package de.mm20.launcher2.ui.launcher

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import de.mm20.launcher2.ui.R
import de.mm20.launcher2.ui.ktx.toDp
import de.mm20.launcher2.ui.launcher.search.SearchBar
import de.mm20.launcher2.ui.launcher.search.SearchBarLevel
import de.mm20.launcher2.ui.launcher.search.SearchColumn
import de.mm20.launcher2.ui.launcher.search.SearchVM
import de.mm20.launcher2.ui.launcher.widgets.WidgetColumn

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScaffold(
    modifier: Modifier = Modifier,
    darkStatusBarIcons: Boolean = false,
    darkNavBarIcons: Boolean = false,
) {
    val viewModel: LauncherScaffoldVM = viewModel()
    val searchVM: SearchVM = viewModel()
    val context = LocalContext.current

    val isSearchOpen by viewModel.isSearchOpen.observeAsState(false)
    val isWidgetEditMode by viewModel.isWidgetEditMode.observeAsState(false)

    val pagerState = rememberPagerState()
    val widgetsScrollState = rememberScrollState()
    val searchScrollState = rememberScrollState()

    val currentPage = pagerState.currentPage
    LaunchedEffect(currentPage) {
        if (currentPage == 1) viewModel.openSearch()
        else viewModel.closeSearch()
    }

    LaunchedEffect(isSearchOpen) {
        if (isSearchOpen) pagerState.animateScrollToPage(1)
        else {
            pagerState.animateScrollToPage(0)
            searchVM.search("")
        }
    }

    BackHandler {
        when {
            isSearchOpen -> {
                viewModel.closeSearch()
                searchVM.search("")
            }
            isWidgetEditMode -> {
                viewModel.setWidgetEditMode(false)
            }
        }
    }


    Box(
        modifier = modifier
    ) {
        var size by remember { mutableStateOf(IntSize.Zero) }

        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = !isWidgetEditMode
        ) {
            if (it == 1) {
                val websearches by searchVM.websearchResults.observeAsState(emptyList())
                val webSearchPadding by animateDpAsState(
                    if (websearches.isEmpty()) 0.dp else 48.dp
                )
                SearchColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .onSizeChanged {
                            size = it
                        }
                        .verticalScroll(searchScrollState, reverseScrolling = true)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 56.dp)
                        .padding(bottom = webSearchPadding)
                )
            }

            if (it == 0) {
                val editModePadding by animateDpAsState(if (isWidgetEditMode) 56.dp else 0.dp)

                val showClockPadding by derivedStateOf {
                    widgetsScrollState.value == 0
                }
                val clockPadding by animateDpAsState(
                    if (showClockPadding) 64.dp else 0.dp
                )
                WidgetColumn(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(widgetsScrollState)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 64.dp)
                        .padding(top = editModePadding),
                    clockHeight = size.height.toDp() - (64.dp - clockPadding),
                    clockBottomPadding = clockPadding,
                    editMode = isWidgetEditMode,
                    onEditModeChange = {
                        viewModel.setWidgetEditMode(it)
                    }
                )
            }
        }
        AnimatedVisibility(visible = isWidgetEditMode,
            enter = slideIn { IntOffset(0, -it.height) },
            exit = slideOut { IntOffset(0, -it.height) }
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.menu_edit_widgets))
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.setWidgetEditMode(false) }) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
                    }
                },
            )
        }

        val searchBarLevel by derivedStateOf {
            when {
                pagerState.isScrollInProgress -> SearchBarLevel.Raised
                !isSearchOpen && widgetsScrollState.value == 0 -> SearchBarLevel.Resting
                isSearchOpen && searchScrollState.value == 0 -> SearchBarLevel.Active
                else -> SearchBarLevel.Raised
            }
        }

        val focusSearchBar by viewModel.searchBarFocused.observeAsState(false)

        val widgetEditModeOffset by animateDpAsState(
            if (isWidgetEditMode) 128.dp else 0.dp
        )

        SearchBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .offset(y = widgetEditModeOffset),
            level = searchBarLevel, focused = focusSearchBar, onFocusChange = {
                if (it) viewModel.openSearch()
                viewModel.setSearchbarFocus(it)
            },
            reverse = true
        )
    }

}
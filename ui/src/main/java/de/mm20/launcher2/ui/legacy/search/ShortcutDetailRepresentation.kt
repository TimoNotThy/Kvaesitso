package de.mm20.launcher2.ui.legacy.search

import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.transition.Scene
import de.mm20.launcher2.badges.BadgeRepository
import de.mm20.launcher2.icons.IconRepository
import de.mm20.launcher2.ktx.dp
import de.mm20.launcher2.ktx.lifecycleOwner
import de.mm20.launcher2.search.data.AppShortcut
import de.mm20.launcher2.search.data.Searchable
import de.mm20.launcher2.ui.R
import de.mm20.launcher2.ui.legacy.searchable.SearchableView
import de.mm20.launcher2.ui.legacy.view.FavoriteToolbarAction
import de.mm20.launcher2.ui.legacy.view.LauncherIconView
import de.mm20.launcher2.ui.legacy.view.ToolbarAction
import de.mm20.launcher2.ui.legacy.view.ToolbarView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppShortcutDetailRepresentation : Representation, KoinComponent {

    private val iconRepository: IconRepository by inject()
    private val badgeRepository: BadgeRepository by inject()

    private var job: Job? = null

    override fun getScene(
        rootView: SearchableView,
        searchable: Searchable,
        previousRepresentation: Int?
    ): Scene {
        val appShortcut = searchable as AppShortcut
        val context = rootView.context as AppCompatActivity
        val scene = Scene.getSceneForLayout(rootView, R.layout.view_application_detail, context)
        scene.setEnterAction {
            with(rootView) {
                setOnClickListener(null)
                setOnLongClickListener(null)
                findViewById<TextView>(R.id.appName).text = appShortcut.label
                findViewById<LauncherIconView>(R.id.icon).apply {
                    icon = iconRepository.getIconIfCached(appShortcut)
                    shape = LauncherIconView.currentShape
                    job = rootView.scope.launch {
                        rootView.lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            launch {
                                iconRepository.getIcon(searchable, (84 * rootView.dp).toInt())
                                    .collectLatest {
                                        icon = it
                                    }
                            }
                            launch {
                                badgeRepository.getBadge(searchable.badgeKey).collectLatest {
                                    badge = it
                                }
                            }
                            launch {
                                LauncherIconView.getDefaultShape().collectLatest {
                                    shape = it
                                }
                            }
                        }
                    }
                }
                val appName = appShortcut.appName
                findViewById<TextView>(R.id.appInfo).text =
                    context.getString(R.string.shortcut_summary, appName)

                val toolbar = findViewById<ToolbarView>(R.id.appToolbar)
                setupToolbar(this, toolbar, appShortcut)

            }
        }
        scene.setExitAction {
            job?.cancel()
        }
        return scene
    }

    private fun setupToolbar(
        searchableView: SearchableView,
        toolbar: ToolbarView,
        shortcut: AppShortcut
    ) {
        val context = searchableView.context
        val favAction = FavoriteToolbarAction(context, shortcut)
        toolbar.addAction(favAction, ToolbarView.PLACEMENT_END)

        val backAction =
            ToolbarAction(R.drawable.ic_arrow_back, context.getString(R.string.menu_back))
        backAction.clickAction = {
            searchableView.back()
        }
        toolbar.addAction(backAction, ToolbarView.PLACEMENT_START)

    }
}
package de.mm20.launcher2.ui.search

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import de.mm20.launcher2.ui.component.SectionDivider
import de.mm20.launcher2.wikipedia.WikipediaViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun wikipediaResult(): LazyListScope.() -> Unit {
    val viewModel: WikipediaViewModel = getViewModel()
    val wikipedia by viewModel.wikipedia.observeAsState()
    return {
        wikipedia?.let {
            item {
                Card(
                    elevation = 0.dp
                ) {
                    WikipediaItem(
                        wikipedia = it,
                        representation = Representation.Full,
                        initialRepresentation = Representation.Full,
                        onRepresentationChange = {}
                    )
                }
            }
            SectionDivider()
        }
    }
}
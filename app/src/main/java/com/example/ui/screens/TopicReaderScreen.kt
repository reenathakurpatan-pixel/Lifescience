package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.FormatSize
import androidx.compose.material.icons.rounded.Functions
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.TableChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CsirSyllabusCatalog
import com.example.model.PersonalNote
import com.example.model.ReaderFontScale
import com.example.model.StudyTable
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import com.example.ui.components.PersonalNoteCard
import com.example.ui.components.UnitBadge
import com.example.ui.theme.JetBrainsMonoFamily

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TopicReaderScreen(
    topicId: String,
    progress: TopicProgress?,
    topicNotes: List<PersonalNote>,
    fontScale: ReaderFontScale,
    onBack: () -> Unit,
    onToggleBookmark: () -> Unit,
    onStatusChange: (TopicStatus) -> Unit,
    onCycleFontScale: () -> Unit,
    onAddTopicNote: () -> Unit,
    onEditNote: (PersonalNote) -> Unit,
    onDeleteNote: (String) -> Unit
) {
    val topic = CsirSyllabusCatalog.findTopic(topicId)
    if (topic == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Topic not found")
        }
        return
    }
    val unit = CsirSyllabusCatalog.findUnit(topic.unitNumber)
    val accent = Color(unit?.accentHex ?: 0xFF0D4A3E)
    val isBookmarked = progress?.bookmarked ?: false
    val currentStatus = progress?.topicStatus() ?: TopicStatus.NOT_STARTED
    val scaledBodySize = (16f * fontScale.multiplier).sp
    val scaledLineHeight = (25f * fontScale.multiplier).sp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Unit ${topic.unitNumber} · ${unit?.shortTitle ?: ""}",
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("topic_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onCycleFontScale,
                        modifier = Modifier.testTag("reader_font_scale_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FormatSize,
                            contentDescription = "Cycle font size (${fontScale.label})"
                        )
                    }
                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.testTag("reader_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Rounded.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Remove bookmark" else "Bookmark topic",
                            tint = if (isBookmarked) accent else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTopicNote,
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text("Add Note") },
                modifier = Modifier.testTag("add_topic_note_fab")
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 840.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(key = "topic_header") {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            UnitBadge(unitNumber = topic.unitNumber, accentColor = accent)
                            Spacer(Modifier.width(8.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Text(
                                    text = "${topic.estimatedMinutes} min · ${fontScale.label} text",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = topic.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = topic.subtitle,
                            style = MaterialTheme.typography.titleSmall,
                            color = accent
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = topic.overview,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = scaledBodySize,
                                lineHeight = scaledLineHeight
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                item(key = "mastery_selector") {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Preparation Status",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(6.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                TopicStatus.entries.forEach { status ->
                                    FilterChip(
                                        selected = currentStatus == status,
                                        onClick = { onStatusChange(status) },
                                        label = { Text(status.label) },
                                        modifier = Modifier.testTag("status_chip_${status.name}")
                                    )
                                }
                            }
                        }
                    }
                }

                items(topic.sections, key = { it.heading }) { section ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = section.heading,
                                style = MaterialTheme.typography.titleMedium,
                                color = accent,
                                fontWeight = FontWeight.Bold
                            )
                            section.bullets.forEach { bullet ->
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(top = 9.dp)
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(accent)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text(
                                        text = bullet,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontSize = scaledBodySize,
                                            lineHeight = scaledLineHeight
                                        ),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                if (topic.comparisonTable != null) {
                    item(key = "comparison_table") {
                        StudyTableCard(table = topic.comparisonTable, accent = accent)
                    }
                }

                if (topic.keyFormulas.isNotEmpty()) {
                    item(key = "key_formulas") {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Rounded.Functions,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = "Key Equations & Numericals",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                                topic.keyFormulas.forEach { formula ->
                                    Surface(
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = formula,
                                            fontFamily = JetBrainsMonoFamily,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (topic.pyqPointers.isNotEmpty()) {
                    item(key = "pyq_pointers") {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.72f)
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Rounded.Lightbulb,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = "PYQ Traps & High-Yield Pointers",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                topic.pyqPointers.forEach { tip ->
                                    Text(
                                        text = "• $tip",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = (14.5f * fontScale.multiplier).sp,
                                            lineHeight = (21f * fontScale.multiplier).sp
                                        ),
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                            }
                        }
                    }
                }

                item(key = "topic_notes_header") {
                    Text(
                        text = "Your Notes on This Chapter (${topicNotes.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (topicNotes.isEmpty()) {
                    item(key = "topic_notes_empty") {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Tap + Add Note to jot down your own mnemonics, PYQ exceptions, or quick formulas for this chapter.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                } else {
                    items(topicNotes, key = { "topic_note_${it.id}" }) { note ->
                        PersonalNoteCard(
                            note = note,
                            onEdit = { onEditNote(note) },
                            onDelete = { onDeleteNote(note.id) }
                        )
                    }
                }

                item(key = "reader_bottom_spacer") {
                    Spacer(Modifier.height(76.dp))
                }
            }
        }
    }
}

@Composable
private fun StudyTableCard(
    table: StudyTable,
    accent: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.TableChart,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = table.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = accent
                )
            }
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .background(accent.copy(alpha = 0.12f), RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    table.headers.forEach { header ->
                        Text(
                            text = header,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .width(136.dp)
                                .padding(horizontal = 6.dp)
                        )
                    }
                }
                table.rows.forEachIndexed { idx, row ->
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        row.forEachIndexed { colIdx, cell ->
                            Text(
                                text = cell,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = if (colIdx == 0) FontWeight.SemiBold else FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .width(136.dp)
                                    .padding(horizontal = 6.dp)
                            )
                        }
                    }
                    if (idx < table.rows.lastIndex) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }
}

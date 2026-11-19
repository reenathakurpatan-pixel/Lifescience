package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.CsirSyllabusCatalog
import com.example.model.PersonalNote
import com.example.model.TopicProgress
import com.example.ui.components.PersonalNoteCard
import com.example.ui.components.TopicCard
import com.example.ui.components.UnitBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDetailScreen(
    unitNumber: Int,
    progressMap: Map<String, TopicProgress>,
    unitNotes: List<PersonalNote>,
    onBack: () -> Unit,
    onOpenTopic: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    onAddUnitNote: () -> Unit,
    onEditNote: (PersonalNote) -> Unit,
    onDeleteNote: (String) -> Unit
) {
    val unit = CsirSyllabusCatalog.findUnit(unitNumber)
    if (unit == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Unit not found")
        }
        return
    }

    val accent = Color(unit.accentHex)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Unit ${unit.unitNumber}: ${unit.shortTitle}",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("unit_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to Syllabus"
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
                onClick = onAddUnitNote,
                icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                text = { Text("Unit Note") },
                modifier = Modifier.testTag("add_unit_note_fab")
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(key = "unit_header") {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = accent.copy(alpha = 0.10f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                UnitBadge(unitNumber = unit.unitNumber, accentColor = accent)
                                Spacer(Modifier.width(8.dp))
                                Surface(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(999.dp)
                                ) {
                                    Text(
                                        text = "${unit.partFocus} · ~${unit.weightagePercent}% Exam Share",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = unit.title,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = unit.summary,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                item(key = "chapters_heading") {
                    Text(
                        text = "Study Chapters (${unit.topics.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(unit.topics, key = { it.id }) { topic ->
                    TopicCard(
                        topic = topic,
                        progress = progressMap[topic.id],
                        accentColor = accent,
                        onClick = { onOpenTopic(topic.id) },
                        onToggleBookmark = { onToggleBookmark(topic.id) }
                    )
                }

                if (unitNotes.isNotEmpty()) {
                    item(key = "unit_notes_heading") {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Your Notes for Unit ${unit.unitNumber} (${unitNotes.size})",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    items(unitNotes, key = { "unit_note_${it.id}" }) { note ->
                        PersonalNoteCard(
                            note = note,
                            onEdit = { onEditNote(note) },
                            onDelete = { onDeleteNote(note.id) },
                            onOpenTopic = onOpenTopic
                        )
                    }
                }

                item(key = "unit_bottom_spacer") {
                    Spacer(Modifier.height(72.dp))
                }
            }
        }
    }
}

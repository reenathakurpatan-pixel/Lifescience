package com.example.ui.screens

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.CsirSyllabusCatalog
import com.example.model.NoteTag
import com.example.model.PersonalNote
import com.example.model.TopicProgress
import com.example.model.TopicStatus
import com.example.ui.components.PersonalNoteCard
import com.example.ui.components.TopicCard

@Composable
fun SavedScreen(
    progressMap: Map<String, TopicProgress>,
    onOpenTopic: (String) -> Unit,
    onToggleBookmark: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val allTopics = CsirSyllabusCatalog.allTopics

    val bookmarkedTopics = remember(progressMap, allTopics) {
        allTopics.filter { progressMap[it.id]?.bookmarked == true }
    }
    val revisingTopics = remember(progressMap, allTopics) {
        allTopics.filter { progressMap[it.id]?.topicStatus() == TopicStatus.REVISING }
    }
    val shownList = if (selectedTab == 0) bookmarkedTopics else revisingTopics

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 840.dp)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Bookmarked (${bookmarkedTopics.size})") },
                    modifier = Modifier.testTag("saved_tab_bookmarks")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Needs Revision (${revisingTopics.size})") },
                    modifier = Modifier.testTag("saved_tab_revising")
                )
            }

            if (shownList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.BookmarkBorder,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = if (selectedTab == 0) "No bookmarked chapters yet" else "No chapters in revision queue",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = if (selectedTab == 0) {
                                    "Tap the bookmark icon on any CSIR chapter for one-tap revision access."
                                } else {
                                    "Set any chapter's status to Revising inside the reader to queue it here."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(shownList, key = { it.id }) { topic ->
                        val unit = CsirSyllabusCatalog.findUnit(topic.unitNumber)
                        val accent = Color(unit?.accentHex ?: 0xFF0D4A3E)
                        TopicCard(
                            topic = topic,
                            progress = progressMap[topic.id],
                            accentColor = accent,
                            onClick = { onOpenTopic(topic.id) },
                            onToggleBookmark = { onToggleBookmark(topic.id) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NotesScreen(
    notes: List<PersonalNote>,
    selectedTag: NoteTag?,
    onSelectTag: (NoteTag?) -> Unit,
    onEditNote: (PersonalNote) -> Unit,
    onDeleteNote: (String) -> Unit,
    onOpenTopic: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredNotes = remember(notes, selectedTag) {
        if (selectedTag == null) notes else notes.filter { it.noteTag() == selectedTag }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 840.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "notes_header") {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "My Revision Notes",
                            style = MaterialTheme.typography.headlineMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${notes.size} saved",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = selectedTag == null,
                            onClick = { onSelectTag(null) },
                            label = { Text("All") }
                        )
                        NoteTag.entries.forEach { tag ->
                            FilterChip(
                                selected = selectedTag == tag,
                                onClick = { onSelectTag(tag) },
                                label = { Text(tag.label) }
                            )
                        }
                    }
                }
            }

            if (filteredNotes.isEmpty()) {
                item(key = "notes_empty") {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.EditNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Build your personal CSIR cheat-sheet",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = "Tap New Note to save high-yield mnemonics, PYQ traps, or tricky formulas synced to your Google account.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(filteredNotes, key = { it.id }) { note ->
                    PersonalNoteCard(
                        note = note,
                        onEdit = { onEditNote(note) },
                        onDelete = { onDeleteNote(note.id) },
                        onOpenTopic = onOpenTopic
                    )
                }
            }

            item(key = "notes_bottom_spacer") {
                Spacer(Modifier.height(72.dp))
            }
        }
    }
}

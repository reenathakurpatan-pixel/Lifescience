package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.CsirSyllabusCatalog
import com.example.data.LocalStudyRepository
import com.example.data.ReaderPreferencesRepository
import com.example.data.StudyDatabase
import com.example.model.AppThemeMode
import com.example.model.PersonalNote
import com.example.model.ReaderFontScale
import com.example.model.UiState
import com.example.ui.components.NoteEditorDialog
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SyllabusHomeScreen
import com.example.ui.screens.TopicReaderScreen
import com.example.ui.screens.UnitDetailScreen
import com.example.ui.theme.BioPrepTheme
import com.example.ui.viewmodel.StudyViewModel

const val ROUTE_SYLLABUS = "syllabus"
const val ROUTE_SAVED = "saved"
const val ROUTE_NOTES = "notes"
const val ROUTE_PROGRESS = "progress"
const val ROUTE_UNIT = "unit/{unitNumber}"
const val ROUTE_TOPIC = "topic/{topicId}"

private data class TopTab(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val topTabs = listOf(
    TopTab(ROUTE_SYLLABUS, R.string.nav_syllabus, Icons.Rounded.MenuBook, Icons.Outlined.MenuBook, "tab_syllabus"),
    TopTab(ROUTE_SAVED, R.string.nav_saved, Icons.Rounded.Bookmark, Icons.Outlined.BookmarkBorder, "tab_saved"),
    TopTab(ROUTE_NOTES, R.string.nav_notes, Icons.Rounded.EditNote, Icons.Outlined.EditNote, "tab_notes"),
    TopTab(ROUTE_PROGRESS, R.string.nav_progress, Icons.Rounded.Analytics, Icons.Outlined.Analytics, "tab_progress")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val prefsRepo = remember(context) { ReaderPreferencesRepository(context.applicationContext) }
            val themeMode by prefsRepo.themeMode.collectAsStateWithLifecycle(initialValue = AppThemeMode.SYSTEM)
            val isDark = when (themeMode) {
                AppThemeMode.SYSTEM -> isSystemInDarkTheme()
                AppThemeMode.LIGHT -> false
                AppThemeMode.DARK -> true
            }
            BioPrepTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BioPrepStudyApp()
                }
            }
        }
    }
}

private data class NoteEditorRequest(
    val existingNote: PersonalNote? = null,
    val defaultUnitNumber: Int = 1,
    val defaultTopicId: String? = null,
    val defaultTopicTitle: String? = null
)

@Composable
private fun BioPrepStudyApp() {
    val viewModel: StudyViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) {
                    "APPLICATION_KEY missing from CreationExtras"
                }
                val database = StudyDatabase.getInstance(app)
                StudyViewModel(
                    studyRepository = LocalStudyRepository(database.studyDao()),
                    prefsRepository = ReaderPreferencesRepository(app)
                )
            }
        }
    )

    val progressState by viewModel.progressState.collectAsStateWithLifecycle()
    val notesState by viewModel.notesState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedNoteTag by viewModel.selectedNoteTag.collectAsStateWithLifecycle()
    val fontScale by viewModel.fontScale.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(viewModel) {
        viewModel.toastEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: ROUTE_SYLLABUS
    val isTopLevelRoute = topTabs.any { it.route == currentRoute }

    var editorRequest by remember { mutableStateOf<NoteEditorRequest?>(null) }

    val progressMap = (progressState as? UiState.Success)?.data.orEmpty()
    val notesList = (notesState as? UiState.Success)?.data.orEmpty()
    val storageError = (progressState as? UiState.Error)?.message
        ?: (notesState as? UiState.Error)?.message

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 680.dp

        Scaffold(
            contentWindowInsets = WindowInsets.navigationBars,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (isTopLevelRoute && !useNavigationRail) {
                    NavigationBar {
                        topTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationBarItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(tab.route) {
                                        popUpTo(ROUTE_SYLLABUS) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = stringResource(tab.labelRes)
                                    )
                                },
                                label = { Text(stringResource(tab.labelRes)) },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                if (currentRoute == ROUTE_NOTES) {
                    ExtendedFloatingActionButton(
                        onClick = { editorRequest = NoteEditorRequest() },
                        icon = { Icon(Icons.Rounded.Add, contentDescription = null) },
                        text = { Text(stringResource(R.string.add_note)) },
                        modifier = Modifier.testTag("add_note_fab")
                    )
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isTopLevelRoute && useNavigationRail) {
                    NavigationRail(modifier = Modifier.fillMaxHeight()) {
                        topTabs.forEach { tab ->
                            val selected = currentRoute == tab.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = {
                                    navController.navigate(tab.route) {
                                        popUpTo(ROUTE_SYLLABUS) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = stringResource(tab.labelRes)
                                    )
                                },
                                label = { Text(stringResource(tab.labelRes)) },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .then(if (isTopLevelRoute) Modifier.statusBarsPadding() else Modifier)
                ) {
                    if (storageError != null) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                text = "Storage notice: $storageError",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_SYLLABUS,
                        modifier = Modifier.weight(1f)
                    ) {
                        composable(ROUTE_SYLLABUS) {
                            SyllabusHomeScreen(
                                searchQuery = searchQuery,
                                onSearchQueryChange = viewModel::updateSearchQuery,
                                progressMap = progressMap,
                                onOpenUnit = { unitNum ->
                                    navController.navigate("unit/$unitNum")
                                },
                                onOpenTopic = { topicId ->
                                    navController.navigate("topic/$topicId")
                                },
                                onToggleBookmark = viewModel::toggleBookmark
                            )
                        }

                        composable(ROUTE_SAVED) {
                            SavedScreen(
                                progressMap = progressMap,
                                onOpenTopic = { topicId ->
                                    navController.navigate("topic/$topicId")
                                },
                                onToggleBookmark = viewModel::toggleBookmark
                            )
                        }

                        composable(ROUTE_NOTES) {
                            NotesScreen(
                                notes = notesList,
                                selectedTag = selectedNoteTag,
                                onSelectTag = viewModel::selectNoteTag,
                                onEditNote = { note ->
                                    editorRequest = NoteEditorRequest(existingNote = note)
                                },
                                onDeleteNote = viewModel::deleteNote,
                                onOpenTopic = { topicId ->
                                    navController.navigate("topic/$topicId")
                                }
                            )
                        }

                        composable(ROUTE_PROGRESS) {
                            ProgressScreen(
                                progressMap = progressMap,
                                notesCount = notesList.size,
                                fontScale = fontScale,
                                themeMode = themeMode,
                                onSelectFontScale = viewModel::setFontScale,
                                onSelectThemeMode = viewModel::setThemeMode,
                                onOpenUnit = { unitNum ->
                                    navController.navigate("unit/$unitNum")
                                }
                            )
                        }

                        composable(
                            route = ROUTE_UNIT,
                            arguments = listOf(navArgument("unitNumber") { type = NavType.IntType })
                        ) { entry ->
                            val unitNum = entry.arguments?.getInt("unitNumber") ?: 1
                            val unitNotes = notesList.filter { it.unitNumber == unitNum }
                            UnitDetailScreen(
                                unitNumber = unitNum,
                                progressMap = progressMap,
                                unitNotes = unitNotes,
                                onBack = { navController.popBackStack() },
                                onOpenTopic = { topicId ->
                                    navController.navigate("topic/$topicId")
                                },
                                onToggleBookmark = viewModel::toggleBookmark,
                                onAddUnitNote = {
                                    editorRequest = NoteEditorRequest(defaultUnitNumber = unitNum)
                                },
                                onEditNote = { note ->
                                    editorRequest = NoteEditorRequest(existingNote = note)
                                },
                                onDeleteNote = viewModel::deleteNote
                            )
                        }

                        composable(
                            route = ROUTE_TOPIC,
                            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
                        ) { entry ->
                            val topicId = entry.arguments?.getString("topicId") ?: ""
                            val topic = CsirSyllabusCatalog.findTopic(topicId)
                            val topicNotes = notesList.filter { it.topicId == topicId }
                            TopicReaderScreen(
                                topicId = topicId,
                                progress = progressMap[topicId],
                                topicNotes = topicNotes,
                                fontScale = fontScale,
                                onBack = { navController.popBackStack() },
                                onToggleBookmark = { viewModel.toggleBookmark(topicId) },
                                onStatusChange = { status -> viewModel.setTopicStatus(topicId, status) },
                                onCycleFontScale = {
                                    val next = when (fontScale) {
                                        ReaderFontScale.COMPACT -> ReaderFontScale.COMFORTABLE
                                        ReaderFontScale.COMFORTABLE -> ReaderFontScale.LARGE
                                        ReaderFontScale.LARGE -> ReaderFontScale.COMPACT
                                    }
                                    viewModel.setFontScale(next)
                                },
                                onAddTopicNote = {
                                    editorRequest = NoteEditorRequest(
                                        defaultUnitNumber = topic?.unitNumber ?: 1,
                                        defaultTopicId = topic?.id,
                                        defaultTopicTitle = topic?.title
                                    )
                                },
                                onEditNote = { note ->
                                    editorRequest = NoteEditorRequest(existingNote = note)
                                },
                                onDeleteNote = viewModel::deleteNote
                            )
                        }
                    }
                }
            }
        }
    }

    val activeEditor = editorRequest
    if (activeEditor != null) {
        NoteEditorDialog(
            initialNote = activeEditor.existingNote,
            defaultUnitNumber = activeEditor.defaultUnitNumber,
            defaultTopicId = activeEditor.defaultTopicId,
            defaultTopicTitle = activeEditor.defaultTopicTitle,
            onDismiss = { editorRequest = null },
            onSave = { unitNum, topicId, topicTitle, title, content, tag ->
                viewModel.saveNote(
                    existingNoteId = activeEditor.existingNote?.id,
                    unitNumber = unitNum,
                    topicId = topicId,
                    topicTitle = topicTitle,
                    title = title,
                    content = content,
                    tag = tag
                )
                editorRequest = null
            }
        )
    }
}

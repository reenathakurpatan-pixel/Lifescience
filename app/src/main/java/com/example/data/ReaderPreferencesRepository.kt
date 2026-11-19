package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.AppThemeMode
import com.example.model.ReaderFontScale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.readerDataStore: DataStore<Preferences> by preferencesDataStore(name = "reader_prefs")

class ReaderPreferencesRepository(private val context: Context) {
    private object Keys {
        val FONT_SCALE = stringPreferencesKey("font_scale")
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val fontScale: Flow<ReaderFontScale> = context.readerDataStore.data.map { prefs ->
        ReaderFontScale.fromRaw(prefs[Keys.FONT_SCALE])
    }

    val themeMode: Flow<AppThemeMode> = context.readerDataStore.data.map { prefs ->
        AppThemeMode.fromRaw(prefs[Keys.THEME_MODE])
    }

    suspend fun setFontScale(scale: ReaderFontScale) {
        context.readerDataStore.edit { prefs ->
            prefs[Keys.FONT_SCALE] = scale.name
        }
    }

    suspend fun setThemeMode(mode: AppThemeMode) {
        context.readerDataStore.edit { prefs ->
            prefs[Keys.THEME_MODE] = mode.name
        }
    }
}

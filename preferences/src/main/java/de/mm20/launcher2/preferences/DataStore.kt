package de.mm20.launcher2.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import de.mm20.launcher2.crashreporter.CrashReporter
import de.mm20.launcher2.preferences.migrations.*

typealias LauncherDataStore = DataStore<Settings>

internal val Context.dataStore: LauncherDataStore by dataStore(
    fileName = "settings.pb",
    serializer = SettingsSerializer,
    produceMigrations = {
        getMigrations(it)
    },
    corruptionHandler = ReplaceFileCorruptionHandler {
        CrashReporter.logException(it)
        Settings.getDefaultInstance()
    }
)

internal const val SchemaVersion = 7

internal fun getMigrations(context: Context): List<DataMigration<Settings>> {
    return listOf(
        FactorySettingsMigration(context),
        Migration_1_2(),
        Migration_2_3(),
        Migration_3_4(),
        Migration_4_5(),
        Migration_5_6(),
        Migration_6_7(),
    )
}
package de.mm20.launcher2.preferences

import android.content.Context
import scheme.Scheme

fun createFactorySettings(context: Context): Settings {
    return Settings.newBuilder()
        .setAppearance(
            Settings.AppearanceSettings
                .newBuilder()
                .setTheme(Settings.AppearanceSettings.Theme.System)
                .setColorScheme(Settings.AppearanceSettings.ColorScheme.Default)
                .setDimWallpaper(false)
                .setCustomColors(Settings.AppearanceSettings.CustomColors.newBuilder()
                    .setAdvancedMode(false)
                    .setBaseColors(DefaultCustomColorsBase)
                    .setLightScheme(DefaultLightCustomColorScheme)
                    .setDarkScheme(DefaultDarkCustomColorScheme)
                )
                .build()
        )
        .setWeather(
            Settings.WeatherSettings
                .newBuilder()
                .setProvider(Settings.WeatherSettings.WeatherProvider.MetNo)
                .setImperialUnits(context.resources.getBoolean(R.bool.default_imperialUnits))
                .build()
        )
        .setMusicWidget(
            Settings.MusicWidgetSettings
                .newBuilder()
                .setFilterSources(true)
                .build()
        )
        .setCalendarWidget(
            Settings.CalendarWidgetSettings
                .newBuilder()
                .setHideAlldayEvents(false)
        )
        .setClockWidget(
            Settings.ClockWidgetSettings
                .newBuilder()
                .setLayout(Settings.ClockWidgetSettings.ClockWidgetLayout.Vertical)
                .setClockStyle(Settings.ClockWidgetSettings.ClockStyle.DigitalClock1)
                .setAlarmPart(true)
                .setBatteryPart(true)
                .setDatePart(true)
                .setMusicPart(true)
                .setFavoritesPart(false)
                .build()
        )
        .setFavorites(
            Settings.FavoritesSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setFileSearch(
            Settings.FilesSearchSettings
                .newBuilder()
                .setLocalFiles(true)
                .setNextcloud(false)
                .setGdrive(false)
                .setOnedrive(false)
                .setNextcloud(false)
        )
        .setContactsSearch(
            Settings.ContactsSearchSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setCalendarSearch(
            Settings.CalendarSearchSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setAppShortcutSearch(
            Settings.AppShortcutSearchSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setCalculatorSearch(
            Settings.CalculatorSearchSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setUnitConverterSearch(
            Settings.UnitConverterSearchSettings
                .newBuilder()
                .setEnabled(true)
                .setCurrencies(true)
        )
        .setWikipediaSearch(
            Settings.WikipediaSearchSettings
                .newBuilder()
                .setEnabled(false)
                .setImages(false)
                .setCustomUrl("")
        )
        .setWebsiteSearch(
            Settings.WebsiteSearchSettings
                .newBuilder()
                .setEnabled(false)
        )
        .setWebSearch(
            Settings.WebSearchSettings
                .newBuilder()
                .setEnabled(true)
        )
        .setBadges(
            Settings.BadgeSettings
                .newBuilder()
                .setNotifications(true)
                .setCloudFiles(true)
                .setShortcuts(true)
                .setSuspendedApps(true)
        )
        .setGrid(
            Settings.GridSettings.newBuilder()
                .setColumnCount(context.resources.getInteger(R.integer.config_columnCount))
                .setIconSize(48)
                .build()
        )
        .setSearchBar(
            Settings.SearchBarSettings.newBuilder()
                .setSearchBarStyle(Settings.SearchBarSettings.SearchBarStyle.Transparent)
                .setAutoFocus(false)
                .build()
        )
        .setIcons(
            Settings.IconSettings.newBuilder()
                .setAdaptify(true)
                .setShape(Settings.IconSettings.IconShape.PlatformDefault)
                .setThemedIcons(false)
                .setIconPack("")
        )
        .setEasterEgg(false)
        .setSystemBars(
            Settings.SystemBarsSettings.newBuilder()
                .setLightNavBar(false)
                .setLightStatusBar(false)
                .setHideStatusBar(false)
                .setHideNavBar(false)
        )
        .setCards(
            Settings.CardSettings.newBuilder()
                .setBorderWidth(0)
                .setRadius(8)
                .setOpacity(1f)
        )
        .build()
}

internal val DefaultCustomColorsBase: Settings.AppearanceSettings.CustomColors.BaseColors
get() {
    val scheme = Scheme.light(0xFFACE330.toInt())
    return Settings.AppearanceSettings.CustomColors.BaseColors.newBuilder()
        .setAccent1(scheme.primary)
        .setAccent2(scheme.secondary)
        .setAccent3(scheme.tertiary)
        .setNeutral1(scheme.surface)
        .setNeutral2(scheme.surfaceVariant)
        .setError(scheme.error)
        .build()
}

internal val DefaultLightCustomColorScheme: Settings.AppearanceSettings.CustomColors.Scheme
    get() {
        val scheme = Scheme.light(0xFFACE330.toInt())
        return Settings.AppearanceSettings.CustomColors.Scheme.newBuilder()
            .setPrimary(scheme.primary)
            .setOnPrimary(scheme.onPrimary)
            .setPrimaryContainer(scheme.primaryContainer)
            .setOnPrimaryContainer(scheme.onPrimaryContainer)
            .setSecondary(scheme.secondary)
            .setOnSecondary(scheme.onSecondary)
            .setSecondaryContainer(scheme.secondaryContainer)
            .setOnSecondaryContainer(scheme.onSecondaryContainer)
            .setTertiary(scheme.tertiary)
            .setOnTertiary(scheme.onTertiary)
            .setTertiaryContainer(scheme.tertiaryContainer)
            .setOnTertiaryContainer(scheme.onTertiaryContainer)
            .setBackground(scheme.background)
            .setOnBackground(scheme.onBackground)
            .setSurface(scheme.surface)
            .setOnSurface(scheme.onSurface)
            .setSurfaceVariant(scheme.surfaceVariant)
            .setOnSurfaceVariant(scheme.onSurfaceVariant)
            .setError(scheme.error)
            .setOnError(scheme.onError)
            .setErrorContainer(scheme.errorContainer)
            .setOnErrorContainer(scheme.onErrorContainer)
            .setInverseSurface(scheme.inverseSurface)
            .setInverseOnSurface(scheme.inverseOnSurface)
            .setInversePrimary(scheme.inversePrimary)
            .setOutline(scheme.outline)
            .build()
    }

internal val DefaultDarkCustomColorScheme: Settings.AppearanceSettings.CustomColors.Scheme
    get() {
        val scheme = Scheme.dark(0xFFACE330.toInt())
        return Settings.AppearanceSettings.CustomColors.Scheme.newBuilder()
            .setPrimary(scheme.primary)
            .setOnPrimary(scheme.onPrimary)
            .setPrimaryContainer(scheme.primaryContainer)
            .setOnPrimaryContainer(scheme.onPrimaryContainer)
            .setSecondary(scheme.secondary)
            .setOnSecondary(scheme.onSecondary)
            .setSecondaryContainer(scheme.secondaryContainer)
            .setOnSecondaryContainer(scheme.onSecondaryContainer)
            .setTertiary(scheme.tertiary)
            .setOnTertiary(scheme.onTertiary)
            .setTertiaryContainer(scheme.tertiaryContainer)
            .setOnTertiaryContainer(scheme.onTertiaryContainer)
            .setBackground(scheme.background)
            .setOnBackground(scheme.onBackground)
            .setSurface(scheme.surface)
            .setOnSurface(scheme.onSurface)
            .setSurfaceVariant(scheme.surfaceVariant)
            .setOnSurfaceVariant(scheme.onSurfaceVariant)
            .setError(scheme.error)
            .setOnError(scheme.onError)
            .setErrorContainer(scheme.errorContainer)
            .setOnErrorContainer(scheme.onErrorContainer)
            .setInverseSurface(scheme.inverseSurface)
            .setInverseOnSurface(scheme.inverseOnSurface)
            .setInversePrimary(scheme.inversePrimary)
            .setOutline(scheme.outline)
            .build()
    }
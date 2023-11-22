package com.example.skysnap.screens

import androidx.annotation.StringRes
import com.example.skysnap.R

enum class OverviewScreens(@StringRes val title: Int) {
    Start(title = R.string.overview_region),
    CountryOverview(title = R.string.overview_country),
    CityOverview(title = R.string.overview_city),
    AppInfo(title= R.string.info_app),
    WeekOverview(title = R.string.overview_week),
    DayOverview(title = R.string.overview_day),
    Starred(title = R.string.overview_starred)
}
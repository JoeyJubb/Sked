package uk.co.bubblebearapps.presentation.util

import org.threeten.bp.ZonedDateTime

interface DateTimeFormatter {
    fun formatMediumDate(instant: ZonedDateTime): String
}
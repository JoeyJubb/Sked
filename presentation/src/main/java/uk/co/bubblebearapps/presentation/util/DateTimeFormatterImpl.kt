package uk.co.bubblebearapps.presentation.util

import android.content.Context
import android.text.format.DateFormat
import org.threeten.bp.ZonedDateTime
import uk.co.bubblebearapps.di.ActivityContext
import java.text.SimpleDateFormat
import javax.inject.Inject

class DateTimeFormatterImpl @Inject constructor(@ActivityContext context: Context) :
        DateTimeFormatter {

    private val mediumFormat =
            org.threeten.bp.format.DateTimeFormatter.ofPattern((DateFormat.getMediumDateFormat(context) as SimpleDateFormat).toPattern())

    override fun formatMediumDate(instant: ZonedDateTime): String = mediumFormat.format(instant)


}

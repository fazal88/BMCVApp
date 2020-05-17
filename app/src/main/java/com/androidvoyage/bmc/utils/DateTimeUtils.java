package com.androidvoyage.bmc.utils;

import android.util.Log;

import com.androidvoyage.bmc.common.CommonConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author Siddhesh
 * @Contributor Mayuri
 */

public class DateTimeUtils {


    /**
     * returns true if compareDate is after start(date)
     *
     * @param date
     * @param dateToCompare
     * @return
     */
    public static boolean isAfterDate(String date, String dateToCompare) {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
        Date strDate = null, compareDate = null;
        try {
            compareDate = sdf.parse(dateToCompare);
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (compareDate != null && strDate != null
                && (compareDate.after(strDate)));


    }


    public static boolean isOneDayEvent(String date, String dateToCompare) {

        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());
        long oneDay = 1000 * 60 * 60 * 24;
        long startLongTime = 0, endLongTime = 0;

        Date strDate = null, compareDate = null;
        try {
            compareDate = sdf.parse(dateToCompare);
            startLongTime = compareDate.getTime();
            strDate = sdf.parse(date);
            endLongTime = strDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = startLongTime - endLongTime;
        return diff < oneDay;

    }

    public static boolean isSameDate(String date, String dateToCompare) {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
        Date strDate = null, compareDate = null;
        try {
            compareDate = sdf.parse(dateToCompare);
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compareDate.compareTo(strDate) == 0;

    }

    public static boolean isAfterTime(String date, String dateToCompare) {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.ENGLISH);
        Date strDate = null, compareDate = null;
        try {
            compareDate = sdf.parse(dateToCompare);
            strDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (strDate.getTime() <= compareDate.getTime());
    }

    public static boolean isOldDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT);
            Date strDate = sdf.parse(date);
            return new Date().before(strDate);
        } catch (ParseException e) {
            Log.d("CATH OLD DATE", "isOldDate: " + e.getMessage());
        }
        return false;
    }

    public static boolean isOldReadableDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT);
            Date strDate = sdf.parse(date);
            return strDate.before(new Date());
        } catch (ParseException e) {
            Log.d("CATH OLD DATE", "isOldDate: " + e.getMessage());
        }
        return false;
    }

    public static boolean isOldTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
            Calendar now = Calendar.getInstance();
            Date c_date = sdf.parse(now.get(Calendar.HOUR) + ":"
                    + now.get(Calendar.MINUTE)
                    + " " + ((now.get(Calendar.AM_PM) == Calendar.AM) ? "am" : "pm"));
            return sdf.parse(time).before(c_date);
        } catch (ParseException e) {
            return false;
        }
    }

    /*
     * @author Mayuri*/

    public static String convertUTCToReadableDateTimeFormat(String UTCFormatDateTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(UTCFormatDateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatDateTime;
        } catch (NullPointerException e) {
            e.printStackTrace();
            date = UTCFormatDateTime;
        }
        return date;
    }

    public static String convertReadableDateTimeToUTCFormat(String ReadableFormatDateTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());

        try {
            Date d1 = format.parse(ReadableFormatDateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
//            sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatDateTime;
        }
        return date;
    }

    public static String getUTCDateTimeFormat(String ReadableFormatDateTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());

        try {
            Date d1 = format.parse(ReadableFormatDateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatDateTime;
        }
        return date;
    }

    public static String getFormattedDateTime(String dateTime, String fromFormat, String toFormat, boolean toUTC) {
        String date = null;
        DateFormat format = new SimpleDateFormat(fromFormat, Locale.getDefault());

        try {
            Date d1 = format.parse(dateTime);
            SimpleDateFormat sdf = new SimpleDateFormat(toFormat, Locale.getDefault());
            if (toUTC) {
                sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            }
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = dateTime;
        }
        return date;
    }

    public static String convertUTCDateToReadableDateFormat(String UTCFormatDate) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(UTCFormatDate);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatDate;
        }
        return date;
    }

    public static String convertReadableDateToUTCDateFormat(String UTCFormatDate) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(UTCFormatDate);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_FORMAT, Locale.getDefault());
//            sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatDate;
        }
        return date;
    }

    public static String convertReadableDateToUTCDateTimeFormat(String UTCFormatDate) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());

        try {
            Date d1 = format.parse(UTCFormatDate);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
//            sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatDate;
        }
        return date;
    }

    public static String convertUTCTimeToReadableTimeFormat(String UTCFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(UTCFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatTime;
        }
        return date;
    }


    public static String convertUTCTimeDateToReadableTimeFormat(String UTCFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(UTCFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatTime;
        }
        return date;
    }

    public static String convertUTCTimeDateToReadableDateFormat(String UTCFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(UTCFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatTime;
        }
        return date;
    }

    public static String convertReadableTimeToUTCTimeFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_TIME_FORMAT, Locale.getDefault());
//            sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    public static String getReadableDateFromUTCDateTimeFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    public static String getReadableTimeFromUTCDateTimeFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    /**
     * @param ReadableFormatTime : CommonConstants.DATE_TIME_PICKER_FORMAT
     * @return : CommonConstants.READABLE_DATE_TIME_FORMAT in string format
     * @author Mayuri
     * should be used only from Date time picker,
     * always check incoming format
     * keep CommonConstants.DATE_TIME_PICKER_FORMAT as incoming format
     */
    public static String convertDateTimePickerToReadableDateTimeFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.DATE_TIME_PICKER_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    /**
     * @param ReadableFormatTime : CommonConstants.DATE_PICKER_FORMAT
     * @return : CommonConstants.READABLE_DATE_FORMAT in string format
     * @author Mayuri
     * should be used only from Date time picker,
     * always check incoming format
     * keep CommonConstants.DATE_PICKER_FORMAT as incoming format
     */
    public static String convertDatePickerToReadableDateFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.DATE_PICKER_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    /**
     * @param ReadableFormatTime : CommonConstants.DATE_PICKER_FORMAT
     * @return : CommonConstants.READABLE_DATE_FORMAT in string format
     * @author Mayuri
     * should be used only from Date time picker,
     * always check incoming format
     * keep CommonConstants.DATE_PICKER_FORMAT as incoming format
     */
    public static String convertTimePickerToReadableTimeFormat(String ReadableFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.TIME_PICKER_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(ReadableFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableFormatTime;
        }
        return date;
    }

    public static Date getDateFromUTCString(String UTCFormatDate) {
        Date date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        try {
            date = format.parse(UTCFormatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentReadableDateTimeFormat() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_TIME_FORMAT, Locale.getDefault());
        return sdf.format(currentTime);
    }

    public static String getCurrentUTCDateTimeFormat() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(CommonConstants.UTC));
        return sdf.format(currentTime);
    }

    public static String getCurrentReadableDateFormat() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
        return sdf.format(currentTime);
    }

    public static String getCurrentReadableTimeFormat() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
        return sdf.format(currentTime);
    }

    public static String hoursFormat(String ReadableHours) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.READABLE_TIME_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(ReadableHours);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.HOURS_PICKER_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = ReadableHours;
        }
        return date;
    }

    public static long getTimeDifference(String startDate) {
        Date eData = getDateFromUTCString(startDate);
        return Calendar.getInstance().getTime().getTime() - eData.getTime();
    }

    public static int getTimeDifference(Date startDate) {

        Calendar a = Calendar.getInstance();
        a.setTime(startDate);

        Calendar b = Calendar.getInstance();

        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static String getAgeFromDate(int dob) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, - dob);
        c.add(Calendar.DAY_OF_MONTH, - 1); // removed extra day to get approx age grater then 18.
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());

        return sdf.format(c.getTime());
    }

    public static Calendar getSelectedDate(String apiDateTime) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat parser = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT);
        Date date;
        try {
            date = parser.parse(apiDateTime);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public static Calendar getReadableDate(String apiDateTime) {
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT).parse(apiDateTime);
            calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public static String convertUTCTimeDateToReadableDateFormatWithoutFormat(String UTCFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(UTCFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.READABLE_DATE_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatTime;
        }
        return date;
    }

    public static String convertUTCDateToUTCTimeDateTimeWithoutFormat(String UTCFormatTime) {
        String date = null;
        DateFormat format = new SimpleDateFormat(CommonConstants.UTC_DATE_FORMAT, Locale.getDefault());
        try {
            Date d1 = format.parse(UTCFormatTime);
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.UTC_DATE_TIME_FORMAT, Locale.getDefault());
            date = sdf.format(d1);
        } catch (ParseException e) {
            e.printStackTrace();
            date = UTCFormatTime;
        }
        return date;
    }
}

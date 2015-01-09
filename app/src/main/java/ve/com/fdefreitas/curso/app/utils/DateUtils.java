package ve.com.fdefreitas.curso.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fernando on 15/10/14.
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    public static SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat appDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String parseToServerDateFormat(String appDate){
        try {
            Date date = appDateFormat.parse(appDate);
            return serverDateFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Couldn't parse date");
            e.printStackTrace();
        }

        return null;
    }

    public static String parseToServerDateFormat(Date appDate){
        return serverDateFormat.format(appDate);
    }

    public static Date parseFromServerDateFormat(String dateStr){
        if(dateStr != null) {
            try {
                return serverDateFormat.parse(dateStr);
            } catch (ParseException e) {
                Log.e(TAG, "Couldn't parse server date" + dateStr, e);
            }
        }
        return null;
    }
}

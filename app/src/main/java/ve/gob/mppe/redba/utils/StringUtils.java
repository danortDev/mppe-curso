package ve.gob.mppe.redba.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by fernando on 15/10/14.
 */
public class StringUtils {

    private static final String TAG = StringUtils.class.getSimpleName();
    public static final int MIN_PASSWORD_LENGTH = 5;
    private static final Pattern NAME_REGEX = Pattern.compile("([A-Za-zñÑáÁéÉíÍóúÚ '-]+)");

    public static String capitalize(String msg){
        String result = "";
        if(msg.length() > 0){
            result = msg.substring(0,1).toUpperCase();
        }
        if(msg.length() > 1){
            result = result + msg.substring(1).toLowerCase();
        }
        return result;
    }

    public static boolean isValidStr(String text){
        return !TextUtils.isEmpty(text);
    }

    public static boolean isValidName(String name){
        return isValidStr(name) && NAME_REGEX.matcher(name).matches();
    }

    public static boolean isValidGender(String text, String defaultStr){
        return isValidStr(text) && !text.equals(defaultStr);
    }

    public static boolean isValidState(String text, String defaultStr){
        return isValidStr(text) && !text.equals(defaultStr);
    }

    public static boolean isValidCountry(String text, String defaultStr){
        return isValidStr(text) && !text.equals(defaultStr);
    }

    public static boolean isValidBirthDate(String date){
        return isValidStr(date) && isValidStr(DateUtils.parseToServerDateFormat(date));
    }

    public static boolean isValidEmail(String email){
        return isValidStr(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        return isValidStr(password) && (password.length() >= MIN_PASSWORD_LENGTH);
    }

    public static boolean isMatch(String msg, String reMsg){
        return isValidStr(msg) && isValidStr(reMsg)
                && msg.equals(reMsg);
    }

}


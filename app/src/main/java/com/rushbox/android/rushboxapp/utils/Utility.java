package com.rushbox.android.rushboxapp.utils;

import com.google.gson.Gson;
import com.rushbox.android.rushboxapp.connections.ResponseOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronner on 06-04-2016.
 */
public class Utility {
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String PATTERN_PHONE = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    public static final String PATTERN_PASSWORD = "^.*(?=.{8,})(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!#$%&? \"]).*$";
    private static Gson gson = new Gson();

    public static boolean isValid(String patternType, String string) {
        try {
            Pattern pattern = Pattern.compile(patternType);
            Matcher matcher = pattern.matcher(string);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResponseOperation JSONObjectToResponseOperation(JSONObject object) {
        try {
            ResponseOperation response = new ResponseOperation(object.getBoolean("ResultOperation"), object.getString("Message"));
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String transfromInnerDate(String jsonString) {
        String jsonReturn = null;
        int starDate = jsonString.indexOf("/Date(");
        int endDate = jsonString.indexOf(")\\/");
        String start = jsonString.substring(0, starDate - 1);
        String end = jsonString.substring(endDate + 3);
        String thisDate = jsonString.substring(starDate - 1, endDate + 3);
        try {
            if (!thisDate.isEmpty()) {
                thisDate = thisDate.replace("\\/Date(", "").replace(")\\/", "");
                int pIndex = thisDate.indexOf("+");
                if (pIndex < 0) pIndex = thisDate.indexOf("-");
                long millisec = 0;
                thisDate = thisDate.substring(0, pIndex);
                millisec = Long.parseLong(thisDate);
                Date realDate = new Date(millisec);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
                thisDate = dateFormat.format(realDate);
                jsonReturn = (start + thisDate + end);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonReturn;
    }
}

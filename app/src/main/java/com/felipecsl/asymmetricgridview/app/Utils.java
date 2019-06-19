package com.felipecsl.asymmetricgridview.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static android.content.Context.ACTIVITY_SERVICE;

/*
 Created by velu on 20/1/18.
 */

public class Utils {

    //Date Format
    public static final String UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DD_MMM_YYYY_HH_MM_A = "dd MMM yyyy HH:mm a";
    public static final String DD_MMM_YYYY_HH_MMA = "dd MMM yyyy HH:mma";
    public static final String DD_MMMM_YYYY = "dd MMMM yyyy";
    public static final String DD_MMM_YYYY = "dd, MMM yyyy";
    public static final String DDMMMYYYY = "dd-MMM-yyyy";
    public static final String DD_MMM_YYYY_1 = "dd MMM, yyyy";
    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_YYYY = "MM-dd-yyyy";
    public static final String MMDDYYYY = "MM/dd/yyyy";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM_A = "HH:mm a";
    public static final String HH_MM = "HH:mm";
    public static final String EEE = "EEE";
    public static  String token="";

    public static Utils utils;
    private static ProgressDialog pDialog;

    public static Utils with() {

        if (utils == null) {

            utils = new Utils();
        }

        return utils;
    }


    // For Checking Internet Availability....
    public static boolean isInternetAvailable(Context c) {

        ConnectivityManager connectivity = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.isConnected()) {
                        return true;
                    }
        }

        return false;
    }


    public static void hideSoftKeyboard(Activity activity) {

        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (view == null) {

            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }


    public static void hideProgressDialog(Context c) {

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

    }

    public static String changeDateFormat(String strDate, String inputFormat,
                                          String outputFormat) {
        try {

            SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
            inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = inFormat.parse(strDate);
            SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat);
            return outFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    public static String changeDateFormatWithoutTimeZone(String strDate, String inputFormat,
                                                         String outputFormat) {
        try {

            SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
            //inFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = inFormat.parse(strDate);
            SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat);
            return outFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }


    public static void showToast(Context c, String message) {

        if (message != null && !message.isEmpty()) {

            Toast toast = Toast.makeText(c, "" + message + "", Toast.LENGTH_SHORT);

            View toastView = toast.getView(); // This'll return the default_icon View of the Toast.

            /* And now you can get the TextView of the default_icon View of the Toast. */
            TextView toastMessage = toastView.findViewById(android.R.id.message);
            toastMessage.setGravity(Gravity.CENTER);
            toast.show();
        }
    }


    public static String getCurrentLocale(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0).getCountry();
        } else {
            return context.getResources().getConfiguration().locale.getCountry();
        }
    }

    public static boolean isAppInForeground(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            ActivityManager.RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
            String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();

            return foregroundTaskPackageName.toLowerCase().equals(context.getPackageName().toLowerCase());
        } else {
            ActivityManager.RunningAppProcessInfo appProcessInfo =
                    new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(appProcessInfo);
            if (appProcessInfo.importance == IMPORTANCE_FOREGROUND ||
                    appProcessInfo.importance == IMPORTANCE_VISIBLE) {
                return true;
            }

            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            return km.inKeyguardRestrictedInputMode();
        }
    }

    public static void clearNotification(Context context) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    public static String removeSpecialString(String data) {

        return data.replaceAll("[A-Za-z!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+", "");
    }


    public static int getViewWidth(View view) {
        WindowManager wm =
                (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int deviceWidth;

        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredWidth();
    }

    public static String getYoutubeVideoThumbnail(String id) {

        return "https://img.youtube.com/vi/" + id + "/mqdefault.jpg";
    }


}

package com.javier.util;

import android.util.Log;

/**
 * Created by javier on 04.01.16.
 */
public class LogHelper {

    /**
     * Enables/disables all logging messages
     */
    private static final boolean LOGGING_ENABLED = !BuildConfig.DEBUG;

    /**
     * Default LOG_TAG
     */
    private static final String LOG_TAG = BuildConfig.APPLICATION_ID;

    //TODO: add warning and error methods

    /**
     * Log event by using the default tag {@link LogHelper#LOG_TAG}
     *
     * @param message
     */
    public static void logEvent(String message) {
        logEvent(LOG_TAG, message);
    }

    /**
     * Log event by using a custom tag
     *
     * @param logTag
     * @param message
     */
    public static void logEvent(String logTag, String message) {
        if (LOGGING_ENABLED) {
            Log.d(logTag, getSourceInformation() + message);
        }
    }


    /**
     * Stack trace element beginning from the start of the stack
     */
    private static Integer mStackTraceLevel = null;

    /**
     * Gets the stack trace of the caller method outside of this class
     *
     * @return Stack trace element
     */
    private static StackTraceElement getCallerStackTraceElement() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        //find stack trace level
        if (mStackTraceLevel == null) {
            mStackTraceLevel = 0;
            while(!elements[mStackTraceLevel].getClassName().equals(LogHelper.class.getName())) {
                mStackTraceLevel++;
            }
            while(elements[mStackTraceLevel].getClassName().equals(LogHelper.class.getName())) {
                mStackTraceLevel++;
            }
        }

        return elements[mStackTraceLevel];
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <method_name>(<class>:<line_number>)
     *
     * @return String - String representing class name, method name, and line
     *         number.
     */
    private static String getSourceInformation() {
        StackTraceElement element = getCallerStackTraceElement();
        return element.getMethodName()  +
                "(" + element.getFileName() + ":" + element.getLineNumber() + ") ";
    }

}

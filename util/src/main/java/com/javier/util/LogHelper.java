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

    /**
     * Stack trace level of the caller method
     */
    private static final int STACK_TRACE_LEVELS_UP = 5;// TODO: magic number

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
            Log.d(logTag, getClassNameMethodNameAndLineNumber() + message);
        }
    }


    /**
     * Gets class name of the given stack trace element without the .java suffix
     * @param element
     * @return
     */
    private static String getClassName(StackTraceElement element) {
        String fileName = element.getFileName();

        // Removing ".java" and returning class name
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Gets the stack trace of the caller method outside of this class
     *
     * @return Stack trace element
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP];
    }

    /**
     * Returns the class name, method name, and line number from the currently
     * executing log call in the form <class_name>.<method_name>()-<line_number>
     *
     * @return String - String representing class name, method name, and line
     *         number.
     */
    private static String getClassNameMethodNameAndLineNumber() {
        StackTraceElement element = getCallerStackTraceElement();
        return "[" + element.getClassName() + "." +
                element.getMethodName() + "()-" +
                element.getLineNumber() + "]: ";
    }

}

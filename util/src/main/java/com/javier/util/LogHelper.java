package com.javier.util;

import android.util.Log;

import java.util.Queue;

/**
 * Extends Android log functions.
 *
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
    private static final String DEFAULT_TAG = BuildConfig.APPLICATION_ID;


    // All the methods must call the logWriter directly, so the caller method would be at the same
    // stack trace index

    public static void logFatal(String message) {
        logWriter(LogPriority.FATAL, DEFAULT_TAG, message);
    }

    public static void logWarning(String message) {
        logWriter(LogPriority.WARN, DEFAULT_TAG, message);
    }

    public static void logError(String message) {
        logWriter(LogPriority.ERROR, DEFAULT_TAG, message);
    }

    public static void log(String message) {
        logWriter(LogPriority.DEBUG, DEFAULT_TAG, message);
    }

    public static void log(LogPriority priority,
                           String message) {
        logWriter(priority, DEFAULT_TAG, message);
    }

    public static void log(LogPriority priority,
                           String tag,
                           String message) {
        logWriter(priority, tag, message);
    }
    private static void logWriter(LogPriority priority,
                                  String tag,
                                  String message)  {
        if (LOGGING_ENABLED) {
            message = getSourceInformation() + message;
            message = getThreadInformation() + message;
            Log.println(priority.getId(), tag, message);
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

    private static String getThreadInformation() {
        Thread thread = Thread.currentThread();
        return "thread " + thread.getId() + ": ";
    }

    public enum LogPriority {
        UNKNOWN(0),
        DEFAULT(1),
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        FATAL(7),
        SILENT(8);

        private int mId;

        LogPriority(int id) {
            mId = id;
        }

        public int getId() {
            return mId;
        }
        public String getString() {
            switch (this){
                case DEFAULT:
                    return "Default";
                case VERBOSE:
                    return "V";
                case DEBUG:
                    return "D";
                case INFO:
                    return "I";
                case WARN:
                    return "W";
                case ERROR:
                    return "E";
                case FATAL:
                    return "F";
                default:
                    return "U";
            }
        }
    }
}

package io.dkozak.energy.energysave

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionHandler : java.lang.Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, exception: Throwable) {
        val stackTrace = StringWriter()
        exception.printStackTrace(PrintWriter(stackTrace))

        val errorReport = StringBuilder()
        errorReport.append(stackTrace.toString())

        Log.e(LOG_TAG, errorReport.toString())

        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(10)
    }

    companion object {
        val LOG_TAG = ExceptionHandler::class.java.simpleName
    }
}


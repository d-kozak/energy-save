package io.dkozak.energy.energysave

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        setContentView(R.layout.activity_main)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val positionTextView = findViewById<TextView>(R.id.positionTextView)

        val requestPositionBtn = findViewById<Button>(R.id.requestPositionBtn)
        requestPositionBtn.setOnClickListener {
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    positionTextView.text = location.toString()
                } else {
                    positionTextView.text = "Last position not known"
                }
            } catch (ex: SecurityException) {
                positionTextView.text = "not allowed $ex"
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                Log.d(TAG, ex.message)
                ex.printStackTrace()
            }
        }


        val automaticUpdatesBtn = findViewById<Button>(R.id.automaticUpdatesBtn)
    }
}

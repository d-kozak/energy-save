package io.dkozak.energy.energysave

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName


    private val useGps = true

    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        setContentView(R.layout.activity_main)


        val provider = if (useGps) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
                positionTextView.text = "Not allowed $ex"
                Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
                Log.d(TAG, ex.message)
                ex.printStackTrace()
            }
        }


        val automaticUpdatesBtn = findViewById<Button>(R.id.automaticUpdatesBtn)

        automaticUpdatesBtn.setOnClickListener {
            requestLocationStaticDutyCycle(provider, 500)
        }
    }

    private fun requestLocationStaticDutyCycle(provider: String, periodInMillis: Long) {
        try {
            locationManager.requestLocationUpdates(provider, periodInMillis, 1F, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    location ?: return
                    positionTextView.text = location.toString()
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

                override fun onProviderEnabled(provider: String?) {}

                override fun onProviderDisabled(provider: String?) {}
            })
        } catch (ex: SecurityException) {
            positionTextView.text = "Not allowed $ex"
        }
    }
}

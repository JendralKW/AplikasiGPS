package com.wijaya.aplikasigps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager //mendeklarasikan untuk menyediakan akses ke layanan lokasi sistem.
    private lateinit var tvGpsLocation: TextView //mendeklarasikan untuk nantinya berfungsi menampilkan lokasi GPS
    private val locationPermissionCode = 2 //memberikan deklarasi  kode permintaan akses
    override fun onCreate(savedInstanceState: Bundle?) { // membuat sebuah instance dari Klien Penyedia Lokasi
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"
        val button: Button = findViewById(R.id.getLocation) //mengaktifkan button agar berfungsi untuk mendapatkan lokasi
        button.setOnClickListener {
            getLocation() //meminta lokasi terakhir yang diketahui
        }
    }
    private fun getLocation() { //mendapatkan data yang diperoleh gps menggunakan access fine location jika telah mendapatkan akses
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) { //menampilkas lokasi berdasarkan lattitude dan longitude, dan akan merubah itu jika lokasi berubah
        tvGpsLocation = findViewById(R.id.textView)
        tvGpsLocation.text = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) //kode blok ini untuk meminta data hasil dari akses GPS apakah diperbolehkan atau tidak
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
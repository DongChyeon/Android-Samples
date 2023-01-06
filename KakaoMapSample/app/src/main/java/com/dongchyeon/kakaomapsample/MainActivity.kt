package com.dongchyeon.kakaomapsample

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dongchyeon.kakaomapsample.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var reverseGeoCoder: MapReverseGeoCoder
    private lateinit var mapViewEventListener: MapViewEventListener

    private var isTrackingMode: Boolean = false
    val activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapViewEventListener = MapViewEventListener()
        binding.mapView.setMapViewEventListener(mapViewEventListener)
        binding.currentLocationBtn.setOnClickListener {
            if (!isTrackingMode) startTracking() else stopTracking()
        }
    }

    // 현재 사용자 위치추적
    @SuppressLint("MissingPermission")
    private fun startTracking() {
        isTrackingMode = true

        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading

        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val uLatitude = userNowLocation?.latitude
        val uLongitude = userNowLocation?.longitude
        val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude!!, uLongitude!!)

        binding.mapView.removeAllPOIItems()

        val marker = MapPOIItem()
        marker.itemName = "현재 위치"
        marker.tag = 0
        marker.mapPoint = uNowPosition
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        binding.mapView.addPOIItem(marker)

        reverseGeoCoder = MapReverseGeoCoder(
            BuildConfig.APP_KEY,
            uNowPosition,
            MapReverseGeoCoderListener(),
            activity
        )
        reverseGeoCoder.startFindingAddress()

        binding.currentLocationBtn.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.red))
    }

    // 위치추적 중지
    private fun stopTracking() {
        isTrackingMode = false

        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff
        binding.currentLocationBtn.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.dark_gray))
    }

    inner class MapViewEventListener : MapView.MapViewEventListener {
        override fun onMapViewInitialized(p0: MapView?) {

        }

        override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

        }

        override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
            p0!!.removeAllPOIItems()

            val marker = MapPOIItem()
            marker.itemName = "선택한 위치"
            marker.tag = 0
            marker.mapPoint = p1
            marker.markerType = MapPOIItem.MarkerType.BluePin
            marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
            p0.addPOIItem(marker)

            reverseGeoCoder =
                MapReverseGeoCoder(BuildConfig.APP_KEY, p1, MapReverseGeoCoderListener(), activity)
            reverseGeoCoder.startFindingAddress()
        }

        override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

        }

        override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

        }
    }

    inner class MapReverseGeoCoderListener : MapReverseGeoCoder.ReverseGeoCodingResultListener {
        override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
            binding.mapView.findPOIItemByTag(0).itemName = p1
        }

        override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {

        }
    }
}
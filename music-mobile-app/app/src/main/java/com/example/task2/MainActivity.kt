package com.example.task2

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.database.ktx.database
import java.util.*

data class Track(val name: String = "", val url: String = "")

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var listView: ListView
    private lateinit var player: SimpleExoPlayer
    private lateinit var storage: FirebaseStorage
    private lateinit var database: DatabaseReference
    private lateinit var trackList: List<Track>
    private var currentTrackIndex = 0

    private lateinit var trackTitle: TextView
    private lateinit var btnPlayPause: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var seekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)
        trackTitle = findViewById(R.id.trackTitle)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        btnPrevious = findViewById(R.id.btnPrevious)
        btnNext = findViewById(R.id.btnNext)
        seekBar = findViewById(R.id.seekBar)

        player = SimpleExoPlayer.Builder(this).build()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        storage = Firebase.storage
        database = Firebase.database.reference

        getLocation()

        btnPlayPause.setOnClickListener { togglePlayPause() }
        btnPrevious.setOnClickListener { playPreviousTrack() }
        btnNext.setOnClickListener { playNextTrack() }

        player.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    seekBar.max = player.duration.toInt()
                    updateSeekBar()
                }
            }
        })

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress.toLong())
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener(OnCompleteListener<Location> { task ->
            if (task.isSuccessful && task.result != null) {
                val location: Location = task.result
                val latitude = location.latitude
                val longitude = location.longitude
                val countryName = fetchCountryName(latitude, longitude)
                if (countryName != null) {
                    fetchTopTracks(countryName)
                } else {
                    // Handle error: Unable to get country name
                }
            }
        })
    }

    private fun fetchCountryName(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses!!.isNotEmpty()) {
            return addresses[0].countryName
        }
        return null
    }

    private fun fetchTopTracks(countryName: String) {
        database.child("music").child(countryName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tracks = mutableListOf<Track>()
                for (trackSnapshot in snapshot.children) {
                    val track = trackSnapshot.getValue(Track::class.java)
                    if (track != null) {
                        tracks.add(track)
                    }
                }
                trackList = tracks
                updateUI(tracks)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }

    private fun updateUI(tracks: List<Track>) {
        val trackNames = tracks.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trackNames)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            currentTrackIndex = position
            playAudioStream(tracks[position].url)
        }
    }

    private fun playAudioStream(audioUrl: String) {
        val storageRef: StorageReference = storage.getReferenceFromUrl(audioUrl)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "YourApp"))
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
            player.setMediaSource(mediaSource)
            player.prepare()
            player.playWhenReady = true
            trackTitle.text = trackList[currentTrackIndex].name
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        }.addOnFailureListener {
            // Handle error: Unable to fetch download URL
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
            btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
        } else {
            player.play()
            btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
        }
    }

    private fun playPreviousTrack() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
            playAudioStream(trackList[currentTrackIndex].url)
        }
    }

    private fun playNextTrack() {
        if (currentTrackIndex < trackList.size - 1) {
            currentTrackIndex++
            playAudioStream(trackList[currentTrackIndex].url)
        }
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                seekBar.progress = player.currentPosition.toInt()
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        handler.removeCallbacksAndMessages(null)
    }
}


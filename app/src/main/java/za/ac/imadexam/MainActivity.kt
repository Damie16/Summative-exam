package za.ac.imadexam

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var addButton: Button
    private lateinit var secondScreenButton: Button
    private lateinit var exitButton: Button
    private lateinit var averageRatingText: TextView

    private val playlist = SongDataHolder.playlist
    private val df = DecimalFormat("#.#")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.AddButton)
        secondScreenButton = findViewById(R.id.SecondscreenButton)
        exitButton = findViewById(R.id.ExitButton)
        averageRatingText = findViewById(R.id.averageRatingText)

        addButton.setOnClickListener { showAddSongDialog() }
        secondScreenButton.setOnClickListener { moveToSecondScreen() }
        exitButton.setOnClickListener { exitApp() }

        updateAverageRating()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showAddSongDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Song to Playlist")

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_song, null)
        builder.setView(dialogView)

        val songTitleInput = dialogView.findViewById<EditText>(R.id.songTitleInput)
        val artistNameInput = dialogView.findViewById<EditText>(R.id.artistNameInput)
        val ratingInput = dialogView.findViewById<EditText>(R.id.rating)
        val commentsInput = dialogView.findViewById<EditText>(R.id.commentsInput)

        builder.setPositiveButton("Add Song") { _, _ ->
            val songTitle = songTitleInput.text.toString().trim()
            val artistName = artistNameInput.text.toString().trim()
            val ratingText = ratingInput.text.toString().trim()
            val rating = ratingText.toFloatOrNull()
            val comments = commentsInput.text.toString().trim()

            when {
                songTitle.isEmpty() -> {
                    Toast.makeText(this, "Please enter a song title", Toast.LENGTH_SHORT).show()
                }
                artistName.isEmpty() -> {
                    Toast.makeText(this, "Please enter an artist name", Toast.LENGTH_SHORT).show()
                }
                rating == null || rating <= 0f || rating > 5f -> {
                    Toast.makeText(this, "Please enter a valid rating (1–5)", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val newSong = Song(songTitle, artistName, rating, comments)
                    playlist.add(newSong)
                    updateAverageRating()
                    Toast.makeText(this, "Song added to playlist!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun updateAverageRating() {
        if (playlist.isEmpty()) {
            averageRatingText.text = "Average rating: 0.0"
        } else {
            val totalRating = playlist.sumOf { it.rating.toDouble() }
            val averageRating = totalRating / playlist.size
            averageRatingText.text = "Average rating: ${df.format(averageRating)}"
        }
    }

    private fun moveToSecondScreen() {
        val intent = Intent(this, DetailView::class.java)
        startActivity(intent)
    }

    private fun exitApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit App")
        builder.setMessage("Are you sure you want to exit?")

        builder.setPositiveButton("Yes") { _, _ ->
            finish()
            System.exit(0)
        }

        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        builder.create().show()
    }
}
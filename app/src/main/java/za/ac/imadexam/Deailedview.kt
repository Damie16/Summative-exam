package za.ac.imadexam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailView : AppCompatActivity() {

    private lateinit var showSongsButton: Button
    private lateinit var showAverageButton: Button
    private lateinit var backToMainButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailedview)

        showSongsButton = findViewById(R.id.ShowSongsButton)
        showAverageButton = findViewById(R.id.showAverageButton)
        backToMainButton = findViewById(R.id.backToMainButton)
        resultTextView = findViewById(R.id.resultTextView)

        showSongsButton.setOnClickListener {
            val playlist = SongDataHolder.playlist
            if (playlist.isEmpty()) {
                resultTextView.text = "The playlist is empty."
            } else {
                val result = StringBuilder()
                for (song in playlist) {
                    result.append("Title: ${song.title}\n")
                    result.append("Artist: ${song.artist}\n")
                    result.append("Rating: ${song.rating}/5\n")
                    result.append("Comments: ${song.comments}\n\n")
                }
                resultTextView.text = result.toString()
            }
        }

        showAverageButton.setOnClickListener {
            val playlist = SongDataHolder.playlist
            if (playlist.isEmpty()) {
                resultTextView.text = "No songs to calculate average rating."
            } else {
                var total = 0f
                for (song in playlist) {
                    total += song.rating
                }
                val average = total / playlist.size
                resultTextView.text = "Average Rating: %.1f".format(average)
            }
        }

        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
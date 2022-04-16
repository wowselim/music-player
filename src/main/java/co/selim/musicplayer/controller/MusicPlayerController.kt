package co.selim.musicplayer.controller

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.MenuItem
import javafx.scene.control.TextInputDialog
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MusicPlayerController : KoinComponent {
    private val primaryStage by inject<Stage>()

    @FXML
    private lateinit var openFile: MenuItem

    @FXML
    private lateinit var openUrl: MenuItem

    @FXML
    private lateinit var close: MenuItem

    @FXML
    private lateinit var play: Button

    @FXML
    private lateinit var pause: Button

    @FXML
    private lateinit var mediaView: MediaView

    fun initialize() {
        openFile.setOnAction {
            val musicFile = FileChooser()
                .apply {
                    val musicFilesFilter = FileChooser.ExtensionFilter("Music files", "*.MP3")
                    extensionFilters.add(musicFilesFilter)
                }
                .showOpenDialog(primaryStage)

            if (musicFile != null) {
                if (musicFile.exists() && musicFile.isFile) {
                    val uri = musicFile.toURI().toURL().toExternalForm()
                    println(uri)
                    mediaView.mediaPlayer = MediaPlayer(Media(uri))
                }
            }
        }

        openUrl.setOnAction {
            val uriInputDialog = TextInputDialog("https://freepd.com/music/Study%20and%20Relax.mp3")
            val uri = uriInputDialog.showAndWait()
            uri.ifPresent { mediaView.mediaPlayer = MediaPlayer(Media(it)) }
        }

        close.setOnAction { primaryStage.hide() }

        play.setOnAction {
            mediaView.mediaPlayer.play()
            play.hide()
            pause.show()
        }

        pause.setOnAction {
            mediaView.mediaPlayer.pause()
            play.show()
            pause.hide()
        }
    }

    private fun Node.hide() {
        isVisible = false
        isManaged = false
    }

    private fun Node.show() {
        isVisible = true
        isManaged = true
    }
}
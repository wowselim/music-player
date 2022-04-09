package co.selim.musicplayer

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MusicPlayerApplication : Application() {
    private lateinit var koin: KoinApplication

    override fun start(primaryStage: Stage) {
        val primaryStageModule = module { single { primaryStage } }
        koin = startKoin {
            modules(primaryStageModule)
        }

        val layout =
            FXMLLoader.load<Parent>(MusicPlayerApplication::class.java.getResource("/fxml/player.fxml"))

        primaryStage.scene = Scene(layout)
        primaryStage.title = "Music Player"
        primaryStage.show()
        primaryStage.centerOnScreen()
    }

    override fun stop() {
        super.stop()
        koin.close()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MusicPlayerApplication::class.java, *args)
        }
    }
}
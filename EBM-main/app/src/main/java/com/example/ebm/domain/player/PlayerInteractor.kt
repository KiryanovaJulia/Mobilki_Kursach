import java.text.SimpleDateFormat

interface PlayerInteractor {
    fun preparePlayer(url: String, action: () -> Unit)
    fun play()
    fun pause()
    fun getCurrentPosFormatted(): String
    fun getCurrentPosUnFormatted(): Int
    fun getPlayerState(): Int
    fun releasePlayer()
    fun setCompletionListener(action: () -> Unit)

}
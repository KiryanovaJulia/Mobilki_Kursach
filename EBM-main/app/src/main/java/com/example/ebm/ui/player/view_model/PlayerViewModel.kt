import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ebm.domain.search.PlaylistStorageInteractor
import com.example.ebm.domain.search.models.Track
import com.example.ebm.ui.player.PlayerState

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val playlistStorageInteractor: PlaylistStorageInteractor
    ):ViewModel() {
    private var screenStateLiveData = MutableLiveData<PlayerState>(PlayerState.Default)
    private var currentPositionLiveData = MutableLiveData<Pair<String, Int>>()
    private var handler = android.os.Handler(Looper.getMainLooper())
    private var timerRunnable = object:Runnable {
        override fun run() {
            if(playerInteractor.getPlayerState() == AndroidMediaPlayerRepositoryImpl.PLAYER_STATE_PLAYING){
                currentPositionLiveData.postValue(Pair(playerInteractor.getCurrentPosFormatted(), playerInteractor.getCurrentPosUnFormatted()))
                handler.postDelayed(this, TIMER_DELAY)
            }
        }

    }

    fun getScreenStateLiveData(): LiveData<PlayerState> = screenStateLiveData
    fun getCurrentPositionLiveData(): MutableLiveData<Pair<String, Int>> = currentPositionLiveData
    fun preparePlayer(track: Track){
        playerInteractor.preparePlayer(track.previewUrl
        ) {
            renderState(PlayerState.Prepared)
        }
        playerInteractor.setCompletionListener {
            handler.removeCallbacks(timerRunnable)
            renderState(PlayerState.Prepared)
        }
    }
    fun startPlayer(){
        playerInteractor.play()
        renderState(PlayerState.Playing)
        handler.postDelayed(timerRunnable, TIMER_DELAY)
    }
    fun pausePlayer(){
        playerInteractor.pause()
        renderState(PlayerState.Paused)
        handler.removeCallbacks(timerRunnable)
    }
    fun playbackControl(){
        when(playerInteractor.getPlayerState()){
            AndroidMediaPlayerRepositoryImpl.PLAYER_STATE_PLAYING -> {
                pausePlayer()
            }
            AndroidMediaPlayerRepositoryImpl.PLAYER_STATE_PREPARED, AndroidMediaPlayerRepositoryImpl.PLAYER_STATE_PAUSED -> {
                startPlayer()
            }
        }
    }
    fun releasePlayer(){
        playerInteractor.releasePlayer()
    }
    fun addToFavorites(track: Track){
        playlistStorageInteractor.addToFavorites(track)
    }
    private fun renderState(state: PlayerState){
        screenStateLiveData.postValue(state)
    }

    companion object {
        const val TIMER_DELAY = 300L
    }
}
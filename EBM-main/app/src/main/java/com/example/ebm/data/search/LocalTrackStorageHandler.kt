import com.example.ebm.domain.search.models.Track

interface LocalTrackStorageHandler {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}
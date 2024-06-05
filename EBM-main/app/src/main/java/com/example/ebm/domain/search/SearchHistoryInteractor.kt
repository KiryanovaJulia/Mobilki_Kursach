import com.example.ebm.domain.search.models.Track

interface SearchHistoryInteractor {
    fun write(input: Track)
    fun clear()
    fun read(): List<Track>
}
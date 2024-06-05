
interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)
    interface TracksConsumer{
        fun consume(searchResult: TracksSearchResult)
    }
}
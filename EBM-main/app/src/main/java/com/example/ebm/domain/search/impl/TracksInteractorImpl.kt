
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository): TracksInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            if(expression.isNotEmpty()){
                consumer.consume(TracksSearchResult(emptyList(), SearchResultType.LOADING))
                try {
                    val tracks = repository.searchTracks(expression)
                    if(tracks.isNotEmpty()){
                        consumer.consume(TracksSearchResult(tracks,SearchResultType.SUCCESS))
                    }else{
                        consumer.consume(TracksSearchResult(emptyList(), SearchResultType.EMPTY))
                    }
                }catch (e: Throwable){
                    e.printStackTrace()
                    consumer.consume(TracksSearchResult(emptyList(), SearchResultType.ERROR))
                }

            }

        }
    }

}
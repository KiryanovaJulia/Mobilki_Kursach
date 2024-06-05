import android.util.Log
import com.example.ebm.data.registration.AccountRequest
import com.example.ebm.data.search.network.MusixmatchApi
import com.example.ebm.domain.account.KtorApi
import com.example.ebm.domain.account.UserData

class RetrofitNetworkClient(private val iTunesService: ITunesApi): NetworkClient {
    override fun doRequest(dto: Any): Response {
        if(dto is ITunesSearchRequest){
            val resp = iTunesService.search(dto.expression).execute()
            val body = resp.body() ?: Response()
            Log.i("RESPONSE", body.toString())
            Log.i("RESPONSE", body.resultCode.toString())
            return body.apply { resultCode = resp.code() }
        }
        else{
            return Response().apply { resultCode=400 }
        }
    }

}
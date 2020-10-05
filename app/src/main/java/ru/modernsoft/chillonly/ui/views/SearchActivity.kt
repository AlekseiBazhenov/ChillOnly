package ru.modernsoft.chillonly.ui.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.data.models.Station
import ru.modernsoft.chillonly.ui.adapters.StationAdapter
import rx.Observer

class SearchActivity : AppCompatActivity() { // TODO: presenter

//    @BindView(R.id.search_results)
//    lateinit var searchResults: RecyclerView
//    @BindView(R.id.search_text)
//    lateinit var searchText: EditText

    private var searchAdapter: StationAdapter? = null

    private val searchObserver = object : Observer<List<Station>> {
        override fun onCompleted() {}

        override fun onError(e: Throwable) {}

        override fun onNext(searchResults: List<Station>) {
//            searchAdapter!!.update(ArrayList(searchResults))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSearchAdapter()
        setTextChangeListener()
    }

//    @OnClick(R.id.close_search)
//    fun closeSearch() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(searchText.windowToken, 0)

//        onBackPressed()
//    }

//    @OnClick(R.id.clear_search)
//    fun clearSearch() {
//        searchText.setText("")
//    }

    private fun setTextChangeListener() {
//        RxTextView.textChangeEvents(searchText).skip(SKIP_SYMBOLS)
//                .flatMap { txtChangeEvt ->
//                    DatabaseHelper.get().find(txtChangeEvt.text().toString()) // TODO: 16.12.16 presenter
//                }.subscribe(searchObserver)
    }

    private fun setSearchAdapter() {
        val layoutManager = LinearLayoutManager(this)
//        searchResults.layoutManager = layoutManager
//        searchAdapter = StationAdapter(this, ArrayList<Station>(), object : StationAdapter.OnStationClick {
//            override fun onStationClick(station: Station) {
////                RadioService.start(this@SearchActivity, station.playerUrl, station.title)
//                RadioService.start(this@SearchActivity, station.id)
//                closeSearch()
//            }
//        })
//        searchResults.adapter = searchAdapter
    }

    companion object {

        private val SKIP_SYMBOLS = 2

        fun open(activity: Activity) {
            activity.startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}

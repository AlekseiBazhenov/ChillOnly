package ru.modernsoft.chillonly.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import kotlinx.android.synthetic.main.activity_stations.*
import kotlinx.android.synthetic.main.toolbar_tabs.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.adapters.StationsPagerAdapter
import ru.modernsoft.chillonly.ui.presenters.StationsPresenterImpl

class StationsActivity : MvpAppCompatActivity(), StationsView {

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var presenter: StationsPresenterImpl

    @ProvidePresenterTag(presenterClass = StationsPresenterImpl::class, type = PresenterType.GLOBAL)
    fun providePresenterTag(): String = javaClass.simpleName

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun providePresenter() = StationsPresenterImpl()

    private var pagerAdapter: StationsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stations)

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.search -> showSearchScreen()
            R.id.rate -> launchMarket()
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showStations() {
        if (pagerAdapter == null) {
            pagerAdapter = StationsPagerAdapter(supportFragmentManager, this)
        }
        progress.visibility = View.GONE
        viewpager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showSearchScreen() {
        SearchActivity.open(this)
    }

    private fun launchMarket() {
        val url = Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        val url = "http://play.google.com/store/apps/details?id=" + packageName
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join ChillOnly " + url)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}

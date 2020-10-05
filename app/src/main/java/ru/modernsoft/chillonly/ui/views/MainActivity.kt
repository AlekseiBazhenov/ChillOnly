package ru.modernsoft.chillonly.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_stations.*
import kotlinx.android.synthetic.main.toolbar_tabs.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.views.fragments.StationsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stations)

        setSupportActionBar(toolbar)

        playerView.setListener(object : ChillPlayerView.PlayerListener {
            override fun onChangePlayerStateClick() {
//                presenter.onChangePlayerStateClick() sharedViewModel
            }

            override fun onAddFavoriteClick() {
//                presenter.onAddFavoriteClick
            }
        })

        val fragment = StationsFragment.create()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
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

    private fun showSearchScreen() {
        SearchActivity.open(this)
    }

    private fun launchMarket() {
        val url = Uri.parse(getAppUrl())
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

    private fun share() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "Join ChillOnly ${getAppUrl()}")
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun getAppUrl() = "http://play.google.com/store/apps/details?id=$packageName"
}

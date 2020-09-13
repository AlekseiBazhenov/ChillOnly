package ru.modernsoft.chillonly.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar_tabs.*
import ru.modernsoft.chillonly.R
import ru.modernsoft.chillonly.ui.views.fragments.StationsListFragment

class StationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stations)

        setSupportActionBar(toolbar) // todo delete

        val fragment = StationsListFragment.create()
        supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rate -> launchMarket()
            R.id.share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSearchScreen() {
        SearchActivity.open(this)
    }

    private fun launchMarket() {
        val url = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
        startActivity(Intent(Intent.ACTION_VIEW, url))
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        val url = "http://play.google.com/store/apps/details?id=$packageName"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join ChillOnly $url")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}

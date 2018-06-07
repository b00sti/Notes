package com.b00sti.notes.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseActivity
import com.b00sti.notes.databinding.ActivityMainBinding
import com.b00sti.notes.ui.adding.NewNoteFragment
import com.b00sti.notes.ui.notes.NotesFragment
import com.b00sti.notes.utils.ResUtils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by b00sti on 05.06.2018
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    override fun getViewModels(): MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_main
    private var mSearch: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbarMain)
        getViewModels().setNavigator(this)
        pushFragments(NotesFragment.newInstance(), R.id.vgMainPlaceholder, first = true, backStack = false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        mSearch = menu.findItem(R.id.action_search)
        val mSearchView = mSearch?.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.vgMainPlaceholder)
                (fragment as? NotesFragment)?.searchFor(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.vgMainPlaceholder)
                (fragment as? NotesFragment)?.searchFor(newText)
                return false
            }
        })
        mSearch?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.vgMainPlaceholder)
                (fragment as? NotesFragment)?.refreshNotes()
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun customizeAsParentView(@StringRes toolbarTitleId: Int) {
        setToolbarTitle(toolbarTitleId)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        fabAddNewNote.visibility = View.VISIBLE
        mSearch?.isVisible = true
    }

    fun customizeAsChildView(@StringRes toolbarTitleId: Int) {
        setToolbarTitle(toolbarTitleId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        fabAddNewNote.visibility = View.GONE
        mSearch?.collapseActionView()
        mSearch?.isVisible = false
    }

    private fun setToolbarTitle(@StringRes id: Int) {
        supportActionBar?.title = ResUtils.getString(id)
    }

    override fun onNewClick() {
        pushFragments(NewNoteFragment.newInstance(), R.id.vgMainPlaceholder)
    }

}
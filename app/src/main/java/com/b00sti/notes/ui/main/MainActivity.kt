package com.b00sti.notes.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.widget.toast
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseActivity
import com.b00sti.notes.databinding.ActivityMainBinding
import com.b00sti.notes.ui.adding.NewNoteFragment
import com.b00sti.notes.ui.notes.NotesFragment
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by b00sti on 05.06.2018
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    override fun getViewModels(): MainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.activity_main
    lateinit var mSearch: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbarMain)
        getViewModels().setNavigator(this)
        pushFragments(NotesFragment.newInstance(), R.id.vgMainPlaceholder, first = true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        mSearch = menu.findItem(R.id.action_search)
        val mSearchView = mSearch.actionView as SearchView
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                toast("Text submitted: " + query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                toast("Text changed: " + newText)
                return false
            }
        })
        mSearch.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                toast("Text collapsed: ")
                return true
            }
        })
        return true
    }

    override fun onBackArrowClick() {

    }

    override fun onNewClick() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle("Add new note")
        fabAddNewNote.visibility = View.GONE
        pushFragments(NewNoteFragment.newInstance(), R.id.vgMainPlaceholder)
        mSearch?.isVisible = false
    }
}
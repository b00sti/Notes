package com.b00sti.notes.ui.notes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseFragment
import com.b00sti.notes.databinding.FragmentNotesBinding
import com.b00sti.notes.model.Note
import com.b00sti.notes.ui.details.NoteDetailsFragment
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by b00sti on 05.06.2018
 */
class NotesFragment : BaseFragment<FragmentNotesBinding, NotesViewModel>(), NotesNavigator {

    private val adapter = NotesAdapter({ goToNoteDetails(it) })

    companion object {
        fun newInstance(): NotesFragment {
            return NotesFragment()
        }
    }

    override fun getViewModels(): NotesViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_notes

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
    }

    private fun initUI() {
        viewModel.notesList.observe(this, Observer { list -> adapter.submitList(list) })
        rvNotes.layoutManager = LinearLayoutManager(getParent())
        rvNotes.adapter = adapter
        viewModel.refresh()
    }

    private fun goToNoteDetails(note: Note) {
        getBase()?.pushFragments(NoteDetailsFragment.newInstance(note), R.id.vgMainPlaceholder)
    }
}
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
import com.b00sti.notes.ui.adding.NewNoteFragment
import com.b00sti.notes.ui.details.NoteDetailsFragment
import com.b00sti.notes.ui.main.MainActivity
import com.b00sti.notes.utils.gone
import com.b00sti.notes.utils.show
import kotlinx.android.synthetic.main.fragment_notes.*

/**
 * Created by b00sti on 05.06.2018
 */
class NotesFragment : BaseFragment<FragmentNotesBinding, NotesViewModel>(), NotesNavigator {

    private val adapter = NotesAdapter(object : NoteItemClicks {
        override fun onItemClick(note: Note) = goToNoteDetails(note)
        override fun onEditClick(note: Note) = goToEditNote(note)
        override fun onDeleteClick(note: Note) = deleteNote(note)
    })

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

    override fun onResume() {
        super.onResume()
        getParent<MainActivity>()?.customizeAsParentView(R.string.title_notes)
    }

    private fun initUI() {
        viewModel.notesList.observe(this, Observer { list -> adapter.submitList(list) })
        rvNotes.layoutManager = LinearLayoutManager(getParent())
        rvNotes.adapter = adapter
        refreshNotes()
    }

    private fun goToNoteDetails(note: Note) {
        getBase()?.pushFragments(NoteDetailsFragment.newInstance(note), R.id.vgMainPlaceholder)
    }

    private fun goToEditNote(note: Note) {
        getBase()?.pushFragments(NewNoteFragment.newInstance(note), R.id.vgMainPlaceholder)
    }

    private fun deleteNote(note: Note) = viewModel.deleteNote(note)

    fun searchFor(queredText: String) = viewModel.searchFor(queredText)

    fun refreshNotes() = viewModel.refresh()

    override fun setNoContentLabelAsVisible(visible: Boolean) = when {
        visible -> tvNoContent.show()
        else -> tvNoContent.gone()
    }
}
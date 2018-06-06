package com.b00sti.notes.ui.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseFragment
import com.b00sti.notes.databinding.FragmentNoteDetailsBinding
import com.b00sti.notes.model.Note
import com.b00sti.notes.ui.main.MainActivity
import com.b00sti.notes.utils.putArgs

/**
 * Created by b00sti on 05.06.2018
 */
class NoteDetailsFragment : BaseFragment<FragmentNoteDetailsBinding, NoteDetailsViewModel>(), NoteDetailsNavigator {

    companion object {
        const val ARG_NOTE = "bundle_note"
        fun newInstance(note: Note): NoteDetailsFragment =
                NoteDetailsFragment().putArgs { putParcelable(ARG_NOTE, note) }
    }

    override fun getViewModels(): NoteDetailsViewModel = ViewModelProviders.of(this).get(NoteDetailsViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_note_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        getParent<MainActivity>()?.customizeAsChildView(R.string.title_note_details)
    }

    private fun initUI() {
        val note = arguments?.getParcelable<Note>(ARG_NOTE)
        note?.let {
            viewModel.note.set(it)
        }
    }

}
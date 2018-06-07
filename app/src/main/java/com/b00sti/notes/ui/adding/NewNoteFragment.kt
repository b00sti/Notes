package com.b00sti.notes.ui.adding

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseFragment
import com.b00sti.notes.databinding.FragmentAddNoteBinding
import com.b00sti.notes.model.Note
import com.b00sti.notes.ui.main.MainActivity
import com.b00sti.notes.utils.putArgs
import kotlinx.android.synthetic.main.fragment_add_note.*

/**
 * Created by b00sti on 05.06.2018
 */
class NewNoteFragment : BaseFragment<FragmentAddNoteBinding, NewNoteViewModel>(), NewNoteNavigator {

    companion object {
        const val ARG_NOTE = "bundle_note"
        fun newInstance(): NewNoteFragment = NewNoteFragment()

        fun newInstance(note: Note): NewNoteFragment =
                NewNoteFragment().putArgs { putParcelable(ARG_NOTE, note) }
    }

    override fun getViewModels(): NewNoteViewModel = ViewModelProviders.of(this).get(NewNoteViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_add_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        getParent<MainActivity>()?.customizeAsChildView(R.string.title_new_note)
    }

    private fun initUI() {
        val note = arguments?.getParcelable<Note>(ARG_NOTE)
        when {
            note != null -> viewModel.note.set(note)
            else -> viewModel.note.set(Note())
        }
    }

    override fun onNoteUpdated() {
        getBase()?.onFinishLoading()
        getBase()?.onBackPressed()
    }

    override fun prepareNoteFromViews() {
        viewModel.note.get()?.tag = etTags.text.toString()
        viewModel.note.get()?.desc = etNote.text.toString()
    }

}
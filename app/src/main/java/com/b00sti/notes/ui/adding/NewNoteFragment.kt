package com.b00sti.notes.ui.adding

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.b00sti.notes.BR
import com.b00sti.notes.R
import com.b00sti.notes.base.BaseFragment
import com.b00sti.notes.databinding.FragmentAddNoteBinding

/**
 * Created by b00sti on 05.06.2018
 */
class NewNoteFragment : BaseFragment<FragmentAddNoteBinding, NewNoteViewModel>(), NewNoteNavigator {

    companion object {
        fun newInstance(): NewNoteFragment {
            return NewNoteFragment()
        }
    }

    override fun getViewModels(): NewNoteViewModel = ViewModelProviders.of(this).get(NewNoteViewModel::class.java)
    override fun getBindingVariable(): Int = BR.vm
    override fun getLayoutId(): Int = R.layout.fragment_add_note

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        initUI()
    }

    private fun initUI() {
        /*viewModel.notesList.observe(this, Observer { list -> adapter.submitList(list) })
        rvNotes.layoutManager = LinearLayoutManager(getParent())
        rvNotes.adapter = adapter
        viewModel.refresh()*/
    }

}
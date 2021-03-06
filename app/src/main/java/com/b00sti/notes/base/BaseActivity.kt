package com.b00sti.notes.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.transition.TransitionInflater
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.toast
import com.b00sti.notes.R
import com.b00sti.notes.utils.ResUtils
import com.b00sti.notes.utils.ViewUtils
import com.b00sti.notes.utils.hideProgressDialog
import com.b00sti.notes.utils.showProgressDialog

/**
 * Created by b00sti on 05.06.2018
 */
abstract class BaseActivity<T : ViewDataBinding, out V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    val viewModel: V by lazy { getViewModels() }
    private lateinit var viewDataBinding: T
    private var pb: DialogFragment? = null
    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        performDataBinding()
    }

    override fun onStop() {
        super.onStop()
        pb?.dismissAllowingStateLoss()
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    override fun showToast(resMsg: Int) = toast(ResUtils.getString(resMsg))
    override fun onStartLoading() = onLoading(true)
    override fun onFinishLoading() = onLoading(false)

    private fun onLoading(loading: Boolean) {
        return when {
            loading -> pb = showProgressDialog()
            else    -> hideProgressDialog(pb)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        currentFocus?.let {
            it.clearFocus()
            ViewUtils.hideSoftInput(this)
        }
        return super.dispatchTouchEvent(ev)
    }

    @JvmOverloads
    fun pushFragments(fragment: Fragment, @IdRes content: Int,
                      shouldAnimate: Boolean = true,
                      backStack: Boolean = true,
                      transition: Boolean = false,
                      first: Boolean = false,
                      view: ArrayList<View?> = ArrayList()) {
        val manager = supportFragmentManager

        val ft = manager.beginTransaction()
        var fragmentPopped = false
        try {
            fragmentPopped = manager.popBackStackImmediate(fragment::class.java.name, 0)
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }

        if (!fragmentPopped) {
            if (shouldAnimate) ft.setCustomAnimations(if (!first) R.anim.slide_in_right else 0,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right)

            if (backStack) ft.addToBackStack(fragment::class.java.name)

            if (transition) {
                fragment.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                fragment.sharedElementReturnTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                for (v in view) {
                    v?.let { it.transitionName?.let { ft.addSharedElement(v, it) } }
                }
            }
        }
        ft.replace(content, fragment, fragment::class.java.name)

        try {
            ft.commit()
            manager.executePendingTransactions()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
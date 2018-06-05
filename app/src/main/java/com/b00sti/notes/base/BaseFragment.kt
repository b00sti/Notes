package com.b00sti.notes.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.toast
import com.b00sti.notes.utils.ResUtils

/**
 * Created by b00sti on 05.06.2018
 */
abstract class BaseFragment<T : ViewDataBinding, out V : BaseViewModel<*>> : Fragment(), BaseNavigator {

    private lateinit var viewDataBinding: T
    val viewModel: V by lazy { getViewModels() }
    private lateinit var mRootView: View

    protected abstract fun getViewModels(): V
    protected abstract fun getBindingVariable(): Int
    @LayoutRes protected abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        prepareDataBindingLayout(inflater, container)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareDataBindingVariables()
    }

    private fun prepareDataBindingLayout(inflater: LayoutInflater, container: ViewGroup?) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mRootView = viewDataBinding.root
    }

    private fun prepareDataBindingVariables() {
        viewDataBinding.setVariable(getBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()
    }

    inline fun <reified T : BaseActivity<*, *>> getParent(): T? = activity as? T
    fun getBase(): BaseActivity<*, *>? = activity as BaseActivity<*, *>
    override fun showToast(resMsg: Int) = getBase()?.toast(ResUtils.getString(resMsg))
    override fun onStartLoading() = getParent<BaseActivity<*, *>>()?.onStartLoading()
    override fun onFinishLoading() = getParent<BaseActivity<*, *>>()?.onFinishLoading()
}
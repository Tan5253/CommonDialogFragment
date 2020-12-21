package com.tan.commondialogfragment

import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes


class DialogViewHolder(view: View) {

    private var views: SparseArray<View>? = null
    private var convertView: View? = null

    init {
        convertView = view
        views = SparseArray()
    }

    companion object{
        fun create(view: View): DialogViewHolder {
            return DialogViewHolder(view)
        }
    }

    /**
     * 获取View
     * @param viewId
     * @param <T>
     * */
    fun <T : View> getView(@IdRes viewId: Int): T {
        var view: View? = views!![viewId]
        if (view == null) {
            view = convertView?.findViewById(viewId)
            views!!.put(viewId, view)
        }
        return view as T
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    fun setText(viewId: Int, text: String?) {
        val textView: TextView = getView(viewId)
        textView.text = text
    }

    /**
     * 设置字体颜色
     *
     * @param viewId
     * @param colorId
     */
    fun setTextColor(viewId: Int, colorId: Int) {
        val textView: TextView = getView(viewId)
        textView.setTextColor(colorId)
    }

    /**
     * 设置背景图片
     *
     * @param viewId
     * @param resId
     */
    fun setBackgroundResource(viewId: Int, resId: Int) {
        val view: View = getView(viewId)
        view.setBackgroundResource(resId)
    }

    /**
     * 设置背景颜色
     *
     * @param viewId
     * @param colorId
     */
    fun setBackgroundColor(viewId: Int, colorId: Int) {
        val view: View = getView(viewId)
        view.setBackgroundColor(colorId)
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    fun setOnClickListener(viewId: Int, callback: () -> Unit) {
        val view: View = getView(viewId)
        view.setOnClickListener {
            callback()
        }
    }

    /**
     * 显示隐藏View
     * @param viewId
     * @param visibility
     */
    fun setVisibility(@IdRes viewId: Int, visibility: Int){
        val view: View = getView(viewId)
        if (visibility!=view.visibility){
            view.visibility = visibility
        }
    }
}
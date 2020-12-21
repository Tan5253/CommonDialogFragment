package com.tan.commondialogfragment

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ScrollView
import androidx.annotation.FloatRange
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


class CommonDialog: DialogFragment() {

    @LayoutRes
    protected var mLayoutResId = 0
    private var mDimAmount = 0.5f //背景昏暗度
    private var mShowBottomEnable= false //是否底部显示
    private var mMargin = 0 //左右边距
    private var mAnimStyle = 0 //进入退出动画
    private var mOutCancel = true //点击外部取消
    var mContext: Context? = null
    private var mWidth = 0
    private var mHeight = 0
    private var convertListener:ViewConvertListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, setDialogStyle())
       // mLayoutResId =setDialogLayoutId()
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (mLayoutResId>0){
            val view: View = inflater.inflate(mLayoutResId, container, false)
            convertListener?.convertView(DialogViewHolder.create(view), this)
             //convertView(DialogViewHolder.create(view), this)
            return view
        }
        return null
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    private fun initParams() {
        val window: Window? = dialog!!.window
        if (window != null) {
            val params: WindowManager.LayoutParams = window.attributes
            params.dimAmount = mDimAmount

            //设置dialog显示位置
            if (mShowBottomEnable) {
                params.gravity = Gravity.BOTTOM
            }

            //设置dialog宽高
            params.width=if(mWidth==0) getScreenWidth(context!!) - 2 * dp2px(context!!, mMargin.toFloat()) else dp2px(context!!, mWidth.toFloat())
            params.height=if(mHeight == 0) WindowManager.LayoutParams.WRAP_CONTENT else dp2px(context!!, mHeight.toFloat())

            //设置dialog动画
            if (mAnimStyle != 0) {
                window.setWindowAnimations(mAnimStyle)
            }
            window.attributes = params
        }
        //isCancelable = mOutCancel //设置无效
        dialog?.setCancelable(mOutCancel)
        dialog?.setCanceledOnTouchOutside(mOutCancel)
    }

    /**
     * 设置背景昏暗度
     *
     * @param dimAmount
     * @return
     */
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0) dimAmount: Float): CommonDialog? {
        mDimAmount = dimAmount
        return this
    }

    /**
     * 是否显示底部
     *
     * @param showBottom
     * @return
     */
    fun setShowBottom(showBottom: Boolean): CommonDialog? {
        mShowBottomEnable = showBottom
        return this
    }

    /**
     * 设置宽高
     *
     * @param width
     * @param height
     * @return
     */
    fun setDialogSize(width: Int, height: Int): CommonDialog? {
        mWidth = width
        mHeight = height
        return this
    }

    /**
     * 设置左右margin
     *
     * @param margin
     * @return
     */
    fun setDialogMargin(margin: Int): CommonDialog? {
        mMargin = margin
        return this
    }

    /**
     * 设置进入退出动画
     *
     * @param animStyle
     * @return
     */
    fun setAnimStyle(@StyleRes animStyle: Int): CommonDialog? {
        mAnimStyle = animStyle
        return this
    }

    /**
     * 设置是否点击外部取消
     *
     * @param outCancel
     * @return
     */
    fun setOutCancel(outCancel: Boolean): CommonDialog? {
        mOutCancel = outCancel
        return this
    }

    fun show(manager: FragmentManager): CommonDialog? {
        super.show(manager, System.currentTimeMillis().toString())
        return this
    }

    /**
     * 设置dialog布局
     *
     * @return
     */
    fun setDialogLayoutId(layoutId: Int=0){
        mLayoutResId=layoutId
    }

    /**
     * 设置dialog样式
     *
     * @return
     */
    fun setDialogStyle(style:Int=R.style.fullScreenDialog): Int{
        return style
    }

    fun setConvertListener(convertListener: ViewConvertListener): CommonDialog? {
        this.convertListener = convertListener
        return this
    }

//    /**
//     * 操作dialog布局
//     *
//     * @param holder
//     * @param dialog
//     */
//    abstract fun convertView(holder: DialogViewHolder, dialog: BaseDialogFragment)

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    fun dp2px(context: Context, dipValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    interface ViewConvertListener{
        fun convertView(holder: DialogViewHolder, dialog: CommonDialog)
    }

    /**
     * 使键盘一直在输入框下方
     * @param parentView 根布局
     * @param childView 需要显示的最下方View
     */
    fun addLayoutListener(parentView: View, childView: View) {
        parentView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            parentView.getWindowVisibleDisplayFrame(rect)
            val mainInvisibleHeight: Int = parentView.rootView.height - rect.bottom
            Log.e("da","${parentView.rootView.height}>>>>>>>>>>>>>>${rect.bottom}》》》》》》》》》$mainInvisibleHeight")
            if (mainInvisibleHeight > 100) {
                val location = IntArray(2)
                childView.getLocationInWindow(location)
                val scrollHeight: Int = location[1] + childView.rootView.height - rect.bottom
                Log.e("da","${location[1]}>>>>>>>>22222>>>>>>${scrollHeight}》》》》》》》》》${childView.rootView.height}")
                parentView.scrollTo(0, scrollHeight)
            } else {
                parentView.scrollTo(0, 0)
            }
        }
    }

    /**
     *
     * @param root 根布局View
     * @param scrollToView 需要移动的View
     */
    private var firstLocation = 0
    fun controlKeyboardLayout(root: View, scrollToView: View) {
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            //获取root在窗体的可视区域
            root.getWindowVisibleDisplayFrame(rect)
            //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
           // val rootInvisibleHeight = root.rootView.height - rect.bottom
            val rootInvisibleHeight = rect.bottom
            //若不可视区域高度大于100，则键盘显示
            if (rootInvisibleHeight > 100) {
                val location = IntArray(2)
                //获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location)
                if (firstLocation < location[1]) {
                    firstLocation = location[1]
                }
                Log.e("data",  " RECT.BOTTOM = " + rect.bottom + " rootInvisibleHeight = " +
                            rootInvisibleHeight + " location[1] = " + location[1]
                )
                //计算root滚动高度，使scrollToView在可见区域
                //val srollHeight = firstLocation + scrollToView.height - rect.bottom
                val srollHeight = firstLocation + scrollToView.height
                Log.e("data",">>>>>>>>>>>"+srollHeight)
                root.scrollTo(0, srollHeight)
            } else { //键盘隐藏
                root.scrollTo(0, 0)
            }
        }
    }

    fun controlKeyboardLayout1(root: View, context: Activity) {
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val rootInvisibleHeight = root.rootView.height - rect.bottom
            //若不可视区域高度大于100，则键盘显示
            if (rootInvisibleHeight > 100) {
                val location = IntArray(2)
                val focus = context.currentFocus
                if (focus != null) {
                    focus.getLocationInWindow(location)
                    val scrollHeight = location[1] + focus.height - rect.bottom
                    if (rect.bottom < location[1] + focus.height) {
                        root.scrollTo(0, scrollHeight)
                    }
                }
            } else {
                //键盘隐藏
                root.scrollTo(0, 0)
            }
        }
    }

}
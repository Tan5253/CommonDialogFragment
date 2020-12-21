package com.tan.commondialogfragment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_sure.setOnClickListener {
            CommonDialog().run {
                setDialogLayoutId(R.layout.layout_dialog_confirm)
                setOutCancel(false)
                setDialogMargin(30)
                setConvertListener(object :CommonDialog.ViewConvertListener{
                    override fun convertView(holder: DialogViewHolder, dialog: CommonDialog) {
                        holder.setOnClickListener(R.id.tv_confirm) {
                            dialog.dismissAllowingStateLoss()
                        }
                        holder.setOnClickListener(R.id.tv_cancel) {
                            dialog.dismissAllowingStateLoss()
                        }
                    }
                })
                show(supportFragmentManager)
            }
        }
        tv_comment.setOnClickListener {
            CommonDialog().run {
                setDialogLayoutId(R.layout.layout_dialog_comment)
                setOutCancel(false)
                //setShowBottom(true)
                //setDialogStyle(R.style.myDialog)
                setConvertListener(object :CommonDialog.ViewConvertListener{
                    override fun convertView(holder: DialogViewHolder, dialog: CommonDialog) {
//                        val tvPublish=holder.getView<TextView>(R.id.tv_publish)
//                        val etComment=holder.getView<EditText>(R.id.et_comment)
//                        val rootVIew=holder.getView<LinearLayout>(R.id.cl_rootView)
//                        addLayoutListener(rootVIew,tvPublish)
//                        controlKeyboardLayout1(holder.getView(R.id.cl_rootView),
//                            mContext as Activity
//                        )
                        holder.setOnClickListener(R.id.tv_publish) {
                            dialog.dismissAllowingStateLoss()
                        }
                    }
                })
                show(supportFragmentManager)
            }
        }
    }
}
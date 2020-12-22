package com.tan.commondialogfragment

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_sure.setOnClickListener {
          confirm()
        }
        tv_comment.setOnClickListener {
           comment()
        }
    }

    private fun confirm(){
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

    private fun comment(){
        CommonDialog().run {
            setDialogLayoutId(R.layout.layout_dialog_comment)
            setOutCancel(false)
            setConvertListener(object :CommonDialog.ViewConvertListener{
                override fun convertView(holder: DialogViewHolder, dialog: CommonDialog) {
                    val tvPublish=holder.getView<TextView>(R.id.tv_publish)
                    val etComment=holder.getView<EditText>(R.id.et_comment)
                    tvPublish.setOnClickListener {
                        val content=etComment.text.toString()
                        if (TextUtils.isEmpty(content)){
                            Toast.makeText(mContext,"评论内容不能为空",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show()
                            dialog.dismissAllowingStateLoss()
                        }
                    }
                }
            })
            show(supportFragmentManager)
        }
    }
}
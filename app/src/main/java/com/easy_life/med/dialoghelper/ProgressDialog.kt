package com.easy_life.med.dialoghelper

import android.app.Activity
import android.app.AlertDialog
import com.easy_life.med.databinding.ProgressDialogLayoutBinding
import com.easy_life.med.databinding.SignDialogBinding

object ProgressDialog {

    fun createProgressDialog(act: Activity): AlertDialog {

        val builder = AlertDialog.Builder(act)
        val rootDialogElement = ProgressDialogLayoutBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }

}
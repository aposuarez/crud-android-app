package com.jmsuarez.crudsample.utils.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.jmsuarez.crudsample.R

class LoadingDialog : DialogFragment() {

    companion object {
        @JvmStatic
        fun newInstance(label: String): LoadingDialog =
            LoadingDialog().apply {
                arguments = Bundle().apply {
                    putString(LABEL_LOADING,label)
                }
                isCancelable = false
            }

        const val LABEL_LOADING = "LABEL_LOADING"
        const val TAG_LOADING = "TAG_LOADING"
    }


    override fun onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.layout_loading, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_rounded)

        val loadingTextView = view.findViewById<TextView>(R.id.loadingLabelTextView)

        val loadingDialogArgs = arguments

        if(loadingDialogArgs != null){
            if(loadingDialogArgs.getString(LABEL_LOADING).isNullOrBlank()){
                loadingTextView.visibility = View.GONE
            }
            else{
                loadingTextView.text = loadingDialogArgs.getString(LABEL_LOADING)
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


}
package com.example.aromal.sampledialogs

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Layout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.emredavarci.noty.Noty
import com.jakewharton.rxbinding2.view.RxView
import com.michael.easydialog.EasyDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_view.view.*

class DialogView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    var view:View? = null
    private val compositeDisposable:CompositeDisposable = CompositeDisposable()
    public interface DialogViewListner{
        fun onOkClicked(dialog:Dialog)
    }
    var listner:DialogViewListner? = null
    private val dialog:Dialog by lazy {
        Dialog(context,R.style.DialogTheme)
    }
    init {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_view,this,true)
        this.textView.text = "Aromal"
        this.setupView()

    }

    fun setupView(){
        dialog.setContentView(this)
        setupShow()
        RxView.clicks(this.okButton).subscribe {
            this.listner?.onOkClicked(this.dialog)

        }.addTo(compositeDisposable)

//        RxView.clicks(this.view!!).subscribe {
//           dismiss()
//
//        }.addTo(compositeDisposable)

    }
    fun dismiss(){
        this.dialog.dismiss()
    }
    private fun setupShow(){
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

    }
    fun show(dialogViewListner: DialogViewListner) {
        this.listner = dialogViewListner
        if (!dialog.isShowing) {
            this.dialog.show()

        }
    }


}

class MainActivity : AppCompatActivity() {
    val disposeBag : CompositeDisposable = CompositeDisposable()
    val dialogView:DialogView by lazy {
        DialogView(this@MainActivity)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setupView()





    }



    fun setupView(){

        RxView.clicks(button).subscribe {

         dialogView.show(object : DialogView.DialogViewListner {
             override fun onOkClicked(dialog: Dialog) {
                dialog.dismiss()
             }

         })
        }.addTo(disposeBag)

    }
}

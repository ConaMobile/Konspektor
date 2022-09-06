package com.conamobile.konspektor.ui

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.conamobile.konspektor.R
import com.conamobile.konspektor.core.shared_pref.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment(private val layoutRes: Int) : Fragment() {
    var myClipboard: ClipboardManager? = null
    var shakeAnim: Animation? = null
    var alphaAnim: Animation? = null
    var fastAlphaAnim: Animation? = null

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myClipboard = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        shakeAnim = AnimationUtils.loadAnimation(context, R.anim.shake)
        alphaAnim = AnimationUtils.loadAnimation(context, R.anim.alpha)
        fastAlphaAnim = AnimationUtils.loadAnimation(context, R.anim.fast_alpha)
        sharedPref = SharedPreferences(requireContext())
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val content =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        content.showSoftInput(editText, 0)
        content.toggleSoftInput(InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun AppCompatTextView.changeTextColor(color: Int) {
        setTextColor(ContextCompat.getColor(context, color))
    }

    fun AppCompatEditText.changeHintColor(color: Int) {
        setHintTextColor(ContextCompat.getColor(context, color))
    }

    fun paste(): String {
        val clipData = myClipboard!!.primaryClip?.getItemAt(0)?.text
        var returnValue = ""
        if (clipData != null) {
            returnValue = clipData.toString()
        }
        return returnValue
    }

    fun EditText.cursorEnd() {
        setSelection(this.length())
    }

    fun navigator(fragment: Int) {
        findNavController().navigate(fragment)
    }

    @SuppressLint("SimpleDateFormat")
    fun nowDate(): String {
        val simpleDateFormat = SimpleDateFormat("EEE, d MMM | HH:mm")
        val date = Date()
        return simpleDateFormat.format(date)
    }

    inline fun onBackPressed(crossinline backPress: () -> Unit) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    backPress.invoke()
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
    }


    fun View.click(clickListener: (View) -> Unit) {
        setOnTouchListener(
            object : View.OnTouchListener {
                @SuppressLint("ClickableViewAccessibility")
                override fun onTouch(v: View, motionEvent: MotionEvent): Boolean {
                    val action = motionEvent.action
                    if (action == MotionEvent.ACTION_DOWN) {
                        v.animate().scaleXBy(-0.2f).setDuration(200).start()
                        v.animate().scaleYBy(-0.2f).setDuration(200).start()
                        return true
                    } else if (action == MotionEvent.ACTION_UP) {
                        clickListener(this@click)
                        v.animate().cancel()
                        v.animate().scaleX(1f).setDuration(1000).start()
                        v.animate().scaleY(1f).setDuration(1000).start()
                        return true
                    }
                    return false
                }
            })
    }
}
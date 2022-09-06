package com.conamobile.konspektor.ui.text_adder

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.conamobile.konspektor.R
import com.conamobile.konspektor.core.utils.UtilObjects
import com.conamobile.konspektor.core.utils.viewBinding
import com.conamobile.konspektor.databinding.FragmentTextAdderBinding
import com.conamobile.konspektor.ui.BaseFragment
import com.conamobile.konspektor.ui.history.model.HistoryModel
import com.conamobile.konspektor.ui.history.viewmodels.HistoryViewModel

class TextAdderFragment : BaseFragment(R.layout.fragment_text_adder) {
    private val binding by viewBinding { FragmentTextAdderBinding.bind(it) }
    private val viewModel: HistoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showKeyboard(binding.editText)
        catchHistoryText()
        pasteButtonClick()
        nextButtonClick()
        textCountManager()
        historyButtonManager()
        textCounterCheck(0)
    }

    private fun catchHistoryText() {
        binding.apply {
            val navController = findNavController()
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key0")
                ?.observe(viewLifecycleOwner) {
                    UtilObjects.apply {
                        if (lastFragment)
                            editText.setText(it)
                        editText.cursorEnd()
                        lastFragment = false
                    }
                }
            if (UtilObjects.isFirstLogin) {
                binding.editText.setText(sharedPref.getSavedTextInstance())
                editText.cursorEnd()
                UtilObjects.isFirstLogin = false
            }
        }
    }

    private fun historyButtonManager() {
        binding.historyBtn.click {
            navigator(R.id.action_textAdderFragment_to_historyFragment)
        }
    }

    private fun textCountManager() {
        binding.editText.addTextChangedListener {
            it?.let { textCounterCheck(it.count()) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun textCounterCheck(size: Int) {
        binding.apply {
            textCount.text = "$size/200"
            if (size > 199) {
                textCount.changeTextColor(R.color.red)
                textCount.startAnimation(shakeAnim)
            } else textCount.changeTextColor(R.color.hint_color)
        }
    }

    private fun pasteButtonClick() {
        binding.apply {
            pasteBtn.click {
                editText.setText(paste())
                editText.cursorEnd()
            }
        }
    }

    private fun nextButtonClick() {
        binding.apply {
            nextBtn.click {
                if (editText.text!!.isNotEmpty()) {
                    viewModel.addNote(HistoryModel(
                        text = editText.text.toString(),
                        date = nowDate()))
//                    navigator(R.id.action_textAdderFragment_to_homeFragment)
                } else {
                    alphaAnim!!.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            editText.changeHintColor(R.color.red)
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            editText.changeHintColor(R.color.hint_color)
                        }

                        override fun onAnimationRepeat(p0: Animation?) {}
                    })
                    editText.startAnimation(alphaAnim)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
        sharedPref.isSavedTextInstance(binding.editText.text.toString())
    }
}
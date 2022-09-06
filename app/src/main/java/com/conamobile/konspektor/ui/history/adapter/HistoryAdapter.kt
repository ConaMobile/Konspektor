package com.conamobile.konspektor.ui.history.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.conamobile.konspektor.R
import com.conamobile.konspektor.core.utils.bouncy.DragDropAdapter
import com.conamobile.konspektor.databinding.HistoryItemBinding
import com.conamobile.konspektor.ui.history.model.HistoryModel

class HistoryAdapter(private val list: ArrayList<HistoryModel>) : ListAdapter<HistoryModel,
        HistoryAdapter.Vh>(MyDiffUtil()),
    DragDropAdapter<HistoryAdapter.Vh> {
    lateinit var itemCLick: ((HistoryModel) -> Unit)
    lateinit var deleteCLick: ((HistoryModel) -> Unit)

    inner class Vh(
        private var itemHistoryBinding: HistoryItemBinding,
        var context: Context,
    ) :
        RecyclerView.ViewHolder(itemHistoryBinding.root) {

        fun onBind(history: HistoryModel) {
            itemHistoryBinding.apply {
                itemText.text = history.text
                itemDate.text = history.date

                itemCard.setOnClickListener {
                    itemCLick.invoke(history)
                }

                deleteIcon.click {
                    deleteCLick.invoke(history)
                }
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<HistoryModel>() {
        override fun areItemsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryModel, newItem: HistoryModel): Boolean {
            return oldItem.text == newItem.text
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
        val animation: Animation = AnimationUtils.loadAnimation(holder.context, R.anim.fast_alpha)
        holder.itemView.startAnimation(animation)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwipedToStart(viewHolder: RecyclerView.ViewHolder, positionOfItem: Int) {
        deleteCLick.invoke(list[positionOfItem])
    }

    override fun onItemSwipedToEnd(viewHolder: RecyclerView.ViewHolder, positionOfItem: Int) {
        itemCLick.invoke(list[positionOfItem])
    }

    override fun onItemSelected(viewHolder: RecyclerView.ViewHolder) {
    }

    override fun onItemReleased(viewHolder: RecyclerView.ViewHolder) {}

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
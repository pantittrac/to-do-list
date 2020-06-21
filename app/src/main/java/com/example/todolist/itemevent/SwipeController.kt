package com.example.todolist.itemevent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class SwipeController(private val listener: OnItemClickListener, private val context: Context): ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == RIGHT) {
            listener.swipeRight(viewHolder.adapterPosition)
        } else if (direction == LEFT) {
            listener.swipeLeft(viewHolder.adapterPosition)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val p = Paint()
        val pText = Paint()
        val itemView = viewHolder.itemView
        var rectF:RectF
        val padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5F, context.resources.displayMetrics)
        val round = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10F, context.resources.displayMetrics)
        pText.textAlign = Paint.Align.CENTER
        pText.color = Color.WHITE
        pText.textSize = 40F
        pText.isAntiAlias = true

        if (dX < 0) {
            p.color = ContextCompat.getColor(context,
                R.color.delete
            )
            rectF = RectF(itemView.right + (dX / 4), itemView.top + padding, itemView.right - padding, itemView.bottom - padding)

            c.drawRoundRect(rectF, round, round, p)
            c.drawText("DELETE", rectF.centerX(), rectF.centerY() + 20, pText)

        } else if (dX > 0) {
            p.color = ContextCompat.getColor(context,
                R.color.edit
            )
            rectF = RectF(itemView.left + padding, itemView.top + padding, itemView.left + (dX / 4), itemView.bottom - padding)

            c.drawRoundRect(rectF, round, round, p)
            c.drawText("EDIT", rectF.centerX(), rectF.centerY() + 20, pText)

        }

        super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY, actionState, isCurrentlyActive)
    }

}
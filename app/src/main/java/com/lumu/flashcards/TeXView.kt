package com.lumu.flashcards

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import io.nano.tex.Graphics2D
import io.nano.tex.LaTeX
import io.nano.tex.TeXRender

class TeXView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    private var textSize = 60
    private var render: TeXRender? = null
    private val g2 = Graphics2D()
    fun setLaTeX(ltx: String?, color: Int) {
        var w = width
        if (w == 0) w = 920
        render = LaTeX.instance().parse(ltx, w, textSize.toFloat(), 50f, color)
        requestLayout()
    }

    fun setTextSize(size: Int) {
        textSize = size
        if (render != null) render!!.textSize = size.toFloat()
        requestLayout()
    }

    fun invalidateRender() {
        render!!.invalidateDrawingCache()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (render == null) return
        val h = render!!.height
        setMeasuredDimension(measuredWidth, h + paddingTop + paddingBottom)
    }

    override fun onDraw(canvas: Canvas) {
        if (render == null) return
        g2.canvas = canvas
        render!!.draw(g2, paddingLeft, paddingTop)
    }
}
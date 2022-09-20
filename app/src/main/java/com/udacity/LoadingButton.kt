package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var loadingAngle=0f
    private var buttonWidth=0f
    private var btntext=""



   private lateinit var  animateCirle:ValueAnimator
    private lateinit var animateButton:ValueAnimator



    private var btnPaint=Paint(Paint.ANTI_ALIAS_FLAG).apply{
      style=Paint.Style.FILL
        textAlign=Paint.Align.CENTER
        textSize=80f
        typeface= Typeface.DEFAULT_BOLD
    }
  private val textPaint =Paint(Paint.ANTI_ALIAS_FLAG).apply {
      style=Paint.Style.FILL
      textAlign=Paint.Align.CENTER
      color=Color.WHITE
      textSize=80f
  }
   private val circlePaint=Paint(Paint.ANTI_ALIAS_FLAG).apply {
       style=Paint.Style.FILL
       isAntiAlias=true
               color=context.getColor(R.color.colorAccent)
   }

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading ->
            {
                //set the text while loading
                btntext = resources.getString(R.string.button_loading)
                //apply animation on cercle
                animateCirle = ValueAnimator.ofFloat(0f, 360f)
                    .apply {
                        duration = 3000
                        interpolator = AccelerateInterpolator(1f)
                        addUpdateListener { animation ->
                            loadingAngle = animatedValue as Float
                            invalidate()
                        }
                    }
                //apply animation on button
                animateButton = ValueAnimator.ofInt(0, widthSize)
                    .apply {
                        duration = 3000
                        repeatMode = ValueAnimator.RESTART
                        repeatCount = 1
                        addUpdateListener {
                            buttonWidth = (animatedValue as Int).toFloat()
                            invalidate()
                        }
                    }
                //start the animation while loading
                animateCirle.start()
                animateButton.start()
                this.isClickable = false

            }

            ButtonState.Completed -> {
                //set the text on complete
                btntext = resources.getString(R.string.download_completed)
                buttonWidth = 0f
                //end the animation after complete
                animateCirle.end()
               animateButton.end()
                this.isClickable = true

            }

        }

        invalidate()
    }


    init {
      isClickable=true
        btntext="Download"
        buttonState=ButtonState.Clicked
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
       drawViews(canvas)
    }
    private fun drawViews(canvas: Canvas?){
        //drawing the rectangle for our button to start
        btnPaint.color=context.getColor(R.color.colorPrimary)
       canvas!!.drawRect(0f,0f,widthSize.toFloat(),heightSize.toFloat(),btnPaint)
        //animate the rectangle
        btnPaint.color=context.getColor(R.color.colorPrimaryDark)
        canvas.drawRect(0f,0f,widthSize.toFloat()*buttonWidth/100,heightSize.toFloat(),btnPaint)

        // draw the text
        canvas.drawText(btntext,widthSize.toFloat()/2,heightSize.toFloat()/1.7f,textPaint)
        //draw the animated circle
        canvas.drawArc( widthSize - 140f, heightSize / 2 - 40f,
            widthSize - 75f, heightSize / 2 + 40f,
            0f, loadingAngle, true,circlePaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}
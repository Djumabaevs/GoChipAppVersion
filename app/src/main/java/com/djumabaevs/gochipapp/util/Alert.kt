package com.djumabaevs.gochipapp.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

//open class Alert : Dialog() {
//
//    private var message: CharSequence? = null
//    private var messageRes = 0
//
//    private var iconRes = 0
//
//    private var positiveButtonText: String? = null
//    private var negativeButtonText: String? = null
//
//    protected var positiveButtonCallback: (() -> Unit)? = null
//    protected var negativeButtonCallback: (() -> Unit)? = null
//
//    override fun setTitle(title: String?): Alert {
//        super.setTitle(title)
//        return this
//    }
//
//    override fun setTitleRes(titleRes: Int): Alert {
//        super.setTitleRes(titleRes)
//        return this
//    }
//
//    fun setMessage(message: CharSequence): Alert {
//        this.message = message
//        return this
//    }
//
//    fun setMessageRes(@StringRes stringRes: Int): Alert {
//        this.messageRes = stringRes
//        return this
//    }
//
//    fun setIconRes(@DrawableRes iconRes: Int): Alert {
//        this.iconRes = iconRes
//        return this
//    }
//
//    fun setPositiveButtonCallback(callback: () -> Unit): Alert {
//        positiveButtonCallback = callback
//        return this
//    }
//
//    fun setNegativeButtonCallback(callback: () -> Unit): Alert {
//        negativeButtonCallback = callback
//        return this
//    }
//
//    fun setPositiveButtonText(text: String): Alert {
//        positiveButtonText = text
//        return this
//    }
//
//    fun setNegativeButtonText(text: String): Alert {
//        negativeButtonText = text
//        return this
//    }
//
//    open fun provideRootView(): View {
//        return makePaddingContainer(context).apply {
//
//            addView(LinearLayout(context).apply {
//                orientation = LinearLayout.VERTICAL
//                setBackgroundResource(R.drawable.background_dialog)
//                setPadding(dpToPx(14), dpToPx(14), dpToPx(14), dpToPx(26))
//
//                // close icon
//                addView(FrameLayout(context).apply {
//                    setSelectableItemBackgroundBorderless()
//                    setOnClickListener {
//                        negativeButtonCallback?.invoke()
//                        dismiss()
//                    }
//                    setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4))
//
//                    addView(AppCompatImageView(context).apply {
//                        setImageDrawable(drawable(R.drawable.ic_close))
//                    }, FrameLayout.LayoutParams(WrapContent, WrapContent))
//                }, LinearLayout.LayoutParams(WrapContent, WrapContent).apply {
//                    setGravity(Gravity.END)
//                    bottomMargin = dpToPx(14)
//                })
//
//                // scrollable icon, title and message
//                addView(ScrollView(context).apply {
//                    isFocusable = false
//
//                    addView(LinearLayout(context).apply {
//                        orientation = LinearLayout.VERTICAL
//
//                        if (iconRes != 0) {
//                            addView(AppCompatImageView(context).apply {
//                                setImageResource(iconRes)
//                            }, LinearLayout.LayoutParams(dpToPx(50), dpToPx(50)).apply {
//                                gravity = Gravity.CENTER_HORIZONTAL
//                                bottomMargin = dpToPx(14)
//                            })
//                        }
//
//                        val title = title()
//                        if (title != null) {
//                            addView(AppCompatTextView(context).apply {
//                                text = title
//                                setTypeface(this.typeface, Typeface.BOLD)
//                                setTextColor(color(R.color.black))
//                                setTextSizeSp(14)
//                                gravity = Gravity.CENTER
//
//                            }, LinearLayout.LayoutParams(WrapContent, WrapContent).apply {
//                                gravity = Gravity.CENTER_HORIZONTAL
//                                bottomMargin = dpToPx(14)
//                            })
//                        }
//
//                        val message = if (messageRes != 0) {
//                            context.getString(messageRes)
//                        } else message
//
//                        if (message != null) {
//                            addView(AppCompatTextView(context).apply {
//                                text = message
//                                setTextSizeSp(12)
//                                gravity = Gravity.CENTER
//                            }, LinearLayout.LayoutParams(WrapContent, WrapContent).apply {
//                                bottomMargin = dpToPx(26)
//                                gravity = Gravity.CENTER_HORIZONTAL
//                            })
//                        }
//                    }, LinearLayout.LayoutParams(MatchParent, WrapContent))
//                }, LinearLayout.LayoutParams(MatchParent, dpToPx(0), 1F).apply {
//                    setMargins(dpToPx(12), dpToPx(0), dpToPx(12), dpToPx(0))
//                })
//
//                // buttons
//                addView(LinearLayout(context).apply {
//                    orientation = LinearLayout.HORIZONTAL
//
//                    if (negativeButtonText != null) {
//                        addView(Button(context).apply {
//                            onButtonClickListener = Runnable {
//                                negativeButtonCallback?.invoke()
//                                dismiss()
//                            }
//                            setPadding(dpToPx(16), dpToPx(0), dpToPx(16), dpToPx(0))
//                            setButtonText(negativeButtonText!!)
//                            setButtonTextSize(14)
//                            setButtonTextColor(color(R.color.green))
//                            setButtonBackground(R.drawable.button_background_light_green)
//                        }, LinearLayout.LayoutParams(WrapContent, dpToPx(38)).apply {
//                            marginEnd = dpToPx(5)
//                        })
//                    }
//
//                    addView(Button(context).apply {
//                        onButtonClickListener = Runnable {
//                            positiveButtonCallback?.invoke()
//                            dismiss()
//                        }
//                        setButtonText(positiveButtonText!!)
//                        setButtonTextSize(14)
//                        setButtonTextColor(color(R.color.white))
//                    }, LinearLayout.LayoutParams(dpToPx(0), dpToPx(38), 1F))
//                }, LinearLayout.LayoutParams(MatchParent, WrapContent).apply {
//                    gravity = Gravity.CENTER_HORIZONTAL
//                    setMargins(dpToPx(12), dpToPx(0), dpToPx(12), dpToPx(0))
//                })
//            }, LinearLayout.LayoutParams(MatchParent, WrapContent))
//        }
//
//    }
//
//    override fun show() {
//        if (positiveButtonText == null) {
//            positiveButtonText = context.getString(R.string.action_ok)
//        }
//
//        val builder = makeSupportBuilder(context)
//        val dialog = builder.create()
//        dialog.setView(provideRootView())
//        showDialog(dialog)
//
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    }
//}
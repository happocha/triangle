package com.example

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class TriangleFragment : Fragment() {

    companion object {
        init {
            System.loadLibrary("triangle")
        }
    }

    interface Callback {
        fun onRadian(radian: Float)
    }

    @Volatile
    private var angle = 0f
    private lateinit var textView: TextView
    private val hash = this.hashCode()

    private val callback: Callback = object : Callback {
        override fun onRadian(radian: Float) {
            requireActivity().runOnUiThread {
                textView.text = radian.toString()
            }
        }
    }

    external fun draw(w: Float, h: Float, scale: Float, angle: Float, hash: Int)
    external fun setRadCallback(callback: Callback?, hash: Int)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_triangle, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)

        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                angle = i.toFloat()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar) = Unit
        })

        val glSurfaceView = view.findViewById<GLSurfaceView>(R.id.glSurfaceView)
        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
            override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
                setRadCallback(callback, hash)
            }
            override fun onDrawFrame(gl: GL10) {
                draw(1000f, 2000f, 0f, angle, hash)
            }
            override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) = Unit
        })
    }
}

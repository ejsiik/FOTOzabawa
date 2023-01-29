package com.example.fotozabawa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fotozabawa.R
import kotlinx.android.synthetic.main.fragment_settings.view.*

class SettingsFragment : Fragment() {

    private lateinit var spinnerT: Spinner
    private lateinit var spinnerA: Spinner
    private lateinit var spinnerbPS: Spinner
    private lateinit var spinneraPS: Spinner
    private lateinit var spinneraSPS: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        timeSpinner(view)
        amountSpinner(view)
        beforePhotoSound(view)
        afterPhotoSound(view)
        afterSeriesSound(view)

        view.btnConfirm.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_fotoFragment)
        }

        return view.rootView
    }

    fun timeSpinner(view: View) {
        val timeTab = arrayOf("1", "2", "5", "10", "30")
        spinnerT = view.findViewById(R.id.spinnerTime)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, timeTab
            )
        }
        spinnerT.adapter = adapter
        spinnerT.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context, timeTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun amountSpinner(view: View) {
        val amountTab = arrayOf("1", "2", "5", "10", "30")
        spinnerA = view.findViewById(R.id.spinnerAmount)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, amountTab
            )
        }
        spinnerA.adapter = adapter
        spinnerA.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context, amountTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun beforePhotoSound(view: View) {
        val bpsTab = arrayOf("1", "2", "3", "4")
        spinnerbPS = view.findViewById(R.id.spinnerbPS)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, bpsTab
            )
        }
        spinnerbPS.adapter = adapter
        spinnerbPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context, bpsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun afterPhotoSound(view: View) {
        val apsTab = arrayOf("1", "2", "3", "4")
        spinneraPS = view.findViewById(R.id.spinneraPS)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, apsTab
            )
        }
        spinneraPS.adapter = adapter
        spinneraPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context, apsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun afterSeriesSound(view: View) {
        val aspsTab = arrayOf("1", "2", "3", "4")
        spinneraSPS = view.findViewById(R.id.spinneraSPS)
        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, aspsTab
            )
        }
        spinneraSPS.adapter = adapter
        spinneraSPS.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Toast.makeText(
                    context, aspsTab[position], Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Toast.makeText(
                    context, "Nothing selected", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
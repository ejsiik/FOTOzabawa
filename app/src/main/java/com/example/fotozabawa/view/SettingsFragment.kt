package com.example.fotozabawa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fotozabawa.R
import com.example.fotozabawa.model.entity.Model
import com.example.fotozabawa.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private lateinit var spinnerT: Spinner
    private lateinit var spinnerA: Spinner
    private lateinit var spinnerbPS: Spinner
    private lateinit var spinneraPS: Spinner
    private lateinit var spinneraSPS: Spinner
    private lateinit var spinnerTBS: Spinner
    private lateinit var spinnerBaner: Spinner
    private lateinit var spinnerFilter: Spinner
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        timeSpinner(view)
        amountSpinner(view)
        beforePhotoSound(view)
        afterPhotoSound(view)
        afterSeriesSound(view)
        timeBeforePhotoSoundSpinner(view)
        banerSpinner(view)
        filterSpinner(view)


        view.btnConfirm.setOnClickListener{
            val time = spinnerT.selectedItem.toString()
            val amount = spinnerA.selectedItem.toString()
            val bps = spinnerbPS.selectedItemId.toInt()
            val aps = spinneraPS.selectedItemId.toInt()
            val asps = spinneraSPS.selectedItemId.toInt()
            val tbps = spinnerTBS.selectedItem.toString()
            val baner = spinnerBaner.selectedItemId.toInt()
            val filter = spinnerFilter.selectedItemId.toInt()

            val model = Model (0, time, amount, bps, aps, asps, tbps, baner, filter)
            viewModel.addSettings(model)
            Toast.makeText(requireContext(), "Zmieniono ustawienia", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_settingsFragment_to_fotoFragment)
        }

        return view.rootView
    }

    fun timeSpinner(view: View) {
        spinnerT = view.spinnerTime
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.time_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerT.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = it.time
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinnerT.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun amountSpinner(view: View) {
        spinnerA = view.spinnerAmount
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.time_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerA.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = it.count
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinnerA.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun beforePhotoSound(view: View) {
        spinnerbPS = view.spinnerbPS
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sound_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerbPS.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = (it.soundBefore + 1).toString()
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinnerbPS.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun afterPhotoSound(view: View) {
        spinneraPS = view.spinneraPS
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sound_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinneraPS.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = (it.soundAfter + 1).toString()
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinneraPS.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun afterSeriesSound(view: View) {
        spinneraSPS = view.spinneraSPS
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sound_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinneraSPS.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = (it.soundFinish + 1).toString()
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinneraSPS.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun timeBeforePhotoSoundSpinner(view: View) {
        spinnerTBS = view.spinnerTimeBefore
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.time_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerTBS.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = it.timeBeforePhotoSound
                        val storedValueIndex = adapter.getPosition(actualIndex)
                        spinnerTBS.setSelection(storedValueIndex)
                    }
                }
            }
        }
    }

    fun banerSpinner(view: View) {
        spinnerBaner = view.spinnerBaner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.baner_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerBaner.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = it.baner
                        spinnerBaner.setSelection(actualIndex)
                    }
                }
            }
        }
    }

    fun filterSpinner(view: View) {
        spinnerFilter = view.spinnerFilter
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.filter_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerFilter.adapter = adapter
                lifecycleScope.launch(Dispatchers.IO){
                    val tmpModel = viewModel.getSettings()
                    tmpModel?.let {
                        val actualIndex = it.filter
                        spinnerFilter.setSelection(actualIndex)
                    }
                }
            }
        }
    }
}
package com.djumabaevs.gochipapp.ble

class ScanResultAdapter(private val inflater: LayoutInflater, private val recyclerView: RecyclerView) : RecyclerView.Adapter<ScanResultViewHolder>(), View.OnClickListener {

    private val scanResults = mutableListOf<ScanResult>()

    fun append(scanResult: ScanResult) {
        scanResults
            .indexOfFirst { it.device.address == scanResult.device.address }
            .takeIf { it != -1 }
            ?.let { index ->
                scanResults[index] = scanResult
                notifyItemChanged(index)
                return
            }

        scanResults += scanResult
        notifyItemInserted(scanResults.size - 1)
    }
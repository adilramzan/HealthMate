package com.example.healthmate.data.repository

import com.example.healthmate.data.model.HealthEntry

interface HealthRepository {
    fun saveHealthEntry(entry: HealthEntry, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun getHealthEntries(
        startDate: String,
        endDate: String,
        onSuccess: (List<HealthEntry>) -> Unit,
        onFailure: (Exception) -> Unit
    )
}
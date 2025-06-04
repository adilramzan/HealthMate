package com.example.healthmate.data.repository

import com.example.healthmate.data.model.HealthEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseHealthRepository : HealthRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun saveHealthEntry(entry: HealthEntry, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId)
            .collection("health_entries").document(entry.date)
            .set(entry)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    override fun getHealthEntries(
        startDate: String,
        endDate: String,
        onSuccess: (List<HealthEntry>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId)
            .collection("health_entries")
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .get()
            .addOnSuccessListener { result ->
                val entries = result.toObjects(HealthEntry::class.java)
                onSuccess(entries)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
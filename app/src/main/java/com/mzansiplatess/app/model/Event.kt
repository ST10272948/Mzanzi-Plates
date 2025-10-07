package com.mzansiplatess.app.model

import java.text.SimpleDateFormat
import java.util.*

data class Event(
    val _id: String? = null,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val address: String,
    val city: String,
    val startDate: String, // ISO 8601 format
    val endDate: String? = null,
    val startTime: String, // HH:mm format
    val endTime: String? = null,
    val price: Double,
    val currency: String = "ZAR",
    val maxAttendees: Int? = null,
    val currentAttendees: Int = 0,
    val image_url: String,
    val organizer: String,
    val contactEmail: String? = null,
    val contactPhone: String? = null,
    val tags: List<String> = emptyList(),
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    val isFullyBooked: Boolean get() = maxAttendees?.let { currentAttendees >= it } ?: false
    
    val spotsRemaining: Int? get() = maxAttendees?.let { maxOf(0, it - currentAttendees) }
    
    fun getFormattedPrice(): String {
        return when (currency.uppercase()) {
            "ZAR" -> "R${price.toInt()}"
            "USD" -> "$${String.format("%.2f", price)}"
            "EUR" -> "€${String.format("%.2f", price)}"
            else -> "${currency}${String.format("%.2f", price)}"
        }
    }
    
    fun getFormattedDateTime(): String {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val displayFormat = SimpleDateFormat("EEE, dd MMM • HH:mm", Locale.getDefault())
            
            val date = dateFormat.parse(startDate)
            val time = timeFormat.parse(startTime)
            
            // Combine date and time for display
            val calendar = Calendar.getInstance()
            calendar.time = date
            val timeCalendar = Calendar.getInstance()
            timeCalendar.time = time
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY))
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE))
            
            displayFormat.format(calendar.time)
        } catch (e: Exception) {
            "$startDate • $startTime"
        }
    }
    
    fun getFormattedDate(): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
            val date = inputFormat.parse(startDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            startDate
        }
    }
    
    fun getFormattedTime(): String {
        return try {
            val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val time = inputFormat.parse(startTime)
            outputFormat.format(time)
        } catch (e: Exception) {
            startTime
        }
    }
    
    fun getFullAddress(): String {
        return if (address.isNotEmpty()) "$address, $city" else city
    }
}

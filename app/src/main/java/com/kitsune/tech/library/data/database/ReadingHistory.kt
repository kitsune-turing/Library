package com.kitsune.tech.library.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reading_history",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("bookId")]
)
data class ReadingHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val bookId: Int,
    val currentPage: Int = 0,
    val status: String = "reading",
    val startedAt: Long = System.currentTimeMillis(),
    val lastReadAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

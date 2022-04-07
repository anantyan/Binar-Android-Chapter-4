package id.anantyan.exerciseproject.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_messages")
@Parcelize
data class Messages(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "fromName") val fromName: String? = null,
    @ColumnInfo(name = "senderName") val senderName: String? = null,
    @ColumnInfo(name = "message") val message: String? = null
) : Parcelable

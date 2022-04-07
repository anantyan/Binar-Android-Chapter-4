package id.anantyan.exerciseproject.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_users")
@Parcelize
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "password") val password: String? = null,
    @ColumnInfo(name = "fullName") val fullName: String? = null,
    @ColumnInfo(name = "pekerjaan") val pekerjaan: String? = null
) : Parcelable

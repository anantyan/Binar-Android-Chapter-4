package id.anantyan.challangechapter4.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tbl_users")
@Parcelize
data class Users(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "username") val username: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "password") val password: String? = null
) : Parcelable

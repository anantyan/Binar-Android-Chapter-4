package id.anantyan.challangechapter4.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.anantyan.challangechapter4.data.NotesDao
import id.anantyan.challangechapter4.data.UsersDao
import id.anantyan.challangechapter4.model.Notes
import id.anantyan.challangechapter4.model.Users

@Database(entities = [
    Notes::class,
    Users::class
], version = 1, exportSchema = false)
abstract class RoomDB: RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun database(context: Context): RoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "db_app"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = db
                return db
            }
        }
    }

    abstract fun notesDao(): NotesDao
    abstract fun usersDao(): UsersDao
}
package com.azamovme.MoviePlayerAkylai.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavRoomModel::class], version = 1)
abstract class LinksRoomDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
}
package com.sd.storyteller.data.local.database

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sd.storyteller.data.local.converter.StoryTypeConverters
import com.sd.storyteller.data.local.dao.StoryDao
import com.sd.storyteller.data.local.entity.StoryEntity

@Database(
    entities = [
        StoryEntity::class
    ],
    version = 1,
    exportSchema = true

)
@TypeConverters(StoryTypeConverters::class)
abstract class StoryNestDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
}
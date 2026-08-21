package com.sd.storyteller.di

/**
 * Created by SDHOLPURIA on 01-08-2026.
 */
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sd.storyteller.core.setting.SettingsRepository
import com.sd.storyteller.core.tts.StoryTextToSpeech
import com.sd.storyteller.data.local.dao.StoryDao
import com.sd.storyteller.data.local.database.StoryNestDatabase
import com.sd.storyteller.data.remote.api.GeminiApi
import com.sd.storyteller.data.repository.StoryRepositoryImpl
import com.sd.storyteller.domain.repository.StoryRepository
import com.sd.storyteller.domain.usecase.DeleteStoryUseCase
import com.sd.storyteller.domain.usecase.GenerateStoryUseCase
import com.sd.storyteller.domain.usecase.GetFavoriteStoriesUseCase
import com.sd.storyteller.domain.usecase.GetStoriesUseCase
import com.sd.storyteller.domain.usecase.GetStoryUseCase
import com.sd.storyteller.domain.usecase.UpdateFavoriteUseCase
import com.sd.storyteller.domain.usecase.UpdateReadingPositionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoryModule {

    @Provides
    @Singleton
    fun provideStoryRepository(
        api: GeminiApi,
        dao: StoryDao
    ): StoryRepository {

        return StoryRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideGenerateStoryUseCase(
        repository: StoryRepository
    ): GenerateStoryUseCase {
        return GenerateStoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): StoryNestDatabase {

        return Room.databaseBuilder(
            context,
            StoryNestDatabase::class.java,
            "storynest.db"
        ).fallbackToDestructiveMigration()
//            .addMigrations(MIGRATION_3_4,MIGRATION_4_5)
            .build()
    }

    @Provides
    @Singleton
    fun provideStoryDao(
        database: StoryNestDatabase
    ): StoryDao {

        return database.storyDao()
    }

    @Provides
    @Singleton
    fun provideGetStoriesUseCase(
        repository: StoryRepository
    ): GetStoriesUseCase {

        return GetStoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetStoryUseCase(
        repository: StoryRepository
    ): GetStoryUseCase {

        return GetStoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteStoryUseCase(
        repository: StoryRepository
    ): DeleteStoryUseCase {

        return DeleteStoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteStoriesUseCase(
        repository: StoryRepository
    ): GetFavoriteStoriesUseCase {

        return GetFavoriteStoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateFavoriteUseCase(
        repository: StoryRepository
    ): UpdateFavoriteUseCase {

        return UpdateFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideStoryTextToSpeech(
        @ApplicationContext context: Context
    ): StoryTextToSpeech {

        return StoryTextToSpeech(context)
    }

    @Provides
    @Singleton
    fun provideUpdateReadingPositionUseCase(
        repository: StoryRepository
    ): UpdateReadingPositionUseCase {

        return UpdateReadingPositionUseCase(repository)
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {

        override fun migrate(
            db: SupportSQLiteDatabase
        ) {

            db.execSQL(
                """
            ALTER TABLE stories
            ADD COLUMN language TEXT NOT NULL DEFAULT 'hi-IN'
            """.trimIndent()
            )
        }
    }
    val MIGRATION_4_5 =
        object : Migration(4, 5) {

            override fun migrate(
                db: SupportSQLiteDatabase
            ) {

                /*
                 * Before version 5:
                 *
                 * 0 was being used as the default value.
                 *
                 * Version 5 uses:
                 *
                 * -1 = never read
                 *  0 = first sentence
                 * >0 = progressed
                 *
                 * Existing records with 0 were previously
                 * considered "not read", so migrate them
                 * to -1.
                 */
                db.execSQL(
                    """
                UPDATE stories
                SET lastReadSentence = -1
                WHERE lastReadSentence = 0
                """.trimIndent()
                )
            }
        }


    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {

        return SettingsRepository(context)
    }
}
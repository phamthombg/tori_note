package com.torilab.note.di

import android.content.Context
import androidx.room.Room
import com.torilab.data.local.NoteDao
import com.torilab.data.local.NotesDatabase
import com.torilab.data.repository.NoteRepositoryImpl
import com.torilab.domain.repository.NoteRepository
import com.torilab.domain.usecase.AddNoteUseCase
import com.torilab.domain.usecase.DeleteNoteUseCase
import com.torilab.domain.usecase.GetNoteByIdUseCase
import com.torilab.domain.usecase.GetNotesUseCase
import com.torilab.domain.usecase.NoteUseCases
import com.torilab.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase =
        Room.databaseBuilder(context, NotesDatabase::class.java, NotesDatabase.DB_NAME).build()

    @Provides
    fun provideNoteDao(db: NotesDatabase): NoteDao = db.noteDao()

    @Provides
    @Singleton
    fun provideNoteRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideNoteUseCases(repo: NoteRepository): NoteUseCases = NoteUseCases(
        getNotes = GetNotesUseCase(repo),
        getNoteById = GetNoteByIdUseCase(repo),
        addNote = AddNoteUseCase(repo),
        updateNote = UpdateNoteUseCase(repo),
        deleteNote = DeleteNoteUseCase(repo)
    )
}
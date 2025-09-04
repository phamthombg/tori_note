package com.torilab.data.mapper

import com.torilab.data.local.NoteEntity
import com.torilab.domain.model.Note

fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)
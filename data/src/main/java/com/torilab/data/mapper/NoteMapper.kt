package com.torilab.data.mapper

import com.torilab.data.local.NoteEntity
import com.torilab.domain.model.Note

/**
 * convert entity Note to domain Note
 * @return domain Note
 */
fun NoteEntity.toDomain() = Note(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * convert domain Note to entity Note
 * @return entity Note
 */
fun Note.toEntity() = NoteEntity(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)
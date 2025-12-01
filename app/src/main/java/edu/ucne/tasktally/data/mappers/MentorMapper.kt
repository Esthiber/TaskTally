package edu.ucne.tasktally.data.mappers

import edu.ucne.tasktally.data.local.entidades.MentorEntity
import edu.ucne.tasktally.domain.models.Mentor

fun MentorEntity.toDomain() = Mentor(
    mentorId = mentorId,
    userId = userId,
    userInfoId = userInfoId
)

fun Mentor.toEntity() = MentorEntity(
    mentorId = mentorId,
    userId = userId,
    userInfoId = userInfoId
)
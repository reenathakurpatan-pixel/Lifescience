package com.example.data

import com.example.model.CsirUnit
import com.example.model.StudyTopic

object CsirSyllabusCatalog {
    val allUnits: List<CsirUnit> = (units1To4 + units5To8 + units9To13).sortedBy { it.unitNumber }

    val allTopics: List<StudyTopic> = allUnits.flatMap { it.topics }

    fun findUnit(unitNumber: Int): CsirUnit? = allUnits.firstOrNull { it.unitNumber == unitNumber }

    fun findTopic(topicId: String): StudyTopic? = allTopics.firstOrNull { it.id == topicId }

    fun searchTopics(query: String): List<StudyTopic> {
        val trimmed = query.trim().lowercase()
        if (trimmed.isEmpty()) return emptyList()
        return allTopics.filter { topic ->
            topic.title.lowercase().contains(trimmed) ||
                topic.subtitle.lowercase().contains(trimmed) ||
                topic.overview.lowercase().contains(trimmed) ||
                topic.keyFormulas.any { it.lowercase().contains(trimmed) } ||
                topic.pyqPointers.any { it.lowercase().contains(trimmed) } ||
                topic.sections.any { sec ->
                    sec.heading.lowercase().contains(trimmed) ||
                        sec.bullets.any { it.lowercase().contains(trimmed) }
                }
        }
    }
}

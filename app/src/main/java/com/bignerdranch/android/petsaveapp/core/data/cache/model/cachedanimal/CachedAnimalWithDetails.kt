package com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedanimal

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bignerdranch.android.petsaveapp.core.data.cache.model.cachedorganization.CachedOrganization
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AdoptionStatus
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Animal
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.AnimalWithDetails
import com.bignerdranch.android.petsaveapp.core.domain.model.animal.Media
import com.bignerdranch.android.petsaveapp.core.utils.DateTimeUtils


@Entity(
    tableName = "animals",
    foreignKeys = [
        ForeignKey(
            entity = CachedOrganization::class,
            parentColumns = ["organizationId"],
            childColumns = ["organizationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("organizationId")]
)
data class CachedAnimalWithDetails(
    @PrimaryKey(autoGenerate = false)
    val animalId: Long,
    val organizationId: String,
    val name: String,
    val type: String,
    val description: String,
    val age: String,
    val species: String,
    val primaryBreed: String,
    val secondaryBreed: String,
    val primaryColor: String,
    val secondaryColor: String,
    val tertiaryColor: String,
    val gender: String,
    val size: String,
    val coat: String,
    val isSpayedOrNeutered: Boolean,
    val isDeclawed: Boolean,
    val hasSpecialNeeds: Boolean,
    val shotsAreCurrent: Boolean,
    val goodWithChildren: Boolean,
    val goodWithDogs: Boolean,
    val goodWithCats: Boolean,
    val adoptionStatus: String,
    val publishedAt: String
) {
    companion object {
        fun fromDomain(domainModel: AnimalWithDetails): CachedAnimalWithDetails {
            val details = domainModel.details
            val healthDetails = details.healthDetails
            val habitatAdaptation = details.habitatAdaptation

            return CachedAnimalWithDetails(
                animalId = domainModel.id,
                organizationId = details.organization.id,
                name = domainModel.name,
                type = domainModel.type,
                description = details.description,
                age = details.age.toString(),
                species = details.species,
                primaryBreed = details.breed.primary,
                secondaryBreed = details.breed.secondary,
                primaryColor = details.colors.primary,
                secondaryColor = details.colors.secondary,
                tertiaryColor = details.colors.tertiary,
                gender = details.gender.toString(),
                size = details.size.toString(),
                coat = details.coat.toString(),
                isSpayedOrNeutered = healthDetails.isSpayedOrNeutered,
                isDeclawed = healthDetails.isDeclawed,
                hasSpecialNeeds = healthDetails.hasSpecialNeeds,
                shotsAreCurrent = healthDetails.shotsAreCurrent,
                goodWithChildren = habitatAdaptation.goodWithChildren,
                goodWithDogs = habitatAdaptation.goodWithDogs,
                goodWithCats = habitatAdaptation.goodWithCats,
                adoptionStatus = domainModel.adoptionStatus.toString(),
                publishedAt = domainModel.publishedAt.toString()
            )
        }
    }

    fun toDomain(
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>,
        organization: CachedOrganization
    ): AnimalWithDetails {
        return AnimalWithDetails(
            id = animalId,
            name = name,
            type = type,
            details = mapDetails(organization),
            media = Media(
                photos = photos.map { it.toDomain() },
                videos = videos.map { it.toDomain() }
            ),
            tags = tags.map { it.tag },
            adoptionStatus = AdoptionStatus.valueOf(adoptionStatus),
            publishedAt = DateTimeUtils.parse(publishedAt)
        )
    }

    fun toAnimalDomain(
        photos: List<CachedPhoto>,
        videos: List<CachedVideo>,
        tags: List<CachedTag>): Animal {
        return Animal(
            id = animalId,
            name = name,
            type = type,
            media = Media(
                photos = photos.map { it.toDomain() },
                videos = videos.map { it.toDomain() }
            ),
            tags = tags.map { it.tag },
            adoptionStatus = AdoptionStatus.valueOf(adoptionStatus),
            publishedAt = DateTimeUtils.parse(publishedAt)
        )
    }

    private fun mapDetails(organization: CachedOrganization): AnimalWithDetails.Details {
        return AnimalWithDetails.Details(
            description = description,
            age = AnimalWithDetails.Details.Age.valueOf(age),
            species = species,
            breed = AnimalWithDetails.Details.Breed(primaryBreed, secondaryBreed),
            colors = AnimalWithDetails.Details.Colors(primaryColor, secondaryColor, tertiaryColor),
            gender = AnimalWithDetails.Details.Gender.valueOf(gender),
            size = AnimalWithDetails.Details.Size.valueOf(size),
            coat = AnimalWithDetails.Details.Coat.valueOf(coat),
            healthDetails = AnimalWithDetails.Details.HealthDetails(
                isSpayedOrNeutered,
                isDeclawed,
                hasSpecialNeeds,
                shotsAreCurrent
            ),
            habitatAdaptation = AnimalWithDetails.Details.HabitatAdaptation(
                goodWithChildren,
                goodWithDogs,
                goodWithCats
            ),
            organization = organization.toDomain()
        )
    }
}

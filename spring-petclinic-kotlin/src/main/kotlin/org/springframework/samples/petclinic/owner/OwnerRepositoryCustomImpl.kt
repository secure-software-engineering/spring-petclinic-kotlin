package org.springframework.samples.petclinic.owner

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("ownerRepositoryImpl")
class OwnerRepositoryCustomImpl : OwnerRepository {
    @PersistenceContext
    private val entityManager: EntityManager? = null
    override fun findByLastName(lastName: String): Collection<Owner> {
        val sqlQuery =
            "SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName = '$lastName'"
        val query = entityManager!!.createQuery(
            sqlQuery,
            Owner::class.java
        )
        return query.resultList
    }

    override fun findById(id: Int): Owner {
        val sqlQuery = "SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id = $id"
        val query = entityManager!!.createQuery(
            sqlQuery,
            Owner::class.java
        )
        return query.singleResult
    }

    override fun save(owner: Owner) {

        // If the object already exists, then we can't directly use the detached object in persist.
        if (owner.id != null) {
            entityManager!!.merge(owner)
            return
        }
        entityManager!!.persist(owner)
    }
}

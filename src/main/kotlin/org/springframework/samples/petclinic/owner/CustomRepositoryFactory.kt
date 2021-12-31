package org.springframework.samples.petclinic.owner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.JpaEntityInformation
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.data.repository.core.RepositoryMetadata
import org.springframework.data.repository.core.support.RepositoryComposition
import java.io.Serializable
import javax.persistence.EntityManager


/**
 *
 * The purpose of this class is to override the default behaviour of the spring JpaRepositoryFactory class.
 * It will produce a GenericRepositoryImpl object instead of SimpleJpaRepository.
 *
 */
class CustomRepositoryFactory(entityManager: EntityManager?) : JpaRepositoryFactory(entityManager!!) {
    protected fun <T, ID : Serializable?> getTargetRepository(
        metadata: RepositoryMetadata, entityManager: EntityManager?
    ): JpaRepository<*, *>? {
        val repositoryInterface = metadata.repositoryInterface
        val information = getRepositoryInformation(metadata, RepositoryComposition.fromMetadata(metadata).fragments)
        val entityInformation: JpaEntityInformation<*, Serializable> = getEntityInformation(metadata.domainType)

        // System.out.println("CustomRepositoryFactory1 - repositoryInterface: " + repositoryInterface.getName());
        return if (repositoryInterface.name.endsWith("OwnerRepository")) {
            //return new OwnerRepositoryCustomImpl((JpaEntityInformation<Owner, ?>) entityInformation, entityManager); //custom implementation
            null
        } else {
            getTargetRepositoryViaReflection(information, entityInformation, entityManager)
        }
    }

    override fun getRepositoryBaseClass(metadata: RepositoryMetadata): Class<*> {
        val repositoryInterface = metadata.repositoryInterface

        // System.out.println("CustomRepositoryFactory2 - repositoryInterface: " + repositoryInterface.getName());
        return if (repositoryInterface.name.endsWith("OwnerRepository")) {
            OwnerRepositoryCustomImpl::class.java // custom implementation
        } else {
            SimpleJpaRepository::class.java
        }
    }
}

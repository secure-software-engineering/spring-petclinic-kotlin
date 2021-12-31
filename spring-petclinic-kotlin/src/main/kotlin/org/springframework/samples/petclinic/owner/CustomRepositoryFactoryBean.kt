package org.springframework.samples.petclinic.owner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
import org.springframework.data.repository.core.support.RepositoryFactorySupport
import java.io.Serializable
import javax.persistence.EntityManager


class CustomRepositoryFactoryBean<T : JpaRepository<S, ID>?, S, ID : Serializable?>(repositoryInterface: Class<out T>?) :
    JpaRepositoryFactoryBean<T, S, ID>(repositoryInterface!!) {
    /**
     * Returns a [RepositoryFactorySupport].
     *
     * @param entityManager
     * @return
     */
    override fun createRepositoryFactory(entityManager: EntityManager): RepositoryFactorySupport {
        return CustomRepositoryFactory(entityManager)
    }
}

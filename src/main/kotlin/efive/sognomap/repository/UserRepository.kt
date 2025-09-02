package efive.sognomap.repository

import efive.sognomap.models.User
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository: JpaRepository<User, Long> {
    fun findByGithubId(githubId: String): User?
}
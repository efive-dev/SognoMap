package efive.sognomap.service

import efive.sognomap.models.User
import efive.sognomap.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2Service(
    private val userRepository: UserRepository
): DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User? {
            val oauth2User = super.loadUser(userRequest)

            val githubId = oauth2User.attributes["id"].toString()
            val username = oauth2User.attributes["login"] as String
            val email = oauth2User.attributes["email"] as String?

            val user: User = userRepository.findByGithubId(githubId)
                ?: User(
                    githubId = githubId,
                    username = username,
                    email = email
                )

            userRepository.save(user)

            return oauth2User
    }
}
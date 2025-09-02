package efive.sognomap.config

import efive.sognomap.service.Oauth2Service
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(
    private val oauth2Service: Oauth2Service
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/", "/login").permitAll()
                auth.anyRequest().authenticated()
            }
            .oauth2Login { oauth ->
                oauth.loginPage("/login")
                oauth.userInfoEndpoint { userInfo ->
                    userInfo.userService(oauth2Service)
                }
                oauth.defaultSuccessUrl("/home", true)
            }
            .logout { logout ->
                logout.logoutSuccessUrl("/")
            }

        return http.build()
    }
}



package efive.sognomap

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class SognoMapApplication

fun main(args: Array<String>) {
    runApplication<SognoMapApplication>(*args)
}

@Controller
class PageController {

    @GetMapping("/")
    @ResponseBody
    fun root(): String {
        return "Welcome to SognoMap! <a href=\"/login\">Login with GitHub</a>"
    }

    @GetMapping("/login")
    fun login(): String {
        return "redirect:/oauth2/authorization/github?prompt=login"
    }
}

@RestController
class HomeController {

    @GetMapping("/home")
    @ResponseBody
    fun home(request: HttpServletRequest): String {
        val csrfToken = request.getAttribute("_csrf") as CsrfToken
        return """
            ✅ You are logged in with GitHub and reached the homepage!<br><br>
            <form action="/logout" method="post">
                <input type="hidden" name="${csrfToken.parameterName}" value="${csrfToken.token}" />
                <button type="submit">Logout</button>
            </form>
        """.trimIndent()
    }
}

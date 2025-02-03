package action.`in`.blog.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

data class Address(
    val city: String,
    val zipcode: String,
) {
    constructor() : this("", "")
}

class CustomAuthenticatedUser(
    authorities: Collection<GrantedAuthority>,
    idToken: OidcIdToken,
    userInfo: OidcUserInfo,
) : DefaultOidcUser(authorities, idToken, userInfo) {
    constructor() : this(
        emptySet(),
        OidcIdToken(
            "token",
            Instant.now(),
            Instant.now().plusSeconds(1000),
            mapOf("sub" to "sub")
        ),
        OidcUserInfo(
            mapOf(
                "id" to UUID.randomUUID(),
                "addressDetails" to Address("Seoul", "000000")
            )
        ),
    )
}

@RestController
class SessionController {

    @GetMapping("/foo")
    fun foo(request: HttpServletRequest) {
        request.session.setAttribute(
            "USER",
            CustomAuthenticatedUser(
                setOf(SimpleGrantedAuthority("ROLE_ADMIN")),
                OidcIdToken(
                    "access-token",
                    Instant.now(),
                    Instant.now().plusSeconds(36000),
                    mapOf(
                        "sub" to "a1ca1319-01f2-49d4-b5e8-899c64d49f63",
                    ),
                ),
                OidcUserInfo(
                    mapOf(
                        "id" to UUID.fromString("a1ca1319-01f2-49d4-b5e8-899c64d49f63"),
                        "addressDetails" to Address("Seoul", "010101")
                    ),
                )
            )
        )
    }

    @GetMapping("/bar")
    fun bar(request: HttpServletRequest): CustomAuthenticatedUser {
        return request.session.getAttribute(
            "USER"
        ) as CustomAuthenticatedUser
    }
}

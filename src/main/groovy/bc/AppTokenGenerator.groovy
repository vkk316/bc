package bc

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import org.springframework.security.core.userdetails.UserDetails

class AppTokenGenerator implements TokenGenerator {

    @Override
    AccessToken generateAccessToken(UserDetails principal) {
        return
    }

    @Override
    AccessToken generateAccessToken(UserDetails principal, Integer expiration) {
        return null
    }
}

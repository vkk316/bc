package bc

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.rest.*
import grails.converters.*
import org.apache.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder

class GoogleController {
    def appUserDetailsService
    def tokenGenerator
    def tokenStorageService
    def accessTokenJsonRenderer
    def googleService
    //...

    def auth() {
        def token = request.JSON.access_token ?: request['access_token'] //get fb token from params or json payload
        if (!googleService.isTokenForApp(token)) { //verify if token is for our app
            respond([error: "Incorrect app access token"], status: HttpStatus.SC_BAD_REQUEST)
            return
        }

        def userInfo = googleService.getUserInfo(token) //get user info from facebook

        if (!userInfo?.user_id) { //something went wrong
            respond([error: userInfo?.error], status: HttpStatus.SC_UNAUTHORIZED)
            return
        }

        userInfo = new GoogleUserInfo(
                id: userInfo.user_id,
                email: userInfo.email,
        )

        User user = User.googleLogin(userInfo)

        if (!user || user.hasErrors()) {
            respond([error: [message: "persist error", errors: user.errors]], status: HttpStatus.SC_INTERNAL_SERVER_ERROR)
            return
        }

        def userDetails = appUserDetailsService.loadUserByUsername(user.username)

        //create and store API access token, whatever your token generation and storage strategy may be,
        //this will work (ie JWT, GORM, etc)
        AccessToken accessToken = tokenGenerator.generateAccessToken(userDetails)
        tokenStorageService.storeToken(accessToken.accessToken, userDetails)

        //authenticationEventPublisher.publishTokenCreation(accessToken)
        SecurityContextHolder.context.setAuthentication(accessToken)

        render contentType: 'application/json', encoding: 'UTF-8', text: accessTokenJsonRenderer.generateJson(accessToken)
    }

    //...
}
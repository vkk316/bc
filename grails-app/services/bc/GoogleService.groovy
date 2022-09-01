package bc

import grails.gorm.transactions.Transactional
import groovyx.net.http.RESTClient

import java.util.logging.Logger

@Transactional
class GoogleService {

    RESTClient client

    void init() {
        client = new RESTClient('https://www.googleapis.com/oauth2/v1/tokeninfo')
        client.handler.failure = client.handler.success //so it doesnt throw Exceptions when status is !=200
    }

    def getUserInfo(String token) {
        def resp = client.get(query: [access_token: token])
        if (resp.status != 200) {
            log.error "Error getting user information! $resp.status: $resp.data"
        } else {
            log.trace(resp.data)
            resp.data
        }
    }

    boolean isTokenForApp(String token) {
        def resp = client.get(query: [access_token: token])
        resp.status == 200
    }
}

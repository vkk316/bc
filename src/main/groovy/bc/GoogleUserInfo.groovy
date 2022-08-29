package bc

import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority

// Not for DaoAuth
class GoogleUserInfo {
    String id
    String email
}

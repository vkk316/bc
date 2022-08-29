package bc;
import grails.plugin.springsecurity.rest.token.generation.jwt.CustomClaimProvider
import org.springframework.security.core.userdetails.UserDetails
import com.nimbusds.jwt.JWTClaimsSet
import groovy.transform.CompileStatic
import bc.AppUserDetails

@CompileStatic
class AdditionalClaimProvider implements CustomClaimProvider {
    @Override
    void provideCustomClaims(JWTClaimsSet.Builder builder, UserDetails details, String principal, Integer expiration) {
        AppUserDetails u = (AppUserDetails) details
        builder.claim('uid', u.id)
    }
}
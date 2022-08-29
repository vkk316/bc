package bc

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic
import bc.GoogleUserInfo
import org.apache.commons.lang3.RandomStringUtils

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String name
    String email
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static User googleLogin(GoogleUserInfo ggInfo) {
        def user = findByUsername(ggInfo.id)
        boolean isNew = false
        if (!user) { //does user already exist?
            isNew = true
            user = new User(username: ggInfo.id, password: RandomStringUtils.randomAlphanumeric(16))

        }
        user.with { //or other fields you want
            email = ggInfo.email
        }

        if (user.isDirty() || isNew) { //if any fields changed or is a new user, save
            user.save(flush: true)
        }

        if (!user.hasErrors()) { //give user roles if user saved properly
            def userRole = Role.findByAuthority("ROLE_USER")
            def facebookRole = Role.findByAuthority("ROLE_FACEBOOK")

            if (!user.authorities.contains(userRole)) UserRole.create(user, userRole, true)
            if (!user.authorities.contains(facebookRole)) UserRole.create(user, facebookRole, true)
        }

        user
    }

    static constraints = {
        password nullable: true, blank: false, password: true
        username nullable: false, blank: false, unique: true
        name nullable: true
    }

    static mapping = {
        password column: '`password`'
    }
}


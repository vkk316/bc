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
    Participant participant
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static User googleLogin(GoogleUserInfo ggInfo) {
        def user = findByUsername(ggInfo.id)
        if (!user) { //does user already exist?
            def participant = new Participant(email: ggInfo.email)
            user = new User(username: ggInfo.id, password: RandomStringUtils.randomAlphanumeric(16), participant: participant)
            user.save()
        }
        user
    }

    static constraints = {
        password nullable: true, blank: false, password: true
        username nullable: false, blank: false, unique: true
    }

    static mapping = {
        password column: '`password`'
    }
}


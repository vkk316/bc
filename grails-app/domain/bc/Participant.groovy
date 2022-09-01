package bc

import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_GOOGLE'])
class Participant {
    String title
    String firstName
    String lastName
    String nickName
    String email
    String phoneNumber
    String address
    String organization
    boolean busReservation
    String speakingTopic
    String participatingReason
    boolean isHalal
    String allergic

    static mapping = {
        speakingTopic type: 'text'
        participatingReason type: 'text'
    }

    static belongsTo = [
            user: User,
    ]

    @Override
    String toString() {
        email
    }
}
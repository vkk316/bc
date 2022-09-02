package bc

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*
import grails.validation.ValidationException
import grails.gorm.transactions.Transactional

@Secured(['ROLE_GOOGLE'])
class ParticipantController {
    static responseFormats = ['json']

    SpringSecurityService springSecurityService

    def index() {
        def result = [valid: false]
        try {
            def user = User.findById(springSecurityService.getCurrentUserId() as Long)
            def instance = Participant.findById(user.participant.id)
            if (instance.hasErrors()) {
                respond instance.errors
                return
            }
            result.data = instance
            result.valid = true
        } catch (error) {
            result.reason = error.message
        }
        render(result as JSON)
    }

    @Transactional
    def save(Participant me) {
        def result = [valid: false]
        try {
            def user = User.findById(springSecurityService.getCurrentUserId() as Long)
            def instance = Participant.findById(user.participant.id)
            if (me.hasErrors()) {
                respond me.errors
                return
            }
            bindData(instance, me)
            instance.save()
            result.data = instance
            result.valid = true
        } catch (ValidationException error) {
            result.reason = me.errors
        } catch (error) {
            result.reason = error.message
        }
        render(result as JSON)
    }
}

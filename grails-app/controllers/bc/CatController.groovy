package bc

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*

import javax.transaction.Transactional

class CatController {
	static responseFormats = ['json']

    def index() {
        def result = [valid: false]
        def cats = Cat.list()
        def totalCount = Cat.count()
        result.data = cats
        result.totalCount = totalCount
        result.valid = true
        render(result as JSON)
    }
}

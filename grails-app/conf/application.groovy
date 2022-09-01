grails.gorm.default.constraints = {
        '*'(nullable: true)
}

grails.plugin.springsecurity.userLookup.userDomainClassName = 'bc.User'
grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName = 'login'
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName = 'permissions'
grails.plugin.springsecurity.authority.groupAuthorityNameField = 'authorities'
grails.plugin.springsecurity.securityConfigType = "Annotation"

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']],
        [pattern: '/error',          access: ['permitAll']],
        [pattern: '/index',          access: ['permitAll']],
        [pattern: '/index.gsp',      access: ['permitAll']],
        [pattern: '/shutdown',       access: ['ROLE_ADMIN']],
        [pattern: '/assets/**',      access: ['permitAll']],
        [pattern: '/**/js/**',       access: ['permitAll']],
        [pattern: '/**/css/**',      access: ['permitAll']],
        [pattern: '/**/images/**',   access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/h2-console/**',      filters: 'none'],
        [pattern: '/console/**',      filters: 'none'],
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/api/oauth/**', 	 filters: 'none'],
        [pattern: '/api/**',     	 filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/**',             filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'],
]

environments {
        development {
                grails.plugin.springsecurity.rest.token.storage.jwt.secret="qzD6h8K6S1088Q06C2Muv21TErJxPYqn"
                grails.plugin.springsecurity.rest.token.storage.jwt.expiration=60 * 60

                system{
                        store {
                                targetDir = './dep/store'
                        }
                }
        }
        production {
                grails.plugin.springsecurity.rest.token.storage.jwt.secret="qzD6h8K5811102Q06Y6Rfk28XErImPYxn"
                grails.plugin.springsecurity.rest.token.storage.jwt.expiration=3600
        }
}

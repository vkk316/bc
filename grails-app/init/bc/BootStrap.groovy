package bc
import bc.GoogleService

class BootStrap {

    def googleService

    def init = { servletContext ->
        googleService.init()
    }
    def destroy = {
    }
}

package bc

class Cat {

    String name
    String gender

    static belongsTo = [
            user: User
    ]

    static mapping = {

    }
}

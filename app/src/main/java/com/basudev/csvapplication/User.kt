package crash.gocolor.gowebs.csvapplication

import java.util.*

class User {
    var fullNames: String? = null
    var dateOfBirth: Date? = null
    var height = 0.0
    var isActive = false
    var roles: List<String>? = null

    constructor() {}
    constructor(
        fullNames: String?,
        dateOfBirth: Date?,
        height: Double,
        isActive: Boolean,
        roles: List<String>?
    ) {
        this.fullNames = fullNames
        this.dateOfBirth = dateOfBirth
        this.height = height
        this.isActive = isActive
        this.roles = roles
    }
}
package com.envel.accountservice.exceptions

class ResourceNotFoundException(override var message: String): RuntimeException(message) {
    var resource: String? = null
    var resourceProperty: String? = null

    constructor(resource: String?, resourceProperty: String?, message: String): this(message) {
        this.resource = resource
        this.resourceProperty = resourceProperty

    }
}
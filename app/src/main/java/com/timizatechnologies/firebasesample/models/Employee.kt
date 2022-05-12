package com.timizatechnologies.firebasesample.models

import com.google.firebase.database.Exclude
import java.io.Serializable

class Employee : Serializable {
    @Exclude
    var key: String? = null
    var name: String? = null
    var position: String? = null

    constructor() {}
    constructor(name: String?, position: String?) {
        this.name = name
        this.position = position
    }
}

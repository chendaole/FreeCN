package com.example.plugin


class PluginCallMe : com.example.pluginlibrary.PluginCallMe {

    constructor(): super()

    override  fun name(name: String): String {
        return  "plugin:${name}"
    }

    override fun old(): String {
        return  "pligin: old"
    }
}
package com.undsf.modrinth.models

class ParamList: ArrayList<Pair<String, String>>() {
    fun add(key: String, value: String) {
        add(Pair(key, value))
    }
}
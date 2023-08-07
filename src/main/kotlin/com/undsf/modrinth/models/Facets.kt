package com.undsf.modrinth.models

class Facets(
    val relationship: LogicRelationship = LogicRelationship.AND
): ArrayList<Facets.Facet>() {
    class Facet(
        val type: FacetType,
        val value: String,
        val operation: OperationType = OperationType.eq,
    ) {
        override fun toString(): String {
            return "${type.field}${operation.stringify}${value}"
        }
    }

    enum class LogicRelationship {
        AND,
        OR
    }

    enum class FacetType(val field: String) {
        projectType("project_type"),
        categories("categories"),
        versions("versions"),
        clientSide("client_side"),
        serverSide("server_side"),
        open_source("open_source"),
    }

    enum class OperationType(val stringify: String) {
        eq(":"),
        neq("!="),
        gte(">="),
        gt(">"),
        lte("<="),
        lt("<")
    }

    override fun toString(): String {
        return joinToString(",", "[", "]") {
            it.toString()
        }
    }

    companion object {
        fun andOf(vararg facets: Facet) {
            val facets = Facets(LogicRelationship.AND)
            facets.addAll(facets)
        }

        fun orOf(vararg facets: Facet) {
            val facets = Facets(LogicRelationship.OR)
            facets.addAll(facets)
        }
    }
}
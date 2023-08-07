package com.undsf.modrinth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.undsf.modrinth.models.*
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

class ModrinthApi(
    private val version: String = "v2",
    private val branch: String? = null,
) {
    private val client = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()

    private val baseURL: String get() {
        val prefix = if (branch != null) "${branch}-" else ""
        return "https://${prefix}api.modrinth.com/${version}"
    }

    private fun buildQuery(params: ParamList?): String {
        if (params.isNullOrEmpty()) return ""
        return params.joinToString("&") {
            val name = URLEncoder.encode(it.first, StandardCharsets.UTF_8)
            val value = URLEncoder.encode(it.second, StandardCharsets.UTF_8)
            "${name}=${value}"
        }
    }

    private fun buildGetRequest(uri: String, params: ParamList?): HttpRequest {
        return buildGetRequest(uri, buildQuery(params))
    }

    private fun buildGetRequest(uri: String, query: String = ""): HttpRequest {
        val url = StringBuilder()
        url.append(baseURL)
        url.append(uri)
        if (query.isNotEmpty()) {
            url.append("?")
            url.append(query)
        }
        return HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url.toString()))
            .build()
    }

    private fun send(uri: String, params: ParamList? = null): String? {
        val req = buildGetRequest(uri, params)
        val resp = client.send(req, HttpResponse.BodyHandlers.ofString())
        if (resp.statusCode() != 200) {
            return null
        }
        return resp.body()
    }

    /**
     * 获取指定工程
     */
    fun getProject(id: String): Project? {
        val uri = "/project/${id}"
        val respJson = send(uri)
        if (respJson.isNullOrEmpty()) return null
        return mapper.readValue<Project>(respJson)
    }

    /**
     * 搜索工程
     */
    fun searchProjects(
        query: String? = null,
        facets: Facets? = null,
        index: SortField? = null,
        offset: Int? = null,
        limit: Int? = null,
    ): SearchResults<ProjectResult>? {
        val uri = "/search"
        val params = ParamList()
        if (query != null) params.add("query", query)
        if (facets != null) params.add("facets", facets.toString())
        if (index != null) params.add("index", index.name)
        if (offset != null) params.add("offset", "$offset")
        if (limit != null) params.add("limit", "$limit")

        val respJson = send(uri, params)
        if (respJson.isNullOrEmpty()) return null
        return mapper.readValue<SearchResults<ProjectResult>>(respJson)
    }

    /**
     * 获取指定版本
     */
    fun getVersion(versionId: String): Version? {
        val uri = "/version/${versionId}"
        val respJson = send(uri)
        if (respJson.isNullOrEmpty()) return null
        return mapper.readValue<Version>(respJson)
    }

    /**
     * 获取指定工程的版本列表
     */
    fun listProjectVersions(
        projectId: String,
        loaders: List<String>? = null,
        gameVersions: List<String>? = null,
        featured: Boolean? = null
    ): List<Version>? {
        val uri = "/project/${projectId}/version"
        val params = ParamList()
        if (loaders != null) {
            val value = loaders.joinToString(",", "[", "]") {
                "\"${it}\""
            }
            params.add("loaders", value)
        }
        if (gameVersions != null) {
            val value = gameVersions.joinToString(",", "[", "]") {
                "\"${it}\""
            }
            params.add("game_versions", value)
        }
        if (featured != null) params.add("featured", featured.toString())

        val respJson = send(uri, params)
        if (respJson.isNullOrEmpty()) return null
        return mapper.readValue<List<Version>>(respJson)
    }

    enum class SortField {
        relevance,
        downloads,
        follows,
        newest,
        updated,
    }
}
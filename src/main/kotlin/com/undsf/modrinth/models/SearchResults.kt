package com.undsf.modrinth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 分页结果
 */
class SearchResults<T: Result>(
    /**
     * 当前页搜索结果
     */
    val hits: List<T>,

    /**
     * 偏移
     */
    val offset: Long,

    /**
     * 数量限制
     */
    val limit: Long,

    /**
     * 搜索到的总量
     */
    @JsonProperty("total_hits")
    val totalHits: Long
)

interface Result

/**
 * 工程搜索结果
 *
 * 简化的Project，有一些
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class ProjectResult(
    /**
     * ID
     */
    @JsonProperty("project_id")
    val id: String,

    /**
     * Slug
     *
     * 正则表达式：^[\w!@$()`.+,"\-']{3,64}$
     */
    val slug: String,

    /**
     * 作者名字
     */
    @JsonProperty("author")
    val authorName: String,

    /**
     * 标题
     */
    val title: String,

    /**
     * 描述（短）
     */
    val description: String,

    /**
     * 分类
     */
    val categories: List<String>,

    /**
     * 支持游戏版本列表
     */
    @JsonProperty("versions")
    val gameVersions: List<String>,
): Result
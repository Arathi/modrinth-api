package com.undsf.modrinth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 工程信息
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class Project(
    /**
     * ID
     */
    val id: String,

    /**
     * Slug
     *
     * 正则表达式：^[\w!@$()`.+,"\-']{3,64}$
     */
    val slug: String,

    /**
     * 标题
     */
    val title: String,

    /**
     * 描述（短）
     */
    val description: String,

    /**
     * 描述（长）
     */
    val body: String,

    /**
     * 客户端支持情况
     */
    @JsonProperty("client_side")
    val clientSide: SideSupport,

    /**
     * 服务端端支持情况
     */
    @JsonProperty("server_side")
    val serverSide: SideSupport,

    /**
     * 分类
     */
    val categories: List<String>,

    /**
     * 额外分类
     */
    @JsonProperty("additional_categories")
    val additionalCategories: List<String>,

    /**
     * 支持游戏版本列表
     */
    @JsonProperty("game_versions")
    val gameVersions: List<String>,

    /**
     * 支持模组加载器列表
     */
    val loaders: List<String>,

    /**
     * 版本列表
     */
    val versions: List<String>,
) {
    enum class SideSupport {
        required,
        optional,
        unsupported,
    }

    override fun toString(): String {
        return "%${id}/${slug} | ${title}"
    }
}

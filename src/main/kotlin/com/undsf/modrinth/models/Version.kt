package com.undsf.modrinth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Version(
    /**
     * ID
     */
    val id: String,

    /**
     * 名称
     */
    val name: String,

    /**
     * 版本号
     */
    @JsonProperty("version_number")
    val versionNumber: String,

    /**
     * 依赖
     */
    val dependencies: List<Dependency>,

    /**
     * 支持游戏版本
     */
    @JsonProperty("game_versions")
    val gameVersions: List<String>,

    /**
     * 版本类型
     */
    @JsonProperty("version_type")
    val versionType: VersionType,

    /**
     * 支持模组加载器类型
     */
    val loaders: List<String>,

    /**
     * 工程ID
     */
    @JsonProperty("project_id")
    val projectId: String,

    /**
     * 作者ID
     */
    @JsonProperty("author_id")
    val authorId: String,

    /**
     * 发布时间
     */
    @JsonProperty("date_published")
    val datePublished: String,

    /**
     * 下载次数
     */
    val downloads: Long,

    /**
     * 文件列表
     */
    val files: List<File>
) {
    val primaryFile: File? get() {
        val pfs = files.filter { it.primary }
        return if (pfs.size == 1) pfs[0]
            else null
    }

    // region 内部类
    /**
     * 依赖
     */
    class Dependency(
        /**
         * 版本ID
         */
        val versionId: String,

        /**
         * 工程ID
         */
        val projectId: String,

        /**
         * 文件名
         */
        val fileName: String,

        /**
         * 依赖类型
         */
        val dependencyType: DependencyType,
    ) {
        /**
         * 依赖类型
         */
        enum class DependencyType {
            required,
            optional,
            incompatible,
            embedded,
        }
    }

    enum class VersionType {
        release,
        beta,
        alpha
    }

    class File(
        /**
         * 哈希码
         */
        val hashes: Hashes,

        /**
         * 下载地址
         */
        val url: String,

        /**
         * 文件名
         */
        val filename: String,

        /**
         * 是否为主文件
         */
        val primary: Boolean,

        /**
         * 文件大小
         */
        val size: Long,

        /**
         * 文件类型
         */
        @JsonProperty("file_type")
        val fileType: String?,
    ) {
        val sha1: String get() = hashes.sha1

        class Hashes(
            val sha512: String,
            val sha1: String
        )

        override fun toString(): String {
            return "${sha1} ${url}"
        }
    }
    // endregion

    override fun toString(): String {
        return "${projectId}/${id} ${versionNumber} ${primaryFile}"
    }
}
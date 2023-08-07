package com.undsf.modrinth

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(ModrinthApiTests::class.java)

class ModrinthApiTests {
    val api = ModrinthApi()

    @Test
    fun testGetProject() {
        // 通过slug获取
        val jei = api.getProject("jei")
        Assertions.assertNotNull(jei)

        // 通过id获取
        val waystones = api.getProject("LOpKHB2A")
        Assertions.assertNotNull(waystones)

        val fabricApi = api.getProject("P7dR8mSH")
        Assertions.assertNotNull(fabricApi)

        // 不存在的工程
        val notExists = api.getProject("NoTeXIsT")
        Assertions.assertNull(notExists)
    }

    @Test
    fun testGetVersion() {
        val jei101 = api.getVersion("6QsZu0uX")
        Assertions.assertNotNull(jei101)

        val notExists = api.getVersion("NoTeXIsT")
        Assertions.assertNull(notExists)
    }

    @Test
    fun testSearchProjects() {
        val all = api.searchProjects()
        Assertions.assertNotNull(all)
        Assertions.assertNotEquals(0, all!!.hits.size)
        logger.info("总共找到${all.totalHits}个搜索结果，当前页有${all.hits.size}个")
    }

    @Test
    fun testListProjectVersions() {
        val versions = api.listProjectVersions(
            "jei",
            loaders = listOf("forge"),
            gameVersions = listOf("1.20.1")
        )
        Assertions.assertNotNull(versions)
        logger.info("找到${versions!!.size}个版本")
    }
}
<?xml version="1.0" encoding="UTF-8"?>
<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform" id="hammer-1.0" version="1.0">
    <output method="text"/>
    <template match="//*">
        <call-template name="elem"/>
    </template>
    <template match="/*">
        <call-template name="elem"/>
        <text>;</text>
    </template>
    <template match="text()">
        <call-template name="text"/>
    </template>
    <template name="elem">
        <call-template name="elem-start"/>
        <call-template name="attrs"/>
        <apply-templates/>
        <call-template name="elem-end"/>
    </template>
    <template name="elem-start">
        <text>e("</text>
        <value-of select="local-name()"/>
        <text>"</text>
    </template>
    <template name="attrs">
        <for-each select="@*">
            <call-template name="attr"/>
        </for-each>
        <if test="child::node()">
            <text>, </text>
        </if>
    </template>
    <template name="attr">
        <text>, a("</text>
        <value-of select="local-name()"/>
        <text>", "</text>
        <value-of select="."/>
        <text>")</text>
    </template>
    <template name="elem-end">
        <text>)</text>
        <call-template name="sibling-comma"/>
    </template>
    <template name="text">
        <variable name="trimmed" select="normalize-space(.)"/>
        <choose>
            <when test="$trimmed != ''">
                <call-template name="text-content">
                    <with-param name="trimmed" select="$trimmed"/>
                </call-template>
            </when>
            <otherwise>
                <value-of select="."/>
            </otherwise>
        </choose>
    </template>
    <template name="text-content">
        <param name="trimmed"/>
        <text>"</text>
        <value-of select="$trimmed"/>
        <text>"</text>
        <call-template name="sibling-comma"/>
    </template>
    <template name="sibling-comma">
        <if test="following-sibling::*">
            <text>, </text>
        </if>
    </template>
</stylesheet>

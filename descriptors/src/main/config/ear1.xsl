<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" encoding="UTF-8" indent="yes"
   xmlns:xslt="http://xml.apache.org/xslt" 
   xslt:indent-amount="2"
    />
  <xsl:template match="/">
    <application xmlns="http://tomee.apache.org/VEAR" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://tomee.apache.org/VEAR vear-1.0.0.xsd" javaSpecificationVersion="1.8">
      <launcher>
        <xsl:element name="mavenResolver">
          <xsl:attribute name="groupId"><xsl:value-of select="/verificationMetadatas/verificationMetadata/@groupId" /></xsl:attribute>
          <xsl:attribute name="artifactId"><xsl:value-of select="/verificationMetadatas/verificationMetadata/@artifactId" /></xsl:attribute>
          <xsl:attribute name="version"><xsl:value-of select="/verificationMetadatas/verificationMetadata/@version" /></xsl:attribute>
          <xsl:attribute name="classifier"><xsl:value-of select="/verificationMetadatas/verificationMetadata/@classifier" /></xsl:attribute>
          <attachDependencies>
            <excludeWithParents groupId="fr.gaellalire.vestige_app.myeeapps" artifactId="myeeapps-ejb1"/>
            <excludeWithParents groupId="fr.gaellalire.vestige_app.myeeapps" artifactId="myeeapps-ejb2"/>
          </attachDependencies>
        </xsl:element>
        <verificationMetadata>
          <text>
            <xsl:value-of select="/verificationMetadatas/verificationMetadata/text()" />
          </text>
        </verificationMetadata>

        <xsl:for-each select="/verificationMetadatas/warVerificationMetadata">
          <xsl:element name="warLauncher">
            <xsl:attribute name="path"><xsl:value-of select="@path" /></xsl:attribute>
            <xsl:element name="mavenResolver">
              <xsl:attribute name="groupId"><xsl:value-of select="@groupId" /></xsl:attribute>
              <xsl:attribute name="artifactId"><xsl:value-of select="@artifactId" /></xsl:attribute>
              <xsl:attribute name="version"><xsl:value-of select="@version" /></xsl:attribute>
              <xsl:attribute name="classifier"><xsl:value-of select="@classifier" /></xsl:attribute>
              <attachDependencies>
                <excludeWithParents groupId="javax" artifactId="javaee-api"/>
              </attachDependencies>
            </xsl:element>
            <verificationMetadata>
              <text>
                <xsl:value-of select="text()" />
              </text>
            </verificationMetadata>
          </xsl:element>
        </xsl:for-each>
      </launcher>
      <configurations>
        <mavenConfig>
          <setClassifierToExtension extension="war" classifier="exploded-assembly" />
          <additionalRepository id="gaellalire-repo" url="https://gaellalire.fr/maven/repository/" />
        </mavenConfig>
      </configurations>
    </application>
  </xsl:template>
</xsl:stylesheet>

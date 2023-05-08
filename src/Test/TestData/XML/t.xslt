<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     version="1.0">
     <xsl:output method="text"/>
     <xsl:template match="shirt">
          <xsl:variable name="shirtColorCode" select="@colorCode"/>
          <xsl:value-of select="/shirts/colors/color[@cid = $shirtColorCode]"/>
          <xsl:text></xsl:text>
          <xsl:apply-templates/>
          <xsl:text></xsl:text>
     </xsl:template>
     <xsl:template match="color"/>
</xsl:stylesheet>
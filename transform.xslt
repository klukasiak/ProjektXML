<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
    <html>
        <body>
            <h2>Pacjenci</h2>
            <ul>
                <xsl:for-each select="przychodnia/pacjenci/pacjent">
                    <li><xsl:value-of select="imie" />  <xsl:value-of select="nazwisko" /></li>
                </xsl:for-each>
            </ul>
        </body>
    </html>
</xsl:template>
</xsl:stylesheet>
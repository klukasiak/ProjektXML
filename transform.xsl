<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output indent="yes" encoding="UTF-8"/>
<xsl:template match="/">
<xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
    <html lang="en">
        <head>
            <title>Transformacja</title>
            <style>
                body {
                    background-color: #93baf9;
                }

                li {
                    padding: 8px;
                }

                table {
                    border-collapse: collapse;
                }

                table, th, td {
                    border: 1px solid black;
                }

                th {
                    padding: 9px;
                }

                td {
                    padding: 5px;
                }
            </style>
        </head>
        <body>
            <h2>Pacjenci</h2>
            <ul>
                <xsl:for-each select="przychodnia/pacjenci/pacjent">
                    <xsl:choose>
                        <xsl:when test="@plec='M'">
                            <li><b><xsl:value-of select="imie" /> <br /> <xsl:value-of select="nazwisko" /></b></li>
                        </xsl:when>
                        <xsl:when test="@plec='K'">
                            <li><i><xsl:value-of select="imie" /> <br /> <xsl:value-of select="nazwisko" /></i></li>
                        </xsl:when>
                    </xsl:choose>
                </xsl:for-each>
            </ul>
            <h3>Wizyty</h3>
            <table>
                <tr>
                    <th>Data wizyty</th>
                    <th>Godzina wizyty</th>
                    <th>Nr pokoju</th>
                </tr>

                <xsl:for-each select="przychodnia/wizyty/wizyta">
                    <xsl:sort select="data_wizyty"/>
                    <tr>
                        <td><xsl:value-of select="data_wizyty"/></td>
                        <td><xsl:value-of select="godzina_wizyty"/></td>
                        <td>
                        <xsl:if test="nr_pokoju=1">:) </xsl:if>
                        <xsl:value-of select="nr_pokoju"/></td>
                    </tr>
                </xsl:for-each>
            </table>

            <h3>Nasi lekarze</h3>

            <h4>Pediatrzy</h4>

            <img src="pediatra.jpeg" alt="pediartra"/>

            <ul>
                <xsl:for-each select="przychodnia/lekarze/lekarz">
                    <xsl:if test="@specjalizacja='pediatra'">
                    <li><i><xsl:value-of select="imie" /> <br /> <xsl:value-of select="nazwisko" /></i></li>
                    </xsl:if>
                </xsl:for-each>
            </ul>

            <h4>Laryngolodzy</h4>

            <img src="laryngolog.jpg" alt="laryngolog"/>

            <ul>
                <xsl:for-each select="przychodnia/lekarze/lekarz">
                    <xsl:if test="@specjalizacja='laryngolog'">
                    <li><i><xsl:value-of select="imie" /> <br /> <xsl:value-of select="nazwisko" /></i></li>
                    </xsl:if>
                </xsl:for-each>
            </ul>
        </body>
    </html>
</xsl:template>
</xsl:stylesheet>
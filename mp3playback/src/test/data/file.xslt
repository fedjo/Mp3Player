<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h1>Artist Biography</h1>
                <table border="1">
                    <tr>
                        <th>Short BIO</th>
                    </tr>
		 <xsl:for-each select="artist/bio">
                    <tr>
                        <td><xsl:value-of select="summary"/></td>
                    </tr>
                 </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet> 

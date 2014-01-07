/********************************************************* begin of preamble
**
** Copyright (C) 2003-2012 Software- und Organisations-Service GmbH. 
** All rights reserved.
**
** This file may be used under the terms of either the 
**
**   GNU General Public License version 2.0 (GPL)
**
**   as published by the Free Software Foundation
**   http://www.gnu.org/licenses/gpl-2.0.txt and appearing in the file
**   LICENSE.GPL included in the packaging of this file. 
**
** or the
**  
**   Agreement for Purchase and Licensing
**
**   as offered by Software- und Organisations-Service GmbH
**   in the respective terms of supply that ship with this file.
**
** THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
** IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
** THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
** PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
** BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
** CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
** SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
** INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
** CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
** ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGE.
********************************************************** end of preamble*/
package com.sos.JSHelper.io.Files;

import com.sos.JSHelper.Listener.JSListenerClass;

/* ---------------------------------------------------------------------------
APL/Software GmbH - Berlin
##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
Montag, 15. Oktober 2007, Klaus.Buettner@sos-berlin.com (KB)
-------------------------------------------------------------------------------
<docu type="smcw" version="1.0">
<project>com.sos.IDocs</project>
<name>JSFileListEntry.java</name>
<title>Datenstruktur f�r Dateiverarbeitung
</title>
<description>
<para>
Diese Klasse rep�sentiert eine Datenstruktur f�r die Dateiverarbeitung
von lokalen und remote Dateien.
</para>
</description>
<params>
</params>
<keywords>
	<keyword>Datenstruktur</keyword>
	<keyword>Dateiliste</keyword>
</keywords>
<categories>
<category>Datenstruktur</category>
</categories>
<date>Montag, 15. Oktober 2007</date>
<copyright>� 2000, 2001 by SOS GmbH Berlin</copyright>
<author>Klaus.Buettner@sos-berlin.com</author>
<changes>
<change who='KB' when='Montag, 15. Oktober 2007' id='created'>
  <what>
  <para>
  created
  </para>
  </what>
</change>
</changes>
</docu>
---------------------------------------------------------------------------- */
/**
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
APL/Software GmbH - Berlin
##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
Montag, 15. Oktober 2007, Klaus.Buettner@sos-berlin.com (KB)
* <br />---------------------------------------------------------------------------
* </p>
* JSFileListEntry - Datenstruktur f�r Dateiverarbeitung
* <p>
* Diese Klasse rep�sentiert eine Datenstruktur f�r die Dateiverarbeitung
* von lokalen und remote Dateien.
* </p>
* <p>
* Verwendung findet diese Struktur beim download von Dateien, deren Name
* �ber einen (regul�ren) Ausdruck definiert ist und dabei dann mehr als
* eine Datei relevant ist.
* </p>
* @author Klaus.Buettner@sos-berlin.com
* @version 0.9
* @see reference
* @exception classname description
*
*/

public class JSFileListEntry extends JSListenerClass {

	private String strRemoteFileName = null;
	private String strLocalFileName = null;
	private long lngNoOfBytesTransferred = 0;
	
	public JSFileListEntry () {
		
	}

	public JSFileListEntry (String pstrLocalFileName) {
		this ("", pstrLocalFileName, 0);
	}

	public JSFileListEntry (String pstrRemoteFileName, String pstrLocalFileName, long plngNoOfBytesTransferred) {
		strRemoteFileName = pstrRemoteFileName;
		strLocalFileName = pstrLocalFileName;
		lngNoOfBytesTransferred = plngNoOfBytesTransferred;
	}

	public String toString () {
		String strT = String.format("RemoteFile = %1$s, LocalFile = %2$s, BytesTransferred = %3$s", this.RemoteFileName(), this.LocalFileName(), this.NoOfBytesTransferred());
		return strT;
	}
	public String RemoteFileName () {
		return strRemoteFileName;
	}

	public void setRemoteFileName (String pstrRemoteFileName) {
		this.strRemoteFileName = pstrRemoteFileName;
	}

	public String LocalFileName () {
		return strLocalFileName;
	}

	public void setLocalFileName (String pstrLocalFileName) {
		this.strLocalFileName = pstrLocalFileName;
	}

	public long NoOfBytesTransferred () {
		return lngNoOfBytesTransferred;
	}

	public void setNoOfBytesTransferred (long plngNoOfBytesTransferred) {
		this.lngNoOfBytesTransferred = plngNoOfBytesTransferred;
	}
}

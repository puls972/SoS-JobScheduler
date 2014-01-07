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
package com.sos.JSHelper.Options;


/**
* \class SOSOptionPassword
*
* \brief SOSOptionPassword -
*
* \details
*
* \section SOSOptionPassword.java_intro_sec Introduction
*
* \section SOSOptionPassword.java_samples Some Samples
*
* \code
*   .... code goes here ...
* \endcode
*
* <p style="text-align:center">
* <br />---------------------------------------------------------------------------
* <br /> APL/Software GmbH - Berlin
* <br />##### generated by ClaviusXPress (http://www.sos-berlin.com) #########
* <br />---------------------------------------------------------------------------
* </p>
* \author KB
* @version $Id: SOSOptionPassword.java 14829 2011-07-15 17:38:31Z kb $16.05.2010
* \see reference
*
* Created on 16.05.2010 19:50:51
 */

public class SOSOptionPassphrase extends SOSOptionPassword {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1374430778591063177L;
	private final String		conClassName		= "SOSOptionPassphrase";
	private final String				strCachedPW			= "";

	public SOSOptionPassphrase(final JSOptionsClass pPobjParent, final String pPstrKey, final String pPstrDescription, final String pPstrValue, final String pPstrDefaultValue,
			final boolean pPflgIsMandatory) {
		super(pPobjParent, pPstrKey, pPstrDescription, pPstrValue, pPstrDefaultValue, pPflgIsMandatory);
		this.setHideValue(true);
	}

	/**
	 *
	 * \brief toString
	 *
	 * \details
	 * returns in any case stars as value of the password, independently what the real value of the password is.
	 * \return
	 *
	 * @return
	 */
}

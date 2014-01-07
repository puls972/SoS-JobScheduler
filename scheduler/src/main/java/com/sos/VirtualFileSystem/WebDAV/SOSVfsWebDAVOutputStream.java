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
package com.sos.VirtualFileSystem.WebDAV;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.apache.webdav.lib.WebdavResource;


public class SOSVfsWebDAVOutputStream extends ByteArrayOutputStream {
	
	@SuppressWarnings("unused")
	private final String	conClassName		= "SOSVfsWebDAV";
	
	@SuppressWarnings("unused")
	private final Logger	logger		= Logger.getLogger(SOSVfsWebDAVOutputStream.class);
	private final WebdavResource	resource;

	/**
	 *
	 * \brief SOSVfsWebDAVOutputStream
	 *
	 * \details
	 *
	 * @param res
	 */
	public SOSVfsWebDAVOutputStream(final WebdavResource res) {
		super();
		resource = res;
	}
	
	/**
	 *
	 * \brief getResource
	 *
	 * \details
	 *
	 * \return WebdavResource
	 */
	public WebdavResource getResource() {
		return resource;
	}
	
	/**
	 *
	 * \brief put
	 *
	 * \details
	 *
	 * @throws HttpException, IOException
	 */
	public void put() throws HttpException, IOException {
		resource.putMethod(this.toByteArray());
		if(resource.exists() == false) {
			String msg = String.format("%1$s: %2$s", resource.getPath(), resource.getStatusMessage());
			throw new HttpException(msg);
		}
	}

	/**
	 *
	 * \brief close
	 *
	 * \details
	 *
	 * \return
	 *
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		super.close();
		resource.close();
	}
}

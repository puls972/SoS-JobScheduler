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
/**
 * I18N Messages and Logging
 * Copyright (C) 2006 John J. Mazzitelli
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package com.sos.i18n.logging.commons;

import com.sos.i18n.Logger;
import com.sos.i18n.LoggerLocale;
import com.sos.i18n.Msg;

import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Similar in functionality to the {@link CommonsLogMsg} utility class, this provides the ability to log messages
 * that are retrieved from a resource bundle with the additional capability of being able to set the base bundle
 * name and locale that this logger will use to log the message. If the locale is not specified or <code>
 * null</code>, the {@link LoggerLocale} will be used to determine what locale will be used.
 *
 * @author  <a href="mailto:jmazzitelli@users.sourceforge.net">John Mazzitelli</a>
 * @version $Revision: 1.1 $
 */
public class CommonsLogger
   extends Logger
{
   private Log m_log;

   /**
    * @see Logger#Logger(String, Msg.BundleBaseName, Locale)
    */
   public CommonsLogger( String             name,
                         Msg.BundleBaseName basename,
                         Locale             locale )
   {
      super( name, basename, locale );
   }

   /**
    * @see Logger#Logger(Class, Msg.BundleBaseName, Locale)
    */
   public CommonsLogger( Class              clazz,
                         Msg.BundleBaseName basename,
                         Locale             locale )
   {
      super( clazz, basename, locale );
   }

   /**
    * @see Logger#Logger(String, Msg.BundleBaseName)
    */
   public CommonsLogger( String             name,
                         Msg.BundleBaseName basename )
   {
      super( name, basename );
   }

   /**
    * @see Logger#Logger(Class, Msg.BundleBaseName)
    */
   public CommonsLogger( Class              clazz,
                         Msg.BundleBaseName basename )
   {
      super( clazz, basename );
   }

   /**
    * @see Logger#Logger(String, Locale)
    */
   public CommonsLogger( String name,
                         Locale locale )
   {
      super( name, locale );
   }

   /**
    * @see Logger#Logger(Class, Locale)
    */
   public CommonsLogger( Class  clazz,
                         Locale locale )
   {
      super( clazz, locale );
   }

   /**
    * @see Logger#Logger(String)
    */
   public CommonsLogger( String name )
   {
      super( name );
   }

   /**
    * @see Logger#Logger(Class)
    */
   public CommonsLogger( Class clazz )
   {
      super( clazz );
   }

   /**
    * @see Logger#isFatalEnabled()
    */
   public boolean isFatalEnabled()
   {
      return m_log.isFatalEnabled();
   }

   /**
    * @see Logger#isErrorEnabled()
    */
   public boolean isErrorEnabled()
   {
      return m_log.isErrorEnabled();
   }

   /**
    * @see Logger#isWarnEnabled()
    */
   public boolean isWarnEnabled()
   {
      return m_log.isWarnEnabled();
   }

   /**
    * @see Logger#isInfoEnabled()
    */
   public boolean isInfoEnabled()
   {
      return m_log.isInfoEnabled();
   }

   /**
    * @see Logger#isDebugEnabled()
    */
   public boolean isDebugEnabled()
   {
      return m_log.isDebugEnabled();
   }

   /**
    * @see Logger#isTraceEnabled()
    */
   public boolean isTraceEnabled()
   {
      return m_log.isTraceEnabled();
   }

   /**
    * Calls {@link CommonsLogMsg#fatal(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#fatal(Throwable, String, Object[])
    */
   public Msg fatal( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.fatal( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#fatal(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#fatal(String, Object[])
    */
   public Msg fatal( String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.fatal( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#error(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#error(Throwable, String, Object[])
    */
   public Msg error( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.error( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#error(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#error(String, Object[])
    */
   public Msg error( String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.error( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#warn(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#warn(Throwable, String, Object[])
    */
   public Msg warn( Throwable throwable,
                    String    key,
                    Object... varargs )
   {
      return CommonsLogMsg.warn( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#warn(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#warn(String, Object[])
    */
   public Msg warn( String    key,
                    Object... varargs )
   {
      return CommonsLogMsg.warn( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#info(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#info(Throwable, String, Object[])
    */
   public Msg info( Throwable throwable,
                    String    key,
                    Object... varargs )
   {
      return CommonsLogMsg.info( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#info(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#info(String, Object[])
    */
   public Msg info( String    key,
                    Object... varargs )
   {
      return CommonsLogMsg.info( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#debug(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#debug(Throwable, String, Object[])
    */
   public Msg debug( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.debug( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#debug(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#debug(String, Object[])
    */
   public Msg debug( String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.debug( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#trace(Log, Throwable, Msg.BundleBaseName, Locale, String, Object[])} to log the
    * message.
    *
    * @see Logger#trace(Throwable, String, Object[])
    */
   public Msg trace( Throwable throwable,
                     String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.trace( m_log, throwable, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Calls {@link CommonsLogMsg#trace(Log, Msg.BundleBaseName, Locale, String, Object[])} to log the message.
    *
    * @see Logger#trace(String, Object[])
    */
   public Msg trace( String    key,
                     Object... varargs )
   {
      return CommonsLogMsg.trace( m_log, getBaseBundleName(), getLocale(), key, varargs );
   }

   /**
    * Creates the Commons-Logging log object.
    *
    * @see Logger#createLogObject(Class)
    */
   protected void createLogObject( Class clazz )
   {
      m_log = LogFactory.getLog( clazz );
   }

   /**
    * Creates the Commons-Logging log object.
    *
    * @see Logger#createLogObject(String)
    */
   protected void createLogObject( String name )
   {
      m_log = LogFactory.getLog( name );
   }
}

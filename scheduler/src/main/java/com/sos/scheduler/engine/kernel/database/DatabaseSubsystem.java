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
package com.sos.scheduler.engine.kernel.database;

import com.google.common.collect.ImmutableMap;
import com.sos.scheduler.engine.common.Lazy;
import com.sos.scheduler.engine.cplusplus.runtime.annotation.ForCpp;
import com.sos.scheduler.engine.kernel.cppproxy.DatabaseC;
import com.sos.scheduler.engine.kernel.scheduler.Subsystem;
import com.sos.scheduler.engine.kernel.variable.UnmodifiableVariableSet;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import static com.sos.scheduler.engine.persistence.SchedulerDatabases.persistenceUnitName;
import static javax.persistence.Persistence.createEntityManagerFactory;

@ForCpp
public class DatabaseSubsystem implements Subsystem {
    private final DatabaseC cppProxy;
    private final Lazy<EntityManagerFactory> entityManagerFactoryLazy = new Lazy<EntityManagerFactory>() {
        @Override protected EntityManagerFactory compute() {
            try {
                return createEntityManagerFactory(persistenceUnitName, entityManagerProperties());
            } catch (PersistenceException e) {
                throw new RuntimeException(e +". Cause: "+e.getCause(), e);     // Hibernate liefert nur nichtssagende Meldung "Unable to build EntityManagerFactory", ohne den interessanten Cause
            }
        }
    };

    public DatabaseSubsystem(DatabaseC cppProxy) {
        this.cppProxy = cppProxy;
    }

    public final void close() {
        if (entityManagerFactoryLazy.isDefined())
            entityManagerFactoryLazy.get().close();   // Schließt auch alle EntityManager
    }

    public final EntityManagerFactory entityManagerFactory() {
        return entityManagerFactoryLazy.get();
    }

    private ImmutableMap<String,String> entityManagerProperties() {
        UnmodifiableVariableSet v = cppDatabaseProperties();
        return new ImmutableMap.Builder<String,String>()
            .put("javax.persistence.jdbc.driver", v.apply("jdbc.driverClass"))
            .put("javax.persistence.jdbc.url", v.apply("path"))
            .put("javax.persistence.jdbc.user", v.apply("user"))
            .put("javax.persistence.jdbc.password", v.apply("password"))
          //.put("hibernate.show_sql", "true")
            .build();
    }

    /** Liefert auch "password" */
    private UnmodifiableVariableSet cppDatabaseProperties() {
        return cppProxy.properties().getSister();
    }
}

/*
 * dev.kobalt.demodb
 * Copyright (C) 2022 Tom.K
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.kobalt.demodb.jvm.database

import org.h2.tools.Server
import org.jetbrains.exposed.sql.Database

object DatabaseRepository {

    var dbServerPort: Int? = null

    val server by lazy {
        Server.createTcpServer("-tcpPort", dbServerPort.toString())
    }

    val main by lazy {
        Database.connect("jdbc:h2:./database;DB_CLOSE_DELAY=-1;", "org.h2.Driver")
    }

}


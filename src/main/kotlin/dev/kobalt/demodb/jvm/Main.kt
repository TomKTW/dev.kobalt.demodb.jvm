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

package dev.kobalt.demodb.jvm

import dev.kobalt.demodb.jvm.database.DatabaseRepository
import dev.kobalt.demodb.jvm.demo.DemoTable
import dev.kobalt.web.administration.extension.transaction
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import org.jetbrains.exposed.sql.SchemaUtils
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val parser = ArgParser("demodb")
    val dbServerPort by parser.option(ArgType.Int, "dbServerPort", null, null)
    parser.parse(args)
    dbServerPort?.let { port ->
        DatabaseRepository.apply {
            this.dbServerPort = port
            this.main.transaction {
                SchemaUtils.createMissingTablesAndColumns(DemoTable)
            }
            this.server.start()
        }
        Runtime.getRuntime().addShutdownHook(thread(start = false) {
            DatabaseRepository.server.shutdown()
        })
    }
}

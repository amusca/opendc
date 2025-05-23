/*
 * Copyright (c) 2024 AtLarge Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.opendc.compute.simulator.telemetry.parquet

import org.apache.parquet.io.api.Binary
import org.apache.parquet.schema.LogicalTypeAnnotation
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.BINARY
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.FLOAT
import org.apache.parquet.schema.PrimitiveType.PrimitiveTypeName.INT64
import org.apache.parquet.schema.Types
import org.opendc.compute.simulator.telemetry.table.battery.BatteryTableReader
import org.opendc.trace.util.parquet.exporter.ExportColumn

/**
 * This object wraps the [ExportColumn]s to solves ambiguity for field
 * names that are included in more than 1 exportable.
 *
 * Additionally, it allows to load all the fields at once by just its symbol,
 * so that these columns can be deserialized. Additional fields can be added
 * from anywhere, and they are deserializable as long as they are loaded by the jvm.
 *
 * ```kotlin
 * ...
 * // Loads the column
 * DfltHostExportColumns
 * ...
 * ```
 */
public object DfltBatteryExportColumns {
    public val TIMESTAMP: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(INT64).named("timestamp"),
        ) { it.timestamp.toEpochMilli() }

    public val TIMESTAMP_ABS: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(INT64).named("timestamp_absolute"),
        ) { it.timestampAbsolute.toEpochMilli() }

    public val BATTERY_NAME: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field =
                Types.required(BINARY)
                    .`as`(LogicalTypeAnnotation.stringType())
                    .named("battery_name"),
        ) { Binary.fromString(it.batteryInfo.name) }

    public val CLUSTER_NAME: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field =
                Types.required(BINARY)
                    .`as`(LogicalTypeAnnotation.stringType())
                    .named("cluster_name"),
        ) { Binary.fromString(it.batteryInfo.clusterName) }

    public val POWER_DRAW: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(FLOAT).named("power_draw"),
        ) { it.powerDraw }

    public val ENERGY_USAGE: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(FLOAT).named("energy_usage"),
        ) { it.energyUsage }

    public val EMBODIED_CARBON: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(FLOAT).named("embodied_carbon_emission"),
        ) { it.embodiedCarbonEmission }

    public val CHARGE: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(FLOAT).named("charge"),
        ) { it.charge }

    public val CAPACITY: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field = Types.required(FLOAT).named("capacity"),
        ) { it.capacity }

    public val BATTERY_STATE: ExportColumn<BatteryTableReader> =
        ExportColumn(
            field =
                Types.optional(BINARY)
                    .`as`(LogicalTypeAnnotation.stringType())
                    .named("battery_state"),
        ) { Binary.fromString(it.batteryState.name) }

    /**
     * The columns that are always included in the output file.
     */
    internal val BASE_EXPORT_COLUMNS =
        setOf(
            BATTERY_NAME,
            CLUSTER_NAME,
            TIMESTAMP,
            TIMESTAMP_ABS,
        )
}

package org.opendc.compute.simulator.scheduler.timeshift

import org.opendc.compute.simulator.scheduler.ComputeScheduler
import org.opendc.compute.simulator.scheduler.SchedulingRequest
import org.opendc.compute.simulator.scheduler.SchedulingResult
import org.opendc.compute.simulator.scheduler.SchedulingResultType
import org.opendc.compute.simulator.scheduler.filters.HostFilter
import org.opendc.compute.simulator.scheduler.weights.HostWeigher
import org.opendc.compute.simulator.service.HostView
import org.opendc.compute.simulator.service.ServiceTask
import org.opendc.simulator.compute.power.CarbonModel
import org.opendc.simulator.compute.power.CarbonReceiver
import java.time.Instant
import java.time.InstantSource
import java.util.LinkedList
import java.util.SplittableRandom
import java.util.random.RandomGenerator
import kotlin.math.min

public class ClusterTimeshifter(
    override val windowSize: Int,
    override val clock: InstantSource,
    override val forecast: Boolean = true,
    override val shortForecastThreshold: Double = 0.2,
    override val longForecastThreshold: Double = 0.35,
    override val forecastSize: Int = 24,

    override val pastCarbonIntensities: LinkedList<Double> = LinkedList<Double>(),
    override var carbonRunningSum: Double = 0.0,
    override var shortLowCarbon: Boolean = false, // Low carbon regime for short tasks (< 2 hours)
    override var longLowCarbon: Boolean = false, // Low carbon regime for long tasks (>= hours)
    override var carbonMod: CarbonModel? = null
): CarbonReceiver, Timeshifter {
    public fun getCurrentShortLowCarbon(): Boolean {
        return shortLowCarbon
    }
    public fun getCurrentLongLowCarbon(): Boolean {
        return longLowCarbon
    }

}

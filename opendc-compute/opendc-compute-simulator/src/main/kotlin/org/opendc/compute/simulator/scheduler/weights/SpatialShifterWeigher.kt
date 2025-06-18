package org.opendc.compute.simulator.scheduler.weights

import org.opendc.compute.simulator.service.HostView
import org.opendc.compute.simulator.service.ServiceTask

/**
 * A [HostWeigher] that weighs the hosts based on the available memory per core on the host.
 *
 * @param multiplier Weight multiplier ratio. A positive value will result in the scheduler preferring hosts with more
 * available core memory, and a negative number will result in the scheduler preferring hosts with less available core
 * memory.
 */
public class SpatialShifterWeigher(override val multiplier: Double = 1.0): HostWeigher{
    override fun getWeight(
        host: HostView,
        task: ServiceTask
    ): Double {
        return multiplier * host.carbonIntensity
    }

    override fun toString(): String = "SpatialShifterWeigher"
}

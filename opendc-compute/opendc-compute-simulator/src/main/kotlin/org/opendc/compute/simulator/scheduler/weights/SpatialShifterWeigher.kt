package org.opendc.compute.simulator.scheduler.weights

import org.opendc.compute.simulator.service.HostView
import org.opendc.compute.simulator.service.ServiceTask
public class SpatialShifterWeigher(override val multiplier: Double = 1.0): HostWeigher {
    override fun getWeight(
        host: HostView,
        task: ServiceTask
    ): Double {
        return multiplier * host.getCarbonIntensity()
    }

    override fun toString(): String = "SpatialShifterWeigher"
}

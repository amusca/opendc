package org.opendc.compute.simulator.scheduler.weights

import org.opendc.compute.simulator.service.HostView
import org.opendc.compute.simulator.service.ServiceTask
public class SpatialShifterForecastWeigher(override val multiplier: Double = 1.0) : HostWeigher {
    override fun getWeight(
        host: HostView,
        task: ServiceTask
    ): Double {
        val taskDurationHours = task.duration.toHours().toInt()
        if( taskDurationHours < 1) {
            return multiplier * host.getCarbonIntensity()
        }
        val carbonModel = host.getCarbonModel()
        val carbonForecast = carbonModel.getForecast(taskDurationHours)
        val sumCarbonIntensity = carbonForecast.sum() + host.getCarbonIntensity()
        return multiplier * (sumCarbonIntensity / (carbonForecast.size + 1))
    }

    override fun toString(): String = "SpatialShifterForecastWeigher"
}

package org.opendc.compute.simulator.scheduler.filters

import org.opendc.compute.simulator.service.HostView
import org.opendc.compute.simulator.service.ServiceTask
import java.util.UUID
public class ClusterFilter(private val selectCluster: String) : HostFilter {
    override fun test(
        host: HostView,
        task: ServiceTask,
    ): Boolean{
        if(host.host.getClusterName() != selectCluster) {
            return false
        }
        return true
    }
}

package org.ahdb.server;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ItemStatsRefreshJob implements Job {

    @Autowired
    private QueryService queryService;

    @Override
    public void execute(JobExecutionContext context) {
        try {
            log.info("refreshDay14ItemStats START");
            queryService.refresh();
            log.info("refreshDay14ItemStats OK");
        } catch (Exception ex) {
            log.error("refreshDay14ItemStats ERR", ex);
        }
    }

}

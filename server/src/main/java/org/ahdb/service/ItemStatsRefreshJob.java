package org.ahdb.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class ItemStatsRefreshJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(ItemStatsRefreshJob.class);

    @Inject
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

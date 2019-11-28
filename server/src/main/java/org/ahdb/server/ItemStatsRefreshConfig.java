package org.ahdb.server;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemStatsRefreshConfig {

    @Bean
    public JobDetail jobADetails() {
        return JobBuilder.newJob(ItemStatsRefreshJob.class)
                .withIdentity("ItemStatsRefreshJob")
                .storeDurably().build();
    }

    @Bean
    public Trigger jobATrigger(JobDetail jobADetails) {
        return TriggerBuilder.newTrigger().forJob(jobADetails)
                .withIdentity("ItemStatsRefreshJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 4 * * ?"))
                .build();
    }

}

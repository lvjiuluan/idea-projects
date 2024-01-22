package com.nowcoder.community.quartz;

import com.nowcoder.community.CommunityApplicationTest;
import org.junit.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import static org.junit.Assert.*;

public class DemoJobTest extends CommunityApplicationTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void execute() {
        try {
            boolean result = scheduler.deleteJob(new JobKey("postScoreRefreshJob", "communityJobGroup"));
            System.out.println(result);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
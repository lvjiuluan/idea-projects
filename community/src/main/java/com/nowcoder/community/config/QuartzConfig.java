package com.nowcoder.community.config;

import com.nowcoder.community.quartz.DemoJob;
import com.nowcoder.community.quartz.PostScoreRefreshJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

// 配置 --> 数据库，以后quartz去访问数据库
@Configuration
public class QuartzConfig {

    // FactoryBean 可简化Bean的实例化过程
    // 1 通过FactoryBean封装了Bean实例化过程
    // 2 将FactoryBean装配到容器里
    // 3 将FactoryBean注入给其它的Bean
    // 4 其它的Bean得到的是FactoryBean所管理的对象实例
   /* @Bean
    public JobDetailFactoryBean demoJobDetail() {
        // 配置JobDetail
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DemoJob.class);
        factoryBean.setName("demoJob");
        factoryBean.setGroup("demoJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }*/

   /* @Bean
    public SimpleTriggerFactoryBean demoTrigger(JobDetail demoJobDetail) {
        // 配置Trigger
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(demoJobDetail);
        factoryBean.setName("demoTrigger");
        factoryBean.setGroup("demoTriggerGroup");
        factoryBean.setRepeatInterval(3000);
        factoryBean.setJobDataAsMap(new JobDataMap());
        return factoryBean;
    }*/


    // 刷新帖子分数的任务
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail() {
        // 配置JobDetail
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("postScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        return factoryBean;
    }

    // 刷新帖子任务触发器
    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
        // 配置Trigger
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail);
        factoryBean.setName("postScoreRefreshTrigger");
        factoryBean.setGroup("communityTriggerGroup");
        // 1分钟触发一次
        factoryBean.setRepeatInterval(1000 * 5 * 1);
        factoryBean.setJobDataAsMap(new JobDataMap());
        return factoryBean;
    }
}

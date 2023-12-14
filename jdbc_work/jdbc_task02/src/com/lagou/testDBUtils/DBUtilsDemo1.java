package com.lagou.testDBUtils;

import com.lagou.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

public class DBUtilsDemo1 {
    /***
     * QueryRunner 核心类创建方式
     * @param args
     */
    public static void main(String[] args) {
        // 1 方式一，手动创建
        QueryRunner qr1 = new QueryRunner();
        // 2 方式二，自动模式，提供数据库连接池对象，DBUtils会自动维护
        QueryRunner qr2 = new QueryRunner(DruidUtils.getDataSource());

    }
}

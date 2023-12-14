package com.lagou.testFastjson;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    // 可以通过name指定输出的名称
    @JSONField(name = "USERNAME",ordinal = 1)
    private String name;
    @JSONField(name = "AGE",ordinal = 2)
    private int age;
    @JSONField(serialize = true,ordinal = 3)
    private String birthday;

}

package com.nowcoder.community.util;

import com.nowcoder.community.CommunityApplicationTest;
import org.apache.commons.lang3.CharUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SensitiveFilterTest extends CommunityApplicationTest {

    @Autowired
    SensitiveFilter sensitiveFilter;

    boolean isSymbol(char c) {
        return (!CharUtils.isAsciiAlphanumeric(c)) && (c < 0x2E80 || c > 0x9FFF);
    }

    @Test
    public void TestConstructor() {
        String words = sensitiveFilter.replaceSensitiveWords("习#进)平和李强，他们在表面上是我们班级的领导者，" +
                "但实际上他们却涉嫌进行赌博和吸毒这种危害社会的行为。" +
                "他们的行为引起了学校和同学们的强烈反对。在一次学校的反赌博、反吸毒活动中，" +
                "他们的丑恶行径被揭露，引起了全校师生的愤怒。此外，他们还利用李强负责的开票投票环节，" +
                "进行舞弊，这更进一步暴露了他们的不正之风。现在，学校正在对他们进行严肃的调查和处理，" +
                "我们全体同学也坚决支持学校的决定，希望能够净化我们的学习环境，让我们的未来更加光明。李强习习进平");
        System.out.println(words);

        System.out.println(isSymbol('#'));
    }

}
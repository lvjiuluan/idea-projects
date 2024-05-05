package shiquinterview;

import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        List<String> list = new ArrayList<>();
        list.add("John Doe,john@doe.com");
        list.add("Bob Smith,bob@smith.com");
        Map<String, String> map = m.splitEmailAddress(list);
        System.out.println(map);
    }

    // 1. 给定一个List<String>，编写一个方法删除所以重复的字符串，并保持原有顺序不变
    public List<String> removeDuplicated(List<String> stringList) {
        List<String> resultList = new ArrayList<>();
        for (String s : stringList) {
            // 判断s是否在resultList里面，如果不在，则添加
            if (!resultList.contains(s)) {
                resultList.add(s);
            }
        }
        return resultList;
    }

    // 2. 给定一个Map<String, Integer>，其中键是名字，值是年龄。编写一个方法找出年龄最大的三个人的名字。
    public List<String> findTopThreeMaxAge(Map<String, Integer> map) {
        List<String> resultList = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Map.Entry[] entriesArray = entries.toArray(new Map.Entry[0]);
        Arrays.sort(entriesArray, (e1, e2) -> ((Integer) e2.getValue() - (Integer) e1.getValue()));
        resultList.add((String) entriesArray[0].getKey());
        resultList.add((String) entriesArray[1].getKey());
        resultList.add((String) entriesArray[2].getKey());
        return resultList;
    }

    // 3. 编写一个方法，接收一个List<String>, 返回一个Map，其中键是列表种的字符串，值是该字符串出现在列表种的次数。
    public Map<String, Integer> countString(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (String s : list) {
            Integer integer = map.get(s);
            if (integer == null) {
                map.put(s, 1);
            } else {
                integer += 1;
                map.put(s, integer);
            }
        }
        return map;
    }

    // 4. 将一个列表划分为数字和字母两个列表
    public void splitList(List<String> list) {
        List<Integer> integerList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        for (String s : list) {
            try {
                int i = Integer.parseInt(s);
                integerList.add(i);
            } catch (NumberFormatException e) {
                stringList.add(s);
            }
        }
        System.out.println("数字列表： " + integerList);
        System.out.println("字母列表： " + stringList);
    }

    // 5. 计算每个学生的平均成绩
    // 5.1 工具方法，通过List<Integer>计算平均值
    public double getAverage(List<Integer> list) {
        int sum = 0;
        for (Integer integer : list) {
            sum += integer;
        }
        return (sum * 1.0) / list.size();
    }

    // 5.2 统计每个学生的平均成绩
    public Map<String, Double> getStudentAverage(Map<String, List<Integer>> scoresMap) {
        Map<String, Double> map = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : scoresMap.entrySet()) {
            String name = entry.getKey();
            List<Integer> scoresList = entry.getValue();
            double average = getAverage(scoresList);
            map.put(name, average);
        }
        return map;
    }

    // 6. 分割名字和邮箱
    public Map<String, String> splitEmailAddress(List<String> list) {
        Map<String, String> map = new HashMap<>();
        for (String s : list) {
            String[] split = s.split(",");
            map.put(split[1], split[0]);
        }
        return map;
    }
}

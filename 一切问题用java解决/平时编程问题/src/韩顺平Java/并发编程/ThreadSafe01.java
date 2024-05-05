package 韩顺平Java.并发编程;

import sun.misc.Lock;

public class ThreadSafe01 {
    public static void main(String[] args) {

    }

    double avgScore(double[] scores) {
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        int count = scores.length;
        double avg = sum / count;
        return avg;
    }
}

class StudentAssistant {

    ThreadLocal<String> realName = new ThreadLocal<>();
    ThreadLocal<Double> totalScore = new ThreadLocal<>();

    final double passScore = 60;

    String determineDegree() {
        double score = totalScore.get();
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "E";
    }

    double determineOptionalCourseScore() {
        double score = totalScore.get();
        if (score >= 90) {
            return 10;
        }
        if (score >= 80) {
            return 20;
        }
        if (score >= 70) {
            return 30;
        }
        if (score >= 60) {
            return 40;
        }
        return 60;
    }
}


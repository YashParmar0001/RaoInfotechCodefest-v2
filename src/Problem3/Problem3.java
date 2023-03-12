package Problem3;

import java.io.*;
import java.util.*;

public class Problem3 {
    public static void main(String[] args) {
        File inputFile = new File("D:\\raoinfotech\\src\\Problem3\\CustomInput.txt");
        File outputFile = new File("D:\\raoinfotech\\src\\Problem3\\P3OuputSmall.txt");

        theTimesheet(inputFile, outputFile);
    }

    static void theTimesheet(File inputFile, File outputFile) {
        HashMap<String, List<Log>> timeSheet = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();

            int noOfLogs = Integer.parseInt(line);

            for (int i = 0; i < noOfLogs; i++) {
                line = br.readLine();
                String[] log = line.split("\\s+");

                String time = log[2];
                String[] times = time.split(":");
                int hour = Integer.parseInt(times[0]);
                int min = Integer.parseInt(times[1]);
                int sec = Integer.parseInt(times[2]);

                Time logTime = new Time(hour, min, sec);
                Log logData = new Log(log[1], logTime, log[3]);

                if (!timeSheet.containsKey(log[0])) {
                    timeSheet.put(log[0], new ArrayList<>());
                }
                timeSheet.get(log[0]).add(logData);
            }

            for (Map.Entry<String, List<Log>> entry: timeSheet.entrySet()) {
                String key = entry.getKey();
                List<Log> value = entry.getValue();

                System.out.println(key);
                for (Log log: value) {
                    System.out.println(log.date + " " + log.time.hour + ":" + log.time.min + ":" + log.time.sec + " " + log.status);
                }
            }

//            try {
//                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
//                PrintWriter pw = new PrintWriter(bw);
//                int caseNo = 1;
//                for (Map.Entry<String, List<Log>> entry: timeSheet.entrySet()) {
//                    String key = entry.getKey();
//                    List<Log> value = entry.getValue();
//                    Time ans = calculateTime(value);
//                    pw.printf("Case #%d: %d:%d:%d\n", caseNo++, ans.hour, ans.min, ans.sec);
//                }
//                pw.close();
//            }catch (IOException e) {
//                e.printStackTrace();
//            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

//    static Time calculateTime(List<Log> logs) {
//        Time lastTime = new Time(0, 0, 0);
////        Time ans = new Time(0, 0, 0);
//        int days = 0;
//        HashMap<Integer, Time> dayMap = new HashMap<>();
//        String lastStatus = "";
//
//        for (Log log: logs) {
//            if (Objects.equals(log.status, "clock-in")) {
//                days++;
//                if (!lastStatus.equals("clock-out") && days != 1) {
//                    Time totalTime = new Time(0, 0, 0);
//                    if (log.time.hour > 19 || (log.time.hour == 19 && log.time.min > 30 && log.time.sec >= 0)) {
//                        Time duration = getDuration(lastTime, new Time(19, 30, 0));
//                        totalTime = addTimes(duration, dayMap.get(days));
//                        dayMap.put(days, totalTime);
//                    }else if (totalTime.hour > 6) {
//                        totalTime.hour = 6;
//                        totalTime.min = 0;
//                        totalTime.sec = 0;
//                        dayMap.put(days, totalTime);
//                    } else {
//                        Time duration = getDuration(lastTime, log.time);
//                        totalTime = addTimes(duration, dayMap.get(days));
//                        dayMap.put(days, totalTime);
//                    }
//                }else {
//                    lastTime = log.time;
//                    dayMap.put(days, new Time(0, 0, 0));
//                }
//                lastStatus = "clock-in";
//            }else if (Objects.equals(log.status, "clock-out")) {
//                lastStatus = "clock-out";
//                Time duration = getDuration(lastTime, log.time);
//                Time totalTime = addTimes(duration, dayMap.get(days));
//                dayMap.put(days, totalTime);
//            }else if (Objects.equals(log.status, "break-start")) {
//                Time duration = getDuration(lastTime, log.time);
//                Time totalTime = addTimes(duration, dayMap.get(days));
//                dayMap.put(days, totalTime);
//            }else {
//                lastTime = log.time;
//                if (lastTime.hour > 19 || (lastTime.hour == 19 && lastTime.min > 30 && lastTime.sec >= 0)) {
//                    lastStatus = "clock-out";
//                }
//            }
//        }
//
//        Time ans = new Time(0, 0, 0);
//        for (Map.Entry<Integer, Time> entry: dayMap.entrySet()) {
//            ans = addTimes(ans, entry.getValue());
////            System.out.println(entry.getKey() + "->" + entry.getValue().hour + ":" +
////                    entry.getValue().min + ":" + entry.getValue().sec);
//        }
//
//        return ans;
//    }

    static Time calculateTime(List<Log> logs) {
        HashMap<Integer, Time> dayMap = new HashMap<>();
        Time lastTime = new Time(0, 0, 0);
        int day = 1;
        for (Log log: logs) {
            switch (log.status) {
                case "clock-in" -> {
                    dayMap.put(day, new Time(0, 0, 0));
                    lastTime = log.time;
                }
                case "clock-out" -> {
                    Time duration = getDuration(lastTime, log.time);
                    Time totalTime = addTimes(duration, dayMap.get(day));
                    dayMap.put(day, totalTime);
                    day++;
                }
                case "break-start" -> {
                    Time duration = getDuration(lastTime, log.time);
                    Time totalTime = addTimes(duration, dayMap.get(day));
                    dayMap.put(day, totalTime);
                }
                case "break-stop" -> {
                    lastTime = log.time;
                }
            }
        }

        Time ans = new Time(0, 0, 0);
        for (Map.Entry<Integer, Time> entry: dayMap.entrySet()) {
            ans = addTimes(ans, entry.getValue());
        }

        return ans;
    }

    static Time addTimes(Time startTime, Time endTime) {
        int totalSeconds = startTime.sec + endTime.sec;
        int totalMinutes = startTime.min + endTime.min + totalSeconds/60;
        int totalHours = startTime.hour + endTime.hour + totalMinutes/60;

        return new Time(totalHours, totalMinutes%60, totalSeconds%60);
    }

    static Time getDuration(Time startTime, Time endTime) {
        int startSeconds = startTime.sec + (startTime.min * 60) + (startTime.hour * 3600);
        int endSeconds = endTime.sec + (endTime.min * 60) + (endTime.hour * 3600);
        int durationSeconds = endSeconds - startSeconds;

        int durationHours = durationSeconds / 3600;
        int durationMinutes = (durationSeconds % 3600) / 60;
        int durationSecondsRemaining = (durationSeconds % 3600) % 60;

        return new Time(durationHours, durationMinutes, durationSecondsRemaining);
    }


    static class Log {
        String date;
        String status;
        Time time;

        public Log(String date, Time time, String status) {
            this.date = date;
            this.time = time;
            this.status = status;
        }
    }

    static class Time {
        int hour, min, sec;
        public Time(int hour, int min, int sec) {
            this.hour = hour;
            this.min = min;
            this.sec = sec;
        }
    }
}

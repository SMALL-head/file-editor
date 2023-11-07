package stats;


import utils.SoutUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class SessionStats {

    private final List<String> Lines = new ArrayList<>();
    Map<String, Long> StatsMap = new HashMap<>();
    String fileName = null;

    public void readStats() {
        try (BufferedReader br = new BufferedReader(new FileReader(".log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("session start")) {
                    Lines.clear();
                    Lines.add(line);
                }
                String[] parts = line.split("\\s+");
                if (parts.length > 2 && (parts[2].equals("load") || parts[2].equals("save"))) {
                    Lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("统计数据读取失败:" + e.getMessage());
        }
    }

    public Date statsCalculator() {
        readStats();

        int length = Lines.size();
        Date start = null;
        Date end = null;
        long duration = 0;
        boolean isLoad = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        StatsMap.clear();

        for (int i = 0; i < length; i++) {
            String[] parts = Lines.get(i).split("\\s+");
            if ((!isLoad) && (parts[2].equals("load"))) {
                try {
                    start = dateFormat.parse(parts[0] + " " + parts[1]);
                    fileName = parts[3];
                } catch (ParseException e) {
                    SoutUtils.sout(e.getMessage());
                    return null;
                }
                isLoad = true;
            }
            if ((isLoad) && (parts[2].equals("save"))) {
                try {
                    end = dateFormat.parse(parts[0] + " " + parts[1]);
                } catch (ParseException e) {
                    SoutUtils.sout(e.getMessage());
                    return null;
                }
                duration = (end.getTime() - start.getTime()) / 1000;
                if (StatsMap.containsKey(fileName)) {
                    StatsMap.replace(fileName, StatsMap.get(fileName) + duration);
                } else {
                    StatsMap.put(fileName, duration);
                }
                isLoad = false;
            }
        }


        if (isLoad) {
            return start;
        } else return null;
    }

    public void printSessionStats(String s) {
        Date start = statsCalculator();
        //正在处理的文件：
        if (!s.equals("q") && start != null) {
            Date end = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());//把当前时间转成Date类型
            long duration = (end.getTime() - start.getTime()) / 1000;
            if (StatsMap.containsKey(fileName)) {
                StatsMap.replace(fileName, StatsMap.get(fileName) + duration);
            } else {
                StatsMap.put(fileName, duration);
            }
        }


        if (s.equals("all")) {
            System.out.println(Lines.get(0));
            Set<String> fileNames = StatsMap.keySet();
            for (String f : fileNames) {
                List<Integer> t = getHoursMinutesSeconds(StatsMap.get(f));
                if (t.get(0) != 0) {
                    System.out.println(f + " " + t.get(0) + "时" + t.get(1) + "分" + t.get(2) + "秒");
                } else if (t.get(1) != 0) {
                    System.out.println(f + " " + t.get(1) + "分" + t.get(2) + "秒");
                } else {
                    System.out.println(f + " " + t.get(2) + "秒");
                }
            }
        } else if (s.equals("current")) {
            System.out.println(Lines.get(0));
            List<Integer> t = getHoursMinutesSeconds(StatsMap.get(fileName));
            if (t.get(0) != 0) {
                System.out.println(fileName + " " + t.get(0) + "时" + t.get(1) + "分" + t.get(2) + "秒");
            } else if (t.get(1) != 0) {
                System.out.println(fileName + " " + t.get(1) + "分" + t.get(2) + "秒");
            } else {
                System.out.println(fileName + " " + t.get(2) + "秒");
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(".log", true);
                 PrintWriter printWriter = new PrintWriter(fileWriter)) {
                Set<String> fileNames = StatsMap.keySet();
                for (String f : fileNames) {
                    List<Integer> t = getHoursMinutesSeconds(StatsMap.get(f));
                    if (t.get(0) != 0) {
                        printWriter.println(f + " " + t.get(0) + "时" + t.get(1) + "分" + t.get(2) + "秒");
                    } else if (t.get(1) != 0) {
                        printWriter.println(f + " " + t.get(1) + "分" + t.get(2) + "秒");
                    } else {
                        printWriter.println(f + " " + t.get(2) + "秒");
                    }
                }
            } catch (IOException e) {
                System.out.println("统计数据记录失败:" + e.getMessage());
            }
        }
    }

    public List<Integer> getHoursMinutesSeconds(long n) {
        List<Integer> values = new ArrayList<>();
        int num = (int) (n);
        int hours = num / 3600;
        int minutes = (num % 3600) / 60;
        int seconds = num % 60;
        values.add(hours);
        values.add(minutes);
        values.add(seconds);
        return values;
    }

}

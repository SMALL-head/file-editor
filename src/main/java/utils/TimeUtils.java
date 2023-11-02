package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String FormattedTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        // 格式化时间
        return dateFormat.format(now);
    }
}

package cn.unuai.util;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by SunShuai on 2018/4/21.
 */
public class Util {
    public static int token_live = 7 * 24 * 60 * 60;//token有效期
    public static int STATE_ZK = 2002;//设备状态：在库
    public static int STATE_SYZ = 2003;//设备状态：使用中
    public static int STATE_YBF = 2004;//设备状态：已报废
    public static int STATE_DRK = 2001;//设备状态：待入库
    public static int REPAIRE_DATE_ERROR = 2;//检修误差天数

    /**
     * 查询结果转化为map格式
     *
     * @param keys
     * @param values
     * @return
     */
    public static List formatQueryResult(String[] keys, List values) {
        List result = new ArrayList();
        for (Object row : values) {
            Object[] cells = (Object[]) row;
            Map map = new HashMap();
            for (int i = 0; i < keys.length; i++) {
                map.put(keys[i], cells[i]);
            }
            result.add(map);
        }
        return result;
    }

    public static String keep2Point(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /**
     * 获取汉字拼音首字母
     *
     * @param str
     * @return
     */
    public static String getPinyinHeader(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 判断是否是英文
     *
     * @param str
     * @return
     */
    public static boolean isEnglish(String str) {
        return str.matches("^[ a-zA-Z]*");
    }

    /**
     * 生成一个token
     *
     * @return
     */
    public static String createToken() {
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 16; i++) {
            String str = uuid.substring(i * 2, i * 2 + 2);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 读取token文件
     */
    private static final String propFileName = "src/main/java/cn/unuai/util/token.properties";
    private static PropertiesConfiguration cfg = null;

    static {
        try {
            cfg = new PropertiesConfiguration(propFileName);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        // 当文件的内容发生改变时，配置对象也会刷新
        cfg.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /**
     * 添加文件中token
     *
     * @param key
     * @return
     */
    public static String getToken(String key) {
        return cfg.getString(key);
    }

    /**
     * 设置文件中token
     *
     * @param key
     * @param value
     * @throws ConfigurationException
     */
    public static void setToken(String key, String value) {
        cfg.setProperty(key, value);
        try {
            cfg.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件中token
     *
     * @param key
     * @throws ConfigurationException
     */
    public static void delToken(String key) {
        cfg.clearProperty(key);
        try {
            cfg.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * md5 加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(str.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String temp = Integer.toHexString(number);
                if (temp.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(temp);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static int getNumberInAssetNo(String assetNo) {
        int i;
        for (i = assetNo.length() - 1; i >= 0; i--) {
            if (assetNo.charAt(i) >= '0' && assetNo.charAt(i) <= '9') {
                continue;
            } else {
                break;
            }
        }
        return Integer.parseInt(assetNo.substring(i + 1));
    }

    public static String getToday() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "-" +
                (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
    }

    public static int getTotalPage(int total, int pageSize) {
        return (total + pageSize - 1) / pageSize;
    }
}

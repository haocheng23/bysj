import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 随机生成充值信息 模拟日志
 */
public class BillInfo {

    /**
     * start ---- end之间的随机数
     * @param start
     * @param end
     * @return
     */
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start) + start);
    }

    /**
     * 随机生成手机号
     *
     * @param args
     */
    public static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static String getTel() {
        int index = getNum(0, telFirst.length);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + thrid;
    }

    /**
     * 随机生成套餐类型
     *
     * @param args
     */
    public static String[] taoCanStr = "随心享18,聚划算38,神州行58,冰激凌88,新概念138,全球通188,不限量288".split(",");

    public static String getTaocan() {
        int index = getNum(0, taoCanStr.length);
        String taocan = taoCanStr[index];
        return taocan;
    }


    /**
     * 随机生成充值地区
     *
     * @param
     */
    public static String[] provinceStr = "北京,天津,河北,山西,内蒙古,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北 ,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,香港,澳门,台湾".split(",");

    public static String getProvince() {
        int index = getNum(0, provinceStr.length);
        String province = provinceStr[index];
        return province;
    }

    /**
     * 随机生成缴费金额
     *
     * @param args
     */
    public static String[] rechargeFeeStr = "10,20,30,50,100,200,500,1000".split(",");

    public static String getRechargeFee() {
        int index = getNum(0, rechargeFeeStr.length);
        String rechargeFee = rechargeFeeStr[index];
        return rechargeFee + "元";
    }

    /**
     * 随机生成缴费时间
     *
     * @param
     */
    public static String getRechargeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, (int) (Math.random() * 3 + 2));
        calendar.set(Calendar.DATE, (int) (Math.random() * 31));
        calendar.set(Calendar.HOUR_OF_DAY, (int) (Math.random() * 24));
        calendar.set(Calendar.MINUTE, (int) (Math.random() * 60));
        calendar.set(Calendar.SECOND, (int) (Math.random() * 60));
        Date time = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(time);
        return dateString;
    }

    /**
     * 随机生成支付方式
     */
    public static String[] rechargeWayStrr = "alipay,wxpay,unipay,cashpay".split(",");

    public static String getRechargeWay() {
        int index = getNum(0, rechargeWayStrr.length);
        String rechargeway = rechargeWayStrr[index];
        return rechargeway;
    }


    /**
     * 随机生成本月使用通话语音总时长
     */
    public static String getCallTime() {
        int flag = getNum(1, 6);
        if (flag == 1) {
            int num1 = getNum(0, 100);
            return String.valueOf(num1);
        } else if (flag == 2) {
            int num2 = getNum(100, 200);
            return String.valueOf(num2);
        } else if (flag == 3) {
            int num3 = getNum(400, 700);
            return String.valueOf(num3);
        } else if (flag == 4){
            int num4 = getNum(700, 1000);
            return String.valueOf(num4);
        } else {
            int num5 = getNum(200, 400);
            return String.valueOf(num5);

        }
    }


    /**
     * 随机生成本月使用短信总条数
     */
    public static String getMessageNum() {
        int flag = getNum(1, 6);
        if (flag == 1) {
            int num1 = getNum(0, 30);
            return String.valueOf(num1);
        } else if (flag == 2) {
            int num2 = getNum(150, 300);
            return String.valueOf(num2);
        } else if (flag == 3) {
            int num3 = getNum(80, 150);
            return String.valueOf(num3);
        } else {
            int num4 = getNum(30, 80);
            return String.valueOf(num4);
        }
    }

    /**
     * 随机生成本月使用总流量
     */
    public static String getFlow() {
        int flag = getNum(1, 6);
        if (flag == 1) {
            int num1 = getNum(0, 1024);
            return String.valueOf(num1);
        } else if (flag == 2) {
            int num2 = getNum(1024, 3072);
            return String.valueOf(num2);
        } else if (flag == 3) {
            int num3 = getNum(6144, 10240);
            return String.valueOf(num3);
        } else {
            int num4 = getNum(3072, 6144);
            return String.valueOf(num4);
        }
    }


    /**
     * 数据封装
     *
     * @return
     */
    public static String getBill() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("phonenumber", getTel());
        map.put("taocan", getTaocan());
        map.put("province", getProvince());
        map.put("rechargefee", getRechargeFee());
        map.put("rechargeTime", getRechargeTime());
        map.put("payway", getRechargeWay());
        map.put("calltime", getCallTime());
        map.put("messagenum", getMessageNum());
        map.put("flow", getFlow());
        String bill = JSON.toJSONString(map);
//        System.out.println(bill);
        return bill;
    }


    /**
     * 将生成的账单数据写入文件
     *
     * @param bill     已封装好的账单数据
     * @param filePath 账单数据日志文件的路径
     */
    public static void info2file(String bill, String filePath) {
//        String filePath = "/root/bysj/data/billInfo.log";
//        String filePath = "D:\\Desktop\\222.log";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, true);
            fos.write(bill.getBytes());
            fos.write("\r\n".getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将账单日志文件发送http请求给nginx服务器
     *
     * @param srcpath 账单日志文件路径
     * @param url     nginx服务器路径
     */
    public static void postJson(String srcpath, String url) {
//        String srcpath = "/root/bysj/data/billInfo.log";
//        String url = "http://192.168.23.11:80/kafka/bysj_bill";
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(srcpath)));
                String line = null;
                while ((line = br.readLine()) != null) {
                    ObjectMapper om = new ObjectMapper();
                    HttpPost httpPost = new HttpPost(url);
                    /*配置请求头*/
                    httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                    /*一行一行读取srcpath文件 并写入请求体中*/
                    httpPost.setEntity(new StringEntity(om.writeValueAsString(JSON.parse(line)),
                            ContentType.create("text/json", "UTF-8")));
                    client = HttpClients.createDefault();
                    response = client.execute(httpPost);
                }
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*清空数据产生文件*/
        String[] cmd = new String[]{"/bin/sh", "-c", "rm -rf " + args[0]};
        try {
            Process process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("删除文件失败！");
        }
        //遍历写到指定文件中
        for (int i = 0; i < getNum(500, 1000); i++) {
            //写入指定文件
            info2file(getBill(), args[0]);
        }
        //上传至nginx
        postJson(args[0], args[1]);
    }
}

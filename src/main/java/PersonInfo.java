import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.SourceTree;
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
 * 随机生成个人信息（姓名、性别、身份证号、年龄、Email、住址、入网时间、手机号）
 */
public class PersonInfo {
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄魏加封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逄姬申扶堵冉宰郦雍却璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东殴殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查后江红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于仲孙太叔申屠公孙乐正轩辕令狐钟离闾丘长孙慕容鲜于宇文司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福百家姓续";
    private static String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽 ";
    private static String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    private static String[] road = "仙山西大厦,白沙河路,赵红广场,机场路,民航街,长城南路,流亭立交桥,虹桥广场,长城大厦,礼阳路,风岗街,中川路,白塔广场,兴阳路,文阳街,绣城路,河城大厦,锦城广场,崇阳街,华城路,康城街,正阳路,和阳广场,中城路,江城大厦,顺城路,安城街,山城广场,春城街,国城路,泰城街,德阳路,明阳大厦,春阳路,艳阳街,秋阳路,硕阳街,青威高速,瑞阳街,丰海路,双元大厦,惜福镇街道,夏庄街道,古庙工业园,中山街,太平路,广西街,潍县广场,博山大厦,湖南路,济宁街,芝罘路,易州广场,荷泽四路,荷泽二街,荷泽一路,荷泽三大厦,观海二广场,广西支街,观海一路,济宁支街,莒县路,平度广场,明水路,蒙阴大厦,青岛路,湖北街,江宁广场,郯城街,天津路,保定街,安徽路,河北大厦,黄岛路,北京街,莘县路,济南街,宁阳广场,日照街,德县路,新泰大厦,荷泽路,山西广场,沂水路,肥城街,兰山路,四方街,平原广场,泗水大厦,浙江路,曲阜街,寿康路,河南广场,泰安路,大沽街,红山峡支路,西陵峡一大厦,台西纬一广场,台西纬四街,台西纬二路,西陵峡二街,西陵峡三路,台西纬三广场,台西纬五路,明月峡大厦,青铜峡路,台西二街,观音峡广场,瞿塘峡街,团岛二路,团岛一街,台西三路,台西一大厦,郓城南路,团岛三街,刘家峡路,西藏二街,西藏一广场,台西四街,三门峡路,城武支大厦,红山峡路,郓城北广场,龙羊峡路,西陵峡街,台西五路,团岛四街,石村广场,巫峡大厦,四川路,寿张街,嘉祥路,南村广场,范县路,西康街,云南路,巨野大厦,西江广场,鱼台街,单县路,定陶街,滕县路,钜野广场,观城路,汶上大厦,朝城路,滋阳街,邹县广场,濮县街,磁山路,汶水街,西藏路,城武大厦,团岛路,南阳街,广州路,东平街,枣庄广场,贵州街,费县路,南海大厦,登州路,文登广场,信号山支路,延安一街,信号山路,兴安支街,福山支广场,红岛支大厦,莱芜二路,吴县一街,金口三路,金口一广场,伏龙山路,鱼山支街,观象二路,吴县二大厦,莱芜一广场,金口二街,海阳路,龙口街,恒山路,鱼山广场,掖县路,福山大厦,红岛路,常州街,大学广场,龙华街,齐河路,莱阳街,黄县路,张店大厦,祚山路,苏州街,华山路,伏龙街,江苏广场,龙江街,王村路,琴屿大厦,齐东路,京山广场,龙山路,牟平街,延安三路,延吉街,南京广场,东海东大厦,银川西路,海口街,山东路,绍兴广场,芝泉路,东海中街,宁夏路,香港西大厦,隆德广场,扬州街,郧阳路,太平角一街,宁国二支路,江西支街,澳门二路,宁国四街,大尧一广场,咸阳支街,洪泽湖路,吴兴二大厦,澄海三路,天台一广场,新湛二路,三明北街,新湛支路,湛山五街,泰州三广场,湛山四大厦,闽江三路,澳门四街,南海支路,吴兴三广场,三明南路,湛山二街,二轻新村镇,江南大厦,吴兴一广场,珠海二街,嘉峪关路,高邮湖街,湛山三路,澳门六广场,泰州二路,东海一大厦,天台二路,微山湖街,洞庭湖广场,珠海支街,福州南路,澄海二街,泰州四路,香港中大厦,澳门五路,新湛三街,澳门一路,正阳关街,宁武关广场,闽江四街,新湛一路,宁国一大厦,王家麦岛,澳门七广场,泰州一路,泰州六街,大尧二路,青大一街,闽江二广场,闽江一大厦,屏东支路,湛山一街,东海西路,徐家麦岛函谷关广场,大尧三路,晓望支街,秀湛二路,逍遥三大厦,澳门九广场,泰州五街,澄海一路,澳门八街,福州北路,珠海一广场,宁国二路,临淮关大厦,燕儿岛路,紫荆关街,武胜关广场,逍遥一街,秀湛四路,居庸关街,山海关路,鄱阳湖大厦,新湛路,漳州街,仙游路,花莲街,乐清广场,巢湖街,台南路,吴兴大厦,云溪广场,太清路".split(",");
    private static final String[] email_suffix = "@gmail.com,@yahoo.com,@msn.com,@qq.com,@163.com,@163.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com".split(",");

    /**
     * 随机数生成
     *
     * @param start 起始数
     * @param end   结尾数
     * @return
     */
    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 生成中文姓名和性别
     */
    public static String name_sex = "";

    public static String getChineseName() {
        int index = getNum(0, firstName.length() - 1);
        String first = firstName.substring(index, index + 1);
        int sex = getNum(0, 1);
        String str = boy;
        int length = boy.length();
        if (sex == 0) {
            str = girl;
            length = girl.length();
            name_sex = "女";
        } else {
            name_sex = "男";
        }
        index = getNum(0, length - 1);
        String second = str.substring(index, index + 1);
        int hasThird = getNum(0, 1);
        String third = "";
        if (hasThird == 1) {
            index = getNum(0, length - 1);
            third = str.substring(index, index + 1);
        }
        return first + second + third;
    }

    /**
     * 生成身份证号码
     */
    public static final Map<String, Integer> areaCode = new HashMap<String, Integer>();

    static {
        areaCode.put("北京市", 110000);
        areaCode.put("市辖区", 110100);
        areaCode.put("东城区", 110101);
        areaCode.put("西城区", 110102);
        areaCode.put("崇文区", 110103);
        areaCode.put("宣武区", 110104);
        areaCode.put("朝阳区", 110105);
        areaCode.put("丰台区", 110106);
        areaCode.put("石景山区", 110107);
        areaCode.put("海淀区", 110108);
        areaCode.put("门头沟区", 110109);
        areaCode.put("房山区", 110111);
        areaCode.put("通州区", 110112);
        areaCode.put("顺义区", 110113);
        areaCode.put("昌平区", 110114);
        areaCode.put("大兴区", 110115);
        areaCode.put("怀柔区", 110116);
        areaCode.put("平谷区", 110117);
        areaCode.put("县", 110200);
        areaCode.put("密云县", 110228);
        areaCode.put("延庆县", 110229);
        areaCode.put("天津市", 120000);
        areaCode.put("市辖区", 120100);
        areaCode.put("和平区", 120101);
        areaCode.put("河东区", 120102);
        areaCode.put("河西区", 120103);
        areaCode.put("南开区", 120104);
        areaCode.put("河北区", 120105);
        areaCode.put("红桥区", 120106);
        areaCode.put("东丽区", 120110);
        areaCode.put("西青区", 120111);
        areaCode.put("津南区", 120112);
        areaCode.put("北辰区", 120113);
        areaCode.put("武清区", 120114);
        areaCode.put("宝坻区", 120115);
        areaCode.put("县", 120200);
        areaCode.put("宁河县", 120221);
        areaCode.put("静海县", 120223);
        areaCode.put("蓟　县", 120225);
        areaCode.put("河北省", 130000);
        areaCode.put("石家庄市", 130100);
        areaCode.put("市辖区", 130101);
        areaCode.put("长安区", 130102);
        areaCode.put("桥东区", 130103);
        areaCode.put("桥西区", 130104);
        areaCode.put("新华区", 130105);
        areaCode.put("井陉矿区", 130107);
        areaCode.put("裕华区", 130108);
        areaCode.put("井陉县", 130121);
        areaCode.put("正定县", 130123);
        areaCode.put("栾城县", 130124);
        areaCode.put("行唐县", 130125);
        areaCode.put("灵寿县", 130126);
        areaCode.put("高邑县", 130127);
        areaCode.put("深泽县", 130128);
        areaCode.put("赞皇县", 130129);
        areaCode.put("无极县", 130130);
        areaCode.put("平山县", 130131);
        areaCode.put("元氏县", 130132);
        areaCode.put("赵　县", 130133);
        areaCode.put("辛集市", 130181);
        areaCode.put("藁城市", 130182);
        areaCode.put("晋州市", 130183);
        areaCode.put("新乐市", 130184);
        areaCode.put("鹿泉市", 130185);
        areaCode.put("唐山市", 130200);
        areaCode.put("市辖区", 130201);
        areaCode.put("路南区", 130202);
        areaCode.put("路北区", 130203);
        areaCode.put("古冶区", 130204);
        areaCode.put("开平区", 130205);
        areaCode.put("丰南区", 130207);
        areaCode.put("丰润区", 130208);
        areaCode.put("滦　县", 130223);
        areaCode.put("滦南县", 130224);
        areaCode.put("乐亭县", 130225);
        areaCode.put("迁西县", 130227);
        areaCode.put("玉田县", 130229);
        areaCode.put("唐海县", 130230);
        areaCode.put("遵化市", 130281);
        areaCode.put("迁安市", 130283);
        areaCode.put("秦皇岛市", 130300);
        areaCode.put("市辖区", 130301);
        areaCode.put("海港区", 130302);
        areaCode.put("山海关区", 130303);
        areaCode.put("北戴河区", 130304);
        areaCode.put("青龙满族自治县", 130321);
        areaCode.put("昌黎县", 130322);
        areaCode.put("抚宁县", 130323);
        areaCode.put("卢龙县", 130324);
        areaCode.put("邯郸市", 130400);
        areaCode.put("市辖区", 130401);
        areaCode.put("邯山区", 130402);
        areaCode.put("丛台区", 130403);
        areaCode.put("复兴区", 130404);
        areaCode.put("峰峰矿区", 130406);
        areaCode.put("邯郸县", 130421);
        areaCode.put("临漳县", 130423);
        areaCode.put("成安县", 130424);
        areaCode.put("大名县", 130425);
        areaCode.put("涉　县", 130426);
        areaCode.put("磁　县", 130427);
        areaCode.put("肥乡县", 130428);
        areaCode.put("永年县", 130429);
        areaCode.put("邱　县", 130430);
        areaCode.put("鸡泽县", 130431);
        areaCode.put("广平县", 130432);
        areaCode.put("馆陶县", 130433);
        areaCode.put("魏　县", 130434);
        areaCode.put("曲周县", 130435);
        areaCode.put("武安市", 130481);
        areaCode.put("邢台市", 130500);
        areaCode.put("市辖区", 130501);
        areaCode.put("桥东区", 130502);
        areaCode.put("桥西区", 130503);
        areaCode.put("邢台县", 130521);
        areaCode.put("临城县", 130522);
        areaCode.put("内丘县", 130523);
        areaCode.put("柏乡县", 130524);
        areaCode.put("隆尧县", 130525);
        areaCode.put("任　县", 130526);
        areaCode.put("南和县", 130527);
        areaCode.put("宁晋县", 130528);
        areaCode.put("巨鹿县", 130529);
        areaCode.put("新河县", 130530);
        areaCode.put("广宗县", 130531);
        areaCode.put("平乡县", 130532);
        areaCode.put("威　县", 130533);
        areaCode.put("清河县", 130534);
        areaCode.put("临西县", 130535);
        areaCode.put("南宫市", 130581);
        areaCode.put("沙河市", 130582);
        areaCode.put("保定市", 130600);
        areaCode.put("市辖区", 130601);
        areaCode.put("新市区", 130602);
        areaCode.put("北市区", 130603);
        areaCode.put("南市区", 130604);
        areaCode.put("满城县", 130621);
        areaCode.put("清苑县", 130622);
        areaCode.put("涞水县", 130623);
        areaCode.put("阜平县", 130624);
        areaCode.put("徐水县", 130625);
        areaCode.put("定兴县", 130626);
        areaCode.put("唐　县", 130627);
        areaCode.put("高阳县", 130628);
        areaCode.put("容城县", 130629);
        areaCode.put("涞源县", 130630);
        areaCode.put("望都县", 130631);
        areaCode.put("安新县", 130632);
        areaCode.put("易　县", 130633);
        areaCode.put("曲阳县", 130634);
        areaCode.put("蠡　县", 130635);
        areaCode.put("顺平县", 130636);
        areaCode.put("博野县", 130637);
        areaCode.put("雄　县", 130638);
        areaCode.put("涿州市", 130681);
        areaCode.put("定州市", 130682);
        areaCode.put("安国市", 130683);
        areaCode.put("高碑店市", 130684);
        areaCode.put("张家口市", 130700);
        areaCode.put("市辖区", 130701);
        areaCode.put("桥东区", 130702);
        areaCode.put("桥西区", 130703);
        areaCode.put("宣化区", 130705);
        areaCode.put("下花园区", 130706);
        areaCode.put("宣化县", 130721);
        areaCode.put("张北县", 130722);
        areaCode.put("康保县", 130723);
        areaCode.put("沽源县", 130724);
        areaCode.put("尚义县", 130725);
        areaCode.put("蔚　县", 130726);
        areaCode.put("阳原县", 130727);
        areaCode.put("怀安县", 130728);
        areaCode.put("万全县", 130729);
        areaCode.put("怀来县", 130730);
        areaCode.put("涿鹿县", 130731);
        areaCode.put("赤城县", 130732);
        areaCode.put("崇礼县", 130733);
        areaCode.put("承德市", 130800);
        areaCode.put("市辖区", 130801);
        areaCode.put("双桥区", 130802);
        areaCode.put("双滦区", 130803);
        areaCode.put("鹰手营子矿区", 130804);
        areaCode.put("承德县", 130821);
        areaCode.put("兴隆县", 130822);
        areaCode.put("平泉县", 130823);
        areaCode.put("滦平县", 130824);
        areaCode.put("隆化县", 130825);
        areaCode.put("丰宁满族自治县", 130826);
        areaCode.put("宽城满族自治县", 130827);
        areaCode.put("围场满族蒙古族自治县", 130828);
        areaCode.put("沧州市", 130900);
        areaCode.put("市辖区", 130901);
        areaCode.put("新华区", 130902);
        areaCode.put("运河区", 130903);
        areaCode.put("沧　县", 130921);
        areaCode.put("青　县", 130922);
        areaCode.put("东光县", 130923);
        areaCode.put("海兴县", 130924);
        areaCode.put("盐山县", 130925);
        areaCode.put("肃宁县", 130926);
        areaCode.put("南皮县", 130927);
        areaCode.put("吴桥县", 130928);
        areaCode.put("献　县", 130929);
        areaCode.put("孟村回族自治县", 130930);
        areaCode.put("泊头市", 130981);
        areaCode.put("任丘市", 130982);
        areaCode.put("黄骅市", 130983);
        areaCode.put("河间市", 130984);
        areaCode.put("廊坊市", 131000);
        areaCode.put("市辖区", 131001);
        areaCode.put("安次区", 131002);
        areaCode.put("广阳区", 131003);
        areaCode.put("固安县", 131022);
        areaCode.put("永清县", 131023);
        areaCode.put("香河县", 131024);
        areaCode.put("大城县", 131025);
        areaCode.put("文安县", 131026);
        areaCode.put("大厂回族自治县", 131028);
        areaCode.put("霸州市", 131081);
        areaCode.put("三河市", 131082);
        areaCode.put("衡水市", 131100);
        areaCode.put("市辖区", 131101);
        areaCode.put("桃城区", 131102);
        areaCode.put("枣强县", 131121);
        areaCode.put("武邑县", 131122);
        areaCode.put("武强县", 131123);
        areaCode.put("饶阳县", 131124);
        areaCode.put("安平县", 131125);
        areaCode.put("故城县", 131126);
        areaCode.put("景　县", 131127);
        areaCode.put("阜城县", 131128);
        areaCode.put("冀州市", 131181);
        areaCode.put("深州市", 131182);
        areaCode.put("山西省", 140000);
        areaCode.put("太原市", 140100);
        areaCode.put("市辖区", 140101);
        areaCode.put("小店区", 140105);
        areaCode.put("迎泽区", 140106);
        areaCode.put("杏花岭区", 140107);
        areaCode.put("尖草坪区", 140108);
        areaCode.put("万柏林区", 140109);
        areaCode.put("晋源区", 140110);
        areaCode.put("清徐县", 140121);
        areaCode.put("阳曲县", 140122);
        areaCode.put("娄烦县", 140123);
        areaCode.put("古交市", 140181);
        areaCode.put("大同市", 140200);
        areaCode.put("市辖区", 140201);
        areaCode.put("城　区", 140202);
        areaCode.put("矿　区", 140203);
        areaCode.put("南郊区", 140211);
        areaCode.put("新荣区", 140212);
        areaCode.put("阳高县", 140221);
        areaCode.put("天镇县", 140222);
        areaCode.put("广灵县", 140223);
        areaCode.put("灵丘县", 140224);
        areaCode.put("浑源县", 140225);
        areaCode.put("左云县", 140226);
        areaCode.put("大同县", 140227);
        areaCode.put("阳泉市", 140300);
        areaCode.put("市辖区", 140301);
        areaCode.put("城　区", 140302);
        areaCode.put("矿　区", 140303);
        areaCode.put("郊　区", 140311);
        areaCode.put("平定县", 140321);
        areaCode.put("盂　县", 140322);
        areaCode.put("长治市", 140400);
        areaCode.put("市辖区", 140401);
        areaCode.put("城　区", 140402);
        areaCode.put("郊　区", 140411);
        areaCode.put("长治县", 140421);
        areaCode.put("襄垣县", 140423);
        areaCode.put("屯留县", 140424);
        areaCode.put("平顺县", 140425);
        areaCode.put("黎城县", 140426);
        areaCode.put("壶关县", 140427);
        areaCode.put("长子县", 140428);
        areaCode.put("武乡县", 140429);
        areaCode.put("沁　县", 140430);
        areaCode.put("沁源县", 140431);
        areaCode.put("潞城市", 140481);
        areaCode.put("晋城市", 140500);
        areaCode.put("市辖区", 140501);
        areaCode.put("城　区", 140502);
        areaCode.put("沁水县", 140521);
        areaCode.put("阳城县", 140522);
        areaCode.put("陵川县", 140524);
        areaCode.put("泽州县", 140525);
        areaCode.put("高平市", 140581);
        areaCode.put("朔州市", 140600);
        areaCode.put("市辖区", 140601);
        areaCode.put("朔城区", 140602);
        areaCode.put("平鲁区", 140603);
        areaCode.put("山阴县", 140621);
        areaCode.put("应　县", 140622);
        areaCode.put("右玉县", 140623);
        areaCode.put("怀仁县", 140624);
        areaCode.put("晋中市", 140700);
        areaCode.put("市辖区", 140701);
        areaCode.put("榆次区", 140702);
        areaCode.put("榆社县", 140721);
        areaCode.put("左权县", 140722);
        areaCode.put("和顺县", 140723);
        areaCode.put("昔阳县", 140724);
        areaCode.put("寿阳县", 140725);
        areaCode.put("太谷县", 140726);
        areaCode.put("祁　县", 140727);
        areaCode.put("平遥县", 140728);
        areaCode.put("灵石县", 140729);
        areaCode.put("介休市", 140781);
        areaCode.put("运城市", 140800);
        areaCode.put("市辖区", 140801);
        areaCode.put("盐湖区", 140802);
        areaCode.put("临猗县", 140821);
        areaCode.put("万荣县", 140822);
        areaCode.put("闻喜县", 140823);
        areaCode.put("稷山县", 140824);
        areaCode.put("新绛县", 140825);
        areaCode.put("绛　县", 140826);
        areaCode.put("垣曲县", 140827);
        areaCode.put("夏　县", 140828);
        areaCode.put("平陆县", 140829);
        areaCode.put("芮城县", 140830);
        areaCode.put("永济市", 140881);
        areaCode.put("河津市", 140882);
        areaCode.put("忻州市", 140900);
        areaCode.put("市辖区", 140901);
        areaCode.put("忻府区", 140902);
        areaCode.put("定襄县", 140921);
        areaCode.put("五台县", 140922);
        areaCode.put("代　县", 140923);
        areaCode.put("繁峙县", 140924);
        areaCode.put("宁武县", 140925);
        areaCode.put("静乐县", 140926);
        areaCode.put("神池县", 140927);
        areaCode.put("五寨县", 140928);
        areaCode.put("岢岚县", 140929);
        areaCode.put("河曲县", 140930);
        areaCode.put("保德县", 140931);
        areaCode.put("偏关县", 140932);
        areaCode.put("原平市", 140981);
        areaCode.put("临汾市", 141000);
        areaCode.put("市辖区", 141001);
        areaCode.put("尧都区", 141002);
        areaCode.put("曲沃县", 141021);
        areaCode.put("翼城县", 141022);
        areaCode.put("襄汾县", 141023);
        areaCode.put("洪洞县", 141024);
        areaCode.put("古　县", 141025);
        areaCode.put("安泽县", 141026);
        areaCode.put("浮山县", 141027);
        areaCode.put("吉　县", 141028);
        areaCode.put("乡宁县", 141029);
        areaCode.put("大宁县", 141030);
        areaCode.put("隰　县", 141031);
        areaCode.put("永和县", 141032);
        areaCode.put("蒲　县", 141033);
        areaCode.put("汾西县", 141034);
        areaCode.put("侯马市", 141081);
        areaCode.put("霍州市", 141082);
        areaCode.put("吕梁市", 141100);
        areaCode.put("市辖区", 141101);
        areaCode.put("离石区", 141102);
        areaCode.put("文水县", 141121);
        areaCode.put("交城县", 141122);
        areaCode.put("兴　县", 141123);
        areaCode.put("临　县", 141124);
        areaCode.put("柳林县", 141125);
        areaCode.put("石楼县", 141126);
        areaCode.put("岚　县", 141127);
        areaCode.put("方山县", 141128);
        areaCode.put("中阳县", 141129);
        areaCode.put("交口县", 141130);
        areaCode.put("孝义市", 141181);
        areaCode.put("汾阳市", 141182);
        areaCode.put("内蒙古自治区", 150000);
        areaCode.put("呼和浩特市", 150100);
        areaCode.put("市辖区", 150101);
        areaCode.put("新城区", 150102);
        areaCode.put("回民区", 150103);
        areaCode.put("玉泉区", 150104);
        areaCode.put("赛罕区", 150105);
        areaCode.put("土默特左旗", 150121);
        areaCode.put("托克托县", 150122);
        areaCode.put("和林格尔县", 150123);
        areaCode.put("清水河县", 150124);
        areaCode.put("武川县", 150125);
        areaCode.put("包头市", 150200);
        areaCode.put("市辖区", 150201);
        areaCode.put("东河区", 150202);
        areaCode.put("昆都仑区", 150203);
        areaCode.put("青山区", 150204);
        areaCode.put("石拐区", 150205);
        areaCode.put("白云鄂博矿区", 150206);
        areaCode.put("九原区", 150207);
        areaCode.put("土默特右旗", 150221);
        areaCode.put("固阳县", 150222);
        areaCode.put("达尔罕茂明安联合旗", 150223);
        areaCode.put("乌海市", 150300);
        areaCode.put("市辖区", 150301);
        areaCode.put("海勃湾区", 150302);
        areaCode.put("海南区", 150303);
        areaCode.put("乌达区", 150304);
        areaCode.put("赤峰市", 150400);
        areaCode.put("市辖区", 150401);
        areaCode.put("红山区", 150402);
        areaCode.put("元宝山区", 150403);
        areaCode.put("松山区", 150404);
        areaCode.put("阿鲁科尔沁旗", 150421);
        areaCode.put("巴林左旗", 150422);
        areaCode.put("巴林右旗", 150423);
        areaCode.put("林西县", 150424);
        areaCode.put("克什克腾旗", 150425);
        areaCode.put("翁牛特旗", 150426);
        areaCode.put("喀喇沁旗", 150428);
        areaCode.put("宁城县", 150429);
        areaCode.put("敖汉旗", 150430);
        areaCode.put("通辽市", 150500);
        areaCode.put("市辖区", 150501);
        areaCode.put("科尔沁区", 150502);
        areaCode.put("科尔沁左翼中旗", 150521);
        areaCode.put("科尔沁左翼后旗", 150522);
        areaCode.put("开鲁县", 150523);
        areaCode.put("库伦旗", 150524);
        areaCode.put("奈曼旗", 150525);
        areaCode.put("扎鲁特旗", 150526);
        areaCode.put("霍林郭勒市", 150581);
        areaCode.put("鄂尔多斯市", 150600);
        areaCode.put("市辖区", 150601);
        areaCode.put("  东胜区", 150602);
        areaCode.put("达拉特旗", 150621);
        areaCode.put("准格尔旗", 150622);
        areaCode.put("鄂托克前旗", 150623);
        areaCode.put("鄂托克旗", 150624);
        areaCode.put("杭锦旗", 150625);
        areaCode.put("乌审旗", 150626);
        areaCode.put("伊金霍洛旗", 150627);
        areaCode.put("呼伦贝尔市", 150700);
        areaCode.put("市辖区", 150701);
        areaCode.put("海拉尔区", 150702);
        areaCode.put("阿荣旗", 150721);
        areaCode.put("莫力达瓦达斡尔族自治旗", 150722);
        areaCode.put("鄂伦春自治旗", 150723);
        areaCode.put("鄂温克族自治旗", 150724);
        areaCode.put("陈巴尔虎旗", 150725);
        areaCode.put("新巴尔虎左旗", 150726);
        areaCode.put("新巴尔虎右旗", 150727);
        areaCode.put("满洲里市", 150781);
        areaCode.put("牙克石市", 150782);
        areaCode.put("扎兰屯市", 150783);
        areaCode.put("额尔古纳市", 150784);
        areaCode.put("根河市", 150785);
        areaCode.put("巴彦淖尔市", 150800);
        areaCode.put("市辖区", 150801);
        areaCode.put("临河区", 150802);
        areaCode.put("五原县", 150821);
        areaCode.put("磴口县", 150822);
        areaCode.put("乌拉特前旗", 150823);
        areaCode.put("乌拉特中旗", 150824);
        areaCode.put("乌拉特后旗", 150825);
        areaCode.put("杭锦后旗", 150826);
        areaCode.put("乌兰察布市", 150900);
        areaCode.put("市辖区", 150901);
        areaCode.put("集宁区", 150902);
        areaCode.put("卓资县", 150921);
        areaCode.put("化德县", 150922);
        areaCode.put("商都县", 150923);
        areaCode.put("兴和县", 150924);
        areaCode.put("凉城县", 150925);
        areaCode.put("察哈尔右翼前旗", 150926);
        areaCode.put("察哈尔右翼中旗", 150927);
        areaCode.put("察哈尔右翼后旗", 150928);
        areaCode.put("四子王旗", 150929);
        areaCode.put("丰镇市", 150981);
        areaCode.put("兴安盟", 152200);
        areaCode.put("乌兰浩特市", 152201);
        areaCode.put("阿尔山市", 152202);
        areaCode.put("科尔沁右翼前旗", 152221);
        areaCode.put("科尔沁右翼中旗", 152222);
        areaCode.put("扎赉特旗", 152223);
        areaCode.put("突泉县", 152224);
        areaCode.put("锡林郭勒盟", 152500);
        areaCode.put("二连浩特市", 152501);
        areaCode.put("锡林浩特市", 152502);
        areaCode.put("阿巴嘎旗", 152522);
        areaCode.put("苏尼特左旗", 152523);
        areaCode.put("苏尼特右旗", 152524);
        areaCode.put("东乌珠穆沁旗", 152525);
        areaCode.put("西乌珠穆沁旗", 152526);
        areaCode.put("太仆寺旗", 152527);
        areaCode.put("镶黄旗", 152528);
        areaCode.put("正镶白旗", 152529);
        areaCode.put("正蓝旗", 152530);
        areaCode.put("多伦县", 152531);
        areaCode.put("阿拉善盟", 152900);
        areaCode.put("阿拉善左旗", 152921);
        areaCode.put("阿拉善右旗", 152922);
        areaCode.put("额济纳旗", 152923);
        areaCode.put("辽宁省", 210000);
        areaCode.put("沈阳市", 210100);
        areaCode.put("市辖区", 210101);
        areaCode.put("和平区", 210102);
        areaCode.put("沈河区", 210103);
        areaCode.put("大东区", 210104);
        areaCode.put("皇姑区", 210105);
        areaCode.put("铁西区", 210106);
        areaCode.put("苏家屯区", 210111);
        areaCode.put("东陵区", 210112);
        areaCode.put("沈北新区", 210113);
        areaCode.put("于洪区", 210114);
        areaCode.put("辽中县", 210122);
        areaCode.put("康平县", 210123);
        areaCode.put("法库县", 210124);
        areaCode.put("新民市", 210181);
        areaCode.put("大连市", 210200);
        areaCode.put("市辖区", 210201);
        areaCode.put("中山区", 210202);
        areaCode.put("西岗区", 210203);
        areaCode.put("沙河口区", 210204);
        areaCode.put("甘井子区", 210211);
        areaCode.put("旅顺口区", 210212);
        areaCode.put("金州区", 210213);
        areaCode.put("长海县", 210224);
        areaCode.put("瓦房店市", 210281);
        areaCode.put("普兰店市", 210282);
        areaCode.put("庄河市", 210283);
        areaCode.put("鞍山市", 210300);
        areaCode.put("市辖区", 210301);
        areaCode.put("铁东区", 210302);
        areaCode.put("铁西区", 210303);
        areaCode.put("立山区", 210304);
        areaCode.put("千山区", 210311);
        areaCode.put("台安县", 210321);
        areaCode.put("岫岩满族自治县", 210323);
        areaCode.put("海城市", 210381);
        areaCode.put("抚顺市", 210400);
        areaCode.put("市辖区", 210401);
        areaCode.put("新抚区", 210402);
        areaCode.put("东洲区", 210403);
        areaCode.put("望花区", 210404);
        areaCode.put("顺城区", 210411);
        areaCode.put("抚顺县", 210421);
        areaCode.put("新宾满族自治县", 210422);
        areaCode.put("清原满族自治县", 210423);
        areaCode.put("本溪市", 210500);
        areaCode.put("市辖区", 210501);
        areaCode.put("平山区", 210502);
        areaCode.put("溪湖区", 210503);
        areaCode.put("明山区", 210504);
        areaCode.put("南芬区", 210505);
        areaCode.put("本溪满族自治县", 210521);
        areaCode.put("桓仁满族自治县", 210522);
        areaCode.put("丹东市", 210600);
        areaCode.put("市辖区", 210601);
        areaCode.put("元宝区", 210602);
        areaCode.put("振兴区", 210603);
        areaCode.put("振安区", 210604);
        areaCode.put("宽甸满族自治县", 210624);
        areaCode.put("东港市", 210681);
        areaCode.put("凤城市", 210682);
        areaCode.put("锦州市", 210700);
        areaCode.put("市辖区", 210701);
        areaCode.put("古塔区", 210702);
        areaCode.put("凌河区", 210703);
        areaCode.put("太和区", 210711);
        areaCode.put("黑山县", 210726);
        areaCode.put("义　县", 210727);
        areaCode.put("凌海市", 210781);
        areaCode.put("北镇市", 210782);
        areaCode.put("营口市", 210800);
        areaCode.put("市辖区", 210801);
        areaCode.put("站前区", 210802);
        areaCode.put("西市区", 210803);
        areaCode.put("鲅鱼圈区", 210804);
        areaCode.put("老边区", 210811);
        areaCode.put("盖州市", 210881);
        areaCode.put("大石桥市", 210882);
        areaCode.put("阜新市", 210900);
        areaCode.put("市辖区", 210901);
        areaCode.put("海州区", 210902);
        areaCode.put("新邱区", 210903);
        areaCode.put("太平区", 210904);
        areaCode.put("清河门区", 210905);
        areaCode.put("细河区", 210911);
        areaCode.put("阜新蒙古族自治县", 210921);
        areaCode.put("彰武县", 210922);
        areaCode.put("辽阳市", 211000);
        areaCode.put("市辖区", 211001);
        areaCode.put("白塔区", 211002);
        areaCode.put("文圣区", 211003);
        areaCode.put("宏伟区", 211004);
        areaCode.put("弓长岭区", 211005);
        areaCode.put("太子河区", 211011);
        areaCode.put("辽阳县", 211021);
        areaCode.put("灯塔市", 211081);
        areaCode.put("盘锦市", 211100);
        areaCode.put("市辖区", 211101);
        areaCode.put("双台子区", 211102);
        areaCode.put("兴隆台区", 211103);
        areaCode.put("大洼县", 211121);
        areaCode.put("盘山县", 211122);
        areaCode.put("铁岭市", 211200);
        areaCode.put("市辖区", 211201);
        areaCode.put("银州区", 211202);
        areaCode.put("清河区", 211204);
        areaCode.put("铁岭县", 211221);
        areaCode.put("西丰县", 211223);
        areaCode.put("昌图县", 211224);
        areaCode.put("调兵山市", 211281);
        areaCode.put("开原市", 211282);
        areaCode.put("朝阳市", 211300);
        areaCode.put("市辖区", 211301);
        areaCode.put("双塔区", 211302);
        areaCode.put("龙城区", 211303);
        areaCode.put("朝阳县", 211321);
        areaCode.put("建平县", 211322);
        areaCode.put("喀喇沁左翼蒙古族自治县", 211324);
        areaCode.put("北票市", 211381);
        areaCode.put("凌源市", 211382);
        areaCode.put("葫芦岛市", 211400);
        areaCode.put("市辖区", 211401);
        areaCode.put("连山区", 211402);
        areaCode.put("龙港区", 211403);
        areaCode.put("南票区", 211404);
        areaCode.put("绥中县", 211421);
        areaCode.put("建昌县", 211422);
        areaCode.put("兴城市", 211481);
        areaCode.put("吉林省", 220000);
        areaCode.put("长春市", 220100);
        areaCode.put("市辖区", 220101);
        areaCode.put("南关区", 220102);
        areaCode.put("宽城区", 220103);
        areaCode.put("朝阳区", 220104);
        areaCode.put("二道区", 220105);
        areaCode.put("绿园区", 220106);
        areaCode.put("双阳区", 220112);
        areaCode.put("农安县", 220122);
        areaCode.put("九台市", 220181);
        areaCode.put("榆树市", 220182);
        areaCode.put("德惠市", 220183);
        areaCode.put("吉林市", 220200);
        areaCode.put("市辖区", 220201);
        areaCode.put("昌邑区", 220202);
        areaCode.put("龙潭区", 220203);
        areaCode.put("船营区", 220204);
        areaCode.put("丰满区", 220211);
        areaCode.put("永吉县", 220221);
        areaCode.put("蛟河市", 220281);
        areaCode.put("桦甸市", 220282);
        areaCode.put("舒兰市", 220283);
        areaCode.put("磐石市", 220284);
        areaCode.put("四平市", 220300);
        areaCode.put("市辖区", 220301);
        areaCode.put("铁西区", 220302);
        areaCode.put("铁东区", 220303);
        areaCode.put("梨树县", 220322);
        areaCode.put("伊通满族自治县", 220323);
        areaCode.put("公主岭市", 220381);
        areaCode.put("双辽市", 220382);
        areaCode.put("辽源市", 220400);
        areaCode.put("市辖区", 220401);
        areaCode.put("龙山区", 220402);
        areaCode.put("西安区", 220403);
        areaCode.put("东丰县", 220421);
        areaCode.put("东辽县", 220422);
        areaCode.put("通化市", 220500);
        areaCode.put("市辖区", 220501);
        areaCode.put("东昌区", 220502);
        areaCode.put("二道江区", 220503);
        areaCode.put("通化县", 220521);
        areaCode.put("辉南县", 220523);
        areaCode.put("柳河县", 220524);
        areaCode.put("梅河口市", 220581);
        areaCode.put("集安市", 220582);
        areaCode.put("白山市", 220600);
        areaCode.put("市辖区", 220601);
        areaCode.put("八道江区", 220602);
        areaCode.put("  江源区", 220605);
        areaCode.put("抚松县", 220621);
        areaCode.put("靖宇县", 220622);
        areaCode.put("长白朝鲜族自治县", 220623);
        areaCode.put("临江市", 220681);
        areaCode.put("松原市", 220700);
        areaCode.put("市辖区", 220701);
        areaCode.put("宁江区", 220702);
        areaCode.put("前郭尔罗斯蒙古族自治县", 220721);
        areaCode.put("长岭县", 220722);
        areaCode.put("乾安县", 220723);
        areaCode.put("扶余县", 220724);
        areaCode.put("白城市", 220800);
        areaCode.put("市辖区", 220801);
        areaCode.put("洮北区", 220802);
        areaCode.put("镇赉县", 220821);
        areaCode.put("通榆县", 220822);
        areaCode.put("洮南市", 220881);
        areaCode.put("大安市", 220882);
        areaCode.put("延边朝鲜族自治州", 222400);
        areaCode.put("延吉市", 222401);
        areaCode.put("图们市", 222402);
        areaCode.put("敦化市", 222403);
        areaCode.put("珲春市", 222404);
        areaCode.put("龙井市", 222405);
        areaCode.put("和龙市", 222406);
        areaCode.put("汪清县", 222424);
        areaCode.put("安图县", 222426);
        areaCode.put("黑龙江省", 230000);
        areaCode.put("哈尔滨市", 230100);
        areaCode.put("市辖区", 230101);
        areaCode.put("道里区", 230102);
        areaCode.put("南岗区", 230103);
        areaCode.put("道外区", 230104);
        areaCode.put("平房区", 230108);
        areaCode.put("松北区", 230109);
        areaCode.put("香坊区", 230110);
        areaCode.put("呼兰区", 230111);
        areaCode.put("依兰县", 230123);
        areaCode.put("方正县", 230124);
        areaCode.put("宾　县", 230125);
        areaCode.put("巴彦县", 230126);
        areaCode.put("木兰县", 230127);
        areaCode.put("通河县", 230128);
        areaCode.put("延寿县", 230129);
        areaCode.put("双城市", 230182);
        areaCode.put("尚志市", 230183);
        areaCode.put("五常市", 230184);
        areaCode.put("齐齐哈尔市", 230200);
        areaCode.put("市辖区", 230201);
        areaCode.put("龙沙区", 230202);
        areaCode.put("建华区", 230203);
        areaCode.put("铁锋区", 230204);
        areaCode.put("昂昂溪区", 230205);
        areaCode.put("富拉尔基区", 230206);
        areaCode.put("碾子山区", 230207);
        areaCode.put("梅里斯达斡尔族区", 230208);
        areaCode.put("龙江县", 230221);
        areaCode.put("依安县", 230223);
        areaCode.put("泰来县", 230224);
        areaCode.put("甘南县", 230225);
        areaCode.put("富裕县", 230227);
        areaCode.put("克山县", 230229);
        areaCode.put("克东县", 230230);
        areaCode.put("拜泉县", 230231);
        areaCode.put("讷河市", 230281);
        areaCode.put("鸡西市", 230300);
        areaCode.put("市辖区", 230301);
        areaCode.put("鸡冠区", 230302);
        areaCode.put("恒山区", 230303);
        areaCode.put("滴道区", 230304);
        areaCode.put("梨树区", 230305);
        areaCode.put("城子河区", 230306);
        areaCode.put("麻山区", 230307);
        areaCode.put("鸡东县", 230321);
        areaCode.put("虎林市", 230381);
        areaCode.put("密山市", 230382);
        areaCode.put("鹤岗市", 230400);
        areaCode.put("市辖区", 230401);
        areaCode.put("向阳区", 230402);
        areaCode.put("工农区", 230403);
        areaCode.put("南山区", 230404);
        areaCode.put("兴安区", 230405);
        areaCode.put("东山区", 230406);
        areaCode.put("兴山区", 230407);
        areaCode.put("萝北县", 230421);
        areaCode.put("绥滨县", 230422);
        areaCode.put("双鸭山市", 230500);
        areaCode.put("市辖区", 230501);
        areaCode.put("尖山区", 230502);
        areaCode.put("岭东区", 230503);
        areaCode.put("四方台区", 230505);
        areaCode.put("宝山区", 230506);
        areaCode.put("集贤县", 230521);
        areaCode.put("友谊县", 230522);
        areaCode.put("宝清县", 230523);
        areaCode.put("饶河县", 230524);
        areaCode.put("大庆市", 230600);
        areaCode.put("市辖区", 230601);
        areaCode.put("萨尔图区", 230602);
        areaCode.put("龙凤区", 230603);
        areaCode.put("让胡路区", 230604);
        areaCode.put("红岗区", 230605);
        areaCode.put("大同区", 230606);
        areaCode.put("肇州县", 230621);
        areaCode.put("肇源县", 230622);
        areaCode.put("林甸县", 230623);
        areaCode.put("杜尔伯特蒙古族自治县", 230624);
        areaCode.put("伊春市", 230700);
        areaCode.put("市辖区", 230701);
        areaCode.put("伊春区", 230702);
        areaCode.put("南岔区", 230703);
        areaCode.put("友好区", 230704);
        areaCode.put("西林区", 230705);
        areaCode.put("翠峦区", 230706);
        areaCode.put("新青区", 230707);
        areaCode.put("美溪区", 230708);
        areaCode.put("金山屯区", 230709);
        areaCode.put("五营区", 230710);
        areaCode.put("乌马河区", 230711);
        areaCode.put("汤旺河区", 230712);
        areaCode.put("带岭区", 230713);
        areaCode.put("乌伊岭区", 230714);
        areaCode.put("红星区", 230715);
        areaCode.put("上甘岭区", 230716);
        areaCode.put("嘉荫县", 230722);
        areaCode.put("铁力市", 230781);
        areaCode.put("佳木斯市", 230800);
        areaCode.put("市辖区", 230801);
        areaCode.put("向阳区", 230803);
        areaCode.put("前进区", 230804);
        areaCode.put("东风区", 230805);
        areaCode.put("郊　区", 230811);
        areaCode.put("桦南县", 230822);
        areaCode.put("桦川县", 230826);
        areaCode.put("汤原县", 230828);
        areaCode.put("抚远县", 230833);
        areaCode.put("同江市", 230881);
        areaCode.put("富锦市", 230882);
        areaCode.put("七台河市", 230900);
        areaCode.put("市辖区", 230901);
        areaCode.put("新兴区", 230902);
        areaCode.put("桃山区", 230903);
        areaCode.put("茄子河区", 230904);
        areaCode.put("勃利县", 230921);
        areaCode.put("牡丹江市", 231000);
        areaCode.put("市辖区", 231001);
        areaCode.put("东安区", 231002);
        areaCode.put("阳明区", 231003);
        areaCode.put("爱民区", 231004);
        areaCode.put("西安区", 231005);
        areaCode.put("东宁县", 231024);
        areaCode.put("林口县", 231025);
        areaCode.put("绥芬河市", 231081);
        areaCode.put("海林市", 231083);
        areaCode.put("宁安市", 231084);
        areaCode.put("穆棱市", 231085);
        areaCode.put("黑河市", 231100);
        areaCode.put("市辖区", 231101);
        areaCode.put("爱辉区", 231102);
        areaCode.put("嫩江县", 231121);
        areaCode.put("逊克县", 231123);
        areaCode.put("孙吴县", 231124);
        areaCode.put("北安市", 231181);
        areaCode.put("五大连池市", 231182);
        areaCode.put("绥化市", 231200);
        areaCode.put("市辖区", 231201);
        areaCode.put("北林区", 231202);
        areaCode.put("望奎县", 231221);
        areaCode.put("兰西县", 231222);
        areaCode.put("青冈县", 231223);
        areaCode.put("庆安县", 231224);
        areaCode.put("明水县", 231225);
        areaCode.put("绥棱县", 231226);
        areaCode.put("安达市", 231281);
        areaCode.put("肇东市", 231282);
        areaCode.put("海伦市", 231283);
        areaCode.put("大兴安岭地区", 232700);
        areaCode.put("呼玛县", 232721);
        areaCode.put("塔河县", 232722);
        areaCode.put("漠河县", 232723);
        areaCode.put("上海市", 310000);
        areaCode.put("市辖区", 310100);
        areaCode.put("黄浦区", 310101);
        areaCode.put("卢湾区", 310103);
        areaCode.put("徐汇区", 310104);
        areaCode.put("长宁区", 310105);
        areaCode.put("静安区", 310106);
        areaCode.put("普陀区", 310107);
        areaCode.put("闸北区", 310108);
        areaCode.put("虹口区", 310109);
        areaCode.put("杨浦区", 310110);
        areaCode.put("闵行区", 310112);
        areaCode.put("宝山区", 310113);
        areaCode.put("嘉定区", 310114);
        areaCode.put("浦东新区 (*)", 310115);
        areaCode.put("金山区", 310116);
        areaCode.put("松江区", 310117);
        areaCode.put("青浦区", 310118);
        areaCode.put("奉贤区", 310120);
        areaCode.put("县", 310200);
        areaCode.put("崇明县", 310230);
        areaCode.put("江苏省", 320000);
        areaCode.put("南京市", 320100);
        areaCode.put("市辖区", 320101);
        areaCode.put("玄武区", 320102);
        areaCode.put("白下区", 320103);
        areaCode.put("秦淮区", 320104);
        areaCode.put("建邺区", 320105);
        areaCode.put("鼓楼区", 320106);
        areaCode.put("下关区", 320107);
        areaCode.put("浦口区", 320111);
        areaCode.put("栖霞区", 320113);
        areaCode.put("雨花台区", 320114);
        areaCode.put("江宁区", 320115);
        areaCode.put("六合区", 320116);
        areaCode.put("溧水县", 320124);
        areaCode.put("高淳县", 320125);
        areaCode.put("无锡市", 320200);
        areaCode.put("市辖区", 320201);
        areaCode.put("崇安区", 320202);
        areaCode.put("南长区", 320203);
        areaCode.put("北塘区", 320204);
        areaCode.put("锡山区", 320205);
        areaCode.put("惠山区", 320206);
        areaCode.put("滨湖区", 320211);
        areaCode.put("江阴市", 320281);
        areaCode.put("宜兴市", 320282);
        areaCode.put("徐州市", 320300);
        areaCode.put("市辖区", 320301);
        areaCode.put("鼓楼区", 320302);
        areaCode.put("云龙区", 320303);
        areaCode.put("九里区", 320304);
        areaCode.put("贾汪区", 320305);
        areaCode.put("泉山区", 320311);
        areaCode.put("丰　县", 320321);
        areaCode.put("沛　县", 320322);
        areaCode.put("铜山县", 320323);
        areaCode.put("睢宁县", 320324);
        areaCode.put("新沂市", 320381);
        areaCode.put("邳州市", 320382);
        areaCode.put("常州市", 320400);
        areaCode.put("市辖区", 320401);
        areaCode.put("天宁区", 320402);
        areaCode.put("钟楼区", 320404);
        areaCode.put("戚墅堰区", 320405);
        areaCode.put("新北区", 320411);
        areaCode.put("武进区", 320412);
        areaCode.put("溧阳市", 320481);
        areaCode.put("金坛市", 320482);
        areaCode.put("苏州市", 320500);
        areaCode.put("市辖区", 320501);
        areaCode.put("沧浪区", 320502);
        areaCode.put("平江区", 320503);
        areaCode.put("金阊区", 320504);
        areaCode.put("虎丘区", 320505);
        areaCode.put("吴中区", 320506);
        areaCode.put("相城区", 320507);
        areaCode.put("常熟市", 320581);
        areaCode.put("张家港市", 320582);
        areaCode.put("昆山市", 320583);
        areaCode.put("吴江市", 320584);
        areaCode.put("太仓市", 320585);
        areaCode.put("南通市", 320600);
        areaCode.put("市辖区", 320601);
        areaCode.put("崇川区", 320602);
        areaCode.put("港闸区", 320611);
        areaCode.put("海安县", 320621);
        areaCode.put("如东县", 320623);
        areaCode.put("启东市", 320681);
        areaCode.put("如皋市", 320682);
        areaCode.put("海门市", 320684);
        areaCode.put("连云港市", 320700);
        areaCode.put("市辖区", 320701);
        areaCode.put("连云区", 320703);
        areaCode.put("新浦区", 320705);
        areaCode.put("海州区", 320706);
        areaCode.put("赣榆县", 320721);
        areaCode.put("东海县", 320722);
        areaCode.put("灌云县", 320723);
        areaCode.put("灌南县", 320724);
        areaCode.put("淮安市", 320800);
        areaCode.put("市辖区", 320801);
        areaCode.put("清河区", 320802);
        areaCode.put("楚州区", 320803);
        areaCode.put("淮阴区", 320804);
        areaCode.put("清浦区", 320811);
        areaCode.put("涟水县", 320826);
        areaCode.put("洪泽县", 320829);
        areaCode.put("盱眙县", 320830);
        areaCode.put("金湖县", 320831);
        areaCode.put("盐城市", 320900);
        areaCode.put("市辖区", 320901);
        areaCode.put("亭湖区", 320902);
        areaCode.put("盐都区", 320903);
        areaCode.put("响水县", 320921);
        areaCode.put("滨海县", 320922);
        areaCode.put("阜宁县", 320923);
        areaCode.put("射阳县", 320924);
        areaCode.put("建湖县", 320925);
        areaCode.put("东台市", 320981);
        areaCode.put("大丰市", 320982);
        areaCode.put("扬州市", 321000);
        areaCode.put("市辖区", 321001);
        areaCode.put("广陵区", 321002);
        areaCode.put("邗江区", 321003);
        areaCode.put("维扬区", 321011);
        areaCode.put("宝应县", 321023);
        areaCode.put("仪征市", 321081);
        areaCode.put("高邮市", 321084);
        areaCode.put("江都市", 321088);
        areaCode.put("镇江市", 321100);
        areaCode.put("市辖区", 321101);
        areaCode.put("京口区", 321102);
        areaCode.put("润州区", 321111);
        areaCode.put("丹徒区", 321112);
        areaCode.put("丹阳市", 321181);
        areaCode.put("扬中市", 321182);
        areaCode.put("句容市", 321183);
        areaCode.put("泰州市", 321200);
        areaCode.put("市辖区", 321201);
        areaCode.put("海陵区", 321202);
        areaCode.put("高港区", 321203);
        areaCode.put("兴化市", 321281);
        areaCode.put("靖江市", 321282);
        areaCode.put("泰兴市", 321283);
        areaCode.put("姜堰市", 321284);
        areaCode.put("宿迁市", 321300);
        areaCode.put("市辖区", 321301);
        areaCode.put("宿城区", 321302);
        areaCode.put("宿豫区", 321311);
        areaCode.put("沭阳县", 321322);
        areaCode.put("泗阳县", 321323);
        areaCode.put("泗洪县", 321324);
        areaCode.put("浙江省", 330000);
        areaCode.put("杭州市", 330100);
        areaCode.put("市辖区", 330101);
        areaCode.put("上城区", 330102);
        areaCode.put("下城区", 330103);
        areaCode.put("江干区", 330104);
        areaCode.put("拱墅区", 330105);
        areaCode.put("西湖区", 330106);
        areaCode.put("滨江区", 330108);
        areaCode.put("萧山区", 330109);
        areaCode.put("余杭区", 330110);
        areaCode.put("桐庐县", 330122);
        areaCode.put("淳安县", 330127);
        areaCode.put("建德市", 330182);
        areaCode.put("富阳市", 330183);
        areaCode.put("临安市", 330185);
        areaCode.put("宁波市", 330200);
        areaCode.put("市辖区", 330201);
        areaCode.put("海曙区", 330203);
        areaCode.put("江东区", 330204);
        areaCode.put("江北区", 330205);
        areaCode.put("北仑区", 330206);
        areaCode.put("镇海区", 330211);
        areaCode.put("鄞州区", 330212);
        areaCode.put("象山县", 330225);
        areaCode.put("宁海县", 330226);
        areaCode.put("余姚市", 330281);
        areaCode.put("慈溪市", 330282);
        areaCode.put("奉化市", 330283);
        areaCode.put("温州市", 330300);
        areaCode.put("市辖区", 330301);
        areaCode.put("鹿城区", 330302);
        areaCode.put("龙湾区", 330303);
        areaCode.put("瓯海区", 330304);
        areaCode.put("洞头县", 330322);
        areaCode.put("永嘉县", 330324);
        areaCode.put("平阳县", 330326);
        areaCode.put("苍南县", 330327);
        areaCode.put("文成县", 330328);
        areaCode.put("泰顺县", 330329);
        areaCode.put("瑞安市", 330381);
        areaCode.put("乐清市", 330382);
        areaCode.put("嘉兴市", 330400);
        areaCode.put("市辖区", 330401);
        areaCode.put("南湖区", 330402);
        areaCode.put("秀洲区", 330411);
        areaCode.put("嘉善县", 330421);
        areaCode.put("海盐县", 330424);
        areaCode.put("海宁市", 330481);
        areaCode.put("平湖市", 330482);
        areaCode.put("桐乡市", 330483);
        areaCode.put("湖州市", 330500);
        areaCode.put("市辖区", 330501);
        areaCode.put("吴兴区", 330502);
        areaCode.put("南浔区", 330503);
        areaCode.put("德清县", 330521);
        areaCode.put("长兴县", 330522);
        areaCode.put("安吉县", 330523);
        areaCode.put("绍兴市", 330600);
        areaCode.put("市辖区", 330601);
        areaCode.put("越城区", 330602);
        areaCode.put("绍兴县", 330621);
        areaCode.put("新昌县", 330624);
        areaCode.put("诸暨市", 330681);
        areaCode.put("上虞市", 330682);
        areaCode.put("嵊州市", 330683);
        areaCode.put("金华市", 330700);
        areaCode.put("市辖区", 330701);
        areaCode.put("婺城区", 330702);
        areaCode.put("金东区", 330703);
        areaCode.put("武义县", 330723);
        areaCode.put("浦江县", 330726);
        areaCode.put("磐安县", 330727);
        areaCode.put("兰溪市", 330781);
        areaCode.put("义乌市", 330782);
        areaCode.put("东阳市", 330783);
        areaCode.put("永康市", 330784);
        areaCode.put("衢州市", 330800);
        areaCode.put("市辖区", 330801);
        areaCode.put("柯城区", 330802);
        areaCode.put("衢江区", 330803);
        areaCode.put("常山县", 330822);
        areaCode.put("开化县", 330824);
        areaCode.put("龙游县", 330825);
        areaCode.put("江山市", 330881);
        areaCode.put("舟山市", 330900);
        areaCode.put("市辖区", 330901);
        areaCode.put("定海区", 330902);
        areaCode.put("普陀区", 330903);
        areaCode.put("岱山县", 330921);
        areaCode.put("嵊泗县", 330922);
        areaCode.put("台州市", 331000);
        areaCode.put("市辖区", 331001);
        areaCode.put("椒江区", 331002);
        areaCode.put("黄岩区", 331003);
        areaCode.put("路桥区", 331004);
        areaCode.put("玉环县", 331021);
        areaCode.put("三门县", 331022);
        areaCode.put("天台县", 331023);
        areaCode.put("仙居县", 331024);
        areaCode.put("温岭市", 331081);
        areaCode.put("临海市", 331082);
        areaCode.put("丽水市", 331100);
        areaCode.put("市辖区", 331101);
        areaCode.put("莲都区", 331102);
        areaCode.put("青田县", 331121);
        areaCode.put("缙云县", 331122);
        areaCode.put("遂昌县", 331123);
        areaCode.put("松阳县", 331124);
        areaCode.put("云和县", 331125);
        areaCode.put("庆元县", 331126);
        areaCode.put("景宁畲族自治县", 331127);
        areaCode.put("龙泉市", 331181);
        areaCode.put("安徽省", 340000);
        areaCode.put("合肥市", 340100);
        areaCode.put("市辖区", 340101);
        areaCode.put("瑶海区", 340102);
        areaCode.put("庐阳区", 340103);
        areaCode.put("蜀山区", 340104);
        areaCode.put("包河区", 340111);
        areaCode.put("长丰县", 340121);
        areaCode.put("肥东县", 340122);
        areaCode.put("肥西县", 340123);
        areaCode.put("芜湖市", 340200);
        areaCode.put("市辖区", 340201);
        areaCode.put("镜湖区", 340202);
        areaCode.put("弋江区", 340203);
        areaCode.put("鸠江区", 340207);
        areaCode.put("三山区", 340208);
        areaCode.put("芜湖县", 340221);
        areaCode.put("繁昌县", 340222);
        areaCode.put("南陵县", 340223);
        areaCode.put("蚌埠市", 340300);
        areaCode.put("市辖区", 340301);
        areaCode.put("龙子湖区", 340302);
        areaCode.put("蚌山区", 340303);
        areaCode.put("禹会区", 340304);
        areaCode.put("淮上区", 340311);
        areaCode.put("怀远县", 340321);
        areaCode.put("五河县", 340322);
        areaCode.put("固镇县", 340323);
        areaCode.put("淮南市", 340400);
        areaCode.put("市辖区", 340401);
        areaCode.put("大通区", 340402);
        areaCode.put("田家庵区", 340403);
        areaCode.put("谢家集区", 340404);
        areaCode.put("八公山区", 340405);
        areaCode.put("潘集区", 340406);
        areaCode.put("凤台县", 340421);
        areaCode.put("马鞍山市", 340500);
        areaCode.put("市辖区", 340501);
        areaCode.put("金家庄区", 340502);
        areaCode.put("花山区", 340503);
        areaCode.put("雨山区", 340504);
        areaCode.put("当涂县", 340521);
        areaCode.put("淮北市", 340600);
        areaCode.put("市辖区", 340601);
        areaCode.put("杜集区", 340602);
        areaCode.put("相山区", 340603);
        areaCode.put("烈山区", 340604);
        areaCode.put("濉溪县", 340621);
        areaCode.put("铜陵市", 340700);
        areaCode.put("市辖区", 340701);
        areaCode.put("铜官山区", 340702);
        areaCode.put("狮子山区", 340703);
        areaCode.put("郊　区", 340711);
        areaCode.put("铜陵县", 340721);
        areaCode.put("安庆市", 340800);
        areaCode.put("市辖区", 340801);
        areaCode.put("迎江区", 340802);
        areaCode.put("大观区", 340803);
        areaCode.put("宜秀区", 340811);
        areaCode.put("怀宁县", 340822);
        areaCode.put("枞阳县", 340823);
        areaCode.put("潜山县", 340824);
        areaCode.put("太湖县", 340825);
        areaCode.put("宿松县", 340826);
        areaCode.put("望江县", 340827);
        areaCode.put("岳西县", 340828);
        areaCode.put("桐城市", 340881);
        areaCode.put("黄山市", 341000);
        areaCode.put("市辖区", 341001);
        areaCode.put("屯溪区", 341002);
        areaCode.put("黄山区", 341003);
        areaCode.put("徽州区", 341004);
        areaCode.put("歙　县", 341021);
        areaCode.put("休宁县", 341022);
        areaCode.put("黟　县", 341023);
        areaCode.put("祁门县", 341024);
        areaCode.put("滁州市", 341100);
        areaCode.put("市辖区", 341101);
        areaCode.put("琅琊区", 341102);
        areaCode.put("南谯区", 341103);
        areaCode.put("来安县", 341122);
        areaCode.put("全椒县", 341124);
        areaCode.put("定远县", 341125);
        areaCode.put("凤阳县", 341126);
        areaCode.put("天长市", 341181);
        areaCode.put("明光市", 341182);
        areaCode.put("阜阳市", 341200);
        areaCode.put("市辖区", 341201);
        areaCode.put("颍州区", 341202);
        areaCode.put("颍东区", 341203);
        areaCode.put("颍泉区", 341204);
        areaCode.put("临泉县", 341221);
        areaCode.put("太和县", 341222);
        areaCode.put("阜南县", 341225);
        areaCode.put("颍上县", 341226);
        areaCode.put("界首市", 341282);
        areaCode.put("宿州市", 341300);
        areaCode.put("市辖区", 341301);
        areaCode.put("埇桥区", 341302);
        areaCode.put("砀山县", 341321);
        areaCode.put("萧　县", 341322);
        areaCode.put("灵璧县", 341323);
        areaCode.put("泗　县", 341324);
        areaCode.put("巢湖市", 341400);
        areaCode.put("市辖区", 341401);
        areaCode.put("居巢区", 341402);
        areaCode.put("庐江县", 341421);
        areaCode.put("无为县", 341422);
        areaCode.put("含山县", 341423);
        areaCode.put("和　县", 341424);
        areaCode.put("六安市", 341500);
        areaCode.put("市辖区", 341501);
        areaCode.put("金安区", 341502);
        areaCode.put("裕安区", 341503);
        areaCode.put("寿　县", 341521);
        areaCode.put("霍邱县", 341522);
        areaCode.put("舒城县", 341523);
        areaCode.put("金寨县", 341524);
        areaCode.put("霍山县", 341525);
        areaCode.put("亳州市", 341600);
        areaCode.put("市辖区", 341601);
        areaCode.put("谯城区", 341602);
        areaCode.put("涡阳县", 341621);
        areaCode.put("蒙城县", 341622);
        areaCode.put("利辛县", 341623);
        areaCode.put("池州市", 341700);
        areaCode.put("市辖区", 341701);
        areaCode.put("贵池区", 341702);
        areaCode.put("东至县", 341721);
        areaCode.put("石台县", 341722);
        areaCode.put("青阳县", 341723);
        areaCode.put("宣城市", 341800);
        areaCode.put("市辖区", 341801);
        areaCode.put("宣州区", 341802);
        areaCode.put("郎溪县", 341821);
        areaCode.put("广德县", 341822);
        areaCode.put("泾　县", 341823);
        areaCode.put("绩溪县", 341824);
        areaCode.put("旌德县", 341825);
        areaCode.put("宁国市", 341881);
        areaCode.put("福建省", 350000);
        areaCode.put("福州市", 350100);
        areaCode.put("市辖区", 350101);
        areaCode.put("鼓楼区", 350102);
        areaCode.put("台江区", 350103);
        areaCode.put("仓山区", 350104);
        areaCode.put("马尾区", 350105);
        areaCode.put("晋安区", 350111);
        areaCode.put("闽侯县", 350121);
        areaCode.put("连江县", 350122);
        areaCode.put("罗源县", 350123);
        areaCode.put("闽清县", 350124);
        areaCode.put("永泰县", 350125);
        areaCode.put("平潭县", 350128);
        areaCode.put("福清市", 350181);
        areaCode.put("长乐市", 350182);
        areaCode.put("厦门市", 350200);
        areaCode.put("市辖区", 350201);
        areaCode.put("思明区", 350203);
        areaCode.put("海沧区", 350205);
        areaCode.put("湖里区", 350206);
        areaCode.put("集美区", 350211);
        areaCode.put("同安区", 350212);
        areaCode.put("翔安区", 350213);
        areaCode.put("莆田市", 350300);
        areaCode.put("市辖区", 350301);
        areaCode.put("城厢区", 350302);
        areaCode.put("涵江区", 350303);
        areaCode.put("荔城区", 350304);
        areaCode.put("秀屿区", 350305);
        areaCode.put("仙游县", 350322);
        areaCode.put("三明市", 350400);
        areaCode.put("市辖区", 350401);
        areaCode.put("梅列区", 350402);
        areaCode.put("三元区", 350403);
        areaCode.put("明溪县", 350421);
        areaCode.put("清流县", 350423);
        areaCode.put("宁化县", 350424);
        areaCode.put("大田县", 350425);
        areaCode.put("尤溪县", 350426);
        areaCode.put("沙　县", 350427);
        areaCode.put("将乐县", 350428);
        areaCode.put("泰宁县", 350429);
        areaCode.put("建宁县", 350430);
        areaCode.put("永安市", 350481);
        areaCode.put("泉州市", 350500);
        areaCode.put("市辖区", 350501);
        areaCode.put("鲤城区", 350502);
        areaCode.put("丰泽区", 350503);
        areaCode.put("洛江区", 350504);
        areaCode.put("泉港区", 350505);
        areaCode.put("惠安县", 350521);
        areaCode.put("安溪县", 350524);
        areaCode.put("永春县", 350525);
        areaCode.put("德化县", 350526);
        areaCode.put("金门县", 350527);
        areaCode.put("石狮市", 350581);
        areaCode.put("晋江市", 350582);
        areaCode.put("南安市", 350583);
        areaCode.put("漳州市", 350600);
        areaCode.put("市辖区", 350601);
        areaCode.put("芗城区", 350602);
        areaCode.put("龙文区", 350603);
        areaCode.put("云霄县", 350622);
        areaCode.put("漳浦县", 350623);
        areaCode.put("诏安县", 350624);
        areaCode.put("长泰县", 350625);
        areaCode.put("东山县", 350626);
        areaCode.put("南靖县", 350627);
        areaCode.put("平和县", 350628);
        areaCode.put("华安县", 350629);
        areaCode.put("龙海市", 350681);
        areaCode.put("南平市", 350700);
        areaCode.put("市辖区", 350701);
        areaCode.put("延平区", 350702);
        areaCode.put("顺昌县", 350721);
        areaCode.put("浦城县", 350722);
        areaCode.put("光泽县", 350723);
        areaCode.put("松溪县", 350724);
        areaCode.put("政和县", 350725);
        areaCode.put("邵武市", 350781);
        areaCode.put("武夷山市", 350782);
        areaCode.put("建瓯市", 350783);
        areaCode.put("建阳市", 350784);
        areaCode.put("龙岩市", 350800);
        areaCode.put("市辖区", 350801);
        areaCode.put("新罗区", 350802);
        areaCode.put("长汀县", 350821);
        areaCode.put("永定县", 350822);
        areaCode.put("上杭县", 350823);
        areaCode.put("武平县", 350824);
        areaCode.put("连城县", 350825);
        areaCode.put("漳平市", 350881);
        areaCode.put("宁德市", 350900);
        areaCode.put("市辖区", 350901);
        areaCode.put("蕉城区", 350902);
        areaCode.put("霞浦县", 350921);
        areaCode.put("古田县", 350922);
        areaCode.put("屏南县", 350923);
        areaCode.put("寿宁县", 350924);
        areaCode.put("周宁县", 350925);
        areaCode.put("柘荣县", 350926);
        areaCode.put("福安市", 350981);
        areaCode.put("福鼎市", 350982);
        areaCode.put("江西省", 360000);
        areaCode.put("南昌市", 360100);
        areaCode.put("市辖区", 360101);
        areaCode.put("东湖区", 360102);
        areaCode.put("西湖区", 360103);
        areaCode.put("青云谱区", 360104);
        areaCode.put("湾里区", 360105);
        areaCode.put("青山湖区", 360111);
        areaCode.put("南昌县", 360121);
        areaCode.put("新建县", 360122);
        areaCode.put("安义县", 360123);
        areaCode.put("进贤县", 360124);
        areaCode.put("景德镇市", 360200);
        areaCode.put("市辖区", 360201);
        areaCode.put("昌江区", 360202);
        areaCode.put("珠山区", 360203);
        areaCode.put("浮梁县", 360222);
        areaCode.put("乐平市", 360281);
        areaCode.put("萍乡市", 360300);
        areaCode.put("市辖区", 360301);
        areaCode.put("安源区", 360302);
        areaCode.put("湘东区", 360313);
        areaCode.put("莲花县", 360321);
        areaCode.put("上栗县", 360322);
        areaCode.put("芦溪县", 360323);
        areaCode.put("九江市", 360400);
        areaCode.put("市辖区", 360401);
        areaCode.put("庐山区", 360402);
        areaCode.put("浔阳区", 360403);
        areaCode.put("九江县", 360421);
        areaCode.put("武宁县", 360423);
        areaCode.put("修水县", 360424);
        areaCode.put("永修县", 360425);
        areaCode.put("德安县", 360426);
        areaCode.put("星子县", 360427);
        areaCode.put("都昌县", 360428);
        areaCode.put("湖口县", 360429);
        areaCode.put("彭泽县", 360430);
        areaCode.put("瑞昌市", 360481);
        areaCode.put("新余市", 360500);
        areaCode.put("市辖区", 360501);
        areaCode.put("渝水区", 360502);
        areaCode.put("分宜县", 360521);
        areaCode.put("鹰潭市", 360600);
        areaCode.put("市辖区", 360601);
        areaCode.put("月湖区", 360602);
        areaCode.put("余江县", 360622);
        areaCode.put("贵溪市", 360681);
        areaCode.put("赣州市", 360700);
        areaCode.put("市辖区", 360701);
        areaCode.put("章贡区", 360702);
        areaCode.put("赣　县", 360721);
        areaCode.put("信丰县", 360722);
        areaCode.put("大余县", 360723);
        areaCode.put("上犹县", 360724);
        areaCode.put("崇义县", 360725);
        areaCode.put("安远县", 360726);
        areaCode.put("龙南县", 360727);
        areaCode.put("定南县", 360728);
        areaCode.put("全南县", 360729);
        areaCode.put("宁都县", 360730);
        areaCode.put("于都县", 360731);
        areaCode.put("兴国县", 360732);
        areaCode.put("会昌县", 360733);
        areaCode.put("寻乌县", 360734);
        areaCode.put("石城县", 360735);
        areaCode.put("瑞金市", 360781);
        areaCode.put("南康市", 360782);
        areaCode.put("吉安市", 360800);
        areaCode.put("市辖区", 360801);
        areaCode.put("吉州区", 360802);
        areaCode.put("青原区", 360803);
        areaCode.put("吉安县", 360821);
        areaCode.put("吉水县", 360822);
        areaCode.put("峡江县", 360823);
        areaCode.put("新干县", 360824);
        areaCode.put("永丰县", 360825);
        areaCode.put("泰和县", 360826);
        areaCode.put("遂川县", 360827);
        areaCode.put("万安县", 360828);
        areaCode.put("安福县", 360829);
        areaCode.put("永新县", 360830);
        areaCode.put("井冈山市", 360881);
        areaCode.put("宜春市", 360900);
        areaCode.put("市辖区", 360901);
        areaCode.put("袁州区", 360902);
        areaCode.put("奉新县", 360921);
        areaCode.put("万载县", 360922);
        areaCode.put("上高县", 360923);
        areaCode.put("宜丰县", 360924);
        areaCode.put("靖安县", 360925);
        areaCode.put("铜鼓县", 360926);
        areaCode.put("丰城市", 360981);
        areaCode.put("樟树市", 360982);
        areaCode.put("高安市", 360983);
        areaCode.put("抚州市", 361000);
        areaCode.put("市辖区", 361001);
        areaCode.put("临川区", 361002);
        areaCode.put("南城县", 361021);
        areaCode.put("黎川县", 361022);
        areaCode.put("南丰县", 361023);
        areaCode.put("崇仁县", 361024);
        areaCode.put("乐安县", 361025);
        areaCode.put("宜黄县", 361026);
        areaCode.put("金溪县", 361027);
        areaCode.put("资溪县", 361028);
        areaCode.put("东乡县", 361029);
        areaCode.put("广昌县", 361030);
        areaCode.put("上饶市", 361100);
        areaCode.put("市辖区", 361101);
        areaCode.put("信州区", 361102);
        areaCode.put("上饶县", 361121);
        areaCode.put("广丰县", 361122);
        areaCode.put("玉山县", 361123);
        areaCode.put("铅山县", 361124);
        areaCode.put("横峰县", 361125);
        areaCode.put("弋阳县", 361126);
        areaCode.put("余干县", 361127);
        areaCode.put("鄱阳县", 361128);
        areaCode.put("万年县", 361129);
        areaCode.put("婺源县", 361130);
        areaCode.put("德兴市", 361181);
        areaCode.put("山东省", 370000);
        areaCode.put("济南市", 370100);
        areaCode.put("市辖区", 370101);
        areaCode.put("历下区", 370102);
        areaCode.put("市中区", 370103);
        areaCode.put("槐荫区", 370104);
        areaCode.put("天桥区", 370105);
        areaCode.put("历城区", 370112);
        areaCode.put("长清区", 370113);
        areaCode.put("平阴县", 370124);
        areaCode.put("济阳县", 370125);
        areaCode.put("商河县", 370126);
        areaCode.put("章丘市", 370181);
        areaCode.put("青岛市", 370200);
        areaCode.put("市辖区", 370201);
        areaCode.put("市南区", 370202);
        areaCode.put("市北区", 370203);
        areaCode.put("四方区", 370205);
        areaCode.put("黄岛区", 370211);
        areaCode.put("崂山区", 370212);
        areaCode.put("李沧区", 370213);
        areaCode.put("城阳区", 370214);
        areaCode.put("胶州市", 370281);
        areaCode.put("即墨市", 370282);
        areaCode.put("平度市", 370283);
        areaCode.put("胶南市", 370284);
        areaCode.put("莱西市", 370285);
        areaCode.put("淄博市", 370300);
        areaCode.put("市辖区", 370301);
        areaCode.put("淄川区", 370302);
        areaCode.put("张店区", 370303);
        areaCode.put("博山区", 370304);
        areaCode.put("临淄区", 370305);
        areaCode.put("周村区", 370306);
        areaCode.put("桓台县", 370321);
        areaCode.put("高青县", 370322);
        areaCode.put("沂源县", 370323);
        areaCode.put("枣庄市", 370400);
        areaCode.put("市辖区", 370401);
        areaCode.put("市中区", 370402);
        areaCode.put("薛城区", 370403);
        areaCode.put("峄城区", 370404);
        areaCode.put("台儿庄区", 370405);
        areaCode.put("山亭区", 370406);
        areaCode.put("滕州市", 370481);
        areaCode.put("东营市", 370500);
        areaCode.put("市辖区", 370501);
        areaCode.put("东营区", 370502);
        areaCode.put("河口区", 370503);
        areaCode.put("垦利县", 370521);
        areaCode.put("利津县", 370522);
        areaCode.put("广饶县", 370523);
        areaCode.put("烟台市", 370600);
        areaCode.put("市辖区", 370601);
        areaCode.put("芝罘区", 370602);
        areaCode.put("福山区", 370611);
        areaCode.put("牟平区", 370612);
        areaCode.put("莱山区", 370613);
        areaCode.put("长岛县", 370634);
        areaCode.put("龙口市", 370681);
        areaCode.put("莱阳市", 370682);
        areaCode.put("莱州市", 370683);
        areaCode.put("蓬莱市", 370684);
        areaCode.put("招远市", 370685);
        areaCode.put("栖霞市", 370686);
        areaCode.put("海阳市", 370687);
        areaCode.put("潍坊市", 370700);
        areaCode.put("市辖区", 370701);
        areaCode.put("潍城区", 370702);
        areaCode.put("寒亭区", 370703);
        areaCode.put("坊子区", 370704);
        areaCode.put("奎文区", 370705);
        areaCode.put("临朐县", 370724);
        areaCode.put("昌乐县", 370725);
        areaCode.put("青州市", 370781);
        areaCode.put("诸城市", 370782);
        areaCode.put("寿光市", 370783);
        areaCode.put("安丘市", 370784);
        areaCode.put("高密市", 370785);
        areaCode.put("昌邑市", 370786);
        areaCode.put("济宁市", 370800);
        areaCode.put("市辖区", 370801);
        areaCode.put("市中区", 370802);
        areaCode.put("任城区", 370811);
        areaCode.put("微山县", 370826);
        areaCode.put("鱼台县", 370827);
        areaCode.put("金乡县", 370828);
        areaCode.put("嘉祥县", 370829);
        areaCode.put("汶上县", 370830);
        areaCode.put("泗水县", 370831);
        areaCode.put("梁山县", 370832);
        areaCode.put("曲阜市", 370881);
        areaCode.put("兖州市", 370882);
        areaCode.put("邹城市", 370883);
        areaCode.put("泰安市", 370900);
        areaCode.put("市辖区", 370901);
        areaCode.put("泰山区", 370902);
        areaCode.put("岱岳区", 370911);
        areaCode.put("宁阳县", 370921);
        areaCode.put("东平县", 370923);
        areaCode.put("新泰市", 370982);
        areaCode.put("肥城市", 370983);
        areaCode.put("威海市", 371000);
        areaCode.put("市辖区", 371001);
        areaCode.put("环翠区", 371002);
        areaCode.put("文登市", 371081);
        areaCode.put("荣成市", 371082);
        areaCode.put("乳山市", 371083);
        areaCode.put("日照市", 371100);
        areaCode.put("市辖区", 371101);
        areaCode.put("东港区", 371102);
        areaCode.put("岚山区", 371103);
        areaCode.put("五莲县", 371121);
        areaCode.put("莒　县", 371122);
        areaCode.put("莱芜市", 371200);
        areaCode.put("市辖区", 371201);
        areaCode.put("莱城区", 371202);
        areaCode.put("钢城区", 371203);
        areaCode.put("临沂市", 371300);
        areaCode.put("市辖区", 371301);
        areaCode.put("兰山区", 371302);
        areaCode.put("罗庄区", 371311);
        areaCode.put("河东区", 371312);
        areaCode.put("沂南县", 371321);
        areaCode.put("郯城县", 371322);
        areaCode.put("沂水县", 371323);
        areaCode.put("苍山县", 371324);
        areaCode.put("费　县", 371325);
        areaCode.put("平邑县", 371326);
        areaCode.put("莒南县", 371327);
        areaCode.put("蒙阴县", 371328);
        areaCode.put("临沭县", 371329);
        areaCode.put("德州市", 371400);
        areaCode.put("市辖区", 371401);
        areaCode.put("德城区", 371402);
        areaCode.put("陵　县", 371421);
        areaCode.put("宁津县", 371422);
        areaCode.put("庆云县", 371423);
        areaCode.put("临邑县", 371424);
        areaCode.put("齐河县", 371425);
        areaCode.put("平原县", 371426);
        areaCode.put("夏津县", 371427);
        areaCode.put("武城县", 371428);
        areaCode.put("乐陵市", 371481);
        areaCode.put("禹城市", 371482);
        areaCode.put("聊城市", 371500);
        areaCode.put("市辖区", 371501);
        areaCode.put("东昌府区", 371502);
        areaCode.put("阳谷县", 371521);
        areaCode.put("莘　县", 371522);
        areaCode.put("茌平县", 371523);
        areaCode.put("东阿县", 371524);
        areaCode.put("冠　县", 371525);
        areaCode.put("高唐县", 371526);
        areaCode.put("临清市", 371581);
        areaCode.put("滨州市", 371600);
        areaCode.put("市辖区", 371601);
        areaCode.put("滨城区", 371602);
        areaCode.put("惠民县", 371621);
        areaCode.put("阳信县", 371622);
        areaCode.put("无棣县", 371623);
        areaCode.put("沾化县", 371624);
        areaCode.put("博兴县", 371625);
        areaCode.put("邹平县", 371626);
        areaCode.put("菏泽市", 371700);
        areaCode.put("市辖区", 371701);
        areaCode.put("牡丹区", 371702);
        areaCode.put("曹　县", 371721);
        areaCode.put("单　县", 371722);
        areaCode.put("成武县", 371723);
        areaCode.put("巨野县", 371724);
        areaCode.put("郓城县", 371725);
        areaCode.put("鄄城县", 371726);
        areaCode.put("定陶县", 371727);
        areaCode.put("东明县", 371728);

    }

    /**
     * 生成身份证号码 ---- 地区 1-6位
     */
    public static int randomAreaCode() {
        int index = (int) (Math.random() * areaCode.size());
        Collection<Integer> values = areaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i < index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;
    }

    /**
     * 生成身份证号码 ---- 出生日期  7-14位
     *
     * @return
     */
    public static String randomBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 60) + 1950);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        String birth = builder.toString();
        return birth;
    }

    /**
     * 生成3位数  15-18位
     */
    public static String randomCode() {
        int code = (int) (Math.random() * 1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    /**
     * 验证18位身份证号码
     */
    public static char calcTrailingNumber(char[] chars) {
        if (chars.length < 17) {
            return ' ';
        }
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        return r[result % 11];
    }

    /**
     * 最终生成身份证
     *
     * @return
     */
    public static String getIdCard() {
        StringBuilder sb = new StringBuilder();
        sb.append(randomAreaCode());
        sb.append(randomBirthday());
        sb.append(randomCode());
        sb.append(calcTrailingNumber(sb.toString().toCharArray()));
        String IdNO = sb.toString();
        return IdNO;
    }

    /**
     * 根据身份证号生成年龄
     */
    public static int getAge(String IdNO) {
        int leh = IdNO.length();
        String dates = "";
        if (leh == 18) {
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            int u = Integer.parseInt(year) - Integer.parseInt(dates);
            return u;
        } else {
            dates = IdNO.substring(6, 8);
            return Integer.parseInt(dates);
        }

    }

    /**
     * 生成手机号码
     */
    public static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String thrid = String.valueOf(getNum(1, 9999) + 10000).substring(1);
        return first + second + thrid;
    }

    /**
     * 生成入网时间
     */
    public static String getInnetTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, (int) (Math.random() * 17) + 2000);
        calendar.set(Calendar.MONTH, (int) (Math.random() * 12));
        calendar.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(calendar.get(Calendar.YEAR) + "-");
        long month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month + "-");
        long date = calendar.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        String innettime = builder.toString();
        return innettime;
    }

    /**
     * 生成积分（0-9999）
     */
    public static int getPoint() {
        return getNum(0, 9999);
    }


    /**
     * 生成Email
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail(int lMin, int lMax) {
        int length = getNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
        return sb.toString();
    }


    /**
     * 生成地址
     *
     * @return
     */
    public static String getRoad() {
        int index = getNum(0, road.length - 1);
        String first = road[index];
        String second = String.valueOf(getNum(11, 150)) + "号";
        String third = "-" + getNum(1, 20) + "-" + getNum(1, 10);
        return first + second + third;
    }

    /**
     * 数据封装
     *
     * @return
     */
    public static String getInfo() {
        LinkedHashMap map = new LinkedHashMap();
        map.put("name", getChineseName());
        map.put("sex", name_sex);
        String IdNO = getIdCard();
        map.put("idcard", IdNO);
        map.put("age", getAge(IdNO));
        map.put("tel", getTel());
        map.put("innet", getInnetTime());
        map.put("point", getPoint());
        map.put("road", getRoad());
        map.put("email", getEmail(6, 9));
        String info = JSON.toJSONString(map);
        return info;
    }

    /**
     * 将生成的个人信息数据写入文件
     *
     * @param info     整合后的个人信息
     * @param filePath 个人信息日志文件的路径
     */
    public static void info2file(String info, String filePath) {
//        String filePath = "/root/bysj/data/personInfo.log";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, true);
            fos.write(info.getBytes());
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
     * 将个人信息日志文件发送http请求给nginx服务器
     *
     * @param srcpath 个人信息日志文件的路径
     * @param url     nginx服务器路径
     */
    public static void postJson(String srcpath, String url) {
//        String srcpath = "/root/bysj/data/personInfo.log";
//        String url = "http://192.168.23.11:80/kafka/bysj_person";
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
        String[] cmd = new String[] { "/bin/sh", "-c", "rm -rf " + args[0] };
        try{
            Process process = Runtime.getRuntime().exec(cmd);
        }catch(IOException e){
            System.out.println("删除文件失败！");
        }
        //遍历写到指定文件中
        for (int i = 0; i < getNum(100, 1000); i++) {
            info2file(getInfo(), args[0]);
        }
        //推送到nginx 并携带两个参数 日志文件路径和nginx服务器路径
        postJson(args[0], args[1]);
    }
}

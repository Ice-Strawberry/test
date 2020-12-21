package com.baidu.test;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * @Author: Wyy
     * @Date: 2020/12/21 10:53
     * @Description: 功能描述 数据库表生成word
     * @Param: []
     * @Return: void
     */
    @Test
    void contextLoads() {
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);
        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
                .fileOutputDir("D:/file")
                // 打开目录
                .openOutputDir(false)
                // 文件类型
//				.fileType(EngineFileType.HTML)
                .fileType(EngineFileType.WORD)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker).build();
        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version("1.0.3")
                .description("生成文档信息描述")
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(getProcessConfig())
                .build();
        // 执行生成
        new DocumentationExecute(config).execute();
    }

    /**
     * 配置想要生成的表+ 配置想要忽略的表
     *
     * @return 生成表配置
     */
    public static ProcessConfig getProcessConfig() {
        // 忽略表名
//		List<String> ignoreTableName = Arrays.asList("a", "test_group");
        // 忽略表前缀，如忽略a开头的数据库表
//		List<String> ignorePrefix = Arrays.asList("a", "t");
        // 忽略表后缀
//		List<String> ignoreSuffix = Arrays.asList("_test", "czb_");
        return ProcessConfig.builder()
                //根据名称指定表生成
                .designatedTableName(Arrays.asList(
                        "user"
                ))
                //根据表前缀生成
//				.designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
//				.designatedTableSuffix(new ArrayList<>())
                //忽略表名
//				.ignoreTableName(ignoreTableName)
                //忽略表前缀
//				.ignoreTablePrefix(ignorePrefix)
                //忽略表后缀
//				.ignoreTableSuffix(ignoreSuffix)
                .build();
    }

    @Test
    void getTime() {
        Map<String,Object> map =new HashMap<>();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String year = "2019";
        String jidu = "2";
        int yearNew = Integer.valueOf(year);
        int b = Integer.valueOf(jidu);
        int firstMonth = 3 * b - 2;
        int lastMonth = 3 * b;
        //当前季度第一月
        LocalDate todayDate = LocalDate.of(yearNew, firstMonth, 1);
        LocalDate firstDay = todayDate.with(TemporalAdjusters.firstDayOfMonth());
        String firstDayNew =firstDay.format(dateTimeFormatter);
        System.out.println("本季度第一天："+firstDay);
        System.out.println("本季度第一天："+firstDayNew);
        //当前季度最后月
        LocalDate lastDate = LocalDate.of(yearNew, lastMonth, 1);
		LocalDate lastDay =lastDate.with(TemporalAdjusters.lastDayOfMonth());
        String lastDayNew =lastDay.format(dateTimeFormatter);
		System.out.println("本季度最后一天："+lastDay);
        System.out.println("本季度最后一天："+lastDayNew);
        map.put("firstDay",firstDayNew);
        map.put("lastDay",lastDayNew);

        System.out.println("...............：");
    }
    @Test
    void  testt() throws ParseException {
        String date ="2020-10-12";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date datePar = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePar);

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        int mouth = datePar.getMonth()+1;
        if(mouth>=1 && mouth<=3){
            System.out.println(year + "年第一季度");
            return ;
        }else if(mouth>=4 && mouth<=6){
            System.out.println(year + "年第二季度");
        }else if(mouth>=7 && mouth<=9){
            System.out.println(year + "年第三季度");
        }else if(mouth>=10 && mouth<=12){
            System.out.println(year + "年第四季度");
        }
    }
    @Test
    Integer  testt1() throws ParseException {
        LocalDateTime localDateTime =LocalDateTime.now();
            switch (localDateTime.getMonthValue()){
                case 1:
                case 2:
                case 3:
                    return 1;
                case 4:
                case 5:
                case 6:
                    return 2;
                case 7:
                case 8:
                case 9:
                    return 3;
                case 10:
                case 11:
                case 12:
                    return 4;
                default:
                    return null;
            }
    }
}

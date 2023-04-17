package com.saika.videocloud;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class VideoCloudApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void generatorTest(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/community?useSSL=false&serverTimezone=UTC", "root", "root")
                .globalConfig(builder -> {
                    builder.author("lxd") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd")
                                                 //  此处更换自己绝对路径
                            .outputDir("F:\\java学习\\千峰\\项目\\第三点五阶段\\videoCloud\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.videocloud") // 设置父包名 例
                            .moduleName("") // 设置父包模块名 // 不用设
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "F:\\java学习\\千峰\\项目\\第三点五阶段\\yi\\newcoder\\src\\main\\resources\\mappers")); // 设置mapperXml生成路径
                })



                .strategyConfig(builder -> {
                    builder.addInclude("comment") // 设置需要生成的表名 此处写写数据库表名

                            .addTablePrefix("r_"); // 设置过滤表前缀,如果表有前缀 如 r_user 可以使用
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new VelocityTemplateEngine())
                .execute();


    }

}

-- 数据库
CREATE SCHEMA `resources` ;

-- 爬虫网页配置表
CREATE TABLE `resources`.`crawler_website` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `web_flag` VARCHAR(15) NULL COMMENT '网页标志，根据此字段判断执行哪个子类',
  `web_name` VARCHAR(20) NULL COMMENT '网页名称',
  `web_url` VARCHAR(45) NULL COMMENT '网页地址',
  `is_del` TINYINT(1) NULL COMMENT '是否删除',
  `is_stop` TINYINT(1) NULL COMMENT '是否停用',
  `crawler_nav` VARCHAR(200) NULL COMMENT '需要爬取的导航，用都号隔开',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_url`, `is_del`, `is_stop`, `crawler_nav`) VALUES ('java1234', 'java1234', 'http://www.java1234.com/', '0', '0', 'Java开源项目分享,Java毕业设计');
INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_url`, `is_del`, `is_stop`, `crawler_nav`) VALUES ('zuidaima', '最代码', 'http://www.zuidaima.com/', '0', '0', '代码');


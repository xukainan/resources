-- 数据库
CREATE SCHEMA `resources` ;

-- 爬虫网页配置表
CREATE TABLE `resources`.`crawler_website` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `web_flag` VARCHAR(15) NULL COMMENT '网页标志，根据此字段判断执行哪个子类',
  `web_name` VARCHAR(20) NULL COMMENT '网页名称',
  `web_conf` VARCHAR(1024) NULL COMMENT '网页配置',
  `is_del` TINYINT(1) NULL COMMENT '是否删除',
  `is_stop` TINYINT(1) NULL COMMENT '是否停用',
  `crawler_nav` VARCHAR(200) NULL COMMENT '需要爬取的导航，用都号隔开',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_conf`, `is_del`, `is_stop`) VALUES ('java1234', 'java1234', '{"Index":"http://www.java1234.com/","Nav":{"page":"index","isNav":"true","Ext":"Java开源项目分享,Java毕业设计","NavLabel":"div.top li > a[rel]","NavArrtibute":"href"},"More":{"page":"Index,Nav","isMore":"true","Ext":"","MoreLabel":"span.more a","MoreArrtibute":"href"},"List":{"page":"Index,Nav,More","isList":"true","Ext":"","ListLabel":"div.listbox a.title","ListArrtibute":"href"},"ByPage":{"page":"Index,List","isPage":"true","Ext":"下一页","PageLabel":"div.dede_pages a","PageArrtibute":"href"}}', '0', '0');
INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_conf`, `is_del`, `is_stop`) VALUES ('zuidaima', '最代码', '', '0', '0');


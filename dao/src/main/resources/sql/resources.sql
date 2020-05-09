-- 数据库
CREATE SCHEMA `resources` ;

-- 爬虫网页配置表
CREATE TABLE `resources`.`crawler_website` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `web_flag` VARCHAR(15) NULL COMMENT '网页标志，根据此字段判断执行哪个子类',
  `web_name` VARCHAR(20) NULL COMMENT '网页名称',
  `web_conf` VARCHAR(2048) NULL COMMENT '网页配置',
  `is_del` TINYINT(1) NULL COMMENT '是否删除',
  `is_stop` TINYINT(1) NULL COMMENT '是否停用',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
);

INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_conf`, `is_del`, `is_stop`) VALUES ('java1234', 'java1234', '{"Index":{"url":"http://www.java1234.com","text":"","child":[{"childName":"Nav","childpage":"Index,Nav"}]},"Nav":{"Ext":"Java开源项目分享,Java毕业设计","Label":"div.top li > a[rel]","Arrtibute":"href","text":"","url":"","child":[{"childName":"More","childpage":"Index,More"}]},"More":{"Ext":"","Label":"span.more a","Arrtibute":"href","text":"","url":"","child":[{"childName":"List","childpage":"Index,List"},{"childName":"ByPage","childpage":"Index,More,ByPage"}]},"List":{"Ext":"","Label":"div.listbox a.title","Arrtibute":"href","text":"","url":"","child":[{"childName":"Title","childpage":""},{"childName":"Link","childpage":""},{"childName":"LinkPw","childpage":""},{"childName":"Image","childpage":""}]},"ByPage":{"Ext":"下一页","Label":"div.dede_pages a","Arrtibute":"href","url":"","text":"","child":[{"childName":"List","childpage":"Index,List"},{"childName":"ByPage","childpage":"Index,More,ByPage"}]},"Title":{"Ext":"","Label":"div.title h2","Arrtibute":"","url":"","text":"true"},"Link":{"Ext":"","Label":"strong a","Arrtibute":"","url":"","text":"true"},"LinkPw":{"Ext":"","Label":"strong span","Arrtibute":"","url":"","text":"true"},"Image":{"Ext":"","Label":"div.content img","Arrtibute":"src","url":"","text":"true"}}', '0', '0');
INSERT INTO `resources`.`crawler_website` (`web_flag`, `web_name`, `web_conf`, `is_del`, `is_stop`) VALUES ('zuidaima', '最代码', '', '0', '0');


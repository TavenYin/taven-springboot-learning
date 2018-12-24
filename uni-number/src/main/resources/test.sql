CREATE TABLE `sys_sno` (
  `sCode` varchar(50) DEFAULT NULL COMMENT '编码',
  `sName` varchar(100) DEFAULT NULL COMMENT '名称',
  `sQz` varchar(50) DEFAULT NULL COMMENT '前缀',
  `sValue` varchar(80) DEFAULT NULL COMMENT '值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sys_sno`(`sCode`, `sName`, `sQz`, `sValue`) VALUES ('order', '订单', 'DD', NULL);

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSerialNo`(IN tsCode VARCHAR(50),OUT result VARCHAR(200) )
BEGIN

   DECLARE  tsValue  VARCHAR(50);
   DECLARE  tdToday  VARCHAR(20);
   DECLARE  nowdate  VARCHAR(20);
   DECLARE  tsQZ     VARCHAR(50);
   DECLARE t_error INTEGER DEFAULT 0;
   DECLARE CONTINUE HANDLER FOR SQLEXCEPTION SET t_error=1;
   START TRANSACTION;
     /* UPDATE sys_sno  SET sValue=sValue WHERE sCode=tsCode;  */
      SELECT sValue INTO tsValue  FROM sys_sno  WHERE sCode=tsCode FOR UPDATE;
      SELECT sQz INTO tsQZ FROM sys_sno WHERE sCode=tsCode ;
    -- 因子表中没有记录，插入初始值
      IF tsValue IS NULL  THEN
          SELECT CONCAT(DATE_FORMAT(NOW(),'%y%m'),'0001') INTO tsValue;
          UPDATE sys_sno SET sValue=tsValue WHERE sCode=tsCode ;
          SELECT CONCAT(tsQZ,tsValue) INTO result;
      ELSE
          SELECT  SUBSTRING(tsValue,1,4) INTO tdToday;
          SELECT  CONVERT(DATE_FORMAT(NOW(),'%y%m'),SIGNED) INTO nowdate;
          -- 判断年月是否需要更新
          IF tdToday = nowdate THEN
             SET  tsValue=CONVERT(tsValue,SIGNED) + 1;
          ELSE
             SELECT CONCAT(DATE_FORMAT(NOW(),'%y%m') ,'0001') INTO tsValue ;
          END IF;
          UPDATE sys_sno SET sValue =tsValue WHERE sCode=tsCode;
          SELECT CONCAT(tsQZ,tsValue) INTO result;
     END IF;

     IF t_error =1 THEN
       ROLLBACK;
       SET result = 'Error';
     ELSE
        COMMIT;
     END IF;
     SELECT  result ;
END;
# MotoZone
### 資策會期末專題
  
  
  
## SQL file location
https://drive.google.com/open?id=1vhaXLNxh8rh4SapbVNjgvTJnnC_ObZS1

## Install instruction:

>### project :

1.open Eclipse  
2.choose import -> import... -> Existing Project into workspace(in General folder) , click next  
3.choose MotoZone folder that you just unzip  
4.wait for all the progress finished   

>### database(use SSMS) :

1.open SSMS  
2.double click MotoZone_v1.6.sql that you just unzip  
3.run it (it will take some times, there are about 160000 rows data in this file)  

>### database connection setting :

1.open eclipse and find MotoZone project  
2.find context.xml in this route : src/main/webapp/META-INF/  
3.modify user and password (modify url if you don't use localhost)   

*connection url for azure SQL Server is not availible now, please use first block to connect, close second block  

>### config location :  

1.web.xml : src/main/webapp/WEB-INF/  
2.context.xml : src/main/webapp/META-INF/  
3.Spring config : config package  
4.Spring MVC config : config package  
5.Spring WebSocket config : config package  
6.View config : config.view package  
7.Hibernate config : src/main/resources  
8.EcPay config : payment_conf.xml  

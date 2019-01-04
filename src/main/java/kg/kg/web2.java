package kg.kg;



import java.io.IOException;  
import java.net.HttpURLConnection;  
import java.net.URL;  
  
  
  
/** 
* @ClassName：OpenBrowse 
* @Description：使用java代码打开关闭浏览器（指定的浏览器或者计算机默认的浏览器） 
*               获取网页字节流内容信息， 
* @date：2017年7月28日 
* 修改备注： 
*/  
public class web2 {  
      
      
    private static String urls = "https://www.jianshu.com/p/e499ce63ed72";  
      
      
    /** 
    * @Description:判断URL指定的页面是否存在 
    * @param URLName URL 
    * @date: 2017年8月2日 下午2:43:33 
    * @修改备注: 
    */  
    public static boolean urlExists(String urlStr) {  
        //urlStr = "http://blog.csdn.net/x1617044578/article/details/866863222";  
        try {  
               //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。  
               HttpURLConnection.setFollowRedirects(false);  
               //到 URL 所引用的远程对象的连接  
               HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();  
                //设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。  
               con.setRequestMethod("HEAD");  
               //从 HTTP 响应消息获取状态码  
               con.connect();  
               con.getHeaderFields();  
               if(con.getResponseCode() == HttpURLConnection.HTTP_OK){  
                   System.out.println("****存在");  
                   return true;  
               }else {  
                   System.out.println("****bu存在");  
                   return false;  
               }  
           } catch (Exception e) {  
               e.printStackTrace();  
               System.out.println("****异常");  
               return false;  
           }  
          
    }  
      
      
    /**  
    * @Description: 使用IE浏览器访问指定URL的页面 
    * @date: 2017年7月28日 下午2:29:49 
    * @修改备注:  
    */  
    public static void openIEBrowser(){  
         //启用cmd运行IE的方式来打开网址。  
        for (int i=0;i<10;i++) {  
            try {  
                Runtime.getRuntime().exec(urls);  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    public static void openchrome(){  
    	try {  
            ProcessBuilder proc = new ProcessBuilder(  
                    "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe",  
                    urls);  
            proc.start();  
        } catch (Exception e) {  
            System.out.println("Error executing progarm.");  
        }  
   }  
      
    /**  
    * @Description: 打开计算机默认的浏览器访问指定的url页面 
    * @date: 2017年7月28日 下午2:30:24 
    * @修改备注:  
    */  
    public static void openBrowse(){  
        if(java.awt.Desktop.isDesktopSupported()){  
            try{  
                //创建一个URI实例,注意不是URL  
                java.net.URI uri=java.net.URI.create(urls);  
                //获取当前系统桌面扩展  
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();  
                //判断系统桌面是否支持要执行的功能  
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){  
                    //获取系统默认浏览器打开链接  
                	for (int i=0;i<1;i++) {  
                		dp.browse(uri);  
                		
                	}
                }  
            }catch(java.lang.NullPointerException e){  
                //此为uri为空时抛出异常  
            }catch(java.io.IOException e){  
                //此为无法获取系统默认浏览器  
            }  
        }  
    }  
      
    /**  
    * @Description: 关闭浏览器（关闭指定的浏览器，在此处是强行关闭浏览器，强行杀死进程） 
    * @date: 2017年7月27日 下午8:31:34 
    * @修改备注:  
    */  
    public static void closeBrowse(){  
        try {  
//            Runtime.getRuntime().exec("taskkill /F /IM QQBrowser.exe");  
//            Runtime.getRuntime().exec("taskkill /F /IM iexplorer.exe");    
            Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");   
        } catch (Exception e) {  
            e.printStackTrace();  
        }    
          
    }  
      
  
    public static void main(String[] args) throws InterruptedException {  
    	
    	int i=0;
    	int j =0;
    	for(;;){
    		j++;
	        openchrome();
	        Thread.sleep(3000);
	        i++;
	        if(i==10){
	        	closeBrowse(); 
	        	i=0;
	        }
	        System.out.println("刷了："+j+"次");
    	}
          
    }  
  
}  

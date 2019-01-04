package kg.kg;



import java.io.IOException;  
import java.net.HttpURLConnection;  
import java.net.URL;  
  
  
  
/** 
* @ClassName��OpenBrowse 
* @Description��ʹ��java����򿪹ر��������ָ������������߼����Ĭ�ϵ�������� 
*               ��ȡ��ҳ�ֽ���������Ϣ�� 
* @date��2017��7��28�� 
* �޸ı�ע�� 
*/  
public class web2 {  
      
      
    private static String urls = "https://www.jianshu.com/p/e499ce63ed72";  
      
      
    /** 
    * @Description:�ж�URLָ����ҳ���Ƿ���� 
    * @param URLName URL 
    * @date: 2017��8��2�� ����2:43:33 
    * @�޸ı�ע: 
    */  
    public static boolean urlExists(String urlStr) {  
        //urlStr = "http://blog.csdn.net/x1617044578/article/details/866863222";  
        try {  
               //���ô����Ƿ�Ӧ���Զ�ִ�� HTTP �ض�����Ӧ����Ϊ 3xx �����󣩡�  
               HttpURLConnection.setFollowRedirects(false);  
               //�� URL �����õ�Զ�̶��������  
               HttpURLConnection con = (HttpURLConnection) new URL(urlStr).openConnection();  
                //���� URL ����ķ����� GET POST HEAD OPTIONS PUT DELETE TRACE ���Ϸ���֮һ�ǺϷ��ģ�����ȡ����Э������ơ�  
               con.setRequestMethod("HEAD");  
               //�� HTTP ��Ӧ��Ϣ��ȡ״̬��  
               con.connect();  
               con.getHeaderFields();  
               if(con.getResponseCode() == HttpURLConnection.HTTP_OK){  
                   System.out.println("****����");  
                   return true;  
               }else {  
                   System.out.println("****bu����");  
                   return false;  
               }  
           } catch (Exception e) {  
               e.printStackTrace();  
               System.out.println("****�쳣");  
               return false;  
           }  
          
    }  
      
      
    /**  
    * @Description: ʹ��IE���������ָ��URL��ҳ�� 
    * @date: 2017��7��28�� ����2:29:49 
    * @�޸ı�ע:  
    */  
    public static void openIEBrowser(){  
         //����cmd����IE�ķ�ʽ������ַ��  
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
    * @Description: �򿪼����Ĭ�ϵ����������ָ����urlҳ�� 
    * @date: 2017��7��28�� ����2:30:24 
    * @�޸ı�ע:  
    */  
    public static void openBrowse(){  
        if(java.awt.Desktop.isDesktopSupported()){  
            try{  
                //����һ��URIʵ��,ע�ⲻ��URL  
                java.net.URI uri=java.net.URI.create(urls);  
                //��ȡ��ǰϵͳ������չ  
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();  
                //�ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���  
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){  
                    //��ȡϵͳĬ�������������  
                	for (int i=0;i<1;i++) {  
                		dp.browse(uri);  
                		
                	}
                }  
            }catch(java.lang.NullPointerException e){  
                //��ΪuriΪ��ʱ�׳��쳣  
            }catch(java.io.IOException e){  
                //��Ϊ�޷���ȡϵͳĬ�������  
            }  
        }  
    }  
      
    /**  
    * @Description: �ر���������ر�ָ������������ڴ˴���ǿ�йر��������ǿ��ɱ�����̣� 
    * @date: 2017��7��27�� ����8:31:34 
    * @�޸ı�ע:  
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
	        System.out.println("ˢ�ˣ�"+j+"��");
    	}
          
    }  
  
}  

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class DownLoadImgs {

	public void downLoadImg(String nodeText) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 定义输入输出流
				InputStream input = null;
				// 执行，返回状态码
				int statusCode;
				HttpClient httpClient = new HttpClient();
				GetMethod getMethod = new GetMethod(nodeText);
				try {
					statusCode = httpClient.executeMethod(getMethod);
					
					InputStream in = getMethod.getResponseBodyAsStream();  
					
//					Date date=new Date();
//					DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					String time=format.format(date);
					// 通过对URL的得到文件名
					String filename = System.currentTimeMillis() + ".jpg";
		             
		            FileOutputStream out = new FileOutputStream(new File("/Users/zeratel3000/Documents/workspace/SpiderTest/img/"+filename));  
		             
		            byte[] b = new byte[2048];  
		            int len = 0;  
		            while((len=in.read(b))!= -1){  
		                out.write(b,0,len);  
		            }  
		            in.close();  
		            out.close();  

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					getMethod.releaseConnection();
				}
				
			}
		}).start();
	}

}

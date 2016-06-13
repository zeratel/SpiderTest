import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class Spider {
	private static HttpClient httpClient = new HttpClient();

	private static ArrayList<String> imgs = new ArrayList<String>();
	/**
	 * @param path
	 *            目标网页的链接
	 * @return 返回布尔值，表示是否正常下载目标页面
	 * @throws Exception
	 *             读取网页流或写入本地文件流的IO异常
	 */
	public static ArrayList<String> downloadPage(String path) throws Exception {
		// 定义输入输出流
		InputStream input = null;
		// 得到 post 方法
		GetMethod getMethod = new GetMethod(path);
		// 执行，返回状态码
		int statusCode = httpClient.executeMethod(getMethod);
		// 针对状态码进行处理
		// 简单起见，只处理返回值为 200 的状态码
		if (statusCode == HttpStatus.SC_OK) {
			input = getMethod.getResponseBodyAsStream();
			
			ArrayList<String> a = new ArrayList<String>();
			
			//解析html获取img的url并存入,已经分离出去
			
			
			// 通过对URL的得到文件名
//			String filename = path.substring(path.lastIndexOf('/') + 1)
//					+ ".html";
//			// 获得文件输出流
//			output = new FileOutputStream(filename);
//			// 输出到文件
//			int tempByte = -1;
//			while ((tempByte = input.read()) > 0) {
//				output.write(tempByte);
//			}
			// 关闭输入流
			if (input != null) {
				input.close();
			}
			return a;
		}
		return null;
	}
	
	public static ArrayList<String> getImgs() {
		return imgs;
	}
	
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		Main m = new Main();
		System.out.println("Spider Start!");
		m.startCrawling("http://gank.io/");
	}

	void startCrawling(String webUrl) {
		SpiderQueue.addUnvisitedUrl(webUrl);
		// 未访问为空则跳出
		while (!SpiderQueue.unVisitedUrlsEmpty() && SpiderQueue.getVisitedUrlNum() < 500) {
			
			//取出未访问队列中的一个url进行分析
			String tempUrl = (String) SpiderQueue.getUnVisitedUrl().deQueue();
			//把未访问的url加入到访问队列中
			SpiderQueue.addVisitedUrl(tempUrl);
			
			HtmlAnalysis ha = new HtmlAnalysis();
			//进行分析并返回其中新的url队列
			SpiderQueue.saveUnvisitedUrls(ha.getNewUrls(tempUrl));

		}
	}

	void spiderTest2() {
		try {
			// 抓取百度首页，输出
			Spider.downloadPage("http://www.baidu.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void spiderTest() {
		// 定义即将访问的链接
		String url = "http://www.baidu.com";
		// 定义一个字符串用来存储网页内容
		String result = "";
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			// 开始实际的连接
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line;
			while ((line = in.readLine()) != null) {
				// 遍历抓取到的每一行并将其存储到result里面
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result = result.replaceAll(">", ">\r\n");

		// Pattern pattern = Pattern.compile("href=\"(.+?)\"");
		Pattern pattern = Pattern.compile("src=\"(.+?)\"");
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(result);
		// 如果找到了
		if (matcher.find()) {
			System.out.println(matcher.group(0));
		}

		File f = new File("./baidu.html");
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(f);
			fo.write(result.getBytes());
			fo.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fo != null) {
					fo.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}

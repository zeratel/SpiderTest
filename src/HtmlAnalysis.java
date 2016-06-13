import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * 
 * @author zeratel3000
 *
 *         这个类应该是用来解析html中的img标签，获取其中的url，创建任务，并调用DownLoad类下载的
 *
 */
public class HtmlAnalysis {
	
	private int imgNum;

	// 返回其中含有gank.io_url的链接
	public ArrayList<String> getNewUrls(String url) {
		ArrayList<String> a = new ArrayList<String>();
		try {
			// a = Spider.downloadPage(url);
			a = parserHtml(url);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return a;
	}

	// 分析页面返回链接地址
	public ArrayList<String> parserHtml(String path) {
		ArrayList<String> links = new ArrayList<String>();

		Parser parser;
		try {
			// 1、构造一个Parser，并设置相关的属性
			parser = new Parser(path);
			parser.setEncoding("gb2312");

			// 2.1、自定义一个Filter，用于过滤<Frame >标签，然后取得标签中的src属性值
			// NodeFilter frameNodeFilter = new NodeFilter() {
			// @Override
			// public boolean accept(Node node) {
			// if (node.getText().startsWith("frame src=")) {
			// return true;
			// } else {
			// return false;
			// }
			// }
			// };

			// 2.2、创建第二个Filter，过滤<a>标签
			// NodeFilter aNodeFilter = new NodeClassFilter(LinkTag.class);
			NodeFilter aNodeFilter = new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					if (node.getText().startsWith("a href=\"/2")) {
						return true;
					} else {
						return false;
					}
				}
			};
			
			NodeFilter imgNodeFilter = new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					if (node.getText().startsWith("img alt=\"\" src=\"")) {
						return true;
					} else {
						return false;
					}
				}
			};
			
			//分析图片
			

			// 2.3、净土上述2个Filter形成一个组合逻辑Filter。
			// OrFilter linkFilter = new OrFilter(frameNodeFilter, aNodeFilter);
			 OrFilter linkFilter = new OrFilter(aNodeFilter,imgNodeFilter);

			// 3、使用parser根据filter来取得所有符合条件的节点
			NodeList nodeList = parser.extractAllNodesThatMatch(linkFilter);

			// 4、对取得的Node进行处理
			for (int i = 0; i < nodeList.size(); i++) {
				Node node = nodeList.elementAt(i);
				String linkURL = "";
				// 如果链接类型为<a />
				if (node instanceof LinkTag) {
					LinkTag link = (LinkTag) node;
					linkURL = link.getLink();
					links.add(linkURL);
				}else{
					//如果类型是img
					String nodeText = node.getText();
					System.out.println("HtmlAnalysis.parserHtml.nodeText:" + nodeText);
					
					int beginPosition = nodeText.indexOf("src=\"");
					nodeText = nodeText.substring(beginPosition+5);
					int endPosition = nodeText.indexOf("\"");
					
					nodeText = nodeText.substring(0, endPosition);
					System.out.println("HtmlAnalysis.parserHtml.nodeText:" + nodeText);
					
//					imgNum++;
//					System.out.println("HtmlAnalysis.parserHtml.imgNum:" + imgNum);
					DownLoadImgs dli = new DownLoadImgs();
					dli.downLoadImg(nodeText);
				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return links;
	}

}

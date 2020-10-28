package demo.gjz.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * <pre>
 * http客户端demo
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/10/28 20:41
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HttpClientDemo {

    private static final Logger logger = Logger.getLogger("HttpClientDemo");

    public static void main(String[] args) {
        String url = "http://localhost:8801";
        httpGetNoParams(url);
    }


    /**
     * http Get请求
     * 无参数
     * @return
     */
    private static void httpGetNoParams(String url){

        // 创建可关闭Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // http get 请求
        HttpGet httpGet = new HttpGet(url);

        //http 请求响应
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK ==  httpResponse.getStatusLine().getStatusCode()) {
                //响应体
                HttpEntity httpEntity = httpResponse.getEntity();
                logger.info("Http 请求成功，响应长度：" + httpEntity.getContentLength());
                logger.info("请求返回结果：" + EntityUtils.toString(httpEntity));
            }else {
                logger.info("Http请求失败，状态码为：" + httpResponse.getStatusLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }

                if (httpResponse != null) {
                    httpResponse.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}


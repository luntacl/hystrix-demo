package com.example.demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.LoggerFactory;

/**
 * Created by liutao on 2017/6/19.
 */
public class HttpCallCommand extends HystrixCommand<Response> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpCallCommand.class);
    private final String url;

    public HttpCallCommand(Setter setter, String url) {
        super(setter);
        this.url = url;
    }

    @Override
    protected Response run() throws Exception {
        logger.info("Execution of Command: url={}", url);
        Request request = new Request.Builder().url(url).build();
        return new OkHttpClient().newCall(request).execute();
    }

    @Override
    public Response getFallback() {
        logger.error("服务调用失败,service:'{}'");
        return null;
    }
}


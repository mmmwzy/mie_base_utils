package com.mie.base.utils.pdf;

import com.mie.base.utils.SpringContextHolder;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by WangRicky on 2018/5/17.
 */
public abstract class HtmlToPdfUtils {
    private static Logger logger = LoggerFactory.getLogger(HtmlToPdfUtils.class);
    public static final String PAGE_SIZE_A4 = "A4";
    public static final String PAGE_SIZE_A5 = "A5";
    public static String WXHTMLTOPDF_BIN_PATH = "/usr/local/bin/wkhtmltopdf";

    public HtmlToPdfUtils() {
    }

    public static void generatePdf(String srcHtml, File destPdf) throws IOException, InterruptedException {
        generatePdf(srcHtml, destPdf, "A4");
    }

    public static void generatePdf(String srcHtml, File destPdf, String pageSize) throws IOException, InterruptedException {
        generatePdf(srcHtml, destPdf, pageSize, true);
    }

    public static void generatePdf(String srcHtml, File destPdf, String pageSize, boolean isVertical) throws IOException, InterruptedException {
        generatePdf(srcHtml, destPdf, pageSize, isVertical, (String)null);
    }

    public static void generatePdf(String srcHtml, File destPdf, String pageSize, boolean isVertical, String title) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap();
        params.put("-B", "0");
        params.put("-L", "0");
        params.put("-R", "0");
        params.put("-T", "0");
        params.put("--disable-smart-shrinking", "");
        params.put("--print-media-type", "");
        params.put("--dpi", "250");
        if (!isVertical) {
            params.put("-O ", "Landscape");
        }

        if (StringUtils.isNotBlank(pageSize)) {
            params.put("--page-size", pageSize);
        }

        if (StringUtils.isNotBlank(title)) {
            params.put("--title", title);
        }

        generatePdfWithWxHtmltopdf(srcHtml, destPdf, params);
    }

    public static void generatePdf(String srcHtml, File destPdf, float width, float height, String title) throws IOException, InterruptedException {
        Map<String, String> params = new HashMap();
        params.put("-B", "0");
        params.put("-L", "0");
        params.put("-R", "0");
        params.put("-T", "0");
        params.put("--disable-smart-shrinking", "");
        params.put("--print-media-type", "");
        params.put("--dpi", "250");
        params.put("--page-height", String.valueOf(height));
        params.put("--page-width", String.valueOf(width));
        if (StringUtils.isNotBlank(title)) {
            params.put("--title", title);
        }

        generatePdfWithWxHtmltopdf(srcHtml, destPdf, params);
    }

    public static void generatePdfWithWxHtmltopdf(String html, File file, Map<String, String> params) throws IOException, InterruptedException {
        String tempDirStr = HtmlToPdfUtils.class.getClassLoader().getResource("").getPath() + "tempDir";
        File tempDir = new File(tempDirStr);
        FileUtils.forceMkdir(tempDir);
        File htmlFile = new File(tempDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".html");
        FileUtils.touch(htmlFile);
        FileUtils.writeByteArrayToFile(htmlFile, html.getBytes());
        if (!file.exists()) {
            FileUtils.touch(file);
        }

        StringBuffer cmd = new StringBuffer();
        cmd.append(WXHTMLTOPDF_BIN_PATH).append(" ");
        addParams(cmd, params);
        cmd.append(htmlFile.getAbsolutePath()).append(" ").append(file.getAbsolutePath());
        logger.info("run cmd:" + cmd);
        Process proc = Runtime.getRuntime().exec(cmd.toString());
        runCmdLog(proc.getInputStream(), proc.getErrorStream());
        proc.waitFor();
        FileUtils.forceDelete(htmlFile);
    }

    private static void addParams(StringBuffer cmd, Map<String, String> params) {
        if (!MapUtils.isEmpty(params)) {
            Iterator keyIterator = params.keySet().iterator();

            while(keyIterator.hasNext()) {
                String key = (String)keyIterator.next();
                String value = (String)params.get(key);
                cmd.append(key).append(" ").append(value).append(" ");
            }

        }
    }

    private static void runCmdLog(InputStream outputStream, InputStream errorStream) {
        HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(outputStream);
        HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(errorStream);
        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) SpringContextHolder.getOneBean(ThreadPoolTaskExecutor.class);
        if (executor != null) {
            executor.execute(output);
            executor.execute(error);
        } else {
            Thread thread1 = new Thread(output);
            thread1.start();
            Thread thread2 = new Thread(error);
            thread2.start();
        }

    }

    public static void main(String[] args) {
        String tempDir = HtmlToPdfUtils.class.getClassLoader().getResource("") + "tempDir";
        String htmlFilePath = tempDir + "/" + System.currentTimeMillis() + ".html";
        File htmlFile = new File(htmlFilePath);
        System.out.println(htmlFile.getAbsolutePath());
    }

    static {
        try {
            String binPath = ((Properties)SpringContextHolder.getBean("sysConfig")).getProperty("wkhtmltopdf.bin.path");
            if (StringUtils.isNotBlank(binPath)) {
                File binFile = new File(binPath);
                if (binFile.exists()) {
                    WXHTMLTOPDF_BIN_PATH = binPath;
                } else {
                    logger.warn("找不到配置文件中wkhtml的可执行文件，配置的路径：" + binPath);
                }
            }
        } catch (Exception var2) {
            logger.error("设置wkhtml的可执行文件路径失败", var2);
        }

    }
}

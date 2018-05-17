package com.mie.base.utils.pdf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * Created by WangRicky on 2018/5/17.
 */
public class HtmlToPdfInterceptor implements Runnable {
    private InputStream is;

    public HtmlToPdfInterceptor(InputStream is) {
        this.is = is;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(this.is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while((line = br.readLine()) != null) {
                System.out.println(line.toString());
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}

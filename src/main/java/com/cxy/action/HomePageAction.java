package com.cxy.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/15.
 */
public class HomePageAction extends BaseAction{
    private InputStream excelStram;

    public InputStream getExcelStram() {
        return excelStram;
    }

    public void setExcelStram(InputStream excelStram) {
        this.excelStram = excelStram;
    }

    public String exportExcel(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("姓名").append("\t").append("手机号").append("\t").append("git账号").append("\t").append("邮箱").append("\n");
        stringBuffer.append("cxy").append("\t").append("18375326969").append("\t").append("cxydy0816").append("\t").append("cxydy@qq.com").append("\n");
        stringBuffer.append("cxy").append("\t").append("18375326969").append("\t").append("cxydy0816").append("\t").append("cxydy@qq.com").append("\n");
        stringBuffer.append("cxy").append("\t").append("18375326969").append("\t").append("cxydy0816").append("\t").append("cxydy@qq.com").append("\n");
        stringBuffer.append("cxy").append("\t").append("18375326969").append("\t").append("cxydy0816").append("\t").append("cxydy@qq.com").append("\n");
        String s = stringBuffer.toString();

        excelStram = new ByteArrayInputStream(s.getBytes(),0,s.getBytes().length);
        return "excel";
    }
}

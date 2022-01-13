package dev.hjjoo.community;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MyCustomJstlTag extends SimpleTagSupport {
    //멤버필드는 선언만!
    private String idVal;
    private String classVal;
    private int iuser;//없으면 0
    private String imgIdVal;
    private String profileImgVal;//없으면 null 처리

    //NULL 을 피하기 위해서
    public MyCustomJstlTag() {
        this.idVal = "";
        this.classVal = "";
        this.imgIdVal = "";
    }

    //SETTER 만 추가
    public void setIdVal(String idVal) {
        this.idVal = idVal;
    }

    public void setClassVal(String classVal) {
        this.classVal = classVal;
    }

    public void setIuser(int iuser) {
        this.iuser = iuser;
    }

    public void setImgIdVal(String imgIdVal) { this.imgIdVal = imgIdVal; }

    public void setProfileImgVal(String profileImgVal) {
        this.profileImgVal = profileImgVal;
    }

    @Override
    public void doTag() throws JspException {
        try {
//            String fixProfileImgVal = "/res/img/defaultProfile.png";
//            if(profileImgVal != null) {
//                fixProfileImgVal = String.format("/images/user/%s/%s", iuser, profileImgVal);
//            }
            String fixProfileImagVal = "".equals(profileImgVal) ? "/res/img/defaultProfile.png" :  String.format("/images/user/%s/%s", iuser, profileImgVal);
            String result = String.format("<div id=\"%s\" class=\"%s\"><img src=\"%s\" id=\"%s\"></div>", idVal, classVal, fixProfileImagVal, imgIdVal);

            getJspContext().getOut().write(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SkipPageException("Exception in MyCustomJstlTag");
        }
    }
}
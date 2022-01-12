package dev.hjjoo.community;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class MyFileUtils {

    //폴더 만들기
    public void makeFolders(String path) {//경로에
        File folder = new File(path);
        if (!folder.exists()) {//폴더가 없다면
            folder.mkdirs();//폴더 만들기
        }
    }

    //폴더 삭제
    public void delFolderFiles(String path, boolean isDelFolder) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] fileArr = file.listFiles();//파일객체로 만든 배열로 넘어감
            for (File f : fileArr) {//순서대로 달라
                if (f.isDirectory()) {// 재귀처리
                    delFolderFiles(f.getPath(), true);
                } else {
                    f.delete();
                }
            }
        }
        if (isDelFolder) {file.delete();}
    }

    //랜덤파일명 만들기(순수하게?)
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();//랜덤한 문자열
    }

    //랜덤한 파일명 만들면서 확장자 구하는
    public String getRandomFileNm(String fileNm) {
        return getRandomFileNm() + getExt(fileNm);
    }

    //확장자 구하기(.포함, ex. .jpg)
    public String getExt(String fileNm) {
        int lastIdx = fileNm.lastIndexOf(".");
        return fileNm.substring(lastIdx);

        //return fileNm.substring(fileNm.lastIndexOf("."));

        //fileNm.indexOf('.');
        //fileNm.lastIndexOf('.');
        //fileNm.substr(x,y); //x는 index y 는 길이
        //fileNm.substring(x,y); //x index, y index;
        //fileNm.substring(x); //x index 부터 끝까지
    }

    //파일 저장 > 저장된 랜덤 파일명 리턴
    public String saveFile(String path, MultipartFile mf) {
        makeFolders(path);
        String randomFileNm = getRandomFileNm(mf.getOriginalFilename());
        File targetFile = new File(path, randomFileNm);// , 주면 자동으로 / 준다, path/randomFileNm 해준다
        try {
            mf.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return randomFileNm;
    }
}

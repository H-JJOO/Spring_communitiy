package dev.hjjoo.community.user;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.MyFileUtils;
import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private MyFileUtils myFileUtils;

    public int login(UserEntity entity) {
        UserEntity dbUser = null;
        try{
            dbUser = mapper.selUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;//에러
        }
        if (dbUser == null) {
            return 2;//아이디 없음
        }
        if (!BCrypt.checkpw(entity.getUpw(),dbUser.getUpw())) {
            return 3;//비번 틀림
        }
        dbUser.setUpw(null);
        dbUser.setRdt(null);
        dbUser.setMdt(null);
        userUtils.setLoginUser(dbUser);//세션에 박아줌
        return 1;//로그인 성공
    }
    
    public int join(UserEntity entity) {//uid, upw, nm, gender
        UserEntity copyEntity = new UserEntity();//객체 복사
        BeanUtils.copyProperties(entity, copyEntity);//깊은 복사! entity 를 copyEntity 로

        //비밀번호 암호화
        String hashPw = BCrypt.hashpw(entity.getUpw(), BCrypt.gensalt());
        copyEntity.setUpw(hashPw);//복사된 거에 비밀번호 암호화
        return mapper.insUser(copyEntity);
    }
    
    //아이디가 없으면 리턴 1, 있으면 리턴 0
    public int idChk(String uid) {
        UserEntity entity = new UserEntity();
        entity.setUid(uid);

        UserEntity result = mapper.selUser(entity);

        return result == null ? 1 : 0;
    }

    //이미지 업로드 처리 및 저장 파일명 리턴
    public String uploadProfileImg(MultipartFile mf) {
        if (mf == null) {return null;}
        final String PATH = Const.UPLOAD_IMG_PATH + "/user/" + userUtils.getLoginUserPk();
        String fileNm = myFileUtils.saveFile(PATH, mf);
        System.out.println("fileNm : " + fileNm);
        if (fileNm == null) { return null; }

        //파일명을 t_user 테이블에 update
        UserEntity entity = new UserEntity();
        entity.setIuser(userUtils.getLoginUserPk());
        entity.setProfileimg(fileNm);

        mapper.updUser(entity);

        return fileNm;
    }
}

package dev.hjjoo.community.user;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.MyFileUtils;
import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.UserDto;
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

    //이미지 업로드(oldFile 삭제) 처리 및 저장 파일명 리턴
    public String uploadProfileImg(MultipartFile mf) {
        if (mf == null) {return null;}

        UserEntity loginUser = userUtils.getLoginUser();//session 값 쓰기위해서

        final String PATH = Const.UPLOAD_IMG_PATH + "/user/" + loginUser.getIuser();//경로 C:/upload/images/user/2
        String fileNm = myFileUtils.saveFile(PATH, mf);//파일저장
        System.out.println("fileNm : " + fileNm);
        if (fileNm == null) { return null; }

        UserEntity entity = new UserEntity();//UserEntity 객체 생성
        entity.setIuser(loginUser.getIuser());//UserEntity 의 iuser 값에 로그인한 유저의 iuser 값 입력

        //기존 파일명
        String oldFilePath = PATH + "/" + userUtils.getLoginUser().getProfileimg();//C:/upload/images/user/2/프로필사진값?
        myFileUtils.delFile(oldFilePath);//기존 파일 삭제

        //파일명을 t_user 테이블에 update
        entity.setProfileimg(fileNm);//파일명 set
        mapper.updUser(entity);//업데이트

        //세션 프로필 파일명을 수정해 준다.
        loginUser.setProfileimg(fileNm);

        return fileNm;

    }

    public int changePassword(UserDto dto) {
        dto.setIuser(userUtils.getLoginUserPk());
        UserEntity dbUser = mapper.selUser(dto);
        if(!BCrypt.checkpw(dto.getCurrentupw(), dbUser.getUpw())) {
            return 2; //현재비밀번호 다름
        }
        String hashedPw = BCrypt.hashpw(dto.getUpw(), BCrypt.gensalt());
        dto.setUpw(hashedPw);
        return mapper.updUser(dto);
    }
}

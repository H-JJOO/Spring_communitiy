package dev.hjjoo.community.user;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/login")
    public void login() {
    }

    @PostMapping("/login")
    public String login(UserEntity entity, RedirectAttributes reAttr) {
        int result = service.login(entity);
        if (result != 1) {
            reAttr.addFlashAttribute(Const.TRY_LOGIN, entity);//session 에 값  넣어서 다시 불러와서 request 에 넣어주고 session 에 값 바로 지움
            switch (result) {
                case 0:
                    reAttr.addFlashAttribute(Const.MSG, Const.ERR_1);
                    break;
                case 2:
                    reAttr.addFlashAttribute(Const.MSG, Const.ERR_2);
                    break;
                case 3:
                    reAttr.addFlashAttribute(Const.MSG, Const.ERR_3);
                    break;
            }
            return "redirect:/user/login";
        }
        return "redirect:/board/list/1";
    }

    @GetMapping("/logout")
    public String logout(HttpSession hs) {
        hs.invalidate();
        return "redirect:/user/login";
    }

    @GetMapping("/join")
    public void join() {
    }

    @PostMapping("/join")
    public String joinProc(UserEntity entity, RedirectAttributes reAttr) {
        int result = service.join(entity);
        if (result == 0) {
            reAttr.addFlashAttribute(Const.MSG, Const.ERR_4);
            return "redirect:/user/join";
        }
        //회원가입 성공하면 로그인 처리
        service.login(entity);
        return "redirect:/board/list";
    }

    @GetMapping("/idChk/{uid}")//@PathVariable 변수명이랑 이름 맞춰야
    @ResponseBody//return 이 Json 으로 된다, 받을때는 RequestBody
    public Map<String, Integer> idChk(@PathVariable String uid) {
        Map<String, Integer> res = new HashMap();
        res.put("result", service.idChk(uid));
        return res;
        // {"result" : 1} 문자열을 Json 형태로 변환
    }

    @GetMapping("/mypage/profile")
    public void mypageProfile() {}

    @ResponseBody
    @PostMapping("/mypage/profile")
    public Map<String, String> mypageProfileProc(MultipartFile profileimg) {
        String filrNm = service.uploadProfileImg(profileimg);
        System.out.println("fileName : " + profileimg.getOriginalFilename());
        Map<String, String> result = new HashMap<>();
        result.put("result", filrNm);
        return result;
    }


}

package dev.hjjoo.community.board;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.model.BoardDto;
import dev.hjjoo.community.model.BoardEntity;
import dev.hjjoo.community.model.BoardPrevNextVo;
import dev.hjjoo.community.model.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService service;

    @GetMapping("/list/{icategory}")
    public String list(@PathVariable int icategory, BoardDto dto, Model model) {
        model.addAttribute(Const.I_CATEGORY, icategory);
        model.addAttribute(Const.LIST, service.selBoardList(dto));
        dto.setIcategory(icategory);
        return "board/list";//본인이 본인거 여는거, 여기서 리다이렉트하면 무한루프
    }

    @GetMapping("/write")
    public void write() {}

    @PostMapping("/write")
    public String writeProc(BoardEntity entity, RedirectAttributes reAttr) {
        int result = service.insBoard(entity);
        if (result == 0) {
            reAttr.addFlashAttribute(Const.MSG, Const.ERR_5);
            reAttr.addAttribute(Const.DATA, entity);
            return "redirect:/board/write";
        }
        return "redirect:/board/list/" + entity.getIcategory();
    }

    @GetMapping("/detail")
    public void detail(BoardDto dto, Model model, HttpServletRequest req) {
        //유저 IP 를 가져왔을 때 IPv6문제, IPv4로 전환. (이클립스) - 퍼옴
        // TODO : https://postitforhooney.tistory.com/entry/WEBWAS-%EC%9C%A0%EC%A0%80-IP-%EB%A5%BC-%EA%B0%80%EC%A0%B8%EC%99%94%EC%9D%84-%EB%95%8C-IPv6%EB%AC%B8%EC%A0%9C-IPv4%EB%A1%9C-%EC%A0%84%ED%99%98-%EC%9D%B4%ED%81%B4%EB%A6%BD%EC%8A%A4
        String lastIp = req.getHeader("X-FORWARDED-FOR");
        if (lastIp == null) {
            lastIp = req.getRemoteAddr();
        }
        System.out.println("lastIp : " + lastIp);
        dto.setLastip(lastIp);
        BoardVo vo = service.selBoard(dto);
        BoardPrevNextVo pnVo = service.selPrevNext(vo);
        model.addAttribute(Const.DATA, vo);
        model.addAttribute(Const.PREV_NEXT, pnVo);

    }



    @GetMapping("/mod")
    public String mod(BoardDto dto, Model model)  {
        model.addAttribute(Const.DATA, service.selBoard(dto));
        return "board/write";
    }

    @PostMapping("/mod")
    public String modProc(BoardEntity entity, RedirectAttributes reAttr) {
        int result = service.updBoard(entity);
        if (result == 0) {
            reAttr.addFlashAttribute(Const.MSG, Const.ERR_6 );
            reAttr.addAttribute(Const.DATA, entity);
            return "redirect:/board/write";
        }
        return "redirect:/board/detail?iboard=" + entity.getIboard();
    }

    @GetMapping("/del")
    public String delProc(BoardEntity entity) {
        int result = service.delBoard(entity);
        return "redirect:/board/list/" + entity.getIcategory();
    }
}

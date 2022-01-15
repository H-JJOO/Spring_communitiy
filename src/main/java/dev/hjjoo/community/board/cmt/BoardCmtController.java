package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.model.BoardCmtEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController//모든 리턴을 JSON 형태로 한다.
@RequestMapping("/board/cmt")
public class BoardCmtController {

    @Autowired private BoardCmtService service;

    @PostMapping
    public Map<String, Integer> insBoardCmt(@RequestBody BoardCmtEntity entity) {//JSON 으로 넘어오는 것을 컴버팅 해준다. (JSON 으로 넘어온다고 알려주는 역할, JSON 으로 넘어오면 반드시 있어야할 어노테이션!)
        System.out.println(entity);
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.insBoardCmt(entity));
        return result;
    }
    //이거아니야 ㅠㅠ
    @GetMapping
    public String list(BoardCmtEntity entity, Model model)  {
        model.addAttribute(Const.LIST, service.selBoardCmtList(entity));
        return "board/cmt";
    }
}

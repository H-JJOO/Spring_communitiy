package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.model.BoardCmtEntity;
import dev.hjjoo.community.model.BoardCmtVo;
import dev.hjjoo.community.model.BoardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController//모든 리턴을 JSON 형태로 한다.
@RequestMapping("/board/cmt")
public class BoardCmtController {

    @Autowired private BoardCmtService service;

    @PostMapping
    public Map<String, Integer> insBoardCmt(@RequestBody BoardCmtEntity entity) {//JSON 으로 넘어오는 것을 컴버팅 해준다. (JSON 으로 넘어온다고 알려주는 역할, JSON 으로 넘어오면 반드시 있어야할 어노테이션!)[C]
        System.out.println(entity);//iboard, ctnt
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.insBoardCmt(entity));
        return result;
    }

    @GetMapping("/{iboard}")//쿼리스트링방식X, [R]
    public List<BoardCmtVo> selBoardCmtList(@PathVariable int iboard)  {
        System.out.println("iboard : " + iboard);
        return service.selBoardCmtList(iboard);
    }

    @PutMapping//[U]
    public Map<String, Integer> updBoardCmt(@RequestBody BoardCmtEntity entity) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.updBoardCmt(entity));
        return result;
    }

    @DeleteMapping("/{icmt}")//메소드를 구분하기위한 어노테이션 [D]
    public Map<String, Integer> delBoardCmt(@PathVariable int icmt) {
        Map<String, Integer> result = new HashMap<>();
        result.put("result", service.delBoardCmt(icmt));
        return result;
    }
}

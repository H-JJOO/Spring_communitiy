package dev.hjjoo.community.board.fav;

import dev.hjjoo.community.Const;
import dev.hjjoo.community.model.BoardFavEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/board/fav")
public class FavController {

    @Autowired
    private FavService service;

    @PostMapping
    public Map<String, Integer> insBoardFav(@RequestBody BoardFavEntity entity) {
        Map<String, Integer> result = new HashMap<>();
        result.put(Const.RESULT, service.insBoardFav(entity));
        return result;
    }

    @GetMapping("/{iboard}")
    public Map<String, Integer> isFav(@PathVariable int iboard) {
        BoardFavEntity dbEntity = service.selBoardFav(iboard);//null 이면 좋아요 안한거, null 아니면 좋아요 있다.
        Map<String, Integer> result = new HashMap<>();
        result.put(Const.RESULT, dbEntity == null ? 0 : 1);
        return result;
    }

    @DeleteMapping("/{iboard}")
    public Map<String, Integer> delBoardFav(@PathVariable int iboard) {
        Map<String, Integer> result = new HashMap<>();
        result.put(Const.RESULT, service.delBoardFav(iboard));
        return result;
    }






}

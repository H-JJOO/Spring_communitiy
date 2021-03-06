package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.BoardCmtEntity;
import dev.hjjoo.community.model.BoardCmtVo;
import dev.hjjoo.community.model.BoardEntity;
import dev.hjjoo.community.model.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCmtService {

    @Autowired private BoardCmtMapper mapper;
    @Autowired private UserUtils userUtils;

    public int insBoardCmt(BoardCmtEntity entity) {//iboard, ctnt
        entity.setIuser(userUtils.getLoginUserPk());//iboard, ctnt, iuser
        mapper.insBoardCmt(entity);//iboard, ctnt, iuser, icmt (BoardCmtMapper - ins 에서 useGeneratedKeys="true" keyProperty="icmt"
        return entity.getIcmt();
    }

    public List<BoardCmtVo> selBoardCmtList(int iboard) {
        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setIboard(iboard);
        return mapper.selBoardCmtList(entity);
    }

    public int updBoardCmt(BoardCmtEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.updBoardCmt(entity);
    }

    public int delBoardCmt(int icmt) {
        BoardCmtEntity entity = new BoardCmtEntity();
        entity.setIcmt(icmt);
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.delBoardCmt(entity);

    }
}

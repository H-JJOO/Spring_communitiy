package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.UserUtils;
import dev.hjjoo.community.model.BoardCmtEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardCmtService {

    @Autowired private BoardCmtMapper mapper;
    @Autowired private UserUtils userUtils;

    public int insBoardCmt(BoardCmtEntity entity) {
        entity.setIuser(userUtils.getLoginUserPk());
        return mapper.insBoardCmt(entity);
    }

    public List<BoardCmtEntity> selBoardCmtList(BoardCmtEntity entity) {
        return mapper.selBoardCmtList(entity);
    }
}

package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.model.BoardCmtEntity;
import dev.hjjoo.community.model.BoardCmtVo;
import dev.hjjoo.community.model.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCmtMapper {
    int insBoardCmt(BoardCmtEntity entity);
    List<BoardCmtVo> selBoardCmtList(BoardCmtEntity entity);
    int delBoardCmt (BoardCmtEntity entity);
}

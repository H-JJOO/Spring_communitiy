package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.model.BoardCmtEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCmtMapper {
    int insBoardCmt(BoardCmtEntity entity);
    List<BoardCmtEntity> selBoardCmtList(BoardCmtEntity entity);
}

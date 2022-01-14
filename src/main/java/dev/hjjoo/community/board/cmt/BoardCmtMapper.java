package dev.hjjoo.community.board.cmt;

import dev.hjjoo.community.model.BoardCmtEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardCmtMapper {
    int insBoardCmt(BoardCmtEntity entity);
}

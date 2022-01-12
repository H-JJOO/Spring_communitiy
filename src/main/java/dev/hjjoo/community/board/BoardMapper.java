package dev.hjjoo.community.board;

import dev.hjjoo.community.model.BoardDto;
import dev.hjjoo.community.model.BoardEntity;
import dev.hjjoo.community.model.BoardVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insBoard(BoardEntity entity);
    List<BoardVo> selBoardList(BoardDto dto);
    BoardVo selBoard(BoardDto dto);
    int addHits(BoardEntity entity);
    int updBoard(BoardEntity entity);
}

package dev.hjjoo.community;

import dev.hjjoo.community.model.BoardCategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonMapper {
    List<BoardCategoryEntity> selMenuCategoryList();
}

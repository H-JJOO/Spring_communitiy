package dev.hjjoo.community;

import dev.hjjoo.community.model.BoardCategoryEntity;
import dev.hjjoo.community.model.SubMenuVo;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("MenuPreparer")
public class MenuPreparer implements ViewPreparer {

    @Autowired
    private CommonMapper mapper;

    private List<BoardCategoryEntity> menuList;
    private List<SubMenuVo> subMenuList;

    @Override
    public void execute(Request request, AttributeContext attributeContext) {
        if(menuList == null) {
            menuList = mapper.selMenuCategoryList();
        }
        System.out.println(" ------- Called MenuPreparer-execute method --------- ");
        attributeContext.putAttribute(Const.MENU_LIST,
                new Attribute(menuList), true);

        if(subMenuList == null) {
            subMenuList = new ArrayList<>();
            subMenuList.add(new SubMenuVo("profile", "프로필"));
            subMenuList.add(new SubMenuVo("password", "비밀번호 변경"));
        }
        attributeContext.putAttribute(Const.SUB_MENU_LIST,
                new Attribute(subMenuList), true);
    }
}
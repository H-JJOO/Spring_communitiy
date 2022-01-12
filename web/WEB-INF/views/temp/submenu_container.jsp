<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="submenu-section">
    <div class="p10">
        <li><a href="" class="font-color-black">프로필</a></li>
        <li><a href="" class="font-color-black">비밀번호변경</a></li>
    </div>
    <div>
        <tiles:insertAttribute name="content"/>
    </div>
</div>

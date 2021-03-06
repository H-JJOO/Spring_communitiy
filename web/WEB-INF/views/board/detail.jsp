<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="data"
     data-icategory="${data.icategory}"
     data-iboard="${data.iboard}"
     data-nm="${sessionScope.loginUser.nm}"
     data-iuser="${sessionScope.loginUser.iuser}"
     data-profileimg="${sessionScope.loginUser.profileimg}">
</div>
<div>
    <a href="/board/detail?iboard=${requestScope.prevNext.previboard}" class="${requestScope.prevNext.previboard == 0 ? 'invisibility' : ''}"><button class="bac-color-yellow m-5 border border-025 font-color-black w-60 h-20 pointer">이전글</button></a>
    <a href="/board/detail?iboard=${requestScope.prevNext.nextiboard}" class="${requestScope.prevNext.nextiboard == 0 ? 'invisibility' : ''}"><button class=" bac-color-yellow m-5 border border-025 font-color-black w-60 h-20 pointer">다음글</button></a>
<c:if test="${sessionScope.loginUser != null}">
    <!--좋아요-->
    <i id="fav_icon" class="pointer far fa-thumbs-up font-size-30 m-10"></i>
</c:if>

<c:if test="${sessionScope.loginUser.iuser == data.iuser}">
</div>

    <div>
        <button class="bac-color-yellow m-5 border border-025 font-color-black w-60 h-20 pointer" id="modBtn">수정</button>
        <button class="bac-color-yellow m-5 border border-025 font-color-black w-60 h-20 pointer" id="delBtn">삭제</button>
    </div>
</c:if>
<div>
    <hr class="m-5">
    <div>카테고리 : ${data.categorynm}</div>
    <div>조회수 : ${data.hits} | 등록일시 : ${data.rdt}</div>
    <div>글쓴이 : ${data.writernm}</div>
    <div>제목 : <c:out value="${data.title}"/></div>
    <hr class="m-5">
    <div><c:out value="${data.ctnt}"/></div>
    <hr class="m-5">
</div>
<div>
    <c:if test="${sessionScope.loginUser != null}" >
        <div class="m-t-20">
            <form id="cmtFrm">
                <input type="text" name="ctnt" class="w-450">
                <input type="button" id="btn_submit" value="댓글달기" class="bac-color-yellow border-025 border w-70 h-20 font-color-black pointer">
            </form>
        </div>
    </c:if>
    <div class="m-t-20 input" id="cmt_list">

    </div>
</div>
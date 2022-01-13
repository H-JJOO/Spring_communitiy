<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="flex-container flex-center flex-direction-column">
    <h1>[Orange]</h1>
    <div class="m-20">Login</div>
    <div>${requestScope.msg}</div>
    <form action="/user/login" method="post" id="login-frm">
        <div><label><input class="h-30 w-250" type="text" name="uid" value="${requestScope.tryLogin.uid}" placeholder="아이디"></label></div>
        <div><label><input class="h-30 w-250" type="password" name="upw" placeholder="비밀번호"></label></div>
        <div class="d-sub-btn m-b-10">
            <input type="submit" value="로그인" class="h-30 w-250 m-t-20 bac-color-orange border-025 border font-color-white">
        </div>
    </form>
    <span class="font-size-5">
        <a href="#" class="m-5">비밀번호 찾기</a>
        <hr class="m-5">
        <a href="#" class="m-5">아이디 찾기</a>
        <hr class="m-5">
        <a href="/user/join" class="m-5">회원가입</a>
    </span>
</div>
<!--
로그인 처리
세션에 UserEntity 객체 주소값 저장 하는데
키값은 Const.LOGIN_USER 를 사용한다.
객체에 iuser, uid, nm, gender, profileimg값만 저장한다.
로그인 성공시 /board/list 주소값 이동
로그인 실패시 login.jsp에서 메시지 출력!
(아이디 없음, 비밀번호 확인, 알 수 없는 에러 발생)
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="flex-container flex-center flex-direction-column">
    <h1>[Lemon]</h1>
    <div class="m-20">Join</div>
    <div>${requestScope.msg}</div>
    <form action="/user/join" method="post" id="join-frm">
        <div><label><input class="h-30 w-250" type="text" name="uid" required placeholder="id"></label></div>
        <div><input class="h-25" type="button" value="id-check" id="id-btn-chk"><span id="id-chk-msg"></span></div>
        <div><label><input class="h-30 w-250" type="password" name="upw" required placeholder="password"></label></div>
        <div><label><input class="h-30 w-250" type="password" id="upw-chk" required placeholder="password check"></label></div>
        <div><label><input class="h-30 w-250" type="text" name="nm" required placeholder="name"></label></div>
        <div class="flex-align-center">
            <label>female <input type="radio" name="gender" value="2" checked></label>
            <label>male <input type="radio" name="gender" value="1"></label>
        </div>
        <div class="flex-align-center">
            <input class="bac-color-orange border h-30 m-t-10 w-100 font-color-white" type="submit" value="JOIN">
            <input class="bac-color-orange border h-30 m-t-10 w-100 font-color-white" type="reset" value="RESET">
        </div>
    </form>
</div>
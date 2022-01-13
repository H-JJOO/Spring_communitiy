<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="my" uri="tld/MyCustomJstlTag.tld" %>
<div class="h-all each-container">
    <div>
        <c:if test="${sessionScope.loginUser != null}">
        <a href="/board/write?icategory=${requestScope.icategory}"><button class="bac-color-orange border h-30  w-100 font-color-white m-5 pointer">글쓰기</button></a>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${fn:length(requestScope.list) == 0}">
            글이 없습니다.
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>no</th>
                    <th>title</th>
                    <th>hits</th>
                    <th>writer</th>
                    <th>reg date</th>
                </tr>
                <c:forEach items="${requestScope.list}" var="item">
                    <tr class="record" data-iboard="${item.iboard}" class="font-spot">
                        <td>${item.iboard}</td>
                        <td><c:out value="${item.title}"/></td>
                        <td>${item.hits}</td>
                        <td>
                            <div class="flex-container flex-center">
                                <span class="m-r-5">${item.writernm}</span>
                                <my:profileImg classVal="circular--img circular--size30"
                                               iuser="${item.iuser}"
                                               profileImgVal="${item.profileimg}"/>
                            </div>
                        </td>
                        <td>${item.rdt}</td>
                    </tr>
                </c:forEach>
            </table>
            <img src="/res/img/Orange.PNG" alt="Orange" class="flex-center h-all w-all m">
        </c:otherwise>
    </c:choose>
</div>

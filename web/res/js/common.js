const msg = {
    isDel : '삭제하시겠습니까?',

    fnIsDel : function (target) {
        return `${target}을(를)` + this.isDel;
    },

    isMod : '수정하시겠습니까?',

    fnIsMod : function (target) {
        return `${target}을(를)` + this.isMod;
    }
}
// {} 객체 생성
// 멤버필드 : 정규식, 문자열, 정수, 함수
const regex = {
    id : /^([a-zA-Z0-9]{4,15})$/, //대소문자 + 숫자 조합으로 4~15글자 인 경우만 OK!
    pw : /^([a-zA-Z0-9!@_]{4,20}$)/, //대소문자+숫자+!@_ 조합으로 4~20글자 인 경우만 OK!
    nm : /^([가-힣]{2,5})$/, //한글조합 2~5글자 (영어, 특수기호X) 인 경우만 OK!
    msg : {
        id : '대소문자 + 숫자 조합으로 4~15글자',
        pw : '대소문자+숫자+!@_ 조합으로 4~20글자',
        nm : '한글조합 2~5글자',
    },
    isWrongWith : function (target, val) {
        return !this[target].test(val);
    }
};
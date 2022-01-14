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
    ctnt : /^[^><]*$/,
    msg : {
        id : '대소문자 + 숫자 조합으로 4~15글자',
        pw : '대소문자+숫자+!@_ 조합으로 4~20글자',
        nm : '한글조합 2~5글자',
        ctnt : '<, > 는 사용할 수 없습니다.',
    },
    isWrongWith : function (target, val) {
        return (target && val) ? !this[target].test(val) : true;//둘다 true 라면 val 로 확인 둘중 하나라도 false 라면 true 넘기기
    }
};

//Ajax 편하게 쓰기~
const myFetch = {//객체를 담고있는 변수
    send: function(fetchObj, cb) {
        return fetchObj//promise 객체
            .then(res => res.json())//=> 에로우펑션에 {} 없다는 것은 리턴하겠다는 의미, 리턴을하면 then 에 연결된 함수는 promise 이던 아니던 promise 객체로 감싸준다
            .then(cb)
            .catch(e => { console.log(e) });
    },
    get: function(url, cb, param) {
        if(param) {
            const queryString = '?' + Object.keys(param).map(key => `${key}=${param[key]}`).join('&');
            url += queryString;
            // url = url + queryString;

        }
        return this.send(fetch(url), cb);
    },
    post: function(url, cb, param) {//param 은 (Obj)객체여야만 한다.
        return this.send(fetch(url, {//객체 안에있는 멤버필드에 접근(send 메소드를 호출)
            'method': 'post',
            'headers': { 'Content-Type': 'application/json' },
            'body': JSON.stringify(param)//param 객체를 Json 형태로 바꿔줌, 즉 문자열로 바꿔줌(key : '', value : '')
        }), cb);
    }
}
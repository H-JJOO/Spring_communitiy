{
    const dataElem = document.querySelector('#data');

    //삭제버튼
    const delBtnElem = document.querySelector('#delBtn');
    if (delBtnElem) {
        delBtnElem.addEventListener('click', () => {
            const icategory = dataElem.dataset.icategory;
            const iboard = dataElem.dataset.iboard;
            if (confirm(msg.fnIsDel(`${iboard}번 글`))) {
                location.href = `/board/del?icategory=${icategory}&iboard=${iboard}`;
            }
        });
    }

    //수정버튼
    const modBtnElem = document.querySelector('#modBtn');

    if (modBtnElem) {
        modBtnElem.addEventListener('click', () => {
            const iboard = dataElem.dataset.iboard;
            location.href = `/board/mod?iboard=${iboard}`;
        });
    }

    //댓글입력
    const cmtFrmElem = document.querySelector('#cmtFrm');
    if (cmtFrmElem) {// true : 로그인 한 상태

        //input-text ctnt 에서 엔터치면 submit 날아가기 때문에 막는다.
        cmtFrmElem.addEventListener('submit', (e) => {
            e.preventDefault();
        });

        cmtFrmElem.btn_submit.addEventListener('click', () => {
            const cmtVal = cmtFrmElem.ctnt.value;
            if (cmtVal.length === 0) {
                alert('댓글 내용을 작성해 주세요.');
                return;
            } else if(regex.isWrongWith('ctnt', cmtVal)) {
                alert(regex.msg.ctnt);
            } else {
                insBoardCmtAjax(cmtVal);
            }
        });

        //Ajax : 댓글입력
        const insBoardCmtAjax = (val) => {
            const param = {//{ = 다음 시작은 객체생성(key, value), function 으로 new 도 가능
                'iboard': dataElem.dataset.iboard,
                'ctnt': val
            };
            myFetch.post('/board/cmt', (data) => {
                console.log(data);
            }, param);
        }
    }
}







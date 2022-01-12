{
    let idChkState = 2; //0: 체크 안함, 0: 아이디 사용 불가능, 1: 아이디 사용가능, 2: 체크 안함

    const joinFrmElem = document.querySelector('#join-frm');
    const idRegex = /^([a-zA-Z0-9]{4,15})$/; //대소문자 + 숫자 조합으로 4~15글자 인 경우만 OK!
    const pwRegex = /^([a-zA-Z0-9!@_]{4,20}$)/; //대소문자+숫자+!@_ 조합으로 4~20글자 인 경우만 OK!
    const nmRegex = /^([가-힣]{2,5})$/; //한글 2~5자 조합 (영어, 특수기호X) 인 경우만 OK!
    const msg1 = '아이디는 대소문자, 숫자조합으로 4~15자 이상 되어야합니다.';

    // const joinBtn = joinFrmElem.querySelector('#join-btn');
    const setIdChkMsg = (data) => {
        idChkState = data.result; //0 or 1
        const idChkMsgElem = joinFrmElem.querySelector('#id-chk-msg');
        switch (data.result) {
            case 0:
                idChkMsgElem.innerText = '사용 중인 아이디입니다.';
                break;
            case 1:
                idChkMsgElem.innerText = '사용 가능한 아이디입니다.';
                break;
        }
    };
//Span 이용
    // if (joinBtn) {
    //     joinBtn.addEventListener('click', (e) => {
    //         if (idChkMsgElem.innerText === '') {
    //             e.preventDefault();
    //             alert('아이디 중복 체크를 해 주세요!');
    //         } else if (idChkMsgElem.innerText === '사용 중인 아이디입니다.'){
    //             alert('다른 아이디를 사용해 주세요.');
    //             e.preventDefault();
    //         } else {
    //         }
    //     })
    // }
    if (joinFrmElem) {
        joinFrmElem.addEventListener('submit', (e) => {
            const uid = joinFrmElem.uid.value;
            const upw = joinFrmElem.upw.value;
            const upwChk = joinFrmElem.querySelector('#upw-chk').value;
            // const upwChk = joinFrmElem.upw-chk.value; //.으로 자식 엘리먼트에 접근시 - 안됨, _ 는 됨
            const nm = joinFrmElem.nm.value;

            if (!idRegex.test(uid)) {
                alert(msg1);
                e.preventDefault();
            } else if (!pwRegex.test(upw)) {
                alert('비밀번호는 대소문자, 숫자, !, @ㅡ _ 조합으로 4~20자 이상 되어야합니다.');
                e.preventDefault();
            } else if (upw !== upwChk) {
                alert('비밀번호를 확인해주세요.');
                e.preventDefault();
            } else if (!nmRegex.test(nm)) {
                alert('이름은 한글 조합으로 2~5자 여야합니다.');
                e.preventDefault();
            } else if (idChkState !== 1) {
                switch (idChkState) {
                    case 0:
                        alert('다른 아이디를 사용해 주세요');
                        break;
                    case 2:
                        alert('아이디 중복 체크를 해 주세요.');
                        break;
                }
                e.preventDefault();
            }
        });

        joinFrmElem.uid.addEventListener('keyup', () => {
            const idChkMsgElem = joinFrmElem.querySelector('#id-chk-msg');
            idChkMsgElem.innerText = '';
            idChkState = 2;
        });

        //아이디 중복 체크 버튼
        const idBtnChkElem = joinFrmElem.querySelector('#id-btn-chk');
        idBtnChkElem.addEventListener('click', () => {
            const idVal = joinFrmElem.uid.value;
            if (idVal.length < 4) {
                alert('아이디는 4자 이상 작성해 주세요.');
                return;
            }
            if (!idRegex.test(idVal)) {
                alert(msg1);
                return;
            }
            //문자열안에 변수값 넣기 편하다(`), GET 방식은 무조건 쿼리스트링으로
            fetch(`/user/idChk/${idVal}`)
                .then(res => res.json())
                .then((data) => {
                    setIdChkMsg(data);
                }).catch((e) => {
                    console.log(e)
                });
            });
        }

    const resetBtn = joinFrmElem.querySelector('#reset');
    if (resetBtn) {
        resetBtn.addEventListener('click', () => {
            const idChkMsgElem = joinFrmElem.querySelector('#id-chk-msg');
            idChkMsgElem.innerText = '';
        })
    }
}
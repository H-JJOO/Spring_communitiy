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
//TODO - 집가서 220117 1~7 댓글리스트 뿌리기~일단 수정삭제버튼까지
    //댓글리스트 뿌리기
    const cmtListElem = document.querySelector('#cmt_list');
    if (cmtFrmElem) {
        //통신 시작!!!
        const getCmtList = () => {
            const iboard = dataElem.dataset.iboard;
            myFetch.get(`/board/cmt/${iboard}`, setCmtList);//setCmtList() 메소드 호출, setCmtList 는 연결
        }
        //댓글이 있으면, 테이블 태그 자바스크립트에서 생성하여 댓글 리스트 뿌려
        //통신 결과물 세팅
        const setCmtList = (list) => {
            //댓글 없으면 "댓글 없음"
            if (list.length === 0) {
                cmtListElem.innerText = '댓글 없음!';
                return
            }

            const table = document.createElement('table');
            table.innerHTML = `
                <tr>
                    <th>no</th>
                    <th>content</th>
                    <th>writer</th>
                    <th></th>
                </tr>
           `;

            list.forEach(item => {
                const tr = document.createElement('tr');

                const imgSrc = item.profileimg === null
                    ? '/res/img/defaultProfile.png'
                    : `/images/user/${item.iuser}/${item.profileimg}`;

                tr.innerHTML = `
                <td>${item.icmt}</td>
                <td>${item.ctnt}</td>
                <td>
                    <span>${item.writernm}</span>
                    <div class="circular--img circular--size30">
                        <img src="${imgSrc}" onerror="this.style.display='none';">
                    </div>
                </td>
            `;
                const td = document.createElement('td');
                tr.appendChild(td);

                if(parseInt(dataElem.dataset.iuser) === item.iuser) {
                    const modBtn = document.createElement('input');
                    modBtn.type = 'button';
                    modBtn.value = '수정';

                    modBtn.addEventListener('click', () => {

                    })

                    const delBtn = document.createElement('input');
                    delBtn.type = 'button';
                    delBtn.value = '삭제';

                    delBtn.addEventListener('click', () => {
                        if (confirm('삭제하시겠습니까?')) {
                            delCmt(item.icmt, tr);
                        }
                    })

                    td.appendChild(modBtn);
                    td.appendChild(delBtn);
                }
                table.appendChild(tr);
            });
            cmtListElem.appendChild(table);
        }

        const delCmt = (icmt, tr) => {
            myFetch.delete(`/board/cmt/${icmt}`, (data) => {
                if (data.result) {
                    tr.remove();
                } else {
                    alert('댓글을 삭제할 수 없습니다.');
                }
            });
        }

        getCmtList();
    }
}







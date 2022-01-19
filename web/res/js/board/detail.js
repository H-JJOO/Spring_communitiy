{
    const dataElem = document.querySelector('#data');

    //글 삭제 버튼
    const delBtnElem = document.querySelector('#delBtn');
    if(delBtnElem) {
        delBtnElem.addEventListener('click', ()=> {
            const icategory = dataElem.dataset.icategory;
            const iboard = dataElem.dataset.iboard;

            if(confirm(msg.fnIsDel(`${iboard}번 글`))) {
                location.href=`/board/del?icategory=${icategory}&iboard=${iboard}`;
            }
        });
    }

    //글 수정 버튼
    const modBtnElem = document.querySelector('#modBtn');
    if(modBtnElem) {
        modBtnElem.addEventListener('click', ()=> {
            const iboard = dataElem.dataset.iboard;
            location.href=`/board/mod?iboard=${iboard}`;
        });
    }

    //댓글입력
    const cmtFrmElem = document.querySelector('#cmtFrm');
    if(cmtFrmElem) { // true: 로그인 한 상태

        //input-text ctnt 에서 엔터치면 submit 날아가기 때문에 막는다.
        cmtFrmElem.addEventListener('submit', (e)=> {
            e.preventDefault();
        });

        cmtFrmElem.btn_submit.addEventListener('click', () => {
            const cmtVal = cmtFrmElem.ctnt.value;
            if(cmtVal.length === 0) {
                alert('댓글 내용을 작성해 주세요.');
            } else if(regex.isWrongWith('ctnt', cmtVal)) {
                alert(regex.msg.ctnt);
            } else {//댓글 insert 시도
                insBoardCmtAjax(cmtVal);
            }
        });

    window.scrollTo(0, document.body.scrollHeight);

    const submitBtnElem = cmtFrmElem.querySelector('#btn_submit');
    submitBtnElem.addEventListener("scroll", () => {

    })

        //Ajax : 댓글입력
        const insBoardCmtAjax = (val) => {
            const param = {//{ = 다음 시작은 객체생성(key, value), function 으로 new 도 가능
                'iboard': dataElem.dataset.iboard,
                'ctnt': val
            };
            myFetch.post('/board/cmt', (data) => {
                console.log('result : ' + data.result);
                switch (data.result) {//data.result 에 icmt 값이 넘어온다.
                    case 0:
                        alert('댓글 등록에 실패하였습니다.');
                        break;
                    default :

                        //기존 table 태그가 있는지 확인
                        const cmtListElem = document.querySelector('#cmt_list');
                        let table = cmtListElem.querySelector('table');
                        if (!table) {
                            cmtListElem.innerHTML = null;//댓글 없음 내용 삭제!
                            table = makeTable()
                            cmtListElem.appendChild(table);
                        }
                        const item = {
                            icmt : data.result,
                            iuser : parseInt(dataElem.dataset.iuser),//타입 맞춰줘야함
                            writernm : dataElem.dataset.nm,
                            profileimg : dataElem.dataset.profileimg,
                            ctnt : cmtFrmElem.ctnt.value,
                        }
                        const tr = makeTr(item);
                        table.appendChild(tr);

                        cmtFrmElem.ctnt.value = null;
                        window.scrollTo(0, document.body.scrollHeight);
                        break;
                };
            }, param);
        }
    }
//TODO - 집가서 220117 1~7 댓글리스트 뿌리기~일단 수정삭제버튼까지



    //통신 시작!!!
    const getCmtList = () => {
        const iboard = dataElem.dataset.iboard;
        myFetch.get(`/board/cmt/${iboard}`, setCmtList);
    }

    //통신 결과물 세팅
    const setCmtList = (list) => {
        const cmtListElem = document.querySelector('#cmt_list');

        //댓글이 없으면 "댓글 없음"
        if(list.length === 0) {
            cmtListElem.innerText = '댓글 없음!';
            return;
        }

        const table = makeTable();
        cmtListElem.appendChild(table);

        list.forEach(item => {
            const tr = makeTr(item);
            table.appendChild(tr);
        });
    }

    const makeTable = () => {
        const table = document.createElement('table');
        table.innerHTML = `
            <tr>
                <th>no</th>
                <th>content</th>
                <th>writer</th>
                <th></th>
            </tr>`;
        return table;
    }

    const makeTr = item => {
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
            modBtn.addEventListener('click',() => {
                const tdArr = tr.querySelectorAll('td');
                const tdCell = tdArr[1];
                console.log(td);

                const modInput = document.createElement('input');
                modInput.value = item.ctnt;
                const saveBtn = document.createElement('input');
                saveBtn.type = 'button';
                saveBtn.value = '저장';
                saveBtn.addEventListener('click', () => {
                    const param = {
                        icmt : item.icmt,
                        ctnt : modInput.value
                    }
                    myFetch.put('/board/cmt', (data) => {
                        switch (data.result) {
                            case 0:
                                alert('댓글 수정에 실패하였습니다.');
                                break;
                            case 1:
                                tdCell.innerText = modInput.value;
                                item.ctnt = modInput.value;//이 코드 없으면 최초 detail 열었을대 입력된 값으로 뜬다.
                                removeCancelBtn();
                                break;
                        }
                    }, param);
                });

                tdCell.innerHTML = null;
                tdCell.appendChild(modInput);
                tdCell.appendChild(saveBtn);

                const cancelBtn = document.createElement('input');
                cancelBtn.type = 'button';
                cancelBtn.value = '취소';
                cancelBtn.addEventListener('click', () => {
                    tdCell.innerText = item.ctnt;
                    removeCancelBtn();
                })

                const removeCancelBtn = () => {
                    modBtn.classList.remove('hidden');//toggle : 있으면 빼주고 없으면 넣어준다., 불안하니까 add 나중에 remove 도 해주면됨
                    delBtn.classList.remove('hidden');
                    cancelBtn.remove();
                }

                td.insertBefore(cancelBtn, modBtn);//중간에 넣는 modBtn 앞에 cancelBtn 넣겠다
                modBtn.classList.add('hidden');//toggle : 있으면 빼주고 없으면 넣어준다., 불안하니까(가끔 꼬인답니다) add 나중에 remove 도 해주면됨
                delBtn.classList.add('hidden');

            });

            const delBtn = document.createElement('input');
            delBtn.type = 'button';
            delBtn.value = '삭제';


            delBtn.addEventListener('click', () => {
                if(confirm('삭제하시겠습니까?')) {
                    delCmt(item.icmt, tr);
                }
            });

            td.appendChild(modBtn);
            td.appendChild(delBtn);
        }
        return tr;
    }
    //댓글삭제
    const delCmt = (icmt, tr) => {
        myFetch.delete(`/board/cmt/${icmt}`, data => {
            if(data.result) {
                tr.remove();

                //만약 댓글이 하나도 없다면, 젤위 메뉴만 있을경우
                if (getTrLen() === 1) {
                    const cmtListElem = document.querySelector('#cmt_list');
                    cmtListElem.innerText = '댓글 없음!';
                }
            } else {
                alert('댓글을 삭제할 수 없습니다.');
            }
        });
    }

    const getTrLen = () => {
        const cmtListElem = document.querySelector('#cmt_list');
        const trArr = cmtListElem.querySelectorAll('table tr');
        return trArr.length;

    }
    getCmtList();

    //좋아요 --------------------------------------------------------------------[start] ----------------
    const favIconElem = document.querySelector('#fav_icon');
    
    const isFav = () => {
        const iboard = dataElem.dataset.iboard;
        myFetch.get(`/board/fav/${iboard}`, (data) => {
            switch (data.result) {
                case 0:
                    disableFav();
                    break;
                case 1:
                    enableFav();
                    break;
            }
        });
    }

    const disableFav = () => {
        if (favIconElem) {
            favIconElem.classList.remove('fas');
            favIconElem.classList.add('far');
        }
    }

    const enableFav = () => {
        if (favIconElem) {
            favIconElem.classList.remove('far');
            favIconElem.classList.add('fas');
        }
    }

    if(dataElem.dataset.iuser) {//로그인 했다면
        isFav();
        favIconElem.addEventListener('click', () => {
            const iboard = dataElem.dataset.iboard;
            //classList.contains : 값이 존재하는지 체크한다.(true/false)
            if (favIconElem.classList.contains('far')) {//no 좋아요
                const param = { iboard };//es6문법, 객체만들때 iboard 만 넣음, 'iboard' : iboard 랑 같음
                myFetch.post(`/board/fav`, (data) => {
                    switch (data.result) {
                        case 0 :
                            alert('좋아요 처리에 실패하였습니다.');
                            break;
                        case 1 : 
                            enableFav();
                            break;
                    }
                }, param);
            } else {//yes 좋아요
                myFetch.delete(`/board/fav/${iboard}`, (data) => {
                    switch (data.result) {
                        case 0 : 
                            alert('좋아요 처리에 실패하였습니다.');
                            break;
                        case 1:
                            disableFav();
                            break;
                    }
                });
            }
        });
    }
    //좋아요 --------------------------------------------------------------------[end] ----------------
}

// Restful API > POST, GET, PUT, DELETE





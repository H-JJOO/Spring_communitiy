{
    const frmElem = document.querySelector('#password-frm');

    if (frmElem) {
        frmElem.addEventListener('submit', (e) => {
            const currentupVal = frmElem.currentupw.value;
            const upwVal = frmElem.upw.value;
            const confirmupwVal = frmElem.confirmupw.value;

            if (currentupVal.length === 0) {
                alert('현재 비밀번호를 작성해 주세요.');
                e.preventDefault();
            } else if (upwVal.length === 0) {
                alert('변경 비밀번호를 작성해 주세요.');
                e.preventDefault();
            } else if (upwVal !== confirmupwVal) {
                alert('변경 비밀번호와 확인 비밀번호가 다릅니다.');
                e.preventDefault();
            } else if (regex.isWrongWith('pw', currentupVal)) {
                alert(`현재 비밀번호가 ${regex.msg.pw} 인지 확인해주세요.`)
                e.preventDefault();
            } else if (regex.isWrongWith('pw', upwVal)) {
                alert(`변경 비밀번호가 ${regex.msg.pw} 인지 확인해주세요.`)
                e.preventDefault();
            }
        });
    }
}
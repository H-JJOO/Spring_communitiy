{
    //Data Element
    const dataElem = document.querySelector('#data');

    //input type="file"
    const profileFileElem = document.querySelector('#profile-img');
    if (profileFileElem) {
        profileFileElem.addEventListener('change', () => {
            const img = profileFileElem.files[0];
            if(img != null) {
                uploadProfileImg(img)
            }
        });
    }

    //프로필 이미지 클릭 이벤트
    const profileViewElem = document.querySelector('#profile-view');
    if (profileViewElem) {
        profileViewElem.addEventListener('click', () => {
            if (profileFileElem) {
                profileFileElem.click();
            }
        });
    }
    //이미지 업로드
    const uploadProfileImg = (img) => {
        const fData = new FormData();
        fData.append('profileimg', img);//UserController 에 매개변수로 지정해주면 간단하게?

        fetch('/user/mypage/profile', {
            'method' : 'post',
            'body' : fData
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setProfileImg(data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    //이미지 세팅
    const setProfileImg = (data) => {
        if (!data.result) {return;}

        const iuser = dataElem.dataset.iuser;
        const img = profileViewElem.querySelector('img');
        img.src = `/images/user/${iuser}/${data.result}`;
    }
}
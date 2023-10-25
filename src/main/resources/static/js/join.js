// 이메일, 닉네임 중복 체크
document.getElementById("btnEmailCheck").addEventListener("click", function (e) { checkExists(e, "email") });
document.getElementById("btnNicknameCheck").addEventListener("click", function (e) { checkExists(e, "nickName") });

// 개인정보 동의 체크 여부
document.getElementById("checkboxAgree").addEventListener("click", checkAgree);

//회원가입 버튼 클릭 시 (POST Request)
document.getElementById("joinForm").addEventListener("submit", submitEventHandler);

// 공백 체크
document.getElementById("nickName").addEventListener("input", checkBlank);
document.getElementById("password").addEventListener("input", checkBlank);
document.getElementById("baekjoonUserId").addEventListener("input", checkBlank);

// border
email = document.getElementById("email");
nickName = document.getElementById("nickName");

email.addEventListener("focus", function (e) { drawBorder(e, "emailDiv") });
email.addEventListener("blur", function (e) { removeBorder(e, "emailDiv") });
nickName.addEventListener("focus", function (e) { drawBorder(e, "nickNameDiv") });
nickName.addEventListener("blur", function (e) { removeBorder(e, "nickNameDiv") });

function checkExists(e, type) {
    let inputElement = document.getElementById(`${type}`);
    var inputValue = inputElement.value; // input 요소의 값
    var statusText = document.getElementById(`${type}Status`) // 인증 결과 메세지 출력

    if (inputValue === ""){
        statusText.innerText = '값을 입력하세요!';
        return;
    }

    if (/\s/.test(inputValue)){
        statusText.innerText = '공백은 허용하지 않아요!!';
        return;
    }

    xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText); //{statusCode: 200, title: "email", data: null, message: "OK"}
            if (response['statusCode'] == 200) {
                statusText.innerText = '인증 완료!😀';
            } else {
                statusText.innerText = '인증 실패🥲.. 다시 시도해주세요';
            }
        } else {
            console.error("API response is false or unexpected", xhr.status)
        }
    }
    xhr.onerror = function () {
        console.error("Request failed");
    };

    xhr.open("POST", `api/check/${type}`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(`value=${inputValue}`);
}

function checkAgree(e){
    const is_checked = e.target.checked;
    if (is_checked){
        document.getElementById("btnSubmitJoin").disabled = false;
        } else {
        document.getElementById("btnSubmitJoin").disabled = true;
        }
}

function submitEventHandler(e) {
    e.preventDefault(); // 기본 제출 동작 방지
    var formData = new FormData(e.target); // FormData
    xhr = new XMLHttpRequest();

    xhr.onload = function () {
    if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);
        window.alert(response['message'])
        if (response['statusCode'] == 201){
            window.location.href = "/login";
        }
    } else {
        window.alert("문제가 생겼어요! 회원가입이 진행되지 못했습니다.");
        console.error("API response is false or unexpected", xhr.status)
        }
    }
    xhr.onerror = function () {
    console.error("Request failed");
    }
    xhr.open("POST", "/api/join", true);
    xhr.send(formData);
}

function checkBlank(e){
    var type = e.target.name;
    var hasSpaces = /\s/.test(e.target.value);
    if (hasSpaces){
        document.getElementById(`${type}Status`).innerText = "공백은 허용하지 않아요!"
    } else{
        document.getElementById(`${type}Status`).innerText = "";
    }
}

function drawBorder(e, type){
    const divDom = document.getElementById(type);
    divDom.style.outline = "2px solid #74DBEF";
}

function removeBorder(e, type){
    const divDom = document.getElementById(type);
    divDom.style.outline = "";
}

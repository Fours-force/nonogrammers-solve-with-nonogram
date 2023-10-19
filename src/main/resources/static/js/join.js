// 이메일, 닉네임 중복 체크
document.getElementById("btnEmailCheck").addEventListener("click", function (e) { checkEventHandler(e, "email") });
document.getElementById("btnNicknameCheck").addEventListener("click", function (e) { checkEventHandler(e, "nickName") });
document.getElementById("checkboxAgree").addEventListener("click", is_checked)

function checkEventHandler(e, type) {
    let inputElement = document.getElementById(`${type}`);
    var inputValue = inputElement.value; // input 요소의 값
    console.log(type)

    xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.status === 200) {
            var response = JSON.parse(xhr.responseText); //{title: "email", content: true, message: "OK"}
            console.log(response)
            var statusText = document.getElementById(`${type}Status`) // 인증 결과 메세지 출력
            if (response['content'] === true) {
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

function is_checked(e){
    console.log(e);
    const is_checked = e.target.checked;
    console.log(is_checked);
    if (is_checked){
        document.getElementById("btnSubmitJoin").disabled = false;
        } else {
        document.getElementById("btnSubmitJoin").disabled = true;
        }
}

// 회원가입 버튼 클릭 시 (POST Request)
// document.getElementById("joinForm").addEventListener("submit", function (e) { submitEventHandler(e) });

// function submitEventHandler(e) {
//     e.preventDefault(); // 기본 제출 동작 방지
//     var formData = new FormData(e.target); // FormData 객체를 사용하여 폼 데이터를 가져옴

//     xhr = new XMLHttpRequest();
//     xhr.onload = function () {
//         if (xhr.status === 201) {
//             var response = JSON.parse(xhr.responseText);
//             window.alert("환영합니다! 회원가입이 완료되었습니다.");
//             // window.location.href = "/"; // 홈으로 이동
//         } else {
//             window.alert("문제가 생겼어요! 회원가입이 진행되지 못했습니다.");
//             console.error("API response is false or unexpected", xhr.status)
//         }
//     }
//     xhr.onerror = function () {
//         console.error("Request failed");
//     }

//     xhr.open("POST", "/join", true);
//     xhr.send(formData);
// };

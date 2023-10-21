function checkIsEmail(e){
    e.preventDefault(); // 기본 제출 동작 방지
    document.getElementById("btnResetPassword").disabled = true;

    var formData = new FormData(e.target);
    xhr = new XMLHttpRequest();

    xhr.onload = function () {
    if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);
        console.log(response);
        if (response['statusCode'] >= 400){
            var message = document.getElementById('failureNotify').classList.remove('hidden');
            document.getElementById('failureMessage').addEventListener('click', function(e){ window.location.reload();})

        } else{
            var message = document.getElementById('successNotify').classList.remove('hidden');
            document.getElementById('successMessage').addEventListener('click', function(e){window.location.href = `${response['data']}`;})
        }
    } else {
        window.alert("문제가 생겼어요! 진행되지 못했습니다.");
        console.error("API response is false or unexpected", xhr.status)
        }
    }
    xhr.onerror = function () {
    console.error("Request failed");
    }
    xhr.open("POST", "/api/reset-password-token", true);
    xhr.send(formData);
}

function resetPassword(e){
    e.preventDefault(); // 기본 제출 동작 방지
    var formData = new FormData(e.target);
    xhr = new XMLHttpRequest();
    console.log(formData);
    xhr.onload = function () {
    if (xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);
        console.log(response);
        console.log(window.location.href);
        window.alert(response["message"]);
        window.location.href = "/";
    } else {
        window.alert("문제가 생겼어요! 진행되지 못했습니다.");
        window.location.href = "/";
        console.error("API response is false or unexpected", xhr.status)
        }
    }
    xhr.onerror = function () {
    console.error("Request failed");
    }
    xhr.open("POST", "/api/reset-password", true);
    xhr.send(formData);
}

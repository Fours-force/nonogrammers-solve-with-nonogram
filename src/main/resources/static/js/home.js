// 프로필 dropdown 버튼
function toggleDropdown(e){
let dropdownElement = document.getElementById("dropdown");
  if (dropdownElement.classList.contains('hidden')){
    dropdownElement.classList.remove('hidden');
  }else{
    dropdownElement.classList.add('hidden');
  }
}
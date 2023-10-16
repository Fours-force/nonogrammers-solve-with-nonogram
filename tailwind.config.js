/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/main/resources/**/*.html"],
  theme: {
    extend: {
        colors: {
            'main-bg' : '#EBF3FF',
            'main-text' : '#7F91B4',
            'main-sm-text' : '#AAB4DA',
            'back' : '#FBFCFD',
            'main-skyblue' : '#74DBEF',
            'main-blue' : '#3272DD',
            'user-git-bg' : '#0E1A28',
            'user-home-btn' : '#4C83DE',
            'user-chk-btn' : '#74CEEF',
            'nav-navy' : '#373B52',
            'commu-pagination-hover' : '#D9D9D9',
            'commu-time-info' : '#828181',
            'commu-cmt-head' : '#0074E4',
            'mypg-border' : '#9096AB',
            'mypg-left-nav' : '#9096AB',
            'nono-mypg-item-boder' : '#264E86',
            'cmt-user-div-bg' : '#E3F8FC',
        },
        fontFamily : {
            super256 : ['"Super Mario 256"'],
            brr : ["brr"],
        },
    },
  },
  plugins: [],
}


// JavaScript Document
function setHeight()
 {
  var h=document.documentElement.scrollHeight;
  document.getElementById("blanks").style.height=h+"px";
 }
 function hidediv()
 {
  document.getElementById("login").style.display="none";
  document.getElementById("blanks").style.display="none";
 }
 function showLogin()
 {
  document.getElementById("login").style.display="block";
  document.getElementById("blanks").style.display="block";
 }

lay('#version').html('-v'+ laydate.v);

//执行一个laydate实例
laydate.render({
  elem: '#testaaa' //指定元素
});



//城市搜索

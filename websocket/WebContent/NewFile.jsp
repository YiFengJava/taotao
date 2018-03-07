<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="application/javascript">
var ws=null;//一个websocket对象就是一个管道
var target="ws://localhost:8080/websocket/echo"
function subOpen(){
	 if ('WebSocket' in window) {
	        ws = new WebSocket(target);
	    } else if ('MozWebSocket' in window) {
	        ws = new MozWebSocket(target);
	    } else {
	        alert('WebSocket is not supported by this browser.');
	        return;
	    }
	 
	 ws.onmessage=function(data){
			console.log(data);
			var div=document.getElementById('backmsg');
			div.innerHTML=data.data
			
		}
}



function subSend(){
    if (ws != null) {
        var message = document.getElementById('msg').value;
     
        ws.send(message);
        document.getElementById('msg').value="";
    } else {
        alert('WebSocket connection not established, please connect.');
    }
}
</script>
</head>


<body>

<button onclick="subOpen();">open</button>

<div id="backmsg"></div>
<input id="msg"/>
<button onclick="subSend();">send</button>
</body>
</html>
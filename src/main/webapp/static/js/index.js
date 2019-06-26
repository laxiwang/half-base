function IsPC() {
    var userAgentInfo = navigator.userAgent;
    var Agents = ["Android", "iPhone",
        "SymbianOS", "Windows Phone",
        "iPad", "iPod"];
    var flag = true;
    for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;
            break;
        }
    }
    //alert(flag);
    var body=document.getElementsByTagName('body')[0];
    var html =document.getElementsByTagName('html')[0];
 /*   if(!flag){
        body.style.width='3500px';
        html.style.width='3500px';
        html.style.overflow="scroll"
    }*/
    return flag;
}
IsPC();
var api = (function () {
    var url="https://"+(window.location.href).split("/")[2];
    console.log(url);

    function otherService() {
        console.log("llega");
        axios.get(url+"/successful").then(res=>{
            console.log(url+"/successful");
            console.log(res.data);
            document.getElementById("mensaje").innerHTML =res.data.Mensaje;
            document.getElementById("fecha").innerHTML =res.data.Date;
        })
    }
    function informacion(){
        window.alert("Digite su usuario y contraseña");
    }
    function login()  {
        console.log("url");
        console.log(url);
        var user={userName:document.getElementById("userName").value,password:document.getElementById("password").value}
        axios.post(url+"/login",user).then(res =>{
            window.location.href="loged.html";
        })
    }
    return {
        login:login,
        otherService:otherService,
        informacion:informacion
    };
})();
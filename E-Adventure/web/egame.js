// JavaScript Document

// variables
var engine;
var appletName = "eagame";
var base_url = "http://ea.lrn:8000/game-service/";

var tries = 0;
var API = null;
var maxTries = 500;
var APIVersion = "";



function connect()
{
   //alert("Start Communication");
   // Hay que hacer lo mismo xo en vez de enviarselo via request, hay que hacerlo via API ! ! ! !
   // var url = base_url + "comm-test?payload=" + handshakeString;
   // var success = sendRequest("GET", url, processInitializationResponse);
   var communicationIni;
   getRTE(window);
   if (API != null)
   {
   // alert("RTE founded!!!!");
   // alert("Attempting to conect to LSM");
    var applet = document.getElementById('eadventure');
    //document.eadventure.connectionEstablished();
    applet.connectionEstablished("Se inicia la conexion con el LMS!!");
    communicationIni = API.LMSInitialize("");
      if (!communicationIni){
     //      alert("The conexion has failed, error " + API.LMSGetLastError());
           document.eadventure.connectionFailed(API.LMSGetLastError());                   
      } else {
       //     alert("Begin the comunication with LSM");
            document.edventure.connectionEstablished();
      }
   }
   else
   {
    //  alert("Doesn´t find the SCORM RTE!!(connect)");
   }
}
;

function disconnect(){
    var endComm;
      
    if (API != null)
   {
    endComm = API.LMSFinish("");  
    if (!endComm){
//           alert("The end conexion has failed, error " + API.LMSGetLastError());
           
                  
      } else {
  //          alert("The conexion with LMS has finised ok");
            document.eadventure.disconnectOK();
      }
      
   }
   else
   {
    //  alert("Doesn´t find the SCORM RTE!!(disconect)");
   }
};


function getLMSData(adventureMessage){
    
    var charString;
    var OK;
    if (API != null)
   {
//   alert("Vamos a enviar API.LMSGetValue("+adventureMessage+")" );
    charString = API.LMSGetValue(adventureMessage);  
    OK = API.LMSGetLastError("");
  //  alert("Nos llega "+charString);
    var applet = document.getElementById('eadventure');
    applet.dataReceived(adventureMessage,charString);
    //document.eadventure.dataReceived(adventureMessage,charString);
    if (OK != "0"){
    //       alert("The get operation was failed, error " + API.LMSGetErrorString(OK));
                  
      } else {
        //ver como se llama el metodo de eagema para pillar datos del LMS
        document.eagame.dataReceived(adventureMessage,charString);
      //  alert(charString + " was send by LMS!!");
      }
   }
   else
   {
    //  alert("Doesn´t find the SCORM RTE, (getLMSdata)!!");
   }
    
};

/*function commit(valueFromApplet1,valueFromApplet2){
    
    var ok;
    if (API != null)
   {
    ok = API.LMSCommit("");
    
    if (!ok){
         //  alert("The end conexion has failed, error " + API.LMSGetErrorString(API.LMSGetLastError("")) );
                  
      } else {
      alert("Haciendo Commit");
        //ver como se llama el metodo de eagame para pillar datos del LMS
       ///document.eagame.getDataS(carString);
         //alert("The data has been send OK!!ole!");
      }
   }
   else
   {
      alert("Doesn´t find the SCORM RTE!!(COMMIT)");
   }
    
};
*/

function setLMSData(valueFromApplet1,valueFromApplet2){
    
    var ok;
    if (API != null)
   {
    ok = API.LMSSetValue(valueFromApplet1,valueFromApplet2); 
    if (!ok){
    //       alert("The end conexion has failed, error " + API.LMSGetErrorString(API.LMSGetLastError("")) );
                  
      } else {
        //ver como se llama el metodo de eagame para pillar datos del LMS
       ///document.eagame.getDataS(carString);
      //   alert("The data has been send OK!!ole!");
      }
   }
   else
   {
    //  alert("Doesn´t find the SCORM RTE!!(setLMSdata)");
   }
    
};


/*
function initRequest(){
var request = false;
try {
request = new ActiveXObject("Msxml2.XMLHTTP");
} catch (e) {
try {
request = new ActiveXObject("Microsoft.XMLHTTP");
} catch (E) {
request = false;
}
}

if ( ! request && typeof XMLHttpRequest != 'undefined') {
request = new XMLHttpRequest();
}
return request;
}


//
function sendRequest(type, url, callback) {
var ajax = initRequest();
if (ajax != null) {
ajax.onreadystatechange = function() {
if (ajax.readyState == 4) {
callback(ajax.responseText);

}
}
ajax.open(type, url, true);
ajax.send(null);
return true;
}
alert("No requester object ative!");
return false;
};

 */


//
function searchRTE(win)
{
   while ((win.API == null) && (win.parent != null) && (win.parent != win))
   {
      tries ++ ;
      if (tries > maxTries)
      {
         return null;
      }
      win = win.parent;

   }
 
   return win.API;
}
;

//
function getRTE(win)
{

   if ((win.parent != null) && (win.parent != win))
   {
      API = searchRTE(win.parent);
   }
   if ((API == null) && (win.opener != null))
   {
      API = searchRTE(win.opener);
   }
   if (API != null)
   {
      APIVersion = API.version;
   }

}
;

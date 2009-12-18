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
    applet.connectionEstablished("The connection has been established");
	if (APIVersion == "SCORM12"){
	communicationIni = API.LMSInitialize("");
	} else if (APIVersion == "SCORM2004"){
	communicationIni = API.Initialize("");
	}
      if (!communicationIni){
          //alert("The connection has failed, error ");
           	if (APIVersion == "SCORM12"){
				applet.connectionFailed(API.LMSGetLastError());
			} else if (APIVersion == "SCORM2004"){
				applet.connectionFailed(API.GetLastError());
			}

		   
		                      
      } else {
         //  alert("Begin the comunication with LSM");
				//applet.connectionEstablished();
      }
   }
   else
   {
     alert("Doesn´t find the SCORM RTE!!(connect)");
   }
}
;

function disconnect(){
    var endComm;
      
	  var applet = document.getElementById('eadventure');
    if (API != null)
   {
			if (APIVersion == "SCORM12"){
				endComm = API.LMSFinish("");  
			} else if (APIVersion == "SCORM2004"){
				endComm = API.Terminate("");  
			}
    
    if (!endComm){
         alert("The end connection has failed, error ");
           
                  
      } else {
          //  alert("The conexion with LMS has finised ok");
            applet.disconnectOK();
      }
      
   }
   else
   {
      alert("Doesn´t find the SCORM RTE!!(disconect)");
   }
};


function getLMSData(adventureMessage){
    
    var charString;
    var OK;
    if (API != null)
   {
	//alert("Se realiza API.LMSGetValue("+adventureMessage+")" );
			if (APIVersion == "SCORM12"){
				charString = API.LMSGetValue(adventureMessage);  
				OK = API.LMSGetLastError("");
			} else if (APIVersion == "SCORM2004"){
				charString = API.GetValue(adventureMessage);  
				OK = API.GetLastError("");
			}
	
	
   //alert("Nos llega "+charString);
    var applet = document.getElementById('eadventure');
    applet.dataFromLMS(adventureMessage,charString);
    if (!OK ){
           alert("The get operation was failed, error " );
                  
      } else {
        applet.dataFormLMS(adventureMessage,charString);
		//alert(charString + " was send by LMS!!");
      }
   }
   else
   {
     alert("Doesn´t find the SCORM RTE, (getLMSdata)!!");
   }
    
};

function commit(valueFromApplet1,valueFromApplet2){
    
    var ok;
    if (API != null)
   {
    ok = API.LMSCommit("");
    
    if (!ok){
          alert("The commit has failed, error " + API.LMSGetErrorString(API.LMSGetLastError("")) );
                  
      } 
   }
   else
   {
      alert("Doesn´t find the SCORM RTE!!(COMMIT)");
   }
    
};


function setLMSData(valueFromApplet1,valueFromApplet2){
    
    var ok;
	var res;
    if (API != null)
   {
   if (APIVersion == "SCORM12"){
				res = API.LMSSetValue(valueFromApplet1,valueFromApplet2); 
				ok = API.LMSGetLastError("");
			} else if (APIVersion == "SCORM2004"){
				res = API.SetValue(valueFromApplet1,valueFromApplet2); 
				ok = API.GetLastError("");
			}
			
    if (!ok){
         alert("The connection has failed, error " );
                  
      }
   }
   else
   {
     alert("Doesn´t find the SCORM RTE!!(setLMSdata)");
   }
    
};





function searchRTE(win)
{
   while ((win.API == null) && (win.parent != null) && 
   (win.parent != win)&&(win.API_1484_11== null))
   {
      tries ++ ;
      if (tries > maxTries)
      {
         return null;
      }
      win = win.parent;

   }
   if (win.API!=null){
	APIVersion = "SCORM12";
	return win.API;
	
   }else if (win.API_1484_11!=null){
   APIVersion = "SCORM2004";
	return win.API_1484_11;

   }
   
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

}
;

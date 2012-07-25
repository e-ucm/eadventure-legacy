var eucm = eucm || {};

eucm.eadventure = (function (ctx, window, undefined) {
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
 
     var communicationIni;
	 var textBox = document.getElementById('scormConsole');
	 
     getRTE(window);
     if (API != null)
     {
		var applet = document.getElementById('eadventure');
      applet.connectionEstablished("The connection has been established");
	  if (APIVersion == "SCORM12"){
	  communicationIni = API.LMSInitialize("");
	  if (textBox!=null){
		textBox.value+="Conecting with SCORM 1.2 \n";
		textBox.value+='LMSInitialize("") \n';
		}
	  } else if (APIVersion == "SCORM2004"){
	  communicationIni = API.Initialize("");
	  if (textBox!=null){
		textBox.value+="Connecting with SCORM 2004 \n";
		textBox.value+='Initialize("") \n';
		}
	  }
	  if (textBox!=null){
		reloadTextArea();
	  }
	  
        if (!communicationIni){
		
	     	if (APIVersion == "SCORM12"){
				  applet.connectionFailed(API.LMSGetLastError());
			  } else if (APIVersion == "SCORM2004"){
				  applet.connectionFailed(API.GetLastError());
			  }

		     
			                
        } 
     }
     else
     {
       alert("Doesnt find the SCORM RTE!!(connect)");
     }
  }
  ;

  function disconnect(){
		var endComm;
	    var applet = document.getElementById('eadventure');
		var textBox = document.getElementById('scormConsole');
		
      if (API != null)
     {
			  if (APIVersion == "SCORM12"){
				  endComm = API.LMSFinish(""); 
				  if (textBox!=null){
					textBox.value+='LMSFinish("") \n';				  
					}
			  } else if (APIVersion == "SCORM2004"){
				  endComm = API.Terminate("");  
				  if (textBox!=null){
					textBox.value+='Terminate("") \n';
					}
			  }
			  if (textBox!=null){
				reloadTextArea();
				}
      
      if (!endComm){
	   alert("The end connection has failed, error ");
	     
	            
        } else {
	    
	      applet.disconnectOK();
        }
        
     }
     else
     {
        alert("Doesnt find the SCORM RTE!!(disconect)");
     }
  };


  function getLMSData(adventureMessage){
      
      var charString;
      var OK;
	  var textBox = document.getElementById('scormConsole');
	  
      if (API != null)
     {
	  
			  if (APIVersion == "SCORM12"){
				  charString = API.LMSGetValue(adventureMessage);  
				  if (textBox!=null){
					textBox.value+='LMSGetValue("'+adventureMessage+'") returns: '+charString+'\n';
					}
				  OK = API.LMSGetLastError("");
			  } else if (APIVersion == "SCORM2004"){
				  charString = API.GetValue(adventureMessage); 
				  if (textBox!=null){
					textBox.value+='GetValue("'+adventureMessage+'") returns: '+charString+'\n';				  
					}
				  OK = API.GetLastError("");
			  }
			  
			  if (textBox!=null){
				reloadTextArea();
				}

      var applet = document.getElementById('eadventure');
      applet.dataFromLMS(adventureMessage,charString);
      if (!OK ){
	     alert("The get operation was failed, error " );
	            
        } else {
	  		applet.dataFromLMS(adventureMessage,charString);
        }
     }
     else
     {
       alert("Doesnt find the SCORM RTE, (getLMSdata)!!");
     }
      
  };

  function commit(valueFromApplet1,valueFromApplet2){
      
      var ok;
      
      if (API != null)
     {
     
     
     if (APIVersion == "SCORM12"){
      ok = API.LMSCommit("");
      } else if (APIVersion == "SCORM2004"){
      	ok = API.Commit("");
      }
      
      if (!ok){
	    alert("The commit has failed, error " + API.LMSGetErrorString(API.LMSGetLastError("")) );
	            
        } 
     }
     else
     {
        alert("Doesnt find the SCORM RTE!!(COMMIT)");
     }
      
  };


  function setLMSData(valueFromApplet1,valueFromApplet2){
      
      var ok;
	  var res;
	  var textBox = document.getElementById('scormConsole');
	  
      if (API != null)
     {
     if (APIVersion == "SCORM12"){
				  res = API.LMSSetValue(valueFromApplet1,valueFromApplet2); 
				  if (textBox!=null){
					textBox.value+='LMSSetValue("'+valueFromApplet1+'","'+valueFromApplet2+'")\n';
					}
				  ok = API.LMSGetLastError("");
			  } else if (APIVersion == "SCORM2004"){
				  res = API.SetValue(valueFromApplet1,valueFromApplet2); 
				  if (textBox!=null){
					textBox.value+='SetValue("'+valueFromApplet1+'","'+valueFromApplet2+'")\n';
					}
				  ok = API.GetLastError("");
			  }
			  if (textBox!=null){
				reloadTextArea();		
				}
			  
      if (!ok){
	   alert("The connection has failed, error " );
	            
        }
     }
     else
     {
       alert("Doesnt find the SCORM RTE!!(setLMSdata)");
     }
      
  };

  function searchRTE(win) {
    while ((win.API == null) && (win.parent != null) && 
          (win.parent != win)&&(win.API_1484_11== null)) {
      tries ++ ;
      if (tries > maxTries) {
        return null;
      }
      win = win.parent;
    }
    if (win.API!=null) {
      APIVersion = "SCORM12";
      return win.API;
    }else if (win.API_1484_11!=null) {
      APIVersion = "SCORM2004";
      return win.API_1484_11;
    }
  }

  function getRTE(win) {
     if ((win.parent != null) && (win.parent != win)) {
        API = searchRTE(win.parent);
     }
     if ((API == null) && (win.opener != null)) {
        API = searchRTE(win.opener);
     }
  }
  
  function mostrarFormulario(){
    var charString;
    var OK;
	var reportPOPUP;
	
    if (APIVersion == "SCORM12"){
        charString = API.LMSGetValue("cmi.comments");  
        charString.replace('\"','"');
        document.getElementById('report').innerHTML=charString;
        
        OK = API.LMSGetLastError("");
    } else if (APIVersion == "SCORM2004"){
        charString = API.GetValue("cmi.comments_from_learner.0.comment");  
        charString.replace('\"','"');
        document.getElementById('report').innerHTML=charString;
        
        OK = API.GetLastError("");
    }
	
	
//	reportPOPUP=window.open(,'report') 

	//reportPOPUP.document.body.innerHTML=charString;
}

  var publicLibrary = {};
  publicLibrary.connect = connect;
  publicLibrary.disconnect = disconnect;
  publicLibrary.getLMSData = getLMSData;
  publicLibrary.commit=commit;
  publicLibrary.setLMSData=setLMSData;
  publicLibrary.mostrarFormulario = mostrarFormulario;
  return publicLibrary;
}
  
)(window, window);



			function controlRadioButton(formulario){
				if (formulario.option[0].checked){
					document.getElementById("valor").style.display = 'block';
				}else{
					document.getElementById("valor").style.display = 'none';
				}
			}
			
			function botonPulsado(formulario){
				if (formulario.option[0].checked){
					eucm.eadventure.setLMSData(formulario.campo.value,formulario.valor.value);
				}else{
					eucm.eadventure.getLMSData(formulario.campo.value);				
				}
			}
			
			function mostrarFormulario(){
				var informe;
				eucm.eadventure.getLMSData(formulario.campo.value);
			}
			
			function reloadTextArea(){
			textareaname=document.getElementById('scormConsole');
			textareaname.scrollTop = textareaname.scrollHeight-8;
			}
			function showConsole(){
				var altura;
				var divPrincipal;
				divPrincipal=document.getElementById('scormConsole');
				if(document.getElementById('moreButton').value=='+'){
				
					document.getElementById('moreButton').value='-';
					document.getElementById('consolet').style.display='block';
					document.getElementById('setValueButton').style.display='block';
					document.getElementById('campolit').style.display='block';
					document.getElementById('fieldText').style.display='block';
					document.getElementById('getValueButton').style.display='block';
					document.getElementById('valorlit').style.display='block';
					document.getElementById('valueText').style.display='block';
					document.getElementById('showReportButton').style.display='block';
					document.getElementById('submitButton').style.display='block';
				}else{
					document.getElementById('moreButton').value='+';
					document.getElementById('consolet').style.display='none';
					document.getElementById('setValueButton').style.display='none';
					document.getElementById('campolit').style.display='none';
					document.getElementById('fieldText').style.display='none';
					document.getElementById('getValueButton').style.display='none';
					document.getElementById('valorlit').style.display='none';
					document.getElementById('valueText').style.display='none';
					document.getElementById('showReportButton').style.display='none';
					document.getElementById('submitButton').style.display='none';
				}
			
			}
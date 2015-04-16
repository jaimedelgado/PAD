$(document).on('ready', inicio);





function inicio()
{
	initGame();
	cuentaAtras();
	
};

var num = 35
function cuentaAtras()
{
    //aquí se establece el valor inicial

   if(num >= 0){
      $('.timer .value').html(num);
      num--;
      setTimeout('cuentaAtras()',1000)
      console.log(num);
   } 

   if(num == 0){
      parar();
   }
};


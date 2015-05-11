function Conversor(factor) {
  this.factor = factor;
  this.fijaConversionPesetasAEuros = fijaConversionPesetasAEuros;
  this.fijaConversionEurosAPesetas = fijaConversionEurosAPesetas;
  this.convierte = convierte;
  this.resetea = resetea;
  this.ui = new ConversorUI();
}
function fijaConversionPesetasAEuros() {
  this.factor = 1 / 166.386;
  refrescaUITrasCambio(this.ui);
}
function fijaConversionEurosAPesetas() {
  this.factor = 166.386;
  refrescaUITrasCambio(this.ui);
}
function refrescaUITrasCambio(ui) {
  ui.resetError();
  ui.resetCantidad();
  ui.resetResultado();
  ui.permutaEtiquetas();  
}
function convierte() {
  var cantidad = this.ui.cantidad();
  this.ui.resetError();
  if (isNaN(cantidad) || cantidad.length == 0) this.ui.errorFormato();
  else this.ui.fijaResultado(cantidad * this.factor);
}
function resetea() {
    this.ui.resetError();
	this.ui.resetResultado();
}
function ConversorUI() {
  this.permutaEtiquetas = permutaEtiquetas;
  this.cantidad = cantidad;
  this.errorFormato = errorFormato;
  this.fijaResultado = fijaResultado;
  this.resetError = resetError;
  this.resetCantidad = resetCantidad;
  this.resetResultado = resetResultado;
} 
function permutaEtiquetas() {
   var etiquetaOrigen = document.getElementById("etiquetaOrigen");
   var etiquetaResultado = document.getElementById("etiquetaResultado");
   var etqOrigen = etiquetaOrigen.innerHTML;
   etiquetaOrigen.innerHTML = etiquetaResultado.innerHTML;
   etiquetaResultado.innerHTML = etqOrigen;
}
function cantidad() {
   return document.getElementById("cantidadAConvertir").value;
}
function errorFormato() {
   document.getElementById("error").innerHTML = "ERROR DE FORMATO";
}
function resetError() {
   document.getElementById("error").innerHTML = "";
}
function resetCantidad() {
   document.getElementById("cantidadAConvertir").value=""
}
function resetResultado() {
   document.getElementById("cantidadResultante").value="";
}
function fijaResultado(resultado) {
   return document.getElementById("cantidadResultante").value=resultado;
}

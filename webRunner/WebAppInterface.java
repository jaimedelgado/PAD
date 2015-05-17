
public class WebAppInterface {
	private int _monedas;
	private int _escudo;
	private int _fuego;
	
	public WebAppInterface(){
		this._monedas = 110;
		this._escudo = 1;
		this._fuego = 4;
	}
	@JavascriptInterface
	public int monedas(){
		return this._monedas;
	}
	@JavascriptInterface
	public int nivelEscudo(){
		return this._escudo;
	}
	@JavascriptInterface
	public int nivelFuego(){
		return this._fuego;
	}
	@JavascriptInterface
	public void sumar(int cantidad){
		System.out.println("Moneda añadida");
	}
}

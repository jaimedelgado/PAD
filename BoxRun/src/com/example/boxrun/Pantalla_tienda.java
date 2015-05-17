package com.example.boxrun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.boxrun.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Pantalla_tienda extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	private TextView dinero=null;
	private TextView dineroEscudo =null;
	private TextView dineroLlama=null;
	private int nivelEscudo=0;
	private int nivelFuego=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tienda);
		dinero = (TextView) findViewById(R.id.tienda_dinero_total);
		dinero.setText(String.valueOf(this.monedas()));
		dineroEscudo = (TextView) findViewById(R.id.dineroEscudo);
		nivelEscudo = nivelEscudo();
		switch(nivelEscudo){
		case 0: dineroEscudo.setText(String.valueOf(Utils.precio_escudo_1)); break;
		case 1: dineroEscudo.setText(String.valueOf(Utils.precio_escudo_2)); break;
		case 2: dineroEscudo.setText(String.valueOf(Utils.precio_escudo_3)); break;
		case 3: dineroEscudo.setText(String.valueOf(Utils.precio_escudo_4)); break;
		case 4: dineroEscudo.setText(String.valueOf(Utils.precio_escudo_5)); break;
		}
		dineroLlama = (TextView) findViewById(R.id.dineroLlama);
		nivelFuego = nivelFuego();
		switch(nivelFuego){
		case 0: dineroLlama.setText(String.valueOf(Utils.precio_llama_1)); break;
		case 1: dineroLlama.setText(String.valueOf(Utils.precio_llama_2)); break;
		case 2: dineroLlama.setText(String.valueOf(Utils.precio_llama_3)); break;
		case 3: dineroLlama.setText(String.valueOf(Utils.precio_llama_4)); break;
		case 4: dineroLlama.setText(String.valueOf(Utils.precio_llama_5)); break;
		}
		RatingBar estrellas1 = (RatingBar) findViewById(R.id.ratingBar1);
		estrellas1.setNumStars(nivelEscudo);
		RatingBar estrellas2 = (RatingBar) findViewById(R.id.ratingBar2);
		estrellas2.setNumStars(nivelFuego);
		
		

	}

	
	public void mejorarEscudo(View v){
		int dinero = Integer.parseInt((String) this.dinero.getText());
		int dineroEscudo = Integer.parseInt((String) this.dineroEscudo.getText());
		if(dinero>=dineroEscudo && this.nivelEscudo<5){
			dinero-=dineroEscudo;
			this.dinero.setText(String.valueOf(dinero));
			this.nivelEscudo++;
			switch(nivelEscudo){
			case 0: this.dineroEscudo.setText(String.valueOf(Utils.precio_escudo_1)); break;
			case 1: this.dineroEscudo.setText(String.valueOf(Utils.precio_escudo_2)); break;
			case 2: this.dineroEscudo.setText(String.valueOf(Utils.precio_escudo_3)); break;
			case 3: this.dineroEscudo.setText(String.valueOf(Utils.precio_escudo_4)); break;
			case 4: this.dineroEscudo.setText(String.valueOf(Utils.precio_escudo_5)); break;
			}
		}

	}
	public void mejorarLlama(View v){
		int dinero = Integer.parseInt((String) this.dinero.getText());
		int dineroLlama = Integer.parseInt((String) this.dineroLlama.getText());
		if(dinero>=dineroLlama && this.nivelFuego<5){
			dinero-=dineroLlama;
			this.dinero.setText(String.valueOf(dinero));
			this.nivelFuego++;
			switch(nivelFuego){
			case 0: this.dineroLlama.setText(String.valueOf(Utils.precio_llama_1)); break;
			case 1: this.dineroLlama.setText(String.valueOf(Utils.precio_llama_2)); break;
			case 2: this.dineroLlama.setText(String.valueOf(Utils.precio_llama_3)); break;
			case 3: this.dineroLlama.setText(String.valueOf(Utils.precio_llama_4)); break;
			case 4: this.dineroLlama.setText(String.valueOf(Utils.precio_llama_5)); break;
			}
		}
	}
	public int nivelFuego(){
		int nivel = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document doc = db.parse(c);
			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node tienda = raiz.getElementsByTagName("tienda").item(0);
		    NodeList objetos = tienda.getOwnerDocument().getElementsByTagName("objeto");
		    for(int i=0; i<objetos.getLength(); i++){
		    	Node objeto = objetos.item(i);
		    	String nombre = objeto.getAttributes().getNamedItem("nombreObjeto").getNodeValue();
		    	if(nombre.equals("llama")){ 
		    		nivel = Integer.parseInt(objeto.getAttributes().getNamedItem("nivel").getNodeValue());
		    	}
		    }
		   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nivel;
	}
	public int nivelEscudo(){
		int nivel = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document doc = db.parse(c);			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node tienda = raiz.getElementsByTagName("tienda").item(0);
		    NodeList objetos = tienda.getOwnerDocument().getElementsByTagName("objeto");
		    for(int i=0; i<objetos.getLength(); i++){
		    	Node objeto = objetos.item(i);
		    	String nombre = objeto.getAttributes().getNamedItem("nombreObjeto").getNodeValue();
		    	if(nombre.equals("escudo")){ 
		    		nivel = Integer.parseInt(objeto.getAttributes().getNamedItem("nivel").getNodeValue());
		    	}
		    }
		   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nivel;
	}
	public int monedas(){
		int monedas = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document doc = db.parse(c);
			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node jugador = raiz.getElementsByTagName("jugador").item(0);
		    monedas = Integer.parseInt(jugador.getAttributes().getNamedItem("monedas").getNodeValue());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return monedas;
	}
	
	public void ir_a_principal(View v){
		Intent actividadPrincipal = new Intent (this, Pantalla_principal.class);
		startActivity(actividadPrincipal);
	}
}

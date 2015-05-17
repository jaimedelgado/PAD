package com.example.boxrun;

import java.io.File;
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

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Pantalla_juego extends Activity {
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

	
	@Override
	@JavascriptInterface
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.pantalla_juego);
	
        WebView myWebView = (WebView) this.findViewById(R.id.webView1);
        myWebView.loadUrl("http://sesat.fdi.ucm.es/BoxRun/index.html");
       
        extracted(myWebView);
        myWebView.addJavascriptInterface(this, "DatosNativos");
        
	        
	      
	}
	@JavascriptInterface
	public int nivelfuego(){
		Node fuego = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;		
			
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document documento = db.parse(c);
		
		    //Obtener el elemento raíz del documento
		    Element raiz = documento.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node tienda = raiz.getElementsByTagName("tienda").item(0);
		    
		    fuego = tienda.getOwnerDocument().getElementsByTagName("objeto").item(1);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return Integer.parseInt(fuego.getAttributes().getNamedItem("nivel").toString());
		
	}
	@JavascriptInterface
	public int nivelescudo(){
		Node escudo = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;		
			
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document documento = db.parse(c);
		
		    //Obtener el elemento raíz del documento
		    Element raiz = documento.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node tienda = raiz.getElementsByTagName("tienda").item(0);
		    escudo = tienda.getOwnerDocument().getElementsByTagName("objeto").item(0);
		   
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return Integer.parseInt(escudo.getAttributes().getNamedItem("nivel").toString());
	}
	@JavascriptInterface
	public int monedas(){
		Node monedas = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;		
			
			db = dbf.newDocumentBuilder();
			File datos = Environment.getDataDirectory();
	        File c = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
			Document documento = db.parse(c);
		
		    //Obtener el elemento raíz del documento
		    Element raiz = documento.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node jugador = raiz.getElementsByTagName("jugador").item(0);
		    monedas = jugador.getOwnerDocument().getElementsByTagName("monedas").item(0);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return Integer.parseInt(monedas.toString());
	}
	
	@JavascriptInterface
	private void extracted(WebView myWebView) {
		myWebView.getSettings().setJavaScriptEnabled(true);
	}

	
}

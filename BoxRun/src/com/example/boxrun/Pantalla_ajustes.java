package com.example.boxrun;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.example.boxrun.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Pantalla_ajustes extends Activity {
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_pantalla_ajustes);
		TextView nombre = (TextView) findViewById(R.id.largeText1);
		nombre.setText(nombre());
		TextView puntuacion = (TextView) findViewById(R.id.textView3);
		puntuacion.setText(puntuacion());
	}
	private String puntuacion() {
		String puntuacion = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(openFileInput("Configuracion.txt"));
			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node jugador = raiz.getElementsByTagName("jugador").item(0);
		    puntuacion = jugador.getAttributes().getNamedItem("puntuacion").getNodeValue();
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
		return puntuacion;
	}
	private String nombre() {
		String nombre = "";
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(openFileInput("Configuracion.txt"));
			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		    Node jugador = raiz.getElementsByTagName("jugador").item(0);
		    nombre = jugador.getAttributes().getNamedItem("nombre").getNodeValue();
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
		return nombre;
	}
	public void ir_a_principal(View v){
		Intent actividadPrincipal = new Intent (this, Pantalla_principal.class);
		startActivity(actividadPrincipal);
	}
}
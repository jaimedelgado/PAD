package com.example.boxrun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.boxrun.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Pantalla_Ranking extends Activity {
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
		setContentView(R.layout.activity_pantalla_ranking);
		//ArrayList<Jugador> jugadores = jugadores();
	
	    RequestQueue queue = Volley.newRequestQueue(this);
	   /* try {
			semaforo.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	    String url ="http://sesat.fdi.ucm.es:8080/BoxRunService/rest/BoxRunService/ranking";
	    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        new Response.Listener<String>() {
		            @Override
		            public void onResponse(String response) {
		            	ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		        		String peticion=response;
		            	String nombre="nombre";
		        		int puntuacion = 0;
		        		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        		DocumentBuilder db;		
		        		try {
		        			File datos = Environment.getDataDirectory();
		        	        File f = new File(getFilesDir().getAbsolutePath() + "/Ranking.xml");  
		        	        FileWriter fw = new FileWriter(f);
		        	        PrintWriter pw = new PrintWriter(fw);
		        	        pw.write(response);
		        	        pw.close();
		        	        fw.close();
		        			db = dbf.newDocumentBuilder();
		        			Document doc = db.parse(openFileInput("Ranking.xml"));
		        			//Document doc = db.parse(peticion);
		        			Element raiz = doc.getDocumentElement();

		        		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		        			NodeList listaJugadores = raiz.getElementsByTagName("jugador");
		        			for(int i=0; i<listaJugadores.getLength(); i++){
		        			    Node jugador = listaJugadores.item(i);
		        			    nombre = jugador.getAttributes().getNamedItem("nombre").getNodeValue();
		        			    puntuacion = Integer.parseInt(jugador.getAttributes().getNamedItem("maxPuntuacion").getNodeValue());
		        			    jugadores.add(new Jugador(nombre, puntuacion));
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
		        		inicializaVistaJugador(jugadores, R.id.Nombre_ranking1, R.id.Puntuacion_ranking1, R.id.puesto1);
		        		inicializaVistaJugador(jugadores, R.id.Nombre_ranking1, R.id.Puntuacion_ranking1, R.id.puesto1);
		            }
		        }, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
		        });
	/*	// Add the request to the RequestQueue.
		queue.add(stringRequest);
		*/
	
		
		
		
	
	}
	private void inicializaVistaJugador(ArrayList<Jugador> jugadores, int idnombre, int idpuntuacion, int idpuesto){
		TextView nombre1 = (TextView)findViewById(idnombre);
		TextView puntuacion1 = (TextView)findViewById(idpuntuacion);
		if(jugadores.size()>0){
			nombre1.setText(jugadores.get(0).getNombre());
			puntuacion1.setText(jugadores.get(0).getPuntuacion());
		}else{
			FrameLayout layout1 = (FrameLayout)findViewById(idpuesto);
			layout1.setVisibility(View.GONE);
		}
	}
	/*public ArrayList<Jugador> jugadores(){
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		peticion="";
		peticionRanking();
		
		String nombre="nombre";
		int puntuacion = 0;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;		
		try {
			db = dbf.newDocumentBuilder();
			//Document doc = db.parse(openFileInput("Configuracion.txt"));
			Document doc = db.parse(peticion);
			Element raiz = doc.getDocumentElement();

		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
			NodeList listaJugadores = raiz.getElementsByTagName("jugador");
			for(int i=0; i<listaJugadores.getLength(); i++){
			    Node jugador = listaJugadores.item(i);
			    nombre = jugador.getAttributes().getNamedItem("nombre").getNodeValue();
			    puntuacion = Integer.parseInt(jugador.getAttributes().getNamedItem("maxPuntuacion").getNodeValue());
			    jugadores.add(new Jugador(nombre, puntuacion));
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
		return jugadores;
	}*/

	/*public ArrayList<Jugador> peticionRanking(){
		ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
		String peticion="";
	    RequestQueue queue = Volley.newRequestQueue(this);
	    try {
			semaforo.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String url ="http://sesat.fdi.ucm.es:8080/BoxRunService/rest/BoxRunService/ranking";
	    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
		        new Response.Listener<String>() {
		            @Override
		            public void onResponse(String response) {
		                // Display the first 500 characters of the response string.
		            	
		            	peticion = response; 
		            	String nombre="nombre";
		        		int puntuacion = 0;
		        		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        		DocumentBuilder db;		
		        		try {
		        			db = dbf.newDocumentBuilder();
		        			//Document doc = db.parse(openFileInput("Configuracion.txt"));
		        			Document doc = db.parse(peticion);
		        			Element raiz = doc.getDocumentElement();

		        		    //Obtener la lista de nodos que tienen etiqueta "EMPLEADO"
		        			NodeList listaJugadores = raiz.getElementsByTagName("jugador");
		        			for(int i=0; i<listaJugadores.getLength(); i++){
		        			    Node jugador = listaJugadores.item(i);
		        			    nombre = jugador.getAttributes().getNamedItem("nombre").getNodeValue();
		        			    puntuacion = Integer.parseInt(jugador.getAttributes().getNamedItem("maxPuntuacion").getNodeValue());
		        			    jugadores.add(new Jugador(nombre, puntuacion));
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
		        		return jugadores;
		                
		            }
		        }, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(
							com.android.volley.VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
		        });
		// Add the request to the RequestQueue.
		queue.add(stringRequest);
	
	}*/
	
}

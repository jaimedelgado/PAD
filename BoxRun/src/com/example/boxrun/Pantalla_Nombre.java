package com.example.boxrun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.example.boxrun.util.SystemUiHider;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Pantalla_Nombre extends Activity {
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
		/*File jugador = new File(new OutputStreamWriter(
		            openFileOutput("Configuracion.xml", Context.MODE_PRIVATE)));
		*/
		
			File datos = Environment.getDataDirectory();
	        File jugador = new File(getFilesDir().getAbsolutePath() + "/Configuracion.xml");   
		
		if(jugador.exists()){
			Intent actividadPrincipal = new Intent (this, Pantalla_principal.class);
			startActivity(actividadPrincipal);
		}else{
			setContentView(R.layout.activity_pantalla_nombre);
			//Button botonJugar = (Button) findViewById(R.id.button1);
			//EditText nombre = (EditText) findViewById(R.id.editText1);
			//String n = nombre.getText().toString();
			//avisa(n);
			/*FileWriter fw = null;
			PrintWriter pw = null;
			try {
				fw = new FileWriter(jugador);
				pw = new PrintWriter(fw);
				pw.print(System.nanoTime());
				pw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
		}
		
		
	}
	private void creaXML(String id, String nombre, String puntuacion) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		
	        DOMImplementation implementation = builder.getDOMImplementation();
	        Document document = implementation.createDocument(null, "Configuracion", null);
	        document.setXmlVersion("1.0");

        	//Main Node
        	Element raiz = document.getDocumentElement();
        	//Por cada key creamos un item que contendrá la key y el value
        	//////////////////escudo
            Element jugador = document.createElement("jugador"); 
    		Attr attr = document.createAttribute("id");
    		attr.setValue(id);
    		jugador.setAttributeNode(attr);
    		attr = document.createAttribute("nombre");
    		attr.setValue(nombre);
    		jugador.setAttributeNode(attr);
    		attr = document.createAttribute("puntuacion");
    		attr.setValue(puntuacion);
    		jugador.setAttributeNode(attr);
    		attr = document.createAttribute("monedas");
    		attr.setValue("0");
    		jugador.setAttributeNode(attr);
    		raiz.appendChild(jugador);
    		
    		//////////////////tienda
            Element tienda = document.createElement("tienda"); 
            raiz.appendChild(tienda);     
        	//////////////////escudo
            Element escudo = document.createElement("objeto"); 
            attr = document.createAttribute("nombreObjeto");
    		attr.setValue("1");
    		escudo.setAttributeNode(attr);
    		attr = document.createAttribute("nivel");
    		attr.setValue("1");
    		escudo.setAttributeNode(attr);
    		tienda.appendChild(escudo);
    		
    		////////////////// FUEGO
    		//Element tienda = document.createElement("tienda"); 
            //raiz.appendChild(tienda);     
            Element fuego = document.createElement("objeto"); 
            attr = document.createAttribute("nombreObjeto");
     		attr.setValue("1");
     		fuego.setAttributeNode(attr);
     		attr = document.createAttribute("nivel");
     		attr.setValue("1");
     		fuego.setAttributeNode(attr);
     		tienda.appendChild(fuego);
     		
     		

	        //Generate XML
	        Source source = new DOMSource(document);
	        //Indicamos donde lo queremos almacenar
	        
	        Result result = new StreamResult(new OutputStreamWriter(
 		            openFileOutput("Configuracion.xml", Context.MODE_PRIVATE))); //nombre del archivo
	 
	        Transformer transformer;
		
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(FileNotFoundException e2){
			e2.printStackTrace();
		}
      
		
	}
	public void comenzar(View v){
		EditText nombre = (EditText) findViewById(R.id.editText1);
		Editable texto = nombre.getText();
		if(!texto.toString().isEmpty()){
			this.creaXML(String.valueOf(System.nanoTime()), texto.toString(), "0");
			Intent actividadPrincipal = new Intent (this, Pantalla_principal.class);
			startActivity(actividadPrincipal);
		}else{
			avisa("Introduce tu nombre");
		}
	}
	
	public void avisa(String text){
		Context context = getApplicationContext();
		//CharSequence text = "Introduce tu nombre";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	public static void main(String args[]){
		//Pantalla_Nombre p = new Pantalla_Nombre();
		
	}

}

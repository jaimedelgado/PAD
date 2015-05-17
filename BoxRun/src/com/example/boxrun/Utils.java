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

public class Utils {
	public static int precio_escudo_1 = 1000;
	public static int precio_escudo_2 = 2000;
	public static int precio_escudo_3 = 4000;
	public static int precio_escudo_4 = 8000;
	public static int precio_escudo_5 = 16000;
	
	public static int precio_llama_1 = 1000;
	public static int precio_llama_2 = 2000;
	public static int precio_llama_3 = 4000;
	public static int precio_llama_4 = 8000;
	public static int precio_llama_5 = 16000;
}

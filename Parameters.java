import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URLConnection;
import java.util.Scanner;
import java.net.URL;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parameters
{
	String ip;
	String ipExterno;
	String nome;
	String email;
	String from;
	String smtp;
	String port;
	String emailSmtp;
	String passwdSmtp;
	
	private String fileName = "config/parameters.json";
	private String siteName = "http://www.meuip.com.br";
	private Scanner scanner;
	
    public void loadJson()
    {
    	JSONParser parser = new JSONParser();

        try {
        	// Escreve arquivo original caso não exista
        	File f = new File(fileName);
        	if(!f.exists()) {
        		System.out.println("Criando arquivo de configurações...");
        	    this.writeParameters(this.getOriginalConfig());
        	}
        	
        	System.out.println("Analisando configurações salvas..."); 
        	FileReader fileReader = new FileReader(fileName);
        	JSONObject json = (JSONObject) parser.parse(fileReader);
        	
        	this.ip = (String) json.get("ip");
        	this.smtp = (String) json.get("smtp");
        	this.nome = (String) json.get("nome");
        	this.email = (String) json.get("email");
        	this.from = (String) json.get("from");
        	this.port = (String) json.get("port");
        	this.emailSmtp = (String) json.get("emailSmtp");
        	this.passwdSmtp = (String) json.get("passwdSmtp");
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
		}
    }
    
    @SuppressWarnings("unchecked")
	private JSONObject getOriginalConfig()
    {
    	JSONObject json = new JSONObject();
    	json.put("ip", "127.0.0.1");
    	json.put("nome", "Robson Cruz");
    	json.put("email", "rcruz@longevo.com.br");
    	json.put("from", "noreply@longevo.com.br");
    	json.put("smtp", "mail.longevo.com.br");
    	json.put("port", "25");
    	json.put("emailSmtp", "noreply@longevo.com.br");
    	json.put("passwdSmtp", "Z=tG+4*k6UCp=5vM");

    	return json;
    }
    	
    	
    @SuppressWarnings("unchecked")
    public void save()
    {
    	JSONObject json = new JSONObject();
    	json.put("ip", this.ip);
    	json.put("nome", this.nome);
    	json.put("email", this.email);
    	json.put("from", this.from);
    	json.put("smtp", this.smtp);
    	json.put("port", this.port);
    	json.put("emailSmtp", this.emailSmtp);
    	json.put("passwdSmtp", this.passwdSmtp);
    	
    	this.writeParameters(json);
    }
    
    /**
     * 
     * @param json
     */
    private void writeParameters(JSONObject json)
    {
    	try {
    		FileWriter jsonFileWriter = new FileWriter(fileName);
    		
    		jsonFileWriter.write(json.toJSONString());
    		jsonFileWriter.flush();
    		jsonFileWriter.close();
    		
    		System.out.println("Arquivo salvo com sucesso"); 
    	} catch (IOException e) { 
    		e.printStackTrace(); 
    	}
    }
    
    public void processIpExterno()
    {
    	String content = null;
    	URLConnection connection = null;
    	
    	try {
    	  connection =  new URL(siteName).openConnection();
    	  scanner = new Scanner(connection.getInputStream());
    	  scanner.useDelimiter("\\Z");
    	  content = scanner.next();
      
    	  Pattern pattern = Pattern.compile(".*<div id=\"div_ip\" style=\"display:inline\">(.*?)</div>.*");
    	  Matcher matcher = pattern.matcher(content.toString());

		    if (matcher.find()) {
		    	this.ipExterno = matcher.group(1).replaceAll("(?s)<!--.*?-->", "").trim();
		    }
    	}catch ( Exception ex ) {
    	    ex.printStackTrace();
    	}
    }
}

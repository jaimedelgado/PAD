public void peticionRanking(){

    //Añadir la librería volley (la he subido al github) y una vez añadida tiene que estar disponible
    //en tiempo de ejecución (Properties -> Java Build Path -> Order and export -> Marcar el 
    //tick de la lib volley) Me daba problemas por eso...
    RequestQueue queue = Volley.newRequestQueue(this);
    String url ="http://sesat.fdi.ucm.es:8080/BoxRunService/rest/BoxRunService/ranking";

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    System.out.println("Response is: "+ response);  // cuando se ha realizado la petición
                    // "response" toma el valor del xml que hay que parsear.
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work!");
                }
            });
    // Add the request to the RequestQueue.
    queue.add(stringRequest);
}

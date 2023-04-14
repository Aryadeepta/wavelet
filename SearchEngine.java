import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    ArrayList<String> searches = new ArrayList<String>();
    public String handleRequest(URI url) {
        num++;
        if (url.getPath().equals("/")) {
            return String.format("Welcome to Arya's Search Engine\nYou are our %dth view!", num);
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                if(searches.contains(parameters[1])){
                    return String.format("%s already in search history", parameters[1]);
                }
                searches.add(parameters[1]);
                return String.format("Added %s to search history", parameters[1]);
            }
            return "Query error: Skill issue";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String ans="";
                    for(String i:searches){if(i.contains(parameters[1])){ans+=i+"\n";}}
                    return(ans);
                }
                return "Querry Error: Skill Issue";
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

package fr.iut45;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.csv.CSVImporter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Fonction{
    public static Graph<String, DefaultEdge> convertir(String cheminFichier) {
        Graph<String, DefaultEdge> graphe = new SimpleGraph<>(DefaultEdge.class);
        String contenu = Files.readString(Paths.get(cheminFichier));
        JsonArray films = JsonParser.parseString(contenu).getAsJsonArray();

        for (JsonElement elementFilm : films) {
            JsonObject film = elementFilm.getAsJsonObject();

            if (film.has("cast")) {
                JsonArray acteurs = film.getAsJsonArray("cast");

                for (int i = 0; i < acteurs.size(); i++) {
                    String acteur1 = acteurs.get(i).getAsString();
                    graphe.addVertex(acteur1);
                    
                    for (int j = i + 1; j < acteurs.size(); j++) {
                        String acteur2 = acteurs.get(j).getAsString();
                        graphe.addVertex(acteur2);
                        graphe.addEdge(acteur1, acteur2);
                    }
                }
            }
        }
        return graphe;
    }
}

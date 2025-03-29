package chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;

public class Config implements Serializable {

    private HashMap<String, Boolean> debug_options;
    private HashMap<String, Integer> board_options;
    private List<HashMap<String, String>> color_schemes;
    private HashMap<String, String> current_color_scheme;

    public Config(){
        debug_options = new HashMap<>();
        debug_options.put("enabled", true);
        debug_options.put("show_controlled", true);
        debug_options.put("show_notation", true);
        debug_options.put("show_console_mode", true);
        debug_options.put("show_turn", true);

        board_options = new HashMap<>();
        board_options.put("square_size", 100);

        color_schemes = new ArrayList<>();

        HashMap<String, String> classic_scheme = new HashMap<>();
        classic_scheme.put("light_square", "#f0d9b5");
        classic_scheme.put("dark_square", "#b58863");
        classic_scheme.put("highlighted_square", "#f0d9b5");
        classic_scheme.put("selected_square", "#f0d9b5");
        classic_scheme.put("controlled_square", "#f0d9b5");
        classic_scheme.put("attacked_square", "#f0d9b5");
        classic_scheme.put("text_color", "#000000");
        classic_scheme.put("highlighted_text_color", "#0000FF");
        classic_scheme.put("selected_text_color", "#000000");

        color_schemes.add(classic_scheme);

        current_color_scheme = color_schemes.getFirst();
    }
    public void setDebugOption(String key, boolean value){
        debug_options.put(key, value);
    }
    public boolean getDebugOption(String key){
        return debug_options.get(key);
    }
    public Color getColor(String key){
        return Color.decode(current_color_scheme.get(key));
    }
}

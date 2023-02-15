package mazegame.data;

import java.util.*;

public enum SphynxRiddles{

    A_BINARY_NINE(new HashMap<String, String>() {{
        put("question", "Write 9 in base 2 (binary).");
        put("answer", "1001");
    }}),
    B_DECIMAL_NINE(new HashMap<String, String>() {{
        put("question", "Write 1001 in base 10 (decimal).");
        put("answer", "9");    
    }}),
    C_LOG2_64(new HashMap<String, String>() {{
        put("question", "What does log_2(64) equal to ?");
        put("answer", "6");          
    }}),
    D_TWO_POWER_5(new HashMap<String, String>() {{
        put("question", "What does 2 to the power of 5 equal to ?");
        put("answer", "32");          
    }}),
    E_DECIMAL_218(new HashMap<String, String>() {{
        put("question", "Write DA in decimal.");
        put("answer", "218");          
    }}),
    F_ALED(new HashMap<String, String>() {{
        put("question", "Whaat just ap89f8zend ?");
        put("answer", "un bug connu de ise.");          
    }});

    private HashMap<String, String> riddle;
    private String question;
    private String answer;

    private SphynxRiddles(HashMap<String, String> riddle){
        this.riddle = riddle;
        this.question = riddle.get("question");
        this.answer = riddle.get("answer");
    }

    public HashMap<String, String> getRiddle(){
        return this.riddle;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

}

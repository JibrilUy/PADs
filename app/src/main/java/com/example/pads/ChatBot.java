package com.example.pads;

import static com.example.pads.Message.SENT_BY_BOT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatBot extends AppCompatActivity {
    private static final String TAG = "Creo"; // Declare the tag

    FloatingActionButton btnReturn;
    ImageButton btnSend;
    EditText editTextQuestion;
    QuestionChatAdapter questionChatAdapter;
    RecyclerView Chatbox;

    List<Message> messageList;

    Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_bot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d(TAG,"ChatBot Created");


        messageList = new ArrayList<>();


        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        questionChatAdapter = new QuestionChatAdapter(ChatBot.this, messageList);

        Chatbox = findViewById(R.id.recyclerViewChatBot);
        Chatbox.setAdapter(questionChatAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        Chatbox.setLayoutManager(llm);

        editTextQuestion = findViewById(R.id.editTextQuestion);

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString().trim();
                addToChat(question,Message.SENT_BY_ME);
                replyBot(question);

                editTextQuestion.setText("");
            }
        });



    }

    void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                questionChatAdapter.notifyDataSetChanged();
                Chatbox.smoothScrollToPosition(questionChatAdapter.getItemCount());
            }
        });

    }

    public Boolean containWord(String word, String sentence){
        if (sentence == null || word == null) {
            return false;
        }

        String[] words = sentence.toLowerCase().split(" ");
        String searchWord = word.toLowerCase();

        for(String w : words){
            if(w.compareTo(searchWord) == 0){
                return true;
            }

        }
        return false;
    }

    public void replyBot(String sentence){
        if (containWord("Hello", sentence)) {
            addToChat("Fuck You", SENT_BY_BOT);
            return;
        }

        if (containWord("income", sentence)) {
            addToChat("Upon analysis, it is evident that the months of November and December exhibit the highest levels of income, surpassing all other months in the evaluated period.", SENT_BY_BOT);
            return;
        }


        if (containWord("expensive", sentence)) {
            addToChat("The period commonly referred to as the 'ber months,' spanning from September to December, is characterized by the highest recorded expenses compared to other months of the year.", SENT_BY_BOT);
            return;
        }

        if (containWord("increased", sentence)) {
            addToChat("During the course of the year, eight months demonstrated a notable increase in net income.", SENT_BY_BOT);
            return;
        }
        if (containWord("expenses", sentence)) {
            addToChat("Throughout the year, expenses experienced an increase on only three occasions", SENT_BY_BOT);
            return;
        }
        if (containWord("prof", sentence)) {
            addToChat("Sir Mark Adrian stands out as the premier professor at Bulacan State University", SENT_BY_BOT);
            return;
        }
        if (containWord("subject", sentence)) {
            addToChat("From a logical perspective, Database Management emerges as the most standarized subject", SENT_BY_BOT);
            return;
        }
        if (containWord("vince", sentence)) {
            addToChat("BOVO KA VINCENT BATUMBAKAL", SENT_BY_BOT);
            return;
        }
        addToChat("This question is beyond the limits of the app", SENT_BY_BOT);



    }


}
package com.example.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.app.api.ApiClient;
import com.example.app.api.PokeApiService;
import com.example.app.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText queryBox;
    private Button submitButton;
    private ImageView imageView;
    private TextView infoText;

    private PokeApiService pokeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryBox = findViewById(R.id.queryBox);
        submitButton = findViewById(R.id.submitButton);
        imageView = findViewById(R.id.imageView);
        infoText = findViewById(R.id.infoText);

        pokeApiService = ApiClient.getRetrofitInstance().create(PokeApiService.class);

        // Button click listener
        submitButton.setOnClickListener(v -> fetchPokemonDetails());
    }

    private void fetchPokemonDetails() {
        String query = queryBox.getText().toString().trim().toLowerCase();
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
            return;
        }

        pokeApiService.getPokemonDetails(query).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonResponse pokemon = response.body();

                    StringBuilder typesBuilder = new StringBuilder();
                    for (PokemonResponse.TypeEntry typeEntry : pokemon.getTypes()) {
                        typesBuilder.append(typeEntry.getType().getName()).append(", ");
                    }

                    // Remove trailing comma and space
                    String types = typesBuilder.length() > 0
                            ? typesBuilder.substring(0, typesBuilder.length() - 2)
                            : "Unknown";

                    String info = "Name: " + pokemon.getName() +
                            "\nHeight: " + pokemon.getHeight() / 10.0 + " m" +
                            "\nWeight: " + pokemon.getWeight() / 10.0 + " kg" +
                            "\nTypes: " + types;


                    infoText.setText(info);

                    Glide.with(MainActivity.this)
                            .load(pokemon.getSprites().getFrontDefault())
                            .into(imageView);
                } else {
                    infoText.setText("Pokémon not found!");
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                infoText.setText("Error fetching Pokémon details!");
                Toast.makeText(MainActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

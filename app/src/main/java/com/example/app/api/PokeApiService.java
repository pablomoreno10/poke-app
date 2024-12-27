package com.example.app.api;

import com.example.app.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeApiService {
    @GET("pokemon/{name}")
    Call<PokemonResponse> getPokemonDetails(@Path("name") String pokemonName);
}


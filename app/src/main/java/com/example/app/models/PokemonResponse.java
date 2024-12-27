package com.example.app.models;

import java.util.List;

public class PokemonResponse {
    private String name;
    private int height;
    private int weight;
    private List<TypeEntry> types;
    private Sprites sprites;

    // Getters
    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public List<TypeEntry> getTypes() {
        return types;
    }

    public Sprites getSprites() {
        return sprites;
    }

    // Inner class for types
    public static class TypeEntry {
        private Type type;

        public Type getType() {
            return type;
        }

        public static class Type {
            private String name;

            public String getName() {
                return name;
            }
        }
    }

    // Inner class for sprites (image URL)
    public static class Sprites {
        private String front_default;

        public String getFrontDefault() {
            return front_default;
        }
    }
}



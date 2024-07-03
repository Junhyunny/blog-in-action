package action.in.blog.controller;

import action.in.blog.domain.PokemonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Objects;

@RestController
public class PokeomonController {

    private final static ClassLoader classLoader = PokeomonController.class.getClassLoader();
    private final static ObjectMapper objectMapper = new ObjectMapper();

    private PokemonResponse getPokemonResponse(BufferedReader reader) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return objectMapper.readValue(
                buffer.toString(),
                PokemonResponse.class
        );
    }

    @GetMapping("/pokemons-fail")
    public PokemonResponse getPokemonsFail() {
        String filePath = Objects.requireNonNull(classLoader.getResource("pokemons.json")).getFile();
        try (
                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader)
        ) {
            return getPokemonResponse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pokemons-success")
    public PokemonResponse getPokemonsSuccess() {
        try (
                InputStream inputStream = classLoader.getResourceAsStream("pokemons.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            return getPokemonResponse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

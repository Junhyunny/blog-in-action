package action.in.blog.controller;

import action.in.blog.domain.PokemonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class PokeomonController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PokemonResponse getPokemonResponse(BufferedReader reader) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return objectMapper.readValue(buffer.toString(), PokemonResponse.class);
    }

    @GetMapping("/pokemons-fail")
    public PokemonResponse getPokemonsFail() {
        String filePath = this.getClass().getClassLoader().getResource("pokemons.json").getFile();
        try (
                FileReader fileReader = new FileReader(filePath);
                BufferedReader reader = new BufferedReader(fileReader)
        ) {
            return getPokemonResponse(reader);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pokemons-success")
    public PokemonResponse getPokemonsSuccess() {
        try (
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("pokemons.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            return getPokemonResponse(reader);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package ee.mihkel.veebipood.dto;

public record LoginCredentials(
        String email,
        String password
) {
}

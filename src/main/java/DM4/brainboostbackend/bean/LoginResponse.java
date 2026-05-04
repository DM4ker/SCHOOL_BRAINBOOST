package DM4.brainboostbackend.bean;

public record LoginResponse(
        String token,
        UserBean user
) {
}

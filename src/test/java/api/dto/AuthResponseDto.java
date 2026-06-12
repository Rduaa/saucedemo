package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseDto {

    @JsonProperty("authenticated")
    private boolean authenticated;

    @JsonProperty("user")
    private String user;

    public boolean isAuthenticated() { return authenticated; }
    public String getUser()          { return user; }
}

package exercise.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

// BEGIN
public class GuestCreateDTO {
    @NotBlank
    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^\\+[0-9]*")
    @Size(min = 11, max = 13)
    private String phoneNumber;

    @Size(min = 4, max = 4)
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;

    public LocalDate getCardValidUntil() {
        return cardValidUntil;
    }

    public void setCardValidUntil(LocalDate cardValidUntil) {
        this.cardValidUntil = cardValidUntil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getClubCard() {
        return clubCard;
    }

    public void setClubCard(String clubCard) {
        this.clubCard = clubCard;
    }
}
// END
